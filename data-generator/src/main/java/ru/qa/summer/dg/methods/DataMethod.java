package ru.qa.summer.dg.methods;

import java.util.List;

@FunctionalInterface
public interface DataMethod {
    String invoke(List<String> args);
}
