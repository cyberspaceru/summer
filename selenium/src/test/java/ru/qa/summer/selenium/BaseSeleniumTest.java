package ru.qa.summer.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import ru.qa.summer.selenium.services.DriverService;
import ru.qa.summer.support.data.ResourceAccessObject;

import java.io.File;

import static java.util.Optional.ofNullable;

public class BaseSeleniumTest {
    protected WebDriver driver;

    @BeforeEach
    public void before() {
        driver = DriverService.getInstance().getDriver();
        File file = new ResourceAccessObject("pages/base-page.html").getFile();
        driver.get(file.getAbsolutePath());
    }

    @AfterEach
    public void after() {
        ofNullable(driver).ifPresent(WebDriver::close);
    }
}
