package com.smt.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.smt.domain.UserInfo;

public class Preference {

    /**
     * 默认preference文件名称
     */
    public static String PREFERENCE_NAME = "settings";


    /********************************** over *************************************************/


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static SharedPreferences getSMTSP() {
        if (sharedPreferences == null)
            sharedPreferences = SMTApplication.getSMTContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEcgEdit() {
        if (editor == null)
            editor = getSMTSP().edit();
        return editor;
    }

    public static void putString(String key, String value) {
        getEcgEdit().putString(key, value).apply();
    }

    public static boolean putStringNow(String key, String value) {
        return getEcgEdit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return getSMTSP().getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getSMTSP().getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        getEcgEdit().putInt(key, value).apply();
    }

    public static boolean putIntNow(String key, int value) {
        return getEcgEdit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return getSMTSP().getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return getSMTSP().getInt(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        getEcgEdit().putLong(key, value).apply();
    }

    public static boolean putLongNow(String key, long value) {
        return getEcgEdit().putLong(key, value).commit();
    }

    public static long getLong(String key) {
        return getSMTSP().getLong(key, -1);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getSMTSP().getLong(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        getEcgEdit().putFloat(key, value).apply();
    }

    public static boolean putFloatNow(String key, float value) {
        return getEcgEdit().putFloat(key, value).commit();
    }

    public static float getFloat(String key) {
        return getSMTSP().getFloat(key, -1);
    }

    public static float getFloat(String key, float defaultValue) {
        return getSMTSP().getFloat(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        getEcgEdit().putBoolean(key, value).apply();
    }

    public static boolean putBooleanNow(String key, boolean value) {
        return getEcgEdit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return getSMTSP().getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSMTSP().getBoolean(key, defaultValue);
    }

    public static String TOKEN = "token";

    private static UserInfo staticUserInfo;

    public synchronized static void setUser(UserInfo userInfo) {
        if (userInfo == null)
            return;

        staticUserInfo = userInfo;
        getEcgEdit()
                .putString("user_id", userInfo.id)
                .putString("user_phone", userInfo.username)
                .putString("user_name", userInfo.name)
                .putString("user_sex", userInfo.sex)
                .putString("user_factoryName", userInfo.factoryName)
                .putString("user_factoryId", userInfo.factoryId)
                .commit();
    }

    public static UserInfo getUser() {

        if (staticUserInfo == null) {
            staticUserInfo = new UserInfo();
            staticUserInfo.id = getString("id", "");
            staticUserInfo.username = getString("username", "");
            staticUserInfo.name = getString("name", "");
            staticUserInfo.sex = getString("sex", "0");
            staticUserInfo.factoryName = getString("factoryName", "");
            staticUserInfo.factoryId = getString("factoryId", "");
        }
        return staticUserInfo;
    }

    /**
     * 清除 登录信息密码和uid、token
     */
    public static void clearUserInfo() {
        setUser(new UserInfo());
    }


}