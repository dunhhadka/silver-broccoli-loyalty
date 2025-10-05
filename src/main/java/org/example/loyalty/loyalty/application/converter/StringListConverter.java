package org.example.loyalty.loyalty.application.converter;

import jakarta.persistence.AttributeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return null;
        }
        return String.join(",", strings);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(s.split(","))
                .filter(str -> !str.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
