package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static java.util.Optional.ofNullable;

@UtilityClass
public class MethodUtil {
    @Nullable
    public static <A extends Annotation> A getAnnotation(Method method, Class<A> aClass) {
        return ofNullable(method.getAnnotation(aClass)).orElse(null);
    }

    public static Class<?> getGenericParameterClass(Method method) {
        Type genericType = method.getGenericReturnType();
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }
}
