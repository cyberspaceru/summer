package ru.qa.summer.utils;

import lombok.experimental.UtilityClass;
import ru.qa.summer.annotations.MethodName;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class MethodCoreUtil {
    public static Map<String, Method> getAnnotatedWithMethodName(Class<?> clazz) {
        Map<String, Method> result = new HashMap<>();
        while (clazz != null) {
            for (Method method : clazz.getMethods()) {
                MethodName mn = method.getAnnotation(MethodName.class);
                if (mn != null) {
                    result.putIfAbsent(mn.value(), method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }
}
