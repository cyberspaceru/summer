package ru.qa.summer.context.providers;

import ru.qa.summer.context.exceptions.ContextParseException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArrayField {
    private final List<Field> fields = new ArrayList<>();
    private final String name;

    public ArrayField(String name) {
        this.name = name;
    }

    public void add(int index, Object value) {
        fields.add(new Field(index, value));
    }

    public List<Object> transform() {
        List<Object> result = new ArrayList<>();
        fields.sort(Comparator.comparingInt(x -> x.index));
        fields.forEach(x -> {
            if (x.index < 0) {
                return;
            }
            if (x.index < result.size()) {
                throw new ContextParseException("Field duplication '" + name + "', " + x.toString());
            }
            int diff = x.index - result.size();
            for (int i = 0; i < diff; i++) {
                result.add(null);
            }
            result.add(x.value);
        });
        return result;
    }

    private static class Field {
        private final int index;
        private final Object value;

        public Field(int index, Object value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "index=" + index +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
