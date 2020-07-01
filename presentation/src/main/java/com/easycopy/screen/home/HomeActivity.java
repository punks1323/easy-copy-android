package com.easycopy.screen.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.documentfile.provider.DocumentFile;

import com.easycopy.BR;
import com.easycopy.R;
import com.easycopy.databinding.ActivityMainBinding;
import com.easycopy.screen.base.BaseActivity;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity<ActivityMainBinding, HomeViewModel> implements HomeNavigator {

    @Inject
    HomeViewModel homeViewModel;

    @Override
    protected int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

        @Override
    protected HomeViewModel getViewModel() {
        return homeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel.setNavigator(this);
        homeViewModel.init();
    }

    @Override
    public void openDirectory(int requestCode) {
        // Choose a directory using the system's file picker.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        // Provide read access to files and sub-directories in the user-selected
        // directory.
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when it loads.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);

        startActivityForResult(intent, requestCode);
    }

    @Override
    public DocumentFile getDocumentFile(Uri treeUri) {
        return DocumentFile.fromTreeUri(this, treeUri);
    }

    @Override
    public void openFile(int requestCode) {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        homeViewModel.onActivityResult(requestCode, resultCode, resultData, getContentResolver());
    }
}
