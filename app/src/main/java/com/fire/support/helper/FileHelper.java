package com.fire.support.helper;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * 文件工具类
 */
public class FileHelper {

    private static FileHelper util;

    synchronized public static FileHelper getInstance() {

        if (util == null) {
            util = new FileHelper();

        }
        return util;

    }

    private FileHelper() {
        super();
    }

    /**
     * 是否有外存卡,并且能够读写
     *
     * @return boolean
     */
    public boolean isExistExternalStore() {

        try {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 得到sd卡路径
     *
     * @return String
     */
    public String getExternalStorePath() {
        if (isExistExternalStore()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }


    /**
     * 得到sd卡路径
     *
     * @return File
     */
    public File getExternalStoreFile() {
        if (isExistExternalStore()) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }


    /**
     * 得到sd卡下载路径
     *
     * @return File
     */
    public File getDownloadCacheDirectory() {
        if (isExistExternalStore()) {
            return Environment.getDownloadCacheDirectory();
        }
        return null;
    }

    /**
     * 将字符串写入文件
     *
     * @param text     String
     * @param fileStr  String
     * @param isAppend boolean
     */
    public void writeFile(String text, String fileStr, boolean isAppend) {

        try {
            File file = new File(fileStr);

            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream f = new FileOutputStream(fileStr, isAppend);
            f.write(text.getBytes());
            f.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    /**
     * 移动文件 1 需要一个write权限 2 目录要同级别,这个很关键,你交换的两个文件夹要有相同的层数.
     *
     * @param srcFileName 源文件完整路径，需要文件名
     * @param destDirName 目的目录路径,不需要文件名
     * @return 文件移动成功返回true，否则返回false
     */
    public boolean moveFile(String srcFileName, String destDirName, String moveName) {

        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File reNameFile = new File(destDirName + File.separator
                + moveName);

        return srcFile.renameTo(reNameFile);


    }

    /**
     * 重命名
     *
     * @param oldFilePath 旧文件的完整路径
     * @param newName     新的文件名
     * @return
     */
    public boolean renameFile(String oldFilePath, String newName) {
        File srcFile = new File(oldFilePath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File destDir = new File(srcFile.getParentFile(), newName);


        return srcFile.renameTo(destDir);

    }


    public boolean renameFile(String oldFilePath, String title, String ext) {
        File srcFile = new File(oldFilePath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File destDir = getDestFile(srcFile, title, ext);

        while (destDir.exists()) {

            title = title + "-1";

            destDir = getDestFile(srcFile, title, ext);
        }


        return srcFile.renameTo(destDir);

    }

    private File getDestFile(File srcFile, String title, String ext) {


        return new File(srcFile.getParentFile(), title + ext);
    }

    /**
     * 拷贝文件
     *
     * @param srcFile
     * @param destFile
     * @return
     * @throws IOException
     */
    public boolean copyFileTo(File srcFile, File destFile) {


        try {

            if (!srcFile.exists() || srcFile.isDirectory()) {


                return false;
            }

            if (destFile.isDirectory()) {


                return false;
            }


            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            int readLen = 0;
            byte[] buf = new byte[1024];
            while ((readLen = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLen);
            }
            fos.flush();
            fos.close();
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public boolean copyFileTo(String srcStr, String destStr) {
        File srcFile = new File(srcStr);
        File destFile = new File(destStr);

        return copyFileTo(srcFile, destFile);
    }

    /**
     * 拷贝文件夹
     *
     * @param srcDir
     * @param destDir
     * @return
     */
    public boolean copyFilesTo(File srcDir, File destDir) {

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        if (!srcDir.exists()) {
            return false;
        }
        if (!srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcFiles = srcDir.listFiles();
        if (srcFiles == null || srcFiles.length == 0) {
            return false;
        }

        try {
            for (int i = 0; i < srcFiles.length; i++) {
                if (srcFiles[i].isFile()) {

                    File destFile = new File(destDir.getPath() + File.separator
                            + srcFiles[i].getName());
                    copyFileTo(srcFiles[i], destFile);
                } else if (srcFiles[i].isDirectory()) {
                    File theDestDir = new File(destDir.getPath() + File.separator
                            + srcFiles[i].getName());
                    copyFilesTo(srcFiles[i], theDestDir);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean copyFilesTo(String srcStr, String destStr) {
        File srcFile = new File(srcStr);
        File destFile = new File(destStr);

        return copyFilesTo(srcFile, destFile);
    }

    /**
     * 移动文件
     *
     * @param srcFile
     * @param destFile
     * @return
     */
    public boolean moveFileTo(File srcFile, File destFile) {
        boolean iscopy = copyFileTo(srcFile, destFile);
        if (!iscopy)
            return false;
        delFile(srcFile.getAbsolutePath());
        return true;
    }

    public boolean moveFileTo(String srcStr, String destStr) {
        File srcFile = new File(srcStr);
        File destFile = new File(destStr);

        return moveFileTo(srcFile, destFile);
    }


    /**
     * 删除文件
     *
     * @param filePath String
     * @return boolean
     */
    public boolean delFileOrDir(String filePath) {
        File file = new File(filePath);

        delFile(file);


        File parent = file.getParentFile();

        if (parent != null && parent.exists() && parent.isDirectory()) {
            String[] list = parent.list();
            if (list != null && list.length == 0) {
                delFile(parent);
            }
        }

        return true;
    }

    /**
     * 删除文件
     *
     * @return
     */
    public boolean delFile(File file) {

        if (!file.exists()) {
            return false;
        }

        final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
        boolean isSuccess = file.renameTo(to);
        if (isSuccess) {
            return to.delete();
        }

        return file.delete();
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public boolean delFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);

        return delFile(file);
    }


    /**
     * 删除文件夹里的所有文件
     *
     * @param path String
     * @return boolean
     */
    public boolean delAllFile(String path) {


        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                delFile(temp);
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);
                delFolder(path + File.separator + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath String
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);

            File myFilePath = new File(folderPath);

            delFile(myFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createFile(File file) {


        try {
            if (file.exists()) {
                // file exists and *is* a directory
                if (file.isFile()) {
                    return;
                }

                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String byteToMB(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        float f = (float) size / mb;
        return String.format(Locale.CHINA, f > 100 ? "%.0f MB" : "%.1f MB", f);


//        if (size >= gb) {
//
//            return String.format(Locale.CHINA, "%.1f GB", (float) size / gb);
//        } else if (size >= mb) {
//            float f = (float) size / mb;
//            return String.format(Locale.CHINA, f > 100 ? "%.0f MB" : "%.1f MB", f);
//        } else if (size > kb) {
//            float f = (float) size / kb;
//            return String.format(Locale.CHINA, f > 100 ? "%.0f KB" : "%.1f KB", f);
//        } else {
//            return String.format(Locale.CHINA, "%d B", size);
//        }
    }


    public String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Set<String> getStoragePath(Context mContext) {
        Set<String> appReadableFolderList = new HashSet<>();

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);

        try {

            String[] arrayOfString = (String[]) mStorageManager.getClass().getMethod("getVolumePaths", new Class[0]).invoke(mStorageManager, new Object[0]);
            int i = 0;
            while (i < arrayOfString.length) {
                if ((((String) mStorageManager.getClass().getMethod("getVolumeState", new Class[]{String.class}).invoke(mStorageManager, new Object[]{arrayOfString[i]})).equals("mounted")) && (new File(arrayOfString[i]).canRead())) {
                    appReadableFolderList.add(arrayOfString[i]);
                }
                i += 1;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appReadableFolderList;
    }


    public long getFolderSize(File file) throws Exception {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }


    private long lastSizeTime;

    public long getFolderAvailableSizeByTime(String path) {

        if (System.currentTimeMillis() - lastSizeTime < 2000) {

            return 0;
        }

        lastSizeTime = System.currentTimeMillis();


        return getFolderAvailableSize(path);
    }


    public long getFolderAvailableSize(String path) {

        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        if (path.startsWith("/storage/emulated/0")) {
            path = "/storage/emulated/0";
        }
        if (path.startsWith("/storage/emulated/legacy")) {
            path = "/storage/emulated/legacy";
        }
        if (path.startsWith("/storage/sdcard0")) {
            path = "/storage/sdcard0";
        }
        if (path.startsWith("/storage/sdcard1")) {
            path = "/storage/sdcard1";
        }

        if (path.startsWith("/storage/sdcard2")) {
            path = "/storage/sdcard2";
        }
        if (path.startsWith("/storage/sdcard/")) {
            path = "/storage/sdcard/";
        }

        long blockSize = 0;
        long availableBlocks = 0;

        long size = 0;

        try {
            StatFs stat = new StatFs(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                blockSize = stat.getBlockSize();
                availableBlocks = stat.getAvailableBlocks();
            }
            size = availableBlocks * blockSize;
        } catch (Exception e) {
            e.printStackTrace();
            size = 0;
        }

        return size;
    }

}
