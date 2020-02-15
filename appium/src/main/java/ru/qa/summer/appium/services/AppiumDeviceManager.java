package ru.qa.summer.appium.services;

import ru.qa.summer.appium.exceptions.DriverStartUpException;
import ru.qa.summer.support.data.ResourceAccessObject;
import ru.qa.summer.support.exceptions.AccessException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.qa.summer.appium.AppiumConfiguration.APPIUM_CONFIGURATION;

public class AppiumDeviceManager {
    public static Device getFreeDevice() {
        return new AppiumDeviceManager().getDevices().get(DriverService.getIndex());
    }

    public List<Device> getDevices() {
        try {
            String content = new ResourceAccessObject(APPIUM_CONFIGURATION.getAppiumAndroidDevicesFile()).load();
            return Arrays.stream(content.split("\n"))
                    .map(x -> {
                        String[] parts = x.split(":");
                        return new Device()
                                .setId(parts[0])
                                .setVersion(Integer.parseInt(parts[1].trim()));
                    }).collect(Collectors.toList());
        } catch (AccessException e) {
            throw new DriverStartUpException(e);
        }
    }

    public static class Device {
        private String id;
        private Integer version;

        public String getId() {
            return id;
        }

        private Device setId(String id) {
            this.id = id;
            return this;
        }

        public Integer getVersion() {
            return version;
        }

        private Device setVersion(Integer version) {
            this.version = version;
            return this;
        }
    }
}
