package com.rweqx.util;

import com.rweqx.constants.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * Faster way of performing a.toLowerCase().contains(b.toLowerCase()) for two
     * from https://stackoverflow.com/questions/86780/how-to-check-if-a-string-contains-another-string-in-a-case-insensitive-manner-in/25379180#25379180
     *
     */
    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public static boolean isPositiveNumber(String text) {
        //System.out.println(text);
        if (text.equalsIgnoreCase("")) {
            return false;
        }
        //Match regex.
        Pattern p = Pattern.compile(Constants.NUMBER_REGEX);
        Matcher m = p.matcher(text);
        if (m.matches() && !text.equals(".")) {
            return true;
        }
        return false;
    }
}
