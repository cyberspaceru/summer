package ru.qa.summer;

import ru.qa.summer.utils.FieldCoreUtil;
import ru.qa.summer.utils.MethodCoreUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PageProcessor {
    private static final Map<String, PageProcessor> space = new HashMap<>();
    private Map<String, Field> fieldsWithElementName;
    private Map<String, Method> methodsWithMethodName;

    public Object getFieldByName(Page page, String name) {
        Field field = fieldsWithElementName.get(name);
        if (field == null) {
            return null;
        }
        try {
            return field.get(page);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public Method getMethodByName(String name) {
        return methodsWithMethodName.get(name);
    }

    public static PageProcessor create(Page page) {
        Class<? extends Page> clazz = page.getClass();
        PageProcessor cache = space.get(clazz.getCanonicalName());
        if (cache != null) {
            return cache;
        }
        cache = new PageProcessor();
        cache.fieldsWithElementName = FieldCoreUtil.getAnnotatedWithElementNameAndUnlock(clazz);
        cache.methodsWithMethodName = MethodCoreUtil.getAnnotatedWithMethodName(clazz);
        space.put(clazz.getCanonicalName(), cache);
        return cache;
    }
}
