package ru.qa.summer.support.i18n;

import ru.qa.summer.support.data.AccessObject;
import ru.qa.summer.support.exceptions.AccessException;
import ru.qa.summer.support.exceptions.InternationalLibraryException;

import java.util.HashMap;
import java.util.Map;

import static ru.qa.summer.support.SupportConfiguration.SUPPORT_CONFIGURATION;

public class InternationalLibrary {
    private final AccessObject access;
    private Map<String, Map<String, String>> library;

    public InternationalLibrary(AccessObject access) {
        this.access = access;
    }

    public String getValue(String libKey) {
        return getValue(libKey, SUPPORT_CONFIGURATION.getInternalizationLanguage());
    }

    public String getValue(String libKey, String i18nKey) {
        Map<String, String> map = getLibrary().get(libKey);
        if (map == null) {
            throw new InternationalLibraryException("Library '" + libKey + "' not contains in " + access.toString());
        }
        return map.get(i18nKey);
    }

    private Map<String, Map<String, String>> getLibrary() {
        if (library != null) {
            return library;
        }
        library = new HashMap<>();
        String text = read();
        String[] lines = text.split("\n");
        String libKey = null;
        String i18nKey = null;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (!line.isEmpty()) {
                if (lines[i].startsWith(" ")) {
                    if (libKey == null) {
                        throw new InternationalLibraryException("I18N key was declared before a library key at " + (i + 1) + " line");
                    }
                    if (i18nKey == null) {
                        i18nKey = line;
                    } else {
                        library.computeIfAbsent(libKey, k -> new HashMap<>()).put(i18nKey, line);
                        i18nKey = null;
                    }
                } else {
                    if (i18nKey != null) {
                        throw new InternationalLibraryException("Library key was read but I18N value was expected instead of at " + (i + 1) + " line");
                    }
                    libKey = line;
                }
            }
        }
        return library;
    }

    private String read() {
        try {
            return access.load();
        } catch (AccessException e) {
            throw new InternationalLibraryException(e);
        }
    }
}
