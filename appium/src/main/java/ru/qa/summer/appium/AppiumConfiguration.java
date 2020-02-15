package ru.qa.summer.appium;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import ru.qa.summer.Configuration;

import static ru.qa.summer.appium.AppiumConfiguration.PROPERTY_PATH;

@Config.Sources("classpath:" + PROPERTY_PATH)
public interface AppiumConfiguration extends Configuration {
    String PROPERTY_PATH = "appium.properties";
    String ANDROID_CAPABILITIES_PREFIX = "appium.android.capabilities";
    String BITBAR_CAPABILITIES_PREFIX = "appium.bitbar.capabilities";
    AppiumConfiguration APPIUM_CONFIGURATION = ConfigFactory.create(AppiumConfiguration.class);

    @Key("appium.timeout")
    @DefaultValue("3000")
    long getAppiumTimeout();

    @Key("appium.application.path")
    @DefaultValue("")
    String getAppiumApplicationPath();

    @Key("appium.url")
    @DefaultValue("")
    String getAppiumUrl();

    @Key("appium.xpath.language")
    @DefaultValue("eng")
    String getXPathLanguage();

    @Key("appium.platform.name")
    @DefaultValue("")
    String getAppiumPlatformName();

    @Key("appium.android.devices-file")
    @DefaultValue("")
    String getAppiumAndroidDevicesFile();

    @Key("appium.bitbar.cloud-address")
    @DefaultValue("https://appium.bitbar.com")
    String getAppiumBitBarAppiumAddress();

    @Key("appium.bitbar.cloud-address")
    @DefaultValue("https://cloud.bitbar.com")
    String getAppiumBitBarCloudAddress();

    @Key("appium.bitbar.api-key")
    @DefaultValue("")
    String getAppiumBitBarApiKey();

    @Key("appium.bitbar.username")
    @DefaultValue("")
    String getAppiumBitBarUsername();

    @Key("appium.bitbar.password")
    @DefaultValue("")
    String getAppiumBitBarPassword();

    @Key("appium.bitbar.user-id")
    @DefaultValue("")
    String getAppiumBitBarUserId();

    @Key("appium.bitbar.is-enabled")
    @DefaultValue("false")
    Boolean getAppiumBitBarIsEnabled();

    @Key("appium.driver-service-class")
    @DefaultValue("ru.qa.summer.appium.services.BaseDriverService")
    String getSeleniumDriverServiceClass();
}
