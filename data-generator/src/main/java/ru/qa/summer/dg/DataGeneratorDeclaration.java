package ru.qa.summer.dg;

import java.util.List;

public class DataGeneratorDeclaration {
    private final int start;
    private final int end;
    private final String name;
    private final List<String> args;

    public DataGeneratorDeclaration(int start, int end, String name, List<String> args) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.args = args;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }
}
