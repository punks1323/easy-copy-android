package com.easycopy.utils

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-03
 */
object StringUtils {
    fun isEmpty(cs: CharSequence?): Boolean {
        return cs == null || cs.length == 0
    }
}