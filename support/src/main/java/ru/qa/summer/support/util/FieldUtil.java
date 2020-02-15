package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.qa.summer.support.util.ClassUtil.annotated;

@UtilityClass
public class FieldUtil {
    public static <T extends Annotation> Map<Field, T> getAnnotated(Class<?> clazz, Class<T> annotation) {
        return annotated(clazz, annotation).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public static boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    public static Class<?> getGenericParameterClass(Field field) {
        Type genericType = field.getGenericType();
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }
}
