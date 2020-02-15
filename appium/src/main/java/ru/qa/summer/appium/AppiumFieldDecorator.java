package ru.qa.summer.appium;

import ru.qa.summer.CoreFieldDecorator;
import ru.qa.summer.utils.InstantiateCoreUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static ru.qa.summer.support.util.FieldUtil.getGenericParameterClass;

public class AppiumFieldDecorator extends CoreFieldDecorator {
    public AppiumFieldDecorator(Object parent) {
        super(parent);
    }

    @Override
    protected Object decorateElementOrElementList(ClassLoader loader, Field field) {
        List<Annotation> annotations = Arrays.asList(field.getAnnotations());
        AppiumFieldAnnotator annotator = new AppiumFieldAnnotator(annotations);
        Class<?> eClass = field.getType();
        AppiumAnchor anchor = new AppiumAnchor(getParent(), -1, annotator);
        if (eClass.isAssignableFrom(AppiumElementList.class)) {
            Class<?> tClass = getGenericParameterClass(field);
            return InstantiateCoreUtils.instantiateElementListAsObject(eClass, anchor, tClass);
        }
        return InstantiateCoreUtils.instantiateElementAsObject(eClass, anchor);
    }
}
