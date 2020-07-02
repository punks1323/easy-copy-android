package com.easycopy.constants

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-01
 */
interface AppConstants {
    interface PREF {
        companion object {
            const val SELECTED_ROOT_PATH = "SELECTED_ROOT_PATH"
            const val SELECTED_ROOT_PATH_URI = "SELECTED_ROOT_PATH_URI"
        }
    }

    companion object {
        const val PREF_NAME = "easy_copy"
        const val BASE_DOMAIN_DEBUG = ""
        const val BASE_DOMAIN_RELEASE = ""
    }
}