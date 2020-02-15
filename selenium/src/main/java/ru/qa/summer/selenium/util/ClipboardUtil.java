package ru.qa.summer.selenium.util;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@UtilityClass
public class ClipboardUtil {

    /**
     * Метод сохранения строки в буфер обмена.
     *
     * @param value строка для сохранения в буфер обмена.
     */
    public static void copyStringToClipboard(String value) {
        StringSelection selection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
