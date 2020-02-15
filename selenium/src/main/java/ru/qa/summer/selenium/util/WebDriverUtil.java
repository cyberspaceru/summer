package ru.qa.summer.selenium.util;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;

@UtilityClass
public class WebDriverUtil {
    public static boolean isSessionValid(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception ignored) {
            // ignored
        }
        try {
            driver.getTitle();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
