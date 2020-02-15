package ru.qa.summer.selenium.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import static java.util.Arrays.stream;

@UtilityClass
public class SeleniumElementUtil {
    public static boolean isValid(@Nullable CharSequence... sequences) {
        return sequences != null && stream(sequences).allMatch(SeleniumElementUtil::isValid);
    }

    public static boolean isValid(@Nullable CharSequence sequence) {
        return sequence != null && !sequence.toString().trim().isEmpty();
    }

    public static boolean isValid(@Nullable String string) {
        return string != null && !string.trim().isEmpty();
    }
}
