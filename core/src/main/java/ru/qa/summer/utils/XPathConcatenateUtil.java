package ru.qa.summer.utils;

import ru.qa.summer.exceptions.UndefinedXPathException;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Stack;

public class XPathConcatenateUtil {
    private XPathConcatenateUtil() {
    }

    public static String concat(List<SimpleEntry<String, Integer>> xpaths) {
        Stack<String> stack = new Stack<>();
        StringBuilder indexes = new StringBuilder();
        for (int i = 0; i < xpaths.size(); i++) {
            boolean isGlobal = false;
            SimpleEntry<String, Integer> entry = xpaths.get(i);
            String xpath = entry.getKey();
            Integer index = entry.getValue();
            if (xpath.startsWith("/")) {
                if (xpath.startsWith("//")) {
                    isGlobal = true;
                }
            } else if (xpath.startsWith(".//")) {
                xpath = xpath.substring(1);
            } else {
                throw new UndefinedXPathException("Can't concat '" + xpath + "'. Use '//<expression>', '/<expression>' or './/<expression>'");
            }
            if (index != -1) {
                indexes.append('(');
                stack.push(xpath + ")[" + (index + 1) + "]");
            } else {
                stack.push(xpath);
            }
            if (isGlobal) {
                break;
            }
        }
        StringBuilder builder = new StringBuilder(indexes.toString());
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        return builder.toString();
    }
}
