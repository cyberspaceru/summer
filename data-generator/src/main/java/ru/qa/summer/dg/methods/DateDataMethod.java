package ru.qa.summer.dg.methods;

import ru.qa.summer.dg.annotations.DataMethodInfo;
import ru.qa.summer.support.util.DateUtil;

import java.util.Date;
import java.util.List;

@DataMethodInfo(name = "date")
public class DateDataMethod implements DataMethod {
    @Override
    public String invoke(List<String> args) {
        if (args.isEmpty()) {
            return DateUtil.formatCurrentDate("MM/dd/yyyy");
        } else if (args.size() == 2) {
            int days = Integer.parseInt(args.get(1));
            Date date = DateUtil.fromCurrent(days);
            return DateUtil.format(date, args.get(0));
        }
        return DateUtil.formatCurrentDate(args.get(0));
    }
}
