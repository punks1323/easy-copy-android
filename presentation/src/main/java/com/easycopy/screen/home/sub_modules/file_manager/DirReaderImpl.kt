package com.easycopy.screen.home.sub_modules.file_manager

import android.content.Context
import android.os.Environment
import com.easycopy.screen.home.model.FileInfo
import java.io.File

class DirReaderImpl(c: Context) : DirReader {
    val context: Context = c

    override fun getFilesAtRootDir(): ArrayList<FileInfo> {
        val listFiles = Environment.getExternalStorageDirectory()?.listFiles()

        val filesList = ArrayList<FileInfo>()
        if (listFiles != null) {
            var i = 0;
            for (f in listFiles) {
                var fileInfo = FileInfo()
                fileInfo.id = i++;
                fileInfo.name = f.name
                fileInfo.isFile = f.isFile
                fileInfo.length = f.length()
                fileInfo.lastModified = f.lastModified()

                filesList.add(fileInfo)
            }
        }

        return filesList;
    }

    override fun getFilesAtDir(dir: String): ArrayList<FileInfo> {
        val listFiles = File(dir).listFiles()

        val filesList = ArrayList<FileInfo>()
        if (listFiles != null) {
            var i = 0;
            for (f in listFiles) {
                val fileInfo = FileInfo()
                fileInfo.id = i++;
                fileInfo.name = f.name
                fileInfo.isFile = f.isFile
                fileInfo.length = f.length()
                fileInfo.lastModified = f.lastModified()

                filesList.add(fileInfo)
            }
        }

        return filesList;
    }
}