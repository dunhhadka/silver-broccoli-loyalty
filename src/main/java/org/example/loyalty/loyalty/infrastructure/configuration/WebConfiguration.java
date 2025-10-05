package org.example.loyalty.loyalty.infrastructure.configuration;

import org.example.loyalty.loyalty.infrastructure.configuration.bind.ParamNameServletModelAttributeResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ParamNameServletModelAttributeResolver(true));
    }
}
