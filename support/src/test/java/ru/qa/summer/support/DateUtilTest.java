package ru.qa.summer.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.qa.summer.support.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilTest {
    @Test
    public void testDateUtilGetDateFromString() throws ParseException {
        String stringDate = "23.10.2019";
        Date date = DateUtil.getDateFromString(stringDate, "dd.MM.yyyy");
        Assertions.assertEquals(new SimpleDateFormat("dd.MM.yyyy").format(date), stringDate);
    }
}
