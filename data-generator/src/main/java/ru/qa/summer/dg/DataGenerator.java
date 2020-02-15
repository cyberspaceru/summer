package ru.qa.summer.dg;

import org.reflections.Reflections;
import ru.qa.summer.dg.annotations.DataMethodInfo;
import ru.qa.summer.dg.methods.DataMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.qa.summer.dg.DataGeneratorConfiguration.DATA_GENERATOR_CONFIGURATION;

public class DataGenerator {
    private static Set<Class<?>> CLASSES;

    private DataGenerator() {
    }

    public static String compile(String row) {
        DataGeneratorDeclaration declaration;
        while ((declaration = find(row)) != null) {
            DataMethod method = findMethod(declaration.getName());
            row = row.substring(0, declaration.getStart()) + method.invoke(declaration.getArgs()) + row.substring(declaration.getEnd() + 1);
        }
        return row;
    }

    private static DataMethod findMethod(String name) {
        if (CLASSES == null) {
            Reflections reflections = new Reflections("ru.qa.summer.dg.methods");
            CLASSES = reflections.getTypesAnnotatedWith(DataMethodInfo.class);
            if (!DATA_GENERATOR_CONFIGURATION.getDgMethodsPackage().isEmpty()) {
                Reflections custom = new Reflections(DATA_GENERATOR_CONFIGURATION.getDgMethodsPackage());
                CLASSES.addAll(custom.getTypesAnnotatedWith(DataMethodInfo.class));
            }
        }
        for (Class<?> clazz : CLASSES) {
            if (clazz.getAnnotation(DataMethodInfo.class).name().equals(name)) {
                try {
                    return (DataMethod) clazz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Can't instantiate a data generator method fot " + name, e);
                }
            }
        }
        throw new RuntimeException("Data generator method with name '" + name + "' not found");
    }

    public static DataGeneratorDeclaration find(String row) {
        char[] chars = row.toCharArray();
        int start = -1;
        int end = -1;
        for (int i = 0; i < chars.length; i++) {
            Character current = chars[i];
            Character next = i + 1 < chars.length ? chars[i + 1] : null;
            if (next != null && current.equals('$') && next.equals('{')) {
                start = i;
            } else if (start != -1 && current.equals('}')) {
                end = i;
                break;
            }
        }
        if (start == -1) {
            return null;
        }
        return parse(row.substring(start + 2, end), start, end);
    }

    private static DataGeneratorDeclaration parse(String row, int start, int end) {
        String name;
        List<String> args = new ArrayList<>();
        if (!row.contains("(")) {
            name = row;
        } else if (row.contains(")")) {
            int startArgs = row.indexOf("(");
            int endArgs = row.lastIndexOf(")");
            name = row.substring(0, startArgs);
            String rowArgs = row.substring(startArgs + 1, endArgs);
            args = parseArgs(rowArgs);
        } else {
            throw new AssertionError("Query '" + row + "' not contains '('");
        }
        return new DataGeneratorDeclaration(start, end, name, args);
    }

    private static List<String> parseArgs(String row) {
        List<String> args = new ArrayList<>();
        char[] chars = row.toCharArray();
        boolean ignore = false;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            Character current = chars[i];
            if (!ignore && current.equals(',')) {
                args.add(builder.toString().trim());
                builder = new StringBuilder();
            } else if (current.equals('[')) {
                ignore = true;
            } else if (current.equals(']')) {
                ignore = false;
            } else {
                builder.append(current);
            }
        }
        if (ignore) {
            throw new AssertionError("Unclosed '['");
        } else {
            args.add(builder.toString().trim());
        }
        return args;
    }
}
