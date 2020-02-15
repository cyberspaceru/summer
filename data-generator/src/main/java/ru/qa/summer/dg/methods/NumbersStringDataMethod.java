package ru.qa.summer.dg.methods;

import org.apache.commons.lang3.RandomStringUtils;
import ru.qa.summer.dg.annotations.DataMethodInfo;

import java.util.List;

@DataMethodInfo(name = "numbers")
public class NumbersStringDataMethod implements DataMethod {
    @Override
    public String invoke(List<String> args) {
        int size = 10;
        if (!args.isEmpty()) {
            size = Integer.parseInt(args.get(0));
        }
        return RandomStringUtils.randomNumeric(size);
    }
}
