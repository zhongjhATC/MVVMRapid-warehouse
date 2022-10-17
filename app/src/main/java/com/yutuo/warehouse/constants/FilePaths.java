package com.yutuo.warehouse.constants;

import android.content.Context;

import java.io.File;

/**
 * 文件名称
 * <p>
 * 外部存储
 * context.getExternalFilesDir(dir)	路径为:/mnt/sdcard/Android/data/< package name >/files/… 
 * context.getExternalCacheDir()	路径为:/mnt/sdcard//Android/data/< package name >/cach/…
 *  内部存储
 * context.getFilesDir()	路径是:/data/data/< package name >/files/…
 * context.getCacheDir()	路径是:/data/data/< package name >/cach/…
 *
 * 直接创建文件：
 * String dirPath = context.getExternalFilesDir(null).getAbsolutePath();
 * String filePath = direPath + "/recording";
 * audioFile = new File(filePath);
 *
 * getExternalStorageDirectory 已经舍弃
 *
 *
 * @author zhongjh
 * @date 2021/5/13
 */
public class FilePaths {

    /**
     * 记录日志文件
     *
     * @param context 上下文
     * @return 文件路径
     */
    public static File log(Context context) {
        return context.getExternalFilesDir(File.separator + "log" + File.separator);
    }

}
