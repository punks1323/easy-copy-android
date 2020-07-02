package com.easycopy.screen.home

import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.easycopy.screen.base.BaseNavigator

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-01
 */
interface HomeNavigator : BaseNavigator {
    fun openFile(requestCode: Int)
    fun openDirectory(requestCode: Int)
    fun getDocumentFile(treeUri: Uri?): DocumentFile
}