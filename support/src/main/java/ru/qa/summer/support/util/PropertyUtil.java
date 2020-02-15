package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;
import ru.qa.summer.support.Support;
import ru.qa.summer.support.data.AccessObject;
import ru.qa.summer.support.exceptions.AccessException;
import ru.qa.summer.support.exceptions.PropertyNotFoundException;
import ru.qa.summer.support.exceptions.PropertyReadingException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;

@UtilityClass
public class PropertyUtil {
    public static String compilePropertyLinks(String source, Properties properties, String prefix) throws PropertyNotFoundException {
        return compilePropertyLinks(source, readWithPrefix(properties, prefix));
    }

    public static String compilePropertyLinks(String source, AccessObject access, String prefix) throws PropertyNotFoundException {
        return compilePropertyLinks(source, readWithPrefix(access, prefix));
    }

    public static String compilePropertyLinks(String source, Map<String, String> properties) throws PropertyNotFoundException {
        Matcher matcher = Support.Patterns.getProperty().matcher(source);
        while (matcher.find()) {
            String group = matcher.group();
            if (group.length() > 3) {
                String key = group.substring(2, group.length() - 1);
                String value = properties.get(key);
                if (value == null) {
                    throw new PropertyNotFoundException("Property with name '" + key + "' not found");
                }
                source = source.replace(group, value);
            }
        }
        return source;
    }

    public static Map<String, String> readWithPrefix(AccessObject access, String prefix) {
        try {
            Properties properties = new Properties();
            properties.load(new StringReader(access.load()));
            return readWithPrefix(properties, prefix);
        } catch (AccessException | IOException e) {
            throw new PropertyReadingException(e);
        }
    }

    public static Map<String, String> readWithPrefix(Properties properties, String prefix) {
        Map<String, String> result = new HashMap<>();
        String p = prefix.endsWith(".") ? prefix : prefix + ".";
        properties.forEach((k, v) -> {
            if (k.toString().startsWith(p)) {
                result.put(k.toString().replace(p, ""), v.toString());
            }
        });
        return Collections.unmodifiableMap(result);
    }
}
