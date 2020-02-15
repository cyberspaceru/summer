package ru.qa.summer.selenium.services;

import org.openqa.selenium.WebDriver;
import ru.qa.summer.exceptions.InstantiateException;
import ru.qa.summer.selenium.enums.BrowserName;

import static ru.qa.summer.selenium.SeleniumConfiguration.SELENIUM_CONFIGURATION;

public abstract class DriverService<T extends WebDriver> {
    private final BrowserName browserName;

    public DriverService() {
        this.browserName = BrowserName.valueOf(SELENIUM_CONFIGURATION.getSeleniumBrowserName().toUpperCase());
    }

    private static DriverService instance;

    public abstract String compileUrl(String url);

    public abstract void open(String url);

    public abstract void mountDriver();

    public abstract void demountDriver();

    public abstract T getDriver();

    public abstract boolean isDriverEmpty();

    public abstract long getImplicitlyTimeout();

    public abstract void setImplicitlyTimeout(long seconds);

    public BrowserName getBrowserName() {
        return browserName;
    }

    private void resolveDriverPath() {
        System.setProperty("webdriver.chrome.driver", SELENIUM_CONFIGURATION.getSeleniumChromePathToExecutableFile());
        System.setProperty("webdriver.gecko.driver", SELENIUM_CONFIGURATION.getSeleniumFirefoxPathToExecutableFile());
    }

    public static DriverService getInstance() {
        if (instance == null) {
            try {
                String className = SELENIUM_CONFIGURATION.getSeleniumDriverServiceClass();
                instance = (DriverService) Class.forName(className).newInstance();
                instance.resolveDriverPath();
            } catch (Exception e) {
                throw new InstantiateException(e);
            }
        }
        return instance;
    }
}
