package ru.qa.summer.appium;

import lombok.Getter;
import ru.qa.summer.annotations.PageName;
import ru.qa.summer.appium.annotations.Id;

@Getter
@PageName("Главная страница")
public class HomePage extends AppiumPage {
    @Id("ru.dpd:id/btnTrack")
    private AppiumElement authorizeButton;
}
