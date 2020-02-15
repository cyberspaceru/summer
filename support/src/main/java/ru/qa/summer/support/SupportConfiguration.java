package ru.qa.summer.support;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import static ru.qa.summer.support.SupportConfiguration.PROPERTY_PATH;

@Config.Sources("classpath:" + PROPERTY_PATH)
public interface SupportConfiguration extends Config {
    String PROPERTY_PATH = "support.properties";
    SupportConfiguration SUPPORT_CONFIGURATION = ConfigFactory.create(SupportConfiguration.class);

    @Key("support.i18n.language")
    @DefaultValue("eng")
    String getInternalizationLanguage();

    @Key("support.loadable.timeout")
    @DefaultValue("180000")
    Long getLoadableTimeout();

    @Key("support.loadable.recovery-packages")
    @DefaultValue("")
    String getLoadableRecoveryPackages();
}
