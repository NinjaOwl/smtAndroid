package com.smt.inter;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import com.smt.dialog.DownDialog;
import com.smt.utils.OpenFiles;

import java.io.File;

public class DownManager implements DownListener, DownDialog.DownDialogListener {

    Context context;
    DownDialog downDialog;
    DownUtil downUtil;

    public DownManager(Context context) {
        this.context = context;
        downDialog = new DownDialog(context);
        downDialog.setOnDialogClickListener(this);
        downUtil = new DownUtil(this);
    }

    public static final int DOWN_START = 0;
    public static final int DOWN_PROGRESS = DOWN_START + 1;
    public static final int DOWN_SUCCESS = DOWN_PROGRESS + 1;
    public static final int DOWN_FAILED = DOWN_SUCCESS + 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWN_START:
                    downDialog.updateView(0, "准备下载", 0);
                    break;
                case DOWN_PROGRESS:
                    int progress = msg.arg1;
                    long speed = (long) msg.obj;
                    downDialog.updateView(progress, "下载中", speed);
                    break;
                case DOWN_SUCCESS:
                    downDialog.updateView(100, "下载完成", 0);
                    downDialog.dissmiss();
                    Toast.makeText(context, "下载完成,文件路径"+downPath, Toast.LENGTH_SHORT).show();

                    openFile(downPath);

                    break;
                case DOWN_FAILED:
                    downDialog.updateView(0, "下载异常", 0);
                    break;
            }
        }
    };

    /**
     * 安装
     */
    private void installApk() {
        File apkFile = new File(downPath);
        if (!apkFile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


    /** 通过隐式意图调用系统安装程序安装APK */
    public void install(String path) {

        File file = new File(path );
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.smt.provider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public void openFile(String path){
        try {
            System.out.println("文件名---->"+path);
            Intent intent = OpenFiles.getFileIntent(context,path);
            context.startActivity(intent);
        }catch (ActivityNotFoundException e) {
            //没有安装第三方的软件会提示
            System.out.println("---->没有找到打开该文件的应用程序");
        } catch (Exception e) {
            //没有安装第三方的软件会提示
            System.out.println("---->其他问题");
        }
    }


    /***
     * 开始下载
     * @param downUrl
     * 下载地址
     * @param downPath
     * 保存的地址
     * @param desc
     * dialog显示的描述
     */
    String downUrl;
    String downPath;

    public void downSatrt(String downUrl, String downPath, String desc) {
        this.downUrl = downUrl;
        this.downPath = downPath;
        downDialog.show(desc);
    }

    @Override
    public void downStart() {
        handler.sendEmptyMessage(DOWN_START);
    }

    @Override
    public void downProgress(int progress, long speed) {
        Message msg = new Message();
        msg.what = DOWN_PROGRESS;
        msg.arg1 = progress;
        msg.obj = speed;
        handler.sendMessage(msg);
    }

    @Override
    public void downSuccess(String downUrl) {
        handler.sendEmptyMessage(DOWN_SUCCESS);

    }

    @Override
    public void downFailed(String failedDesc) {
        handler.sendEmptyMessage(DOWN_FAILED);
    }

    @Override
    public void noSure() {
        downUtil.cacleDown();
    }

    @Override
    public void sure() {
        downUtil.downFile(downUrl, downPath);
    }
}
