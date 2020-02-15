package ru.qa.summer;

import ru.qa.summer.support.FieldDecorator;
import ru.qa.summer.utils.FieldCoreUtil;

import java.lang.reflect.Field;

public abstract class CoreFieldDecorator extends FieldDecorator {
    private Object parent;

    public CoreFieldDecorator(Object parent) {
        this.parent = parent;
    }

    public Object getParent() {
        return parent;
    }

    @Override
    protected boolean mustBeDecorated(Field field) {
        return FieldCoreUtil.isParametrizedElementList(field) || FieldCoreUtil.isElement(field);
    }

    public static class Stub extends CoreFieldDecorator {
        public Stub(Object parent) {
            super(parent);
        }

        @Override
        protected Object decorateElementOrElementList(ClassLoader loader, Field field) {
            return null;
        }
    }
}
