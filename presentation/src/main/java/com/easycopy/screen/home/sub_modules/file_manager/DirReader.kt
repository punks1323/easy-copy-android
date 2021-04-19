package com.easycopy.screen.home.sub_modules.file_manager

import com.easycopy.screen.home.model.FileInfo

/**
 * @author pankaj
 * @version 1.0
 * @since 2021-04-19
 */
interface DirReader {
    fun getFilesAtRootDir(): ArrayList<FileInfo>
    fun getFilesAtDir(dir: String): ArrayList<FileInfo>
}