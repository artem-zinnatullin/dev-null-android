package com.artemzin.android.dev_null.util;

public class StringUtil {

    private StringUtil() {}

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String getOnlyDigitsFromString(String textWithDigits) {
        if (isNullOrEmpty(textWithDigits)) {
            return "";
        }
        return textWithDigits.replaceAll("\\D", "");
    }

    public static String getOnlyUserAddedTextInsteadOfSharps(String pattern, String text) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < pattern.length(); i++) {
            if (i >= text.length()) break;

            if (pattern.charAt(i) == '#') {
                stringBuilder.append(text.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    public static String insertSymbolsInsteadSharps(final String pattern, final String text, final boolean stopWhenTextFullyInserted) {
        final StringBuilder formattedText = new StringBuilder();

        int charPosFromTextToInsert = 0;

        for (int i = 0; i < pattern.length(); i++) {
            if (stopWhenTextFullyInserted && charPosFromTextToInsert > text.length() - 1) break; // No more symbols to insert

            if (pattern.charAt(i) == '#' && charPosFromTextToInsert < text.length()) {
                formattedText.append(text.charAt(charPosFromTextToInsert));
                charPosFromTextToInsert++;
            } else
                formattedText.append(pattern.charAt(i));
        }

        return formattedText.toString();
    }

    public static String getPatternPrefix(String pattern) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '{' && pattern.length() > i + 1) {
                for (int j = i + 1; j < pattern.length(); j++) {
                    if (pattern.charAt(j) == '}') {
                        return stringBuilder.toString();
                    }

                    stringBuilder.append(pattern.charAt(j));
                }
            }
        }

        return stringBuilder.toString();
    }

    public static String getPatternFormattingPart(String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '}') return pattern.substring(i + 1);
        }

        return pattern;
    }

    public static String substringWithoutException(String text, int startIndex, int endIndex) {
        if (isNullOrEmpty(text) || startIndex >= text.length()) {
            return "";
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final int realEndIndex = endIndex >= text.length() ? text.length() - 1 : endIndex;


        for (int i = startIndex; i <= realEndIndex; i++) {
            stringBuilder.append(text.charAt(i));
        }

        return stringBuilder.toString();
    }
}
