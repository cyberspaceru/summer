package ru.qa.summer.context;

import ru.qa.summer.dg.DataGenerator;

public class ValueWrapper {
    private String value;
    private boolean isGenerated;

    public ValueWrapper(String value) {
        this.value = value;
    }

    public String get() {
        if (!isGenerated) {
            value = DataGenerator.compile(value);
        }
        return value;
    }
}
