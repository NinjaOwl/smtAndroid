package com.smt.utils;

import com.smt.domain.BaseResult;
import com.smt.domain.Factory;
import com.smt.domain.Resources;
import com.smt.domain.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 解析返回的信息
 */
public class ParseUtils implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -894205250398423721L;

        public static BaseResult getResult(String result) {
            BaseResult baseResult = null;
        try {
            baseResult = new BaseResult();
            JSONObject jsonObject = new JSONObject(result);
            baseResult.code = jsonObject.optString("code");
            baseResult.msg = jsonObject.optString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return baseResult;
    }
    /** 获取用户 */
    public static UserInfo getUser(String result) {
        UserInfo user = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            user = new UserInfo();
            user.code = jsonObject.optString("code");
            user.msg = jsonObject.optString("msg");
            if (jsonObject.has("data")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data").getJSONObject("user");
                user.id = jsonObject1.optString("id");
                user.username = jsonObject1.optString("username");
                user.name = jsonObject1.optString("name");
                user.sex = jsonObject1.optString("sex");
                user.factoryName = jsonObject1.optString("factory_name");
                user.factoryId = jsonObject1.optString("factory_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    /** 获取Token */
    public static String getToken(String result) {
        String token = "";
        try {
            JSONObject jsonObject = new JSONObject(result);
            token = jsonObject.getJSONObject("data").optString("app_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    /** 获取工厂列表 */
    public static ArrayList<Factory> getFactorys(String result) {
        ArrayList<Factory> factories = new ArrayList<Factory>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray circleList = new JSONArray(jsonObject.getJSONObject("data").optString("list"));
            int length = circleList.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject1 = circleList.optJSONObject(i);
                Factory factory = new Factory();
                factory.id = jsonObject1.optString("factory_id");
                factory.name = jsonObject1.optString("factory_name");
                factory.address = jsonObject1.optString("factory_address");
                factories.add(factory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factories;
    }


    /** 资源列表 */
    public static ArrayList<Resources> getResources(String result) {
        ArrayList<Resources> resources = new ArrayList<Resources>();
        try {
            JSONArray circleList = new JSONArray(new JSONObject(result).getJSONObject("data").optString("list"));
            int length = circleList.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = circleList.optJSONObject(i);
                Resources resource = getResources(jsonObject);
                if (resource != null)
                    resources.add(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resources;
    }


    /**
     * 解析单个Resources信息
     * @param jsonObject
     * @return
     */
    public static Resources getResources(JSONObject jsonObject) {
        if (jsonObject == null)
            return null;
        try {
            Resources resources = new Resources();
            resources.id = jsonObject.optString("res_id");
            resources.title = jsonObject.optString("res_name");
            resources.note = jsonObject.optString("res_desc");
            resources.thumbImageUrl = jsonObject.optString("res_thumb");
            resources.videoUrl = jsonObject.optString("res_url");
            return resources;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}