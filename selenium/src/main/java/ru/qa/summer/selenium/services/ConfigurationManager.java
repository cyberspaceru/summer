package ru.qa.summer.selenium.services;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.qa.summer.support.data.ResourceAccessObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static ru.qa.summer.selenium.SeleniumConfiguration.*;
import static ru.qa.summer.support.util.PropertyUtil.readWithPrefix;

@UtilityClass
public class ConfigurationManager {
    private static Map<String, Function<String, Object>> mapRules;

    public static ChromeOptions compileChrome() {
        ChromeOptions options = new ChromeOptions();
        options.merge(compileByPrefix(CHROME_CAPABILITIES_PREFIX));
        if (!SELENIUM_CONFIGURATION.getSeleniumChromeArguments().isEmpty()) {
            List<String> arguments = stream(SELENIUM_CONFIGURATION.getSeleniumChromeArguments().split(","))
                    .map(String::trim)
                    .collect(toList());
            options.addArguments(arguments);
        }
        options.setCapability(BROWSER_NAME, "chrome");
        return options;
    }

    public static FirefoxOptions compileFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        options.merge(compileByPrefix(FIREFOX_CAPABILITIES_PREFIX));
        if (!SELENIUM_CONFIGURATION.getSeleniumFirefoxArguments().isEmpty()) {
            List<String> arguments = stream(SELENIUM_CONFIGURATION.getSeleniumFirefoxArguments().split(","))
                    .map(String::trim)
                    .collect(toList());
            options.addArguments(arguments);
        }
        options.setCapability(BROWSER_NAME, "firefox");
        return options;
    }

    public static Capabilities compileSelenoid() {
        return compileByPrefix(SELENOID_CAPABILITIES_PREFIX);
    }

    public static Capabilities compileByPrefix(String prefix) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, String> map = readWithPrefix(new ResourceAccessObject(PROPERTY_PATH), prefix);
        map.forEach((k, v) -> {
            boolean mapped = getMapRules().entrySet().stream().anyMatch(entry ->
                    doIfEquals(entry.getKey(), k, v, capabilities, entry.getValue())
            );
            if (!mapped) {
                capabilities.setCapability(k, v);
            }
        });
        return capabilities;
    }

    private static Map<String, Function<String, Object>> getMapRules() {
        if (mapRules == null) {
            mapRules = new HashMap<>();
            mapRules.put("enableVNC", Boolean::parseBoolean);
            mapRules.put("enableVideo", Boolean::parseBoolean);
        }
        return mapRules;
    }

    private static boolean doIfEquals(String expected, String actual, String value, DesiredCapabilities capabilities, Function<String, Object> to) {
        if (expected.equalsIgnoreCase(actual)) {
            capabilities.setCapability(expected, to.apply(value));
            return true;
        }
        return false;
    }
}
