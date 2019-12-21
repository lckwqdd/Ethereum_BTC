package com.mirko.androidutil.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Mirko on 2018/3/15.
 * 文件工具类
 */

public class FileUtils {


    public static FileUtils instance;

    private Context context;

    public static FileUtils getInstance() {
        if (instance == null) {
            synchronized (FileUtils.class) {
                if (instance == null) {
                    instance = new FileUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    /**
     * 获取根目录
     */
    public static File getRootDir() {

        if (FileUtils.instance.context == null) return null;
        File dir = new File(FileUtils.instance.context.getFilesDir().getParentFile(), "/futon");
        if (!dir.exists())
            dir.mkdirs();
        return dir;
    }

    /**
     * 图片存储目录
     */
    public static File getImageDir() {
        File imageDir = new File(getRootDir().getAbsolutePath() + "/images/");
        if (!imageDir.exists()) imageDir.mkdirs();
        return imageDir;
    }

    /**
     * 数据库存储目录
     */
    public static File getDbDir() {
        File dbDir = new File(getRootDir().getAbsolutePath() + "/databases/");
        if (!dbDir.exists()) dbDir.mkdirs();
        return dbDir;
    }

    /**
     * 日志存储目录
     */
    public static File getLogDir() {
        File logDir = new File(getRootDir().getAbsolutePath() + "/logs/");
        if (!logDir.exists()) logDir.mkdirs();
        return logDir;
    }

    /**
     * 同步日志存储目录
     */
    public static File getSyncLogDir() {
        File logDir = new File(getRootDir().getAbsolutePath() + "/logs/sync/");
        if (!logDir.exists()) logDir.mkdirs();
        return logDir;
    }

    /**
     * 文件存储目录
     */
    public static File getNormalFilesDir() {
        File filesDir = new File(getRootDir().getAbsolutePath() + "/files/");
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        return filesDir;
    }


    /**
     * 创建根缓存目录
     *
     * @return
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
//        LogUtils.d("test","返回路径："+cacheRootPath);
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 格式化文件大小，根据文件大小不同使用不同单位
     *
     * @param size 文件大小
     * @return 字符串形式的大小，包含单位(B,KB,MB,GB,TB,PB)
     */
    public static String formatFileSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024L) {
            return size + " B";
        } else if (size < 1048576L) {
            return formater.format(size / 1024f) + " KB";
        } else if (size < 1073741824L) {
            return formater.format(size / 1048576f) + " MB";
        } else if (size < 1099511627776L) {
            return formater.format(size / 1073741824f) + " GB";
        } else if (size < 1125899906842624L) {
            return formater.format(size / 1099511627776f) + " TB";
        } else if (size < 1152921504606846976L) {
            return formater.format(size / 1125899906842624f) + " PB";
        }
        return "size: out of range";
    }

    /**
     * 从路径中获取文件名，包含扩展名
     *
     * @param path 路径
     * @return 如果所传参数是合法路径，截取文件名，如果不是返回原值
     */
    public static String getFileName(String path) {
        if (path != null && (path.contains("/") || path.contains("\\"))) {
            String fileName = path.trim();
            int beginIndex;
            if ((beginIndex = fileName.lastIndexOf("\\")) != -1) {
                fileName = fileName.substring(beginIndex + 1);
            }
            if ((beginIndex = fileName.lastIndexOf("/")) != -1) {
                fileName = fileName.substring(beginIndex + 1);
            }
            return fileName;
        }
        return path;
    }

    /**
     * 从路径中获取文件名，不包含扩展名
     *
     * @param path 路径
     * @return 如果所传参数是合法路径，截取文件名，如果不是返回原值
     */
    public static String getFileNameWithoutSuffix(String path) {
        if (path != null && (path.contains("/") || path.contains("\\"))) {
            String fileName = path.trim();
            int beginIndex;
            if ((beginIndex = fileName.lastIndexOf("\\")) != -1) {
                fileName = fileName.substring(beginIndex + 1);
            }
            if ((beginIndex = fileName.lastIndexOf("/")) != -1) {
                fileName = fileName.substring(beginIndex + 1);
            }
            return deleteSuffix(fileName);
        }
        return deleteSuffix(path);
    }

    /**
     * 获取扩展名
     *
     * @param s 路径或后缀
     * @return 不存在后缀时返回null
     */
    public static String getSuffix(String s) {
        if (s.contains(".")) {
            return s.substring(s.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 返回去掉扩展名的文件名
     */
    public static String deleteSuffix(String fileName) {
        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * 检查是否有同名文件，有则在自动在文件名后加当前时间的毫秒值
     */
    public static File checkAndRename(File target) {
        String fileName = target.getName();
        if (target.exists()) {
            if (fileName.contains(".")) {
                String sub = fileName.substring(0, fileName.lastIndexOf("."));
                fileName = fileName.replace(sub, sub + "_" + System.currentTimeMillis());
            } else {
                fileName = fileName + "_" + System.currentTimeMillis();
            }
            return new File(target.getParent(), fileName);
        }
        return target;
    }

    /**
     * 移动文件或文件夹
     *
     * @param src     要移动的文件或文件夹
     * @param target  目标文件或文件夹。类型需与源相同，如源为文件，则目标也必须是文件
     * @param replace 当有重名文件时是否替换。传false时，自动在原文件名后加上当前时间的毫秒值
     * @return 移动成功返回true, 否则返回false
     */
    public static boolean moveFile(File src, File target, boolean replace) {
        if (src == null || !src.exists() || target == null) {
            return false;
        }
        if (!replace) {
            target = checkAndRename(target);
        }
        copy(src, target);

        //如果文件存在，并且大小与源文件相等，则写入成功，删除源文件
        if (src.isFile()) {
            if (target.exists() && target.length() == src.length()) {
                src.delete();
                return true;
            }
        } else {
            if (getDirSize(src) == getDirSize(target)) {
                delelteDir(src, true);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除文件夹
     *
     * @param dir         文件夹
     * @param includeSelf 是否包括本身
     */
    public static void delelteDir(File dir, boolean includeSelf) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    delelteDir(f, true);
                } else {
                    f.delete();
                }
            }
        }
        if (includeSelf)
            dir.delete();
    }

    /**
     * 获取文件夹的大小
     *
     * @param dir 目录
     * @return 所传参数是目录且存在，则返回文件夹大小，否则返回-1
     */
    public static long getDirSize(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            long size = 0;
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        size += getDirSize(file);
                    } else {
                        size += file.length();
                    }
                }
                return size;
            }
            return 0;
        }
        return -1;
    }

    /**
     * 快速复制文件
     *
     * @param source 源文件
     * @param target 目标文件
     */
    public static void nioCopyFile(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close(in, out, inStream, outStream);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 关闭一个或多个流对象
     *
     * @param closeables 可关闭的流对象列表
     */
    public static void close(Closeable... closeables) throws IOException {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        }
    }

    /*
     * 复制文件夹
     * @param sourceDir 源文件夹
     * @param targetDir 目标文件夹
     */
    private static void copyDir(File sourceDir, File targetDir) {
        //目标目录新建源文件夹
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        // 获取源文件夹当前下的文件或目录
        File[] files = sourceDir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                nioCopyFile(file, new File(targetDir, file.getName()));
            } else {
                copyDir(file, new File(targetDir, file.getName()));
            }
        }
    }

    /**
     * 复制文件或文件夹
     *
     * @param src    源文件或文件夹
     * @param target 目标文件或文件夹
     */
    public static void copy(String src, String target) {
        copy(new File(src), new File(target));
    }

    /**
     * 复制文件或文件夹
     *
     * @param src    源文件或文件夹
     * @param target 目标文件或文件夹。类型需与源相同，如源为文件，则目标也必须是文件
     */
    public static void copy(File src, File target) {
        if (src.isFile()) {
            nioCopyFile(src, target);
        } else {
            copyDir(src, target);
        }
    }

    /**
     * 去掉字符串中重复部分字符串
     *
     * @param dup  重复部分字符串
     * @param strs 要去重的字符串
     * @return 按参数先后顺序返回一个字符串数组
     */
    public static String[] removeDuplicate(String dup, String... strs) {
        for (int i = 0; i < strs.length; i++) {
            if (strs[i] != null) {
                strs[i] = strs[i].replaceAll(dup + "+", "");
            }
        }
        return strs;
    }

    /**
     * 获取随机UUID文件名
     *
     * @param fileName 原文件名
     * @return 生成的文件名
     */
    public static String generateRandonFileName(String fileName) {
        // 获得扩展名
        int beginIndex = fileName.lastIndexOf(".");
        String ext = "";
        if (beginIndex != -1) {
            ext = fileName.substring(beginIndex);
        }
        return UUID.randomUUID().toString() + ext;
    }

    /**
     * 删除文件夹
     *
     * @param file 文件夹
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean delelteDirectory(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    delelteDirectory(f);
                } else {
                    f.delete();
                }
            }
        }

        return file.delete();
    }

    /**
     * 获取文件夹的大小
     *
     * @param dir 目录
     * @return 所传参数是目录且存在，则返回文件夹大小，否则返回-1
     */
    public static long getDirectorySize(File dir) {
        if (dir.isDirectory() && dir.exists()) {
            long size = 0;
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        size += getDirectorySize(file);
                    } else {
                        size += file.length();
                    }
                }
                return size;
            }
            return 0;
        }
        return -1;
    }

    /**
     * 根据文件路径加载bitmap
     *
     * @param path 文件绝对路径
     */
    public static Bitmap getBitmap(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件路径加载bitmap
     *
     * @param path 文件绝对路径
     * @param w    宽
     * @param h    高
     */
    public static Bitmap getBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为ture只获取图片大小
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            // 返回为空
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                // 缩放
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<>(BitmapFactory.decodeFile(path, opts));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存bitmap到文件
     *
     * @param photoFile 文件
     */
    public static void saveBitmapToFile(Bitmap bitmap, File photoFile) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(photoFile);
            if (bitmap != null) {
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                    fileOutputStream.flush();
                }
            }
        } catch (Exception e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩数据
     */
    public static byte[] compress(byte[] data) {
        GZIPOutputStream gzip = null;
        ByteArrayOutputStream baos = null;
        byte[] newData = null;
        try {
            baos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(baos);

            gzip.write(data);
            gzip.finish();
            gzip.flush();

            newData = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (gzip != null) gzip.close();
                if (baos != null) baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return newData;
    }

    /**
     * 压缩文件
     *
     * @param source 源文件
     * @param target 目标文件
     * @throws IOException
     */
    public static void compressFile(File source, File target) throws IOException {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        GZIPOutputStream gzout = null;
        try {
            fin = new FileInputStream(source);
            fout = new FileOutputStream(target);
            gzout = new GZIPOutputStream(fout);
            byte[] buf = new byte[1024];
            int num;
            while ((num = fin.read(buf)) != -1) {
                gzout.write(buf, 0, num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (gzout != null)
                gzout.close();
            if (fout != null)
                fout.close();
            if (fin != null)
                fin.close();
        }
    }


    /**
     * 二、从resource中的raw文件夹中获取文件并读取数据（资源文件只能读不能写）
     *
     * @param fileInRaw
     * @return
     */
    public static String readFromRaw(Context context, int fileInRaw) {
        String res = "";
        try {
            InputStream in = context.getResources().openRawResource(fileInRaw);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
//            res = EncodingUtils.getString(buffer, "GBK");
             res = new String(buffer,"GBK");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 读取文件内容
     *
     * @return
     * @throws IOException
     */
    public static String readSDFile(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte[] buffer = new byte[length];
        fis.read(buffer);

//        String res = EncodingUtils.getString(buffer, "UTF-8");
        String res = new String(buffer,"GBK");
        fis.close();
        return res;
    }
}
