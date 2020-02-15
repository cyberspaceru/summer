package ru.qa.summer.appium.util;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;

@UtilityClass
public class WebDriverUtil {
    public static boolean isSessionValid(WebDriver driver) {
        try {
            driver.getTitle();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
