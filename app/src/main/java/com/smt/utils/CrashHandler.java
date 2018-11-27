package com.smt.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.smt.config.SMTApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * Log日志的tag
     * String	:	TAG
     */
    private static final String TAG = "CrashHandler";
    /**
     * 系统默认的UncaughtException处理类
     * Thread.UncaughtExceptionHandler	: mDefaultHandler
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     * CrashHandler			:		mInstance
     */
    private static CrashHandler mInstance = new CrashHandler();
    /**
     * 程序的Context对象
     * Context			:		mContext
     */
    private Context mContext;
    /**
     * 用来存储设备信息和异常信息
     * Map<String,String>			:		mLogInfo
     *
     * @since 2013-3-21下午8:46:15
     */
    private Map<String, String> mLogInfo = new HashMap<>();
    /**
     * 用于格式化日期,作为日志文件名的一部分(FIXME 注意在windows下文件名无法使用：等符号！)
     * SimpleDateFormat			:		mSimpleDateFormat
     *
     * @since 2013-3-21下午8:46:39
     */
    private SimpleDateFormat mSimpleDateFormat = TimeUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * Creates a new instance of CrashHandler.
     */
    private CrashHandler() {
    }

    /**
     * getInstance:{获取CrashHandler实例 ,单例模式 }
     *
     * @return CrashHandler
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
     * 2013-3-21下午8:52:24	Modified By Norris
     */
    public static CrashHandler getInstance() {
        return mInstance;
    }

    /**
     * init:{初始化}
     *
     * @param paramContext
     * @return void
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
     */
    public void init(Context paramContext) {
        mContext = paramContext;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该重写的方法来处理
     * (non-Javadoc)
     *
     * @see UncaughtExceptionHandler#uncaughtException(Thread, Throwable)
     */
    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {

        //提交緩存数据到本地文件
        FileLogUtil.commit();

        //保存错误信息到本地文件
        handleException(paramThrowable);

        //调用系统
        mDefaultHandler.uncaughtException(paramThread, paramThrowable);


//        if (!handleException(paramThrowable) && mDefaultHandler != null) {
//            // 如果自定义的没有处理则让系统默认的异常处理器来处理
//            mDefaultHandler.uncaughtException(paramThread, paramThrowable);
//        } else {
//
//
////            //生产环境开启自动启动
////            if (!Constants.isDebugPackage) {
////                /**
////                 * 自动重启3次！（避免因欢迎页 崩溃导致无限重启！）
////                 */
////                int crashRestartTimes = sharedPreferences.getInt("crash_restart_times", 0);
////                EcgLog.e(TAG, "crashRestartTimes:" + crashRestartTimes);
////                if (crashRestartTimes < 2) {
////                    sharedPreferences.edit().putInt("restart_time", ++crashRestartTimes).commit();
////
////                    //1秒后重启程序
////                    Intent intent = new Intent();
////                    intent.setClass(mContext, WelcomeActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    PendingIntent restartIntent =
////                            PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
////                    AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
////                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
////
////                } else {
////                    sharedPreferences.edit().putInt("crash_restart_times", 0).commit();
////                }
////            }
//
//
//            //自杀
//            try {
//                //提交緩存数据到本地文件
//                FileLogUtil.commit();
//
//                //发送错误报告到友盟
//                MobclickAgent.reportError(mContext, paramThrowable);
//
//                //程序异常崩溃前保存数据
//                MobclickAgent.onKillProcess(mContext);
//
//                AppManager.getAppManager().killProcess();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * handleException:{自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.}
     *
     * @param paramThrowable
     * @return true:如果处理了该异常信息;否则返回false.
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
     */
    public boolean handleException(Throwable paramThrowable) {
        if (paramThrowable == null)
            return false;

        //生产环境给及提示信息
//        if (!Constants.isDebugPackage) {
//            new Thread() {
//                public void run() {
//                    Looper.prepare();
//                    EcgToast.makeText(mContext, mContext.getString(R.string.crash_text), Toast.LENGTH_SHORT).show();
//                    Looper.loop();
//                }
//            }.start();
//        }

        // 获取设备参数信息
        getDeviceInfo(mContext);
        // 保存日志文件
        saveCrashLogToFile(paramThrowable);

        return true;
    }

    /**
     * getDeviceInfo:{获取设备参数信息}
     *
     * @param paramContext
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
     */
    public void getDeviceInfo(Context paramContext) {

        // 迭代Build的字段key-value  此处的信息主要是为了在服务器端手机各种版本手机报错的原因
        try {

            //获取版本号
            PackageInfo packageInfo = paramContext.getPackageManager().
                    getPackageInfo(paramContext.getPackageName(), 0);
            mLogInfo.put("versionName", packageInfo.versionName);
            mLogInfo.put("versionCode", packageInfo.versionCode + "");

            // 反射机制
            Field[] mFields = Build.class.getDeclaredFields();
            for (Field field : mFields) {
                field.setAccessible(true);
                mLogInfo.put(field.getName(), field.get("").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * saveCrashLogToFile:{将崩溃的Log保存到本地}
     * TODO 可拓展，将Log上传至指定服务器路径
     *
     * @param paramThrowable
     * @return FileName
     * @throws
     * @since I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
     */
    private String saveCrashLogToFile(Throwable paramThrowable) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mLogInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            mStringBuffer.append(key + "=" + value + "\r\n");
//            EcgLog.e(TAG, key + " : " + value);
        }
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        paramThrowable.printStackTrace(mPrintWriter);
//        EcgLog.je(TAG, paramThrowable);
        Throwable mThrowable = paramThrowable.getCause();
        // 迭代栈队列把所有的异常信息写入writer中
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            // 换行  每个个异常栈之间换行
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        //记得关闭
        mPrintWriter.close();
        String mResult = mWriter.toString();
        mStringBuffer.append(mResult);
        // 保存文件，设置文件名
        String mTime = mSimpleDateFormat.format(new Date());
        String mFileName = "crash_" + mTime + ".txt";

        try {
            File mDirectory = new File(SMTApplication.getCrashLogDir());
            if (!mDirectory.exists()) {
                mDirectory.mkdir();
            }
            FileOutputStream mFileOutputStream = new FileOutputStream(mDirectory + "/"
                    + mFileName);
            mFileOutputStream.write(mStringBuffer.toString().getBytes());
            mFileOutputStream.close();
            return mFileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
