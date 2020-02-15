package ru.qa.summer.support;

import ru.qa.summer.support.exceptions.FieldDecorateException;
import ru.qa.summer.support.util.ClassUtil;

import java.lang.reflect.Field;

public abstract class FieldDecorator {
    protected abstract Object decorateElementOrElementList(ClassLoader loader, Field field);

    protected abstract boolean mustBeDecorated(Field field);

    public void decorate(Object o) {
        Class clazz = o.getClass();
        ClassLoader loader = clazz.getClassLoader();
        ClassUtil.fields(clazz).forEach(field -> {
            try {
                if (mustBeDecorated(field)) {
                    Object decorated = decorateElementOrElementList(loader, field);
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    field.set(o, decorated);
                }
            } catch (Exception e) {
                throw new FieldDecorateException("Can't decorate a field '" + field.getName() +
                        "' of '" + o.getClass().getCanonicalName() + "'", e);
            }
        });
    }
}
