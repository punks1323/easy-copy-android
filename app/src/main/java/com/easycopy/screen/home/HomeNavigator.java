package com.easycopy.screen.home;

import android.net.Uri;

import androidx.documentfile.provider.DocumentFile;

import com.easycopy.screen.base.BaseNavigator;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-01
 */
public interface HomeNavigator extends BaseNavigator {
    void openFile(int requestCode);

    void openDirectory(int requestCode);

    DocumentFile getDocumentFile(Uri treeUri);
}
