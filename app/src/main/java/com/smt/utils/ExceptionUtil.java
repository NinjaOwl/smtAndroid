package com.smt.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 获取 异常的栈信息
 * Created by Administrator on 2016/8/29.
 */
public class ExceptionUtil {


    public static String getStackTrace(Exception e)

    {
        try {

            if (e != null) {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));

                return "\n" + baos.toString();
            }
            return "Exception e is null!";

        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return "Exception Exception Exception!";

    }

    public static String getStackTrace(Throwable ex)

    {
        try {
            if (ex != null) {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ex.printStackTrace(new PrintStream(baos));

                return "\n" + baos.toString();
            }
            return "Throwable ex is null!";

        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return "Exception Exception Exception!";

    }
}
