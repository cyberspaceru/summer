package ru.qa.summer.dg.methods;

import ru.qa.summer.dg.annotations.DataMethodInfo;

import java.util.Date;
import java.util.List;

@DataMethodInfo(name = "timestamp")
public class TimestampDataMethod implements DataMethod {
    @Override
    public String invoke(List<String> args) {
        return Long.toString(new Date().getTime());
    }
}
