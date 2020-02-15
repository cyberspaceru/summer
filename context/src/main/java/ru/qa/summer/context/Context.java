package ru.qa.summer.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private static Context INSTANCE;
    public static final String NAME_AND_TYPE_DIVIDER = ":";
    private final Map<String, Object> entities = new HashMap<>();

    public void add(EntityProvider provider) {
        add(provider.provide());
    }

    public void add(List<Object> entities) {
        for (Object entity : entities) {
            ContextEntity e = entity instanceof ContextEntity ? (ContextEntity) entity : ((List<ContextEntity>) entity).get(0);
            Object found = this.entities.get(e.getName());
//            if (found == null) {
            this.entities.put(e.getName(), entity);
//            }
//            else {
//                for (Map.Entry<String, Object> entry : entity.entrySet()) {
//                    found.put(entry.getKey(), entry.getValue());
//                }
//            }
        }
    }

    public Object getEntity(String name, String type) {
        String expected = name + (type == null ? "" : ":" + type);
        for (String key : entities.keySet()) {
            if (key.equalsIgnoreCase(expected)) {
                return entities.get(key);
            }
        }
        return null;
    }

    public void refresh() {
        this.entities.clear();
    }

    public static Context current() {
        if (INSTANCE == null) {
            INSTANCE = new Context();
        }
        return INSTANCE;
    }
}
