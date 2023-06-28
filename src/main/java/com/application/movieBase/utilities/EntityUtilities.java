package com.application.movieBase.utilities;

import java.lang.reflect.Field;

public class EntityUtilities {
    public static <E, D> E updateFromDTO(E entity, D dto) throws IllegalAccessException {
        Class<?> entityClass = entity.getClass();
        Class<?> dtoClass = dto.getClass();

        Field[] entityFields = entityClass.getDeclaredFields();
        Field[] dtoFields = dtoClass.getDeclaredFields();

        for (Field dtoField : dtoFields) {
            String fieldName = dtoField.getName();
            dtoField.setAccessible(true);
            Object fieldValue = dtoField.get(dto);

            for (Field entityField : entityFields) {
                if (entityField.getName().equals(fieldName)) {
                    entityField.setAccessible(true);
                    entityField.set(entity, fieldValue);
                    break;
                }
            }
        }

        return entity;
    }
}
