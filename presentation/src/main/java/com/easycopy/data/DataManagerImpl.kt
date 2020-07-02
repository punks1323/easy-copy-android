package com.easycopy.data

import com.easycopy.data.local.pref.PrefManager

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
class DataManagerImpl(private val prefManager: PrefManager) : DataManager {

    override var selectedDir: String?
        get() = prefManager.selectedDir
        set(selectedDir) {
            prefManager.selectedDir = selectedDir
        }

    override var selectedDirUri: String?
        get() = prefManager.selectedDirUri
        set(selectedDirUri) {
            prefManager.selectedDirUri = selectedDirUri
        }

}