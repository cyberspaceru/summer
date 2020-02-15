package ru.qa.summer.dg.methods;

import ru.qa.summer.dg.annotations.DataMethodInfo;
import ru.qa.summer.support.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@DataMethodInfo(name = "first-work-day")
public class WorkDateDataMethod implements DataMethod {
    @Override
    public String invoke(List<String> args) {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        if (args.size() == 2) {
            calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(args.get(1)));
        }
        Date date = DateUtil.nextWorkDateOrSelf(calendar);
        if (args.isEmpty()) {
            return DateUtil.format(date, "MM/dd/yyyy");
        }
        return DateUtil.format(date, args.get(0));
    }
}
