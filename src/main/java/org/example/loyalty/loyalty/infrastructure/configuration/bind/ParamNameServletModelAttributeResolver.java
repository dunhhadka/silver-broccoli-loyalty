package org.example.loyalty.loyalty.infrastructure.configuration.bind;

import jakarta.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.util.WebUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ParamNameServletModelAttributeResolver extends ServletModelAttributeMethodProcessor {

    private static final ConcurrentHashMap<Class<?>, Map<String, String>> definitionsCache = new ConcurrentHashMap<>();

    public ParamNameServletModelAttributeResolver(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAnnotationPresent(SupportParamName.class);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        log.info("bind request params");

        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;

        bind(servletRequest, servletBinder);
    }

    private void bind(ServletRequest request, ServletRequestDataBinder binder) {
        Map<String, ?> propertyValues = parsePropertyValues(request, binder);

        var mpvs = new MutablePropertyValues(propertyValues);
        MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
        if (multipartRequest != null) {
            buildMultipart(multipartRequest.getMultiFileMap(), mpvs);
        }

        String attr = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
        mpvs.addPropertyValues((Map<String, String>) request.getAttribute(attr));
        binder.bind(mpvs);
    }

    private void buildMultipart(MultiValueMap<String, MultipartFile> multiFileMap, MutablePropertyValues mpvs) {
        for (var entry : multiFileMap.entrySet()) {
            String key = entry.getKey();
            List<MultipartFile> values = entry.getValue();
            if (values.size() == 1) {
                var value = values.get(0);
                if (!value.isEmpty()) {
                    mpvs.add(key, value);
                }
            } else {
                mpvs.add(key, values);
            }
        }
    }

    // return map <field, value>
    private Map<String, ?> parsePropertyValues(ServletRequest request, ServletRequestDataBinder binder) {
        Map<String, Object> params = new LinkedHashMap<>();
        Enumeration<?> paramNames = request.getParameterNames();
        Map<String, String> paramMappings = getParameterMappings(binder);

        // pase value from request to field
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);

            String fieldName = paramMappings.get(paramName);
            if (fieldName == null) {
                fieldName = paramName;
            }

            if (values == null || values.length == 0) {
                // do nothing
            } else if (values.length == 1) {
                params.put(fieldName, values[0]);
            } else {
                params.put(fieldName, values);
            }
        }

        return params;
    }

    // get structure of target class
    private Map<String, String> getParameterMappings(ServletRequestDataBinder binder) {
        Class<?> targetClass = binder.getTarget().getClass();
        Map<String, String> map = definitionsCache.get(targetClass);
        if (map == null) {
            Field[] fields = targetClass.getDeclaredFields();
            map = new HashMap<>();
            for (var field : fields) {
                var annotation = field.getAnnotation(ParamName.class);
                if (annotation != null && !annotation.value().isEmpty()) {
                    map.put(annotation.value(), field.getName());
                }
            }
            definitionsCache.put(targetClass, map);
            return map;
        }
        return map;
    }
}
