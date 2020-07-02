package com.easycopy.data.local.pref

import android.content.SharedPreferences
import com.easycopy.constants.AppConstants.PREF

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-03
 */
class PrefManagerImpl(private val pref: SharedPreferences) : PrefManager {

    override var selectedDir: String?
        get() = pref.getString(PREF.Companion.SELECTED_ROOT_PATH, null)
        set(selectedDir) {
            pref.edit().putString(PREF.Companion.SELECTED_ROOT_PATH, selectedDir).apply()
        }

    override var selectedDirUri: String?
        get() = pref.getString(PREF.Companion.SELECTED_ROOT_PATH_URI, null)
        set(selectedDirUri) {
            pref.edit().putString(PREF.Companion.SELECTED_ROOT_PATH_URI, selectedDirUri).apply()
        }

}