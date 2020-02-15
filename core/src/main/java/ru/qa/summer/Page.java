package ru.qa.summer;

import ru.qa.summer.exceptions.IncorrectFieldTypeException;
import ru.qa.summer.exceptions.MethodExecutionException;
import ru.qa.summer.exceptions.MethodNotFoundException;
import ru.qa.summer.support.FieldDecorator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static ru.qa.summer.utils.InstantiateCoreUtils.instantiateFieldDecorator;
import static ru.qa.summer.utils.InstantiateCoreUtils.instantiatePageAnnotator;

public abstract class Page<A extends PageAnnotator, F extends FieldDecorator> {
    protected final A annotator;
    protected final PageProcessor processor;

    public Page(Class<A> aClass, Class<F> dClass) {
        this.annotator = instantiatePageAnnotator(aClass, getClass());
        this.processor = PageProcessor.create(this);
        instantiateFieldDecorator(dClass, this).decorate(this);
    }

    protected Object invoke(String name, Object... objs) {
        Method method = processor.getMethodByName(name);
        if (method == null) {
            String message = String.format("Method '%s' not found in '%s'", name, getClass().getCanonicalName());
            throw new MethodNotFoundException(message);
        }
        try {
            return method.invoke(this, objs);
        } catch (Exception e) {
            String message = String.format("Method '%s' not found in '%s' throw exception", name, getClass().getCanonicalName());
            throw new MethodExecutionException(message, e instanceof InvocationTargetException ? e.getCause() : e);
        }
    }

    public String getName() {
        return annotator.getName();
    }

    protected A getAnnotator() {
        return annotator;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name, Class<T> clazz) {
        Object object = processor.getFieldByName(this, name);
        if (object == null) {
            return null;
        }
        if (clazz.isAssignableFrom(object.getClass())) {
            return (T) object;
        }
        String message = String.format("Expected type for '%s' field is '%s' but actually was '%s'", name,
                clazz.getCanonicalName(), object.getClass().getCanonicalName());
        throw new IncorrectFieldTypeException(message);
    }
}
