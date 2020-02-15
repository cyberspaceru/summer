package ru.qa.summer.context;

import ru.qa.summer.context.exceptions.ContextParseException;
import ru.qa.summer.support.util.NamingUtil;

import java.util.TreeMap;

import static ru.qa.summer.context.Context.NAME_AND_TYPE_DIVIDER;

public class ContextEntity extends TreeMap<String, Object> {
    private final String name;
    private final int index;

    public ContextEntity(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static ContextEntity parse(String name) {
        String[] parts = name.split(NAME_AND_TYPE_DIVIDER);
        int index = 0;
        if (parts.length > 2) {
            throw new ContextParseException("Too much '" + NAME_AND_TYPE_DIVIDER + "' for '" + name + "'");
        }
        if (NamingUtil.containsIndexer(name)) {
            index = NamingUtil.extractIndex(name);
            name = NamingUtil.removeIndexer(name);
        }
        return new ContextEntity(name, index);
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
