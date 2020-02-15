package ru.qa.summer.dg.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.qa.summer.dg.DataGenerator;
import ru.qa.summer.support.util.DateUtil;

public class DateTest {
    @Test
    public void test() {
        String date = DateUtil.formatCurrentDate("MM/dd/yyyy");
        Assertions.assertEquals(date, DataGenerator.compile("${date}"));
        DataGenerator.compile("${first-work-day(yyyy-MM-dd, 1)}");
    }
}
