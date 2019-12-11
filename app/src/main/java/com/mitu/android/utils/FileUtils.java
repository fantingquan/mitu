package com.mitu.android.utils;

import java.io.File;

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/29 15:52.
 * description:文件处理
 */
public class FileUtils {

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        try {
            if (directory != null && directory.exists() && directory.isDirectory()) {
                for (File child : directory.listFiles()) {
                    if (child.isDirectory()) {
                        deleteFilesByDirectory(child);
                    }
                    child.delete();
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 删除文件，传入文件名
     * @param fileName
     */
    public static void deleteFilesByFile(String fileName) {
        try {
            if (fileName.isEmpty())
                return;

            File file = new File(fileName);
            file.delete();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
