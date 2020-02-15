package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ClassUtil {
    public static List<Class<?>> getClasses(Class<?> clazz) {
        List<Class<?>> result = new ArrayList<>();
        while (clazz != Object.class) {
            result.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public static <T extends Annotation> Stream<AbstractMap.SimpleEntry<Field, T>> annotated(Class<?> clazz, Class<T> annotation) {
        return fields(clazz)
                .map(field -> new AbstractMap.SimpleEntry<>(field, field.getAnnotation(annotation)))
                .filter(p -> p.getValue() != null);
    }

    public static List<Field> getDeclaredFields(Class<?> clazz) {
        return fields(clazz).collect(Collectors.toList());
    }

    public static Stream<Field> fields(Class<?> clazz) {
        return classes(clazz).flatMap(x -> Arrays.stream(x.getDeclaredFields()));
    }

    public static Stream<Class<?>> classes(Class<?> clazz) {
        return getClasses(clazz).stream();
    }

    public static Class<?> getGenericType(Class<?> clazz, int index) {
        if (index < 0) {
            return null;
        }
        Type generic = clazz.getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) generic).getActualTypeArguments();
            if (types.length > index && types[index] instanceof TypeVariable) {
                Object result = ((TypeVariable) types[index]).getGenericDeclaration();
                if (result instanceof Class) {
                    return (Class) result;
                }
            }
        }
        return null;
    }
}
