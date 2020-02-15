package ru.qa.summer.appium.services;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.qa.summer.support.data.ResourceAccessObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static ru.qa.summer.appium.AppiumConfiguration.*;
import static ru.qa.summer.support.util.PropertyUtil.readWithPrefix;

@UtilityClass
public class ConfigurationManager {
    private static Map<String, Function<String, Object>> mapRules;

    public static DesiredCapabilities compileAndroid() {
        DesiredCapabilities options = new DesiredCapabilities();
        AppiumDeviceManager.Device device = AppiumDeviceManager.getFreeDevice();
        options.merge(compileByPrefix(ANDROID_CAPABILITIES_PREFIX));
        options.setCapability("app", resolveApplicationPath().getAbsolutePath());
        options.setCapability("platformName", "Android");
        options.setCapability("deviceName", "any");
        options.setCapability("udid", device.getId());
        options.setCapability("platformVersion", device.getVersion());
        return options;
    }

    public static Capabilities compileBitBar() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File file = new File(APPIUM_CONFIGURATION.getAppiumApplicationPath());
//        String id = BitBarUtil.uploadFile(file);
        String id = "130222692";
        Assertions.assertNotNull(id, "File not uploaded to BitBar");
        capabilities.setCapability("bitbar_app", id);
        capabilities.setCapability("bitbar_apiKey", APPIUM_CONFIGURATION.getAppiumBitBarApiKey());
        capabilities.setCapability("automationName", "Appium");
        return capabilities.merge(compileByPrefix(BITBAR_CAPABILITIES_PREFIX));
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

    private File resolveApplicationPath() {
        String path = APPIUM_CONFIGURATION.getAppiumApplicationPath();

        return new File(path);
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
