package org.management.account.utils;

import java.util.Collection;

public final class StringUtils {
    private StringUtils() {
    }

    public static String[] parse(String str, String delimiter) {
        String[] separatedStr = str.split(delimiter);
        if (separatedStr == null || separatedStr.length == 0 || separatedStr[0] == "") {
            throw new IllegalArgumentException();
        }
        return separatedStr;
    }

    public static String join(Collection<String> permissionSet, String delimiter) {
        return String.join(delimiter, permissionSet);
    }
}
