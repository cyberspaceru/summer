package ru.qa.summer.support.primitives;

import lombok.Data;

@Data
public class StringPair {
    private String key;
    private String value;

    public StringPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public StringPair() {
    }
}
