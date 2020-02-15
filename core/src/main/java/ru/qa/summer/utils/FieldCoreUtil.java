package ru.qa.summer.utils;

import lombok.experimental.UtilityClass;
import ru.qa.summer.Element;
import ru.qa.summer.ElementList;
import ru.qa.summer.annotations.ElementName;
import ru.qa.summer.exceptions.DuplicateFieldNameException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static ru.qa.summer.support.util.ClassUtil.annotated;

@UtilityClass
public class FieldCoreUtil {
    public static Map<String, Field> getAnnotatedWithElementNameAndUnlock(Class<?> clazz) {
        Map<String, Field> result = new HashMap<>();
        annotated(clazz, ElementName.class).forEach(pair -> {
            String name = pair.getValue().value();
            if (result.containsKey(name)) {
                String message = format("Field with '%s' name duplicated in '%s' page", name, clazz.getCanonicalName());
                throw new DuplicateFieldNameException(message);
            }
            pair.getKey().setAccessible(true);
            result.put(name, pair.getKey());
        });
        return result;
    }

    public static boolean isElement(Field field) {
        return isElement(field.getType());
    }

    public static boolean isElement(Class<?> clazz) {
        return Element.class.isAssignableFrom(clazz);
    }

    public static boolean isParametrizedElementList(Field field) {
        return isElementList(field) && hasGenericParameter(field);
    }

    public static boolean isElementList(Field field) {
        return ElementList.class.isAssignableFrom(field.getType());
    }

    public static boolean hasGenericParameter(Field field) {
        return field.getGenericType() instanceof ParameterizedType;
    }
}
