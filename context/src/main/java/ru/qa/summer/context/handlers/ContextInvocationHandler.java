package ru.qa.summer.context.handlers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.qa.summer.context.ValueWrapper;
import ru.qa.summer.context.annotations.ContextFieldName;
import ru.qa.summer.context.exceptions.ContextValueException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public abstract class ContextInvocationHandler implements InvocationHandler {
    protected final Class<?> cClass;

    public ContextInvocationHandler(@NotNull Class<?> cClass) {
        this.cClass = cClass;
    }

    public static Object getValue(Class<?> vType, ValueWrapper valueWrapper) {
        String value = valueWrapper.get();
        if (vType.equals(String.class)) {
            return value;
        } else if (vType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (vType.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (vType.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (vType.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (vType.isEnum()) {
            Enum[] enums = (Enum[]) vType.getEnumConstants();
            for (Enum e : enums) {
                if (e.name().equals(value)) {
                    return e;
                }
            }
            throw new ContextValueException("Enum value of '" + vType.getCanonicalName() + "' not found for '" + value + "'." +
                    " Possible values are [" + Arrays.stream(enums).map(Enum::name).collect(Collectors.joining(", ")) + "]");
        }
        throw new ContextValueException("'" + vType.getCanonicalName() + "' is not supported as Context Entity Field Value");
    }

    @Nullable
    public static String getContextFieldName(Method method) {
        return ofNullable(method.getAnnotation(ContextFieldName.class))
                .map(ContextFieldName::value)
                .orElse(null);
    }
}
