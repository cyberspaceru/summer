package ru.qa.summer.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.regex.Pattern;

public class Support {
    private static class Holder {
        private static final Gson GSON = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

    private Support() {
    }

    public static Gson getGson() {
        return Holder.GSON;
    }

    public static class Patterns {
        private static class Holder {
            private static final Pattern PROPERTY = Pattern.compile("\\$\\{.*}");
            private static final Pattern DOT = Pattern.compile("\\.");
            private static final Pattern SPACE = Pattern.compile("\\s");
            private static final Pattern COLON = Pattern.compile(":");
            private static final Pattern SEMICOLON = Pattern.compile(";");
            private static final Pattern LEFT_SQUARE_BRACKET = Pattern.compile("\\[");
            private static final Pattern RIGHT_SQUARE_BRACKET = Pattern.compile("]");
            private static final Pattern SLASH = Pattern.compile("/");
            private static final Pattern BACKSLASH = Pattern.compile("\\\\");
        }

        private Patterns() {
        }

        public static Pattern getProperty() {
            return Holder.PROPERTY;
        }

        public static Pattern getDot() {
            return Holder.DOT;
        }

        public static Pattern getSpace() {
            return Holder.SPACE;
        }

        public static Pattern getColon() {
            return Holder.COLON;
        }

        public static Pattern getSemicolon() {
            return Holder.SEMICOLON;
        }

        public static Pattern getLeftSquareBracket() {
            return Holder.LEFT_SQUARE_BRACKET;
        }

        public static Pattern getRightSquareBracket() {
            return Holder.RIGHT_SQUARE_BRACKET;
        }

        public static Pattern getSlash() {
            return Holder.SLASH;
        }

        public static Pattern getBackslash() {
            return Holder.BACKSLASH;
        }
    }
}
