package com.smt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smt.R;
import com.smt.dialog.WaitDialog;
import com.smt.inter.InstallListener;

import java.io.File;

public class BaseInstallActivity extends BaseActivity implements InstallListener {

    //安装应用的流程
    protected void installProcess(File apk) {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startInstallPermissionSettingActivity();
                }
                return;
            }
        }
        //有权限，开始安装应用程序
        installApk(apk);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10086){
            installProcess(new File(downPath));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
    }

    //安装应用
    protected void installApk(File apk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        } else {//Android7.0之后获取uri要用contentProvider
            Uri uri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", apk);//android 7.0以上
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }

    String downPath = "";
    @Override
    public void downSuccess(String downPath) {
        this.downPath = downPath;
        installProcess(new File(downPath));
    }
}
