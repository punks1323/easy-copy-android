package com.easycopy.screen.home.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-03
 */
@Data
public class DirInfo {
    String parentDirName;
    List<FileInfo> files;

    @Data
    public static class FileInfo {
        String name;
        String uri;
        Boolean isDirectory;
        Boolean isFile;
        Long lastModified;
        Long length;

    }

}
