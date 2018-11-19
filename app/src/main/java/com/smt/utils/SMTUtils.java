package com.smt.utils;


import java.io.File;

/**
 * 工具类
 */
public class SMTUtils {
    public static void createFileDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists())
            file.delete();
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
    }
}
