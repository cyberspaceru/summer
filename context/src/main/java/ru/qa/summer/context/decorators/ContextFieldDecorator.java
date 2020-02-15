package ru.qa.summer.context.decorators;

import ru.qa.summer.context.annotations.ContextObject;
import ru.qa.summer.context.annotations.ContextObjectName;
import ru.qa.summer.support.FieldDecorator;

import java.lang.reflect.Field;

import static java.util.Optional.ofNullable;

public abstract class ContextFieldDecorator extends FieldDecorator {
    @Override
    protected boolean mustBeDecorated(Field field) {
        return ofNullable(field.getAnnotation(ContextObject.class)).isPresent()
                || ofNullable(field.getAnnotation(ContextObjectName.class)).isPresent()
                || ofNullable(field.getType().getAnnotation(ContextObjectName.class)).isPresent();
    }
}
