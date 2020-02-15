package ru.qa.summer.context.decorators;

import java.lang.reflect.Field;

public class StraightContextFieldDecorator extends ContextFieldDecorator {
    @Override
    protected Object decorateElementOrElementList(ClassLoader loader, Field field) {
        return null;
    }

    @Override
    protected boolean mustBeDecorated(Field field) {
        return false;
    }
}
