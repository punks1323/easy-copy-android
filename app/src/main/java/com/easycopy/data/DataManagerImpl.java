package com.easycopy.data;

import com.easycopy.data.local.pref.PrefManager;

import javax.inject.Inject;

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
public class DataManagerImpl implements DataManager {


    private PrefManager prefManager;

    public DataManagerImpl(PrefManager prefManager) {
        this.prefManager = prefManager;
    }

    @Override
    public void setSelectedDir(String selectedDir) {
        prefManager.setSelectedDir(selectedDir);
    }

    @Override
    public String getSelectedDir() {
        return prefManager.getSelectedDir();
    }

    @Override
    public void setSelectedDirUri(String selectedDirUri) {
        prefManager.setSelectedDirUri(selectedDirUri);
    }

    @Override
    public String getSelectedDirUri() {
        return prefManager.getSelectedDirUri();
    }
}
