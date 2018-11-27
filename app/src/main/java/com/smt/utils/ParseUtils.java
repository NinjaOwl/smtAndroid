package com.smt.utils;

import com.smt.domain.APPVersion;
import com.smt.domain.Attachment;
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

    /** 获取APPVersion */
    public static APPVersion getAPPVersion(String result) {
        APPVersion appVersion = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            appVersion = new APPVersion();
            appVersion.code = jsonObject.optString("code");
            appVersion.msg = jsonObject.optString("msg");
            if (jsonObject.has("data")) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data").getJSONObject("version");
                appVersion.versionId = jsonObject1.optString("version_id");
                appVersion.versionCode = jsonObject1.optString("version_code");
                appVersion.versionContent = jsonObject1.optString("version_content");
                appVersion.versionURL = jsonObject1.optString("version_url");
                appVersion.fileSize = jsonObject1.optString("file_size");
                appVersion.isForce = false;
                if(jsonObject1.optInt("is_force") == 1)//强制是1
                    appVersion.isForce = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appVersion;
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

    /**
     * 获取工厂列表
     * {
     * 	"code": 200,
     * 	"msg": "请求成功",
     * 	"data": {
     * 		"list": [{
     * 			"res_id": "1",
     * 			"res_name": "机器1操作视频",
     * 			"res_desc": "机器1操作视频",
     * 			"res_suffix": "mp4",
     * 			"res_thumb": null,
     * 			"res_url": "http:\/\/www.baidu.com\/a.mp4"
     *                }],
     * 		"paging": {
     * 			"count": "1",
     * 			"page_count": 1,
     * 			"cur_page": 1,
     * 			"page_size": 10
     *        }    * 	}
     * }
     * */
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
    public static ArrayList<Resources> getResourcesList(String result) {
        ArrayList<Resources> resources = new ArrayList<Resources>();
        try {
            JSONArray circleList = new JSONArray(new JSONObject(result).getJSONObject("data").optString("list"));
            int length = circleList.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = circleList.optJSONObject(i);
                Resources resource = getResourcesFromList(jsonObject);
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
    public static Resources getResourcesFromList(JSONObject jsonObject) {
        if (jsonObject == null)
            return null;
        try {
            Resources resources = new Resources();
            resources.id = jsonObject.optString("res_id");
            resources.title = jsonObject.optString("res_name");
            resources.note = jsonObject.optString("res_desc");
            resources.thumbImageUrl = jsonObject.optString("res_thumb");
            resources.videoUrl = jsonObject.optString("res_url");
            resources.createTime = jsonObject.optString("res_date");
            return resources;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 解析ResourcesAttachment信息
     * @return
     */
    public static Resources getResourcesAttachment(String result) {
        Resources resources = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            resources = new Resources();
            resources.code = jsonObject.optString("code");
            resources.msg = jsonObject.optString("msg");

            JSONObject jsonRes = jsonObject.getJSONObject("data").getJSONObject("res");
            resources.id = jsonRes.optString("res_id");
            resources.title = jsonRes.optString("res_name");
            resources.note = jsonRes.optString("res_desc");
            resources.thumbImageUrl = jsonRes.optString("res_thumb");
            resources.videoUrl = jsonRes.optString("res_url");
            resources.createTime = jsonObject.optString("res_date");


            ArrayList<Attachment> attachments = new ArrayList<Attachment>();
            JSONArray attachmentsJSONArray = new JSONArray(new JSONObject(result).getJSONObject("data").optString("attachments"));
            int length = attachmentsJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonAttachment = attachmentsJSONArray.optJSONObject(i);
                Attachment attachment = new Attachment();
                attachment.id = jsonAttachment.optString("attach_id");
                attachment.name = jsonAttachment.optString("attach_name");
                attachment.desc = jsonAttachment.optString("attach_desc");
                attachment.suffix = jsonAttachment.optString("attach_suffix");
                attachment.url = jsonAttachment.optString("attach_url");
                attachments.add(attachment);
            }
            resources.attachments = attachments;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resources;
    }

    /** 总页数 */
    public static int getPageCount(String result) {
        int pageCount = 99;
        try {
            JSONObject jsonObject = new JSONObject(result);
            pageCount = jsonObject.getJSONObject("data").getJSONObject("paging").optInt("page_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pageCount;
    }
}