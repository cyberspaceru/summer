package ru.qa.summer.dg;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources("classpath:data-generator.properties")
public interface DataGeneratorConfiguration extends Config {
    DataGeneratorConfiguration DATA_GENERATOR_CONFIGURATION = ConfigFactory.create(DataGeneratorConfiguration.class);

    @Config.Key("data-generator.package")
    @Config.DefaultValue("")
    String getDgMethodsPackage();
}
