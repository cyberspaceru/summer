package ru.qa.summer.context.handlers;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class StraightInvocationHandler extends ContextInvocationHandler {
    public StraightInvocationHandler(@NotNull String objectName, @NotNull String objectType, @NotNull Class<?> cClass) {
        super(cClass);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        return null;
    }
}
