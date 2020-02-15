package ru.qa.summer.support.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.qa.summer.support.data.ResourceAccessObject;

public class InternationalLibraryTest {
    @Test
    public void test() {
        InternationalLibrary library = new InternationalLibrary(new ResourceAccessObject("library-example.i18n"));
        Assertions.assertEquals("my english phrase", library.getValue("KEY"));
    }
}
