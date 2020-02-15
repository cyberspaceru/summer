package ru.qa.summer.context.decorators;

import ru.qa.summer.context.annotations.ContextObject;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static ru.qa.summer.context.Context.current;
import static ru.qa.summer.context.handlers.CachedInvocationHandler.createProxy;
import static ru.qa.summer.support.util.FieldUtil.getGenericParameterClass;
import static ru.qa.summer.support.util.FieldUtil.isList;

public class CachedContextFieldDecorator extends ContextFieldDecorator {
    @Override
    protected Object decorateElementOrElementList(ClassLoader loader, Field field) {
        Class<?> cClass = isList(field) ? getGenericParameterClass(field) : field.getType();
        String objectName = field.getName();
        ContextObject contextTypeName = field.getAnnotation(ContextObject.class);
        String objectTypeName = (contextTypeName == null || contextTypeName.value().isEmpty()) ? null : contextTypeName.value();
        Object asObject = current().getEntity(objectName, objectTypeName);
        Object proxy = asObject == null ? null : createProxy(asObject, cClass);
        if (proxy == null) {
            return null;
        }
        if (isList(field)) {
            return proxy instanceof List ? proxy : Collections.singletonList(proxy);
        } else {
            if (proxy instanceof List) {
                List list = (List) proxy;
                return list.isEmpty() ? null : list.get(0);
            }
            return proxy;
        }
    }
}
