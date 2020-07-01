package com.easycopy.utils;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-03
 */
public class StringUtils {

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
