package ru.qa.summer.appium.services;

import org.openqa.selenium.WebDriver;
import ru.qa.summer.exceptions.InstantiateException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.qa.summer.appium.AppiumConfiguration.APPIUM_CONFIGURATION;

public abstract class DriverService<T extends WebDriver> {
    private static AtomicInteger index = new AtomicInteger(0);

    public DriverService() {
    }

    private static List<DriverService> instances = new CopyOnWriteArrayList<>();

    public static int getIndex() {
        return index.get();
    }

    public abstract void mountDriver();

    public abstract void demountDriver();

    public abstract T getDriver();

    public abstract boolean isDriverEmpty();

    public abstract long getImplicitlyTimeout();

    public abstract void setImplicitlyTimeout(long seconds);

    public static DriverService getInstance() {
        int index = DriverService.index.get() + 1;
        if (instances.size() < index) {
            int counter = 0;
            while (instances.size() < (index - 1)) {
                if (instances.size() < counter + 1) {
                    instances.add(null);
                }
                counter++;
            }
            try {
                String className = APPIUM_CONFIGURATION.getSeleniumDriverServiceClass();
                instances.add((DriverService) Class.forName(className).newInstance());
            } catch (Exception e) {
                throw new InstantiateException(e);
            }
        }
        return instances.get(index - 1);
    }

    public static void setIndex(int index) {
        DriverService.index.set(index);
    }
}
