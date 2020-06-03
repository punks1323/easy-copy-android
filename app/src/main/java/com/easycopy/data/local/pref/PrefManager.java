package com.easycopy.data.local.pref;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-03
 */
public interface PrefManager {

    void setSelectedDir(String selectedDir);

    String getSelectedDir();

    void setSelectedDirUri(String selectedDirUri);

    String getSelectedDirUri();
}
