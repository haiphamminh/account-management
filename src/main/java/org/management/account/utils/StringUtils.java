package org.management.account.utils;

import org.management.account.constant.Const;

import java.util.Collection;
import java.util.Set;

public final class StringUtils {
    private StringUtils() {
    }

    public static String[] parse(String str, String delimiter) {
        String[] separatedStr = str.split(delimiter);
        if (separatedStr == null || separatedStr.length < 1) {
            throw new IllegalArgumentException();
        }
        return separatedStr;
    }

    public static String join(Collection<String> permissionSet, String delimiter) {
        return String.join(delimiter, permissionSet);
    }
}
