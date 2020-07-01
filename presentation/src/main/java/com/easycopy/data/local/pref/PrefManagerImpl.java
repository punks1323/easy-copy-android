package com.easycopy.data.local.pref;

import android.content.SharedPreferences;

import com.easycopy.constants.AppConstants;

import javax.inject.Inject;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-03
 */
public class PrefManagerImpl implements PrefManager {

    private final SharedPreferences pref;

    public PrefManagerImpl(SharedPreferences pref) {
        this.pref = pref;
    }

    @Override
    public void setSelectedDir(String selectedDir) {
        pref.edit().putString(AppConstants.PREF.SELECTED_ROOT_PATH, selectedDir).apply();
    }

    @Override
    public String getSelectedDir() {
        return pref.getString(AppConstants.PREF.SELECTED_ROOT_PATH, null);
    }

    @Override
    public void setSelectedDirUri(String selectedDirUri) {
        pref.edit().putString(AppConstants.PREF.SELECTED_ROOT_PATH_URI, selectedDirUri).apply();
    }

    @Override
    public String getSelectedDirUri() {
        return pref.getString(AppConstants.PREF.SELECTED_ROOT_PATH_URI, null);
    }
}
