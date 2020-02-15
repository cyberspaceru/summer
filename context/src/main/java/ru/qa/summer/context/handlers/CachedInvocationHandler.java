package ru.qa.summer.context.handlers;

import org.jetbrains.annotations.NotNull;
import ru.qa.summer.context.ContextEntity;
import ru.qa.summer.context.ValueWrapper;
import ru.qa.summer.context.annotations.ContextFieldName;
import ru.qa.summer.context.providers.ArrayField;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

import static ru.qa.summer.support.util.MethodUtil.getGenericParameterClass;

public class CachedInvocationHandler extends ContextInvocationHandler {
    private final Map<String, Object> cache;

    public CachedInvocationHandler(@NotNull ContextEntity entity, @NotNull Class<?> cClass) {
        super(cClass);
        this.cache = createCache(entity, cClass);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        ContextFieldName fieldName = method.getAnnotation(ContextFieldName.class);
        String name = fieldName == null ? method.getName() : fieldName.value();
        Object value = cache.get(name);
        if (value instanceof ValueWrapper) {
            return getValue(method.getReturnType(), (ValueWrapper) value);
        } else if (value instanceof ContextEntity) {
            Object proxy = createProxy(value, method.getReturnType());
            cache.put(name, proxy);
            return proxy;
        } else if (value instanceof List) {
            return value;
        }
        if (value instanceof ArrayField) {
            List<Object> list = new ArrayList<>();
            boolean returnList = method.getReturnType().equals(List.class);
            Class<?> type = returnList ? getGenericParameterClass(method) : method.getReturnType();
            for (Object item : ((ArrayField) value).transform()) {
                if (item == null) {
                    list.add(null);
                } else if (item instanceof ValueWrapper) {
                    list.add(getValue(type, (ValueWrapper) item));
                } else if (item instanceof ContextEntity) {
                    list.add(createProxy(item, type));
                }
            }
            cache.put(name, list);
            return list;
        }
        return value;
    }

    private Map<String, Object> createCache(@NotNull ContextEntity entity, @NotNull Class<?> cClass) {
        Map<String, Object> result = new HashMap<>();
        Arrays.stream(cClass.getMethods()).forEach(x -> {
            ContextFieldName fieldName = x.getAnnotation(ContextFieldName.class);
            String name = fieldName == null ? x.getName() : fieldName.value();
            result.put(name, entity.get(name));
        });
        return result;
    }

    public static Object createProxy(Object asObject, Class<?> to) {
        if (asObject instanceof List) {
            List asList = (List) asObject;
            List<Object> result = new ArrayList<>();
            for (Object o : asList) {
                if (o == null) {
                    result.add(null);
                }
                result.add(createProxy((ContextEntity) o, to));
            }
            return result;
        }
        return createProxy((ContextEntity) asObject, to);
    }

    public static Object createProxy(ContextEntity entity, Class<?> to) {
        CachedInvocationHandler cached = new CachedInvocationHandler(entity, to);
        return Proxy.newProxyInstance(to.getClassLoader(), new Class<?>[]{to}, cached);
    }
}
