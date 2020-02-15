package ru.qa.summer.support.util;

import org.jetbrains.annotations.NotNull;
import ru.qa.summer.support.exceptions.NameParseException;

public class NamingUtil {
    private NamingUtil() {
    }

    public static boolean containsIndexer(@NotNull String s) {
        return s.matches(".*\\[\\d*]");
    }

    public static int extractIndex(@NotNull String s) {
        if (containsIndexer(s)) {
            String sInt = s.substring(s.lastIndexOf('[')).trim()
                    .replaceAll("[\\[\\]]", "");
            return Integer.parseInt(sInt);
        }
        throw new NameParseException("Indexer not found for '" + s + "'");
    }

    @NotNull
    public static String removeIndexer(@NotNull String s) {
        if (containsIndexer(s)) {
            return s.substring(0, s.lastIndexOf('[')).trim();
        }
        return s;
    }

}
