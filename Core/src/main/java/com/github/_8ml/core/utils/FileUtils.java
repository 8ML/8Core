package com.github._8ml.core.utils;
/*
Created by @8ML (https://github.com/8ML) on June 29 2021
*/

import java.io.File;

public class FileUtils {

    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

}
