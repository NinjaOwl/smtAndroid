package com.smt.config;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.view.WindowManager;


import com.smt.utils.CrashHandler;
import com.smt.utils.SMTUtils;

import java.io.File;

public class SMTApplication extends Application {

    /**
     * 根目录
     */
    private static String rootDir;
    /**
     * 缓存目录
     */
    private static String cachePic;
    /**
     * 上下文
     */
    private static Context mAppContext;
    /**
     * CrashLog目录
     */
    private static String crashLogDir;
    /**
     * attachment目录
     */
    private static String attachmentDir;
    /**
     * 版本号 800
     */
    private static int versionCode = 0;
    /**
     * 版本Name v2.4.0
     */
    private static String versionName = "";

    /**
     * 屏幕宽度
     */
    private static int screenWidth = 0;
    /**
     * 屏幕高度
     */
    private static int screenHeight = 0;

    /**
     * 返回屏幕宽度
     */
    public static int getScreenWidth() {
        return screenWidth;
    }

    /**
     * 返回屏幕高度
     */
    public static int getScreenHeight() {
        return screenHeight;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //必须在最前面初始化
        mAppContext = this.getApplicationContext();
        /** ============= 初始化文件路劲 ============== */
        initFilePath();
        /** =============  初始化全局异常捕捉器 ============== */
        CrashHandler.getInstance().init(this);

        /** ============= 获取设备信息 ============== */
        {
            try {
                //获取版本号
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionCode = packageInfo.versionCode;
                versionName = packageInfo.versionName;

                WindowManager wm = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
                screenWidth = wm.getDefaultDisplay().getWidth();// 屏幕宽度
                screenHeight = wm.getDefaultDisplay().getHeight();// 屏幕高度
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //系统路劲初始化
    private void initFilePath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {   // 创建一个文件夹对象，赋值为外部存储器的目录
            rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            rootDir = getCacheDir().getAbsolutePath();
        }
        //根目录
        rootDir = rootDir + "/smt/";
        cachePic = rootDir + "cachePic/";
        crashLogDir = rootDir + "crashLogDir/";
        attachmentDir = rootDir + "attachmentDir/";
        createFileDir();
    }

    public static void createFileDir() {
        SMTUtils.createFileDir(rootDir);
        SMTUtils.createFileDir(cachePic);
        SMTUtils.createFileDir(crashLogDir);
        SMTUtils.createFileDir(attachmentDir);
    }


    public static int getVersionCode() {
        return versionCode;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static String getVersion() {
        return versionName + "(" + versionCode + ")";
    }

    public static String getRootDir() {
        return rootDir;
    }

    public static String getCachePic() {

        if (!TextUtils.isEmpty(cachePic)) {
            File file = new File(cachePic);
            if (!file.exists())
                file.mkdirs();
        }

        return cachePic;
    }

    public static Context getSMTContext() {
        return mAppContext;
    }

    public static String getCrashLogDir() {
        return crashLogDir;
    }

    public static String getAttachmentDirDir() {
        return attachmentDir;
    }
}