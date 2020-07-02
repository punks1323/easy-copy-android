package com.easycopy.screen.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import com.easycopy.BR
import com.easycopy.R
import com.easycopy.databinding.ActivityMainBinding
import com.easycopy.screen.base.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity<ActivityMainBinding?, HomeViewModel?>(), HomeNavigator {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: ViewModel
        get() = homeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setNavigator(this)
        homeViewModel.init()
    }

    override fun openDirectory(requestCode: Int) {
        // Choose a directory using the system's file picker.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)

        // Provide read access to files and sub-directories in the user-selected
        // directory.
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when it loads.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
        startActivityForResult(intent, requestCode)
    }

    override fun getDocumentFile(treeUri: Uri?): DocumentFile {
        return DocumentFile.fromTreeUri(this, treeUri!!)!!
    }

    override fun openFile(requestCode: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        startActivityForResult(intent, requestCode)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        homeViewModel.onActivityResult(requestCode, resultCode, resultData, contentResolver)
    }

}