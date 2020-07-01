package com.easycopy.screen.home

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import androidx.databinding.ObservableField
import com.easycopy.data.DataManager
import com.easycopy.screen.base.BaseViewModel
import com.easycopy.screen.home.model.DirInfo
import com.easycopy.screen.home.model.FileInfo
import com.easycopy.use_case.WSConnector
import com.easycopy.use_case.WSConnector.ConnectionListener
import com.easycopy.use_case.model.ConnectionStatus
import com.easycopy.utils.StringUtils
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.util.*
import javax.inject.Inject

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-01
 */
class HomeViewModel @Inject constructor(private val dataManager: DataManager, private val WSConnector: WSConnector) : BaseViewModel<HomeNavigator?>() {
    var state = ObservableField<String>()
    var selectedDir = ObservableField<String>()

    fun choseRootDir() {
        navigator!!.openDirectory(REQUEST_CODE_READ_DIR_TREE)
    }

    fun connect() {
        WSConnector.connect(
                object : ConnectionListener {
                    override fun connectionCallback(connectionStatus: ConnectionStatus) {
                        state.set(connectionStatus.name)
                    }
                }
        )
    }

    fun disconnect() {
        WSConnector.disconnect()
    }

    fun openFile() {
        navigator!!.openFile(REQUEST_CODE_READ_FILE)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?, contentResolver: ContentResolver) {
        try {
            if (requestCode == REQUEST_CODE_READ_FILE && resultCode == Activity.RESULT_OK) {
                if (resultData != null) {
                    val uri = resultData.data
                    val parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r")
                    val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
                    val fileInputStream = FileInputStream(fileDescriptor)
                    val os = ByteArrayOutputStream()
                    val buffer = ByteArray(1024)
                    var len: Int
                    while (fileInputStream.read(buffer).also { len = it } != -1) {
                        os.write(buffer, 0, len)
                    }
                    fileInputStream.close()
                    parcelFileDescriptor.close()
                    Timber.i("Writing started....")
                    //ws.send(ByteString.of(os.toByteArray()));
                    Timber.i("Writing completed....")
                }
            } else if (requestCode == REQUEST_CODE_READ_DIR_TREE && resultCode == Activity.RESULT_OK) {
                if (resultData != null) {
                    val treeUri = resultData.data
                    val pickedDir = navigator!!.getDocumentFile(treeUri)
                    dataManager.selectedDir = pickedDir.name
                    dataManager.selectedDirUri = pickedDir.uri.toString()
                    init()
                    val dirInfo = DirInfo()
                    dirInfo.parentDirName = pickedDir.name
                    dirInfo.files = ArrayList()
                    for (documentFile in pickedDir.listFiles()) {
                        val fileInfo = FileInfo()
                        fileInfo.isDirectory = documentFile.isDirectory
                        fileInfo.isFile = documentFile.isFile
                        fileInfo.name = documentFile.name
                        fileInfo.uri = documentFile.uri.toString()
                        fileInfo.lastModified = documentFile.lastModified()
                        fileInfo.length = documentFile.length()
                        dirInfo.files!!.add(fileInfo)
                    }

                    // Timber.w("JSON:: " + new ObjectMapper().writeValueAsString(dirInfo));
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun init() {
        val selectedPath = dataManager.selectedDir
        selectedDir.set(if (StringUtils.isEmpty(selectedPath)) "" else selectedPath)
    }

    companion object {
        const val REQUEST_CODE_READ_FILE = 6666
        const val REQUEST_CODE_READ_DIR_TREE = 6667
        const val CONNECTED = "CONNECTED"
        const val DISCONNECTED = "DISCONNECTED"
        const val FAILED = "FAILED"
    }

    init {
        Timber.w("CONSTRUCTOR")
    }
}