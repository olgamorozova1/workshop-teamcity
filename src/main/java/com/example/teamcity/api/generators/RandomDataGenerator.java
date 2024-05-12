package com.example.teamcity.api.generators;

import com.example.teamcity.api.generators.annotations.DefaultValue;
import com.example.teamcity.api.generators.annotations.Optional;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.example.teamcity.api.generators.RandomData.getEmail;
import static com.example.teamcity.api.generators.RandomData.getInt;
import static com.example.teamcity.api.generators.RandomData.getString;
import static org.apache.commons.lang3.RandomUtils.nextBoolean;

public class RandomDataGenerator {

    @SneakyThrows
    public static <T> T generateRandomClassData(Class<T> clazz) {
        T object = clazz.newInstance();
        var fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Optional.class)) {
                DefaultValue defaultValueAnnotation = field.getAnnotation(DefaultValue.class);
                if (defaultValueAnnotation != null) {
                    field.set(object, defaultValueAnnotation.value());
                } else {
                    Class<?> fieldType = field.getType();
                    if (String.class.equals(fieldType)) {
                        if (field.getName().contains("email")) {
                            field.set(object, getEmail());
                            continue;
                        }
                        field.set(object, getString(10));
                    } else if (Integer.class.equals(fieldType) || int.class.equals(fieldType)) {
                        field.set(object, getInt(1));
                    } else if (Boolean.class.equals(fieldType) || boolean.class.equals(fieldType)) {
                        field.set(object, nextBoolean());
                    } else if (List.class.isAssignableFrom(fieldType)) {
                        List<Object> list = new ArrayList<>();
                        Class<?> elementType = (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        Object element = elementType.newInstance();
                        element = generateRandomClassData(element.getClass());
                        list.add(element);
                        field.set(object, list);
                    } else if (!fieldType.isPrimitive()) {
                        Object nestedObj = fieldType.newInstance();
                        nestedObj = generateRandomClassData(nestedObj.getClass());
                        field.set(object, nestedObj);
                    }
                }
            }
        }
        return object;
    }
}
