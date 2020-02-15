package ru.qa.summer.appium.services;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.qa.summer.appium.exceptions.DriverStartUpException;
import ru.qa.summer.appium.exceptions.UndefinedPlatformException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.function.Function;

import static ru.qa.summer.appium.AppiumConfiguration.APPIUM_CONFIGURATION;
import static ru.qa.summer.appium.services.ConfigurationManager.compileBitBar;

public class BaseDriverService extends DriverService<WebDriver> {
    private WebDriver webDriver;
    private long currentImplicitlyTimeout;

    public BaseDriverService() {
        this.currentImplicitlyTimeout = APPIUM_CONFIGURATION.getAppiumTimeout();
    }

    private URL getAppiumUrl() {
        try {
            return URI.create(APPIUM_CONFIGURATION.getAppiumUrl()).toURL();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void mountDriver() {
        URL url = getAppiumUrl();
        switch (APPIUM_CONFIGURATION.getAppiumPlatformName().trim()) {
            case "ios":
                throw new NotImplementedException("ios not supported yet");
            case "android":
                webDriver = mountDefaultOrBitBar(ConfigurationManager.compileAndroid(), c -> new AndroidDriver<>(url, c));
                break;
            default:
                throw new UndefinedPlatformException("Can't launch '" + APPIUM_CONFIGURATION.getAppiumPlatformName() + "' platform");
        }
    }

    private <T extends Capabilities> WebDriver mountDefaultOrBitBar(T capabilities, Function<T, WebDriver> localWebDriver) {
        if (APPIUM_CONFIGURATION.getAppiumBitBarIsEnabled()) {
            URL url;
            try {
                url = URI.create(APPIUM_CONFIGURATION.getAppiumBitBarAppiumAddress() + "/wd/hub").toURL();
            } catch (MalformedURLException e) {
                throw new DriverStartUpException(e);
            }
            return new RemoteWebDriver(url, capabilities.merge(compileBitBar()));
        }
        return localWebDriver.apply(capabilities);
    }

    @Override
    public void demountDriver() {

    }

    @Override
    public WebDriver getDriver() {
        if (isDriverEmpty()) {
            mountDriver();
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
