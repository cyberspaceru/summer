package ru.qa.summer.selenium;

import ru.qa.summer.CoreFieldDecorator;
import ru.qa.summer.utils.InstantiateCoreUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static ru.qa.summer.support.util.FieldUtil.getGenericParameterClass;

public class SeleniumFieldDecorator extends CoreFieldDecorator {
    public SeleniumFieldDecorator(Object parent) {
        super(parent);
    }

    @Override
    protected Object decorateElementOrElementList(ClassLoader loader, Field field) {
        List<Annotation> annotations = Arrays.asList(field.getAnnotations());
        SeleniumFieldAnnotator annotator = new SeleniumFieldAnnotator(annotations);
        Class<?> eClass = field.getType();
        SeleniumAnchor anchor = new SeleniumAnchor(getParent(), -1, annotator);
        if (eClass.isAssignableFrom(SeleniumElementList.class)) {
            Class<?> tClass = getGenericParameterClass(field);
            return InstantiateCoreUtils.instantiateElementListAsObject(eClass, anchor, tClass);
        }
        return InstantiateCoreUtils.instantiateElementAsObject(eClass, anchor);
    }
}
