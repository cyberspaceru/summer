package ru.qa.summer.utils;

import lombok.experimental.UtilityClass;
import ru.qa.summer.*;
import ru.qa.summer.exceptions.InstantiateException;
import ru.qa.summer.support.FieldDecorator;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class InstantiateCoreUtils {
    public static <A extends PageAnnotator, P extends Page> A instantiatePageAnnotator(Class<A> aClass, Class<P> pClass) {
        try {
            return aClass.getConstructor(List.class).newInstance(Arrays.asList(pClass.getAnnotations()));
        } catch (Exception e) {
            throw new InstantiateException(e);
        }
    }

    public static <T extends Anchor, E extends Element> E instantiateElement(Class<E> eClass, T anchor) {
        try {
            return eClass.getConstructor(anchor.getClass()).newInstance(anchor);
        } catch (Exception e) {
            throw new InstantiateException(e);
        }
    }

    public static Object instantiateElementAsObject(Class<?> eClass, Anchor anchor) {
        try {
            return eClass.getConstructor(anchor.getClass()).newInstance(anchor);
        } catch (Exception e) {
            throw new InstantiateException(e);
        }
    }

    public static <E extends ElementList, A extends Anchor, T extends Element> E instantiateElementList(Class<E> eClass, A anchor, Class<T> tClass) {
        try {
            return eClass.getConstructor(anchor.getClass(), Class.class).newInstance(anchor, tClass);
        } catch (Exception e) {
            throw new InstantiateException(e);
        }
    }

    public static Object instantiateElementListAsObject(Class<?> eClass, Anchor anchor, Class<?> tClass) {
        try {
            return eClass.getConstructor(anchor.getClass(), Class.class).newInstance(anchor, tClass);
        } catch (Exception e) {
            throw new InstantiateException(e);
        }
    }

    public static <T extends FieldDecorator> T instantiateFieldDecorator(Class<T> fClass, Object parent) {
        try {
            return fClass.getConstructor(Object.class).newInstance(parent);
        } catch (Exception e) {
            throw new InstantiateException(e);
        }
    }
}
