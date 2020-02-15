package ru.qa.summer.selenium.services;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.qa.summer.selenium.exceptions.DriverStartUpException;
import ru.qa.summer.selenium.exceptions.StandNotFoundException;
import ru.qa.summer.selenium.exceptions.UndefinedBrowserException;
import ru.qa.summer.support.data.ResourceAccessObject;
import ru.qa.summer.support.exceptions.PropertyNotFoundException;
import ru.qa.summer.support.util.PropertyUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

import static ru.qa.summer.selenium.SeleniumConfiguration.PROPERTY_PATH;
import static ru.qa.summer.selenium.SeleniumConfiguration.SELENIUM_CONFIGURATION;
import static ru.qa.summer.selenium.services.ConfigurationManager.compileSelenoid;
import static ru.qa.summer.selenium.util.WebDriverUtil.isSessionValid;
import static ru.qa.summer.support.util.PropertyUtil.compilePropertyLinks;

public class BaseDriverService extends DriverService<WebDriver> {
    private final Map<String, String> stands;
    private WebDriver webDriver;
    private long currentImplicitlyTimeout;

    public BaseDriverService() {
        this.currentImplicitlyTimeout = SELENIUM_CONFIGURATION.getSeleniumTimeout();
        this.stands = PropertyUtil.readWithPrefix(new ResourceAccessObject(PROPERTY_PATH), "selenium.stands");
    }

    @Override
    public String compileUrl(String url) {
        try {
            return compilePropertyLinks(url, stands);
        } catch (PropertyNotFoundException e) {
            throw new StandNotFoundException(e);
        }
    }

    @Override
    public void open(String url) {
        getDriver().get(compileUrl(url));
    }

    @Override
    public void mountDriver() {
        switch (SELENIUM_CONFIGURATION.getSeleniumBrowserName().trim()) {
            case "chrome":
                webDriver = mountDefaultOrRemote(ConfigurationManager.compileChrome(), ChromeDriver::new);
                break;
            case "firefox":
                webDriver = mountDefaultOrRemote(ConfigurationManager.compileFirefox(), FirefoxDriver::new);
                break;
            default:
                throw new UndefinedBrowserException("Can't launch '" + SELENIUM_CONFIGURATION.getSeleniumBrowserName() + "' browser");
        }
        webDriver.manage().window().maximize();
    }

    private <T extends Capabilities> WebDriver mountDefaultOrRemote(T capabilities, Function<T, WebDriver> localWebDriver) {
        if (SELENIUM_CONFIGURATION.getSeleniumSelenoidIsEnabled()) {
            URL url;
            try {
                url = URI.create(SELENIUM_CONFIGURATION.getSeleniumSelenoidRemoteAddress()).toURL();
            } catch (MalformedURLException e) {
                throw new DriverStartUpException(e);
            }
            return new RemoteWebDriver(url, capabilities.merge(compileSelenoid()));
        }
        return localWebDriver.apply(capabilities);
    }

    @Override
    public void demountDriver() {

    }

    @Override
    public WebDriver getDriver() {
        if (isDriverEmpty() || !isSessionValid(webDriver)) {
            mountDriver();
            if (SELENIUM_CONFIGURATION.getSeleniumUrl() != null) {
                open(SELENIUM_CONFIGURATION.getSeleniumUrl());
            }
        }
        return webDriver;
    }

    @Override
    public boolean isDriverEmpty() {
        return webDriver == null;
    }

    @Override
    public long getImplicitlyTimeout() {
        return currentImplicitlyTimeout;
    }

    @Override
    public void setImplicitlyTimeout(long ms) {
        currentImplicitlyTimeout = ms;
    }
}
