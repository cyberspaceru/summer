package ru.qa.summer.selenium;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import ru.qa.summer.Configuration;

import static ru.qa.summer.selenium.SeleniumConfiguration.PROPERTY_PATH;

/**
 * @author Ilya Tatsiy
 */
@Config.Sources("classpath:" + PROPERTY_PATH)
public interface SeleniumConfiguration extends Configuration {
    String PROPERTY_PATH = "selenium.properties";
    String CHROME_CAPABILITIES_PREFIX = "selenium.chrome.capabilities";
    String FIREFOX_CAPABILITIES_PREFIX = "selenium.firefox.capabilities";
    String SELENOID_CAPABILITIES_PREFIX = "selenium.selenoid.capabilities";
    SeleniumConfiguration SELENIUM_CONFIGURATION = ConfigFactory.create(SeleniumConfiguration.class);

    @Key("selenium.timeout")
    @DefaultValue("3000")
    long getSeleniumTimeout();

    @Key("selenium.url")
    String getSeleniumUrl();

    @Key("selenium.driver-service-class")
    @DefaultValue("ru.qa.summer.selenium.services.BaseDriverService")
    String getSeleniumDriverServiceClass();

    @Key("selenium.browser-name")
    @DefaultValue("chrome")
    String getSeleniumBrowserName();

    @Key("selenium.xpath.concatenation-enabled")
    @DefaultValue("true")
    boolean isXPathConcatenationEnabled();

    @Key("selenium.xpath.language")
    @DefaultValue("eng")
    String getXPathLanguage();

    // region selenoid
    @Key("selenium.selenoid.is-enabled")
    @DefaultValue("false")
    Boolean getSeleniumSelenoidIsEnabled();

    @Key("selenium.selenoid.remote-address")
    @DefaultValue("")
    String getSeleniumSelenoidRemoteAddress();
    // endregion

    // region chrome
    @Key("selenium.chrome.path-to-executable-file")
    @DefaultValue("C:/chromedriver.exe")
    String getSeleniumChromePathToExecutableFile();

    @Key("selenium.chrome.arguments")
    @DefaultValue("")
    String getSeleniumChromeArguments();
    // endregion

    // region firefox
    @Key("selenium.firefox.path-to-executable-file")
    @DefaultValue("C:/geckodriver.exe")
    String getSeleniumFirefoxPathToExecutableFile();

    @Key("selenium.firefox.arguments")
    @DefaultValue("")
    String getSeleniumFirefoxArguments();
    // endregion
}
