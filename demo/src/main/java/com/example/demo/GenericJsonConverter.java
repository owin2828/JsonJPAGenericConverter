package com.example.demo;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 데이터를 문자열로 변환하여 JPA를 이용해 DB에 저장하기 위한 Converter
 * Generic 타입으로 선언되어, 저장하고자 하는 Class별로 구현 가능
 */

public class GenericJsonConverter implements AttributeConverter<Object, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object additionalData) {
        // AdditionalData -> Json문자열로 변환
        try {
            return additionalData.getClass().getName() + "|" + objectMapper.writeValueAsString(additionalData);
        } catch (JsonProcessingException e) {
            
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String jsonStr) {
        // Json 문자열 -> AdditionalData로 변환
        try {
            final String[] parts = jsonStr.split("\\|");
            return objectMapper.readValue(parts[1], Class.forName(parts[0]));
        } catch (IOException | ClassNotFoundException e) {
            
            throw new RuntimeException(e);
        }
    }
}