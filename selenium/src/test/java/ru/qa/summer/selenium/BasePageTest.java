package ru.qa.summer.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.qa.summer.selenium.pages.base.BasePage;
import ru.qa.summer.selenium.pages.base.elements.ListItemWithSection;
import ru.qa.summer.support.util.BenchmarkUtil;


public class BasePageTest extends BaseSeleniumTest {
    @Test
    public void test() {
        BasePage page = new BasePage();
        Assertions.assertEquals("Page Title", page.getTitle().getText());
        long time = BenchmarkUtil.simple(() -> {
            // Section
            Assertions.assertEquals("This is a section with a textarea", page.getSection().getLabel().getText());
            Assertions.assertTrue(page.getSection().getTextArea().getText().isEmpty());
            page.getSection().getTextArea().sendKeys("New Text");
            Assertions.assertEquals("New Text", page.getSection().getTextArea().getText());
            // Section of List
            Assertions.assertEquals(7, page.getSection().getList().size());
            ListItemWithSection listItem = page.getSection().getList().get(1).to(ListItemWithSection.class);
            Assertions.assertEquals("List Item 2 (Inside some section)", listItem.getLabel().getText());
            Assertions.assertEquals("List Item 2 Button", listItem.getButton().getText());
            // Section of Sub-List
            ListItemWithSection subListItem = page.getSection().getList().get(2).toList(ListItemWithSection.class).get(0);
            Assertions.assertEquals("List Sub-Item 3.1 (Inside some section)", subListItem.getLabel().getText());
            Assertions.assertEquals("List Sub-Item 3.1 Button", subListItem.getButton().getText());
            // Sub-Section
            Assertions.assertEquals("This is a sub-section with a table", page.getSection().getSubSection().getLabel().getText());
        });
        System.out.println("Elapsed: " + time);
    }
}
