package com.smt.http;

import java.util.HashMap;

public class SMTURL {
    public static final String URL = "http://47.98.205.118/";
    /** 登录 */
    public static String LOGIN = URL+"v1/auth/login";
    public static HashMap<String, String> loginParams(String username,String password){
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("username", username);
        params.put("password", password);
        return params;
    }

    /** 自动登录 */
    public static String LOGIN_AUTO = URL+"v1/auth/get";


    /** 工厂列表 */
    public static String FACTORY_LIST = URL+"v1/factory/list";


    /** 资源列表 */
    public static String RESOURCE_LIST = URL+"v1/res/list";

    public static HashMap<String, String> resourceListParams(String factoryId,String resName,String page){
        HashMap<String, String> params = new HashMap<>();
        params.put("factory_id", factoryId);
        params.put("res_name", resName);
        params.put("page", page);//页码
        params.put("max", "1");//每页显示条数
        return params;
    }

    /** 资源信息 */
    public static String RESOURCE_INFO = URL+"v1/res/get";

    public static HashMap<String, String> resourceInfoParams(String resId){
        HashMap<String, String> params = new HashMap<>();
        params.put("res_id", resId);
        return params;
    }

    /** 版本更新 */
    public static String APP_VERSION = URL+"v1/app/get";
}
