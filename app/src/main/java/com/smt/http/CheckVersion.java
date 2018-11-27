package com.smt.http;

import android.content.Context;

import com.smt.activity.LoginActivity;
import com.smt.config.SMTApplication;
import com.smt.domain.APPVersion;
import com.smt.inter.DownManager;
import com.smt.inter.InstallListener;
import com.smt.utils.LogUtils;
import com.smt.utils.ParseUtils;

import java.io.IOException;

import okhttp3.Request;

public class CheckVersion {
    public static void checkVersion(final Context context, final InstallListener installListener){
        NetRequest.postFormRequest(SMTURL.APP_VERSION, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                APPVersion appVersion = ParseUtils.getAPPVersion(result);
                LogUtils.println("appVersion",appVersion.toString());
                LogUtils.println("versionName",SMTApplication.getVersionName());
                if(!SMTApplication.getVersionName().equals(appVersion.versionCode)){
                    DownManager downManagerApk = new DownManager(context,installListener);
//                    String downUrlApk = "http://58.63.233.48/app.znds.com/down/20170712/ystjg_2.6.0.1059_dangbei.apk";
//                    downManagerApk.downSatrt(downUrlApk, SMTApplication.getRootDir() + "/smt.apk", appVersion.versionContent);
                    downManagerApk.downSatrt(appVersion.versionURL, SMTApplication.getRootDir() + "/smt.apk", appVersion.versionContent);
                }
            }
            @Override
            public void requestFailure(Request request, IOException e) {
            }
        });
    }
}
