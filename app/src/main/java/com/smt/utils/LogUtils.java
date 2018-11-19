package com.smt.utils;


import android.text.TextUtils;

import java.io.File;

/**
 * 日志输出
 */
public class LogUtils {

    public static void println(String msgTag,String msg) {
        if(TextUtils.isEmpty(msgTag))
            System.out.println(msgTag+"--->"+msg);
        else
            System.out.println(msgTag+"：--->"+msg);
    }
}
