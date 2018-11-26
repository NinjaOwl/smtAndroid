package com.smt.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smt.R;
import com.smt.config.SMTApplication;
import com.smt.domain.Resources;
import com.smt.inter.DownManager;
import com.smt.utils.LogUtils;
import com.smt.utils.OpenFiles;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class ResourcesActivity extends BaseActivity {
    @BindView(R.id.jz_video)
    JzvdStd jzVideo;
    @BindView(R.id.note)
    TextView noteTV;
    @BindView(R.id.attachment_title)
    TextView attachmentTitleTV;
    @BindView(R.id.download_attachment)
    Button downloadAttachment;
    @BindView(R.id.downloadApk)
    Button downloadApk;

    String id = "1";
    String title = "";
    String createTime = "2018.11.12";
    String videoUrl = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
    String thumbImageUrl = "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png";
    String attachmentTitle = "附件标题";
    String attachmentSuffix = "png";
    String attachmentUrl = "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png";
    String note = "此视频拍摄于2018年12月12日，这块需要着重关注，一定要注意生产安全。";

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        ButterKnife.bind(this);
        showTop("资源详情",true);

        resources = (Resources)getIntent().getParcelableExtra("resources");
        LogUtils.println("resources",resources.toString());

//        resources = new Resources(id,title,createTime,videoUrl,thumbImageUrl,
//                attachmentTitle,attachmentSuffix,attachmentUrl,note);

        jzVideo.setUp(resources.videoUrl,resources.title, JzvdStd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load(resources.thumbImageUrl).into(jzVideo.thumbImageView);

        downloadAttachment.setOnClickListener(this);

        if("".equals(resources.attachmentTitle)){
            downloadAttachment.setVisibility(View.GONE);
            attachmentTitleTV.setText("暂无附件");
        }
        noteTV.setText(resources.note);
        if("".equals(resources.attachmentTitle)){
            noteTV.setText("暂无说明");
        }

        downloadApk.setVisibility(View.GONE);
        downloadApk.setOnClickListener(this);
    }

    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.download_attachment:
                String downUrl = resources.attachmentUrl;
                String downPath = SMTApplication.getRootDir() +resources.attachmentTitle+"."+resources.attachmentSuffix;
                DownManager downManager = new DownManager(this);
                downManager.downSatrt(downUrl, downPath, "是否下载文件");
                break;
            case R.id.downloadApk:
                String downUrlApk = "http://58.63.233.48/app.znds.com/down/20170712/ystjg_2.6.0.1059_dangbei.apk";
                String downPathApk = SMTApplication.getRootDir() + "/tengxun.apk";
                DownManager downManagerApk = new DownManager(ResourcesActivity.this);
                downManagerApk.downSatrt(downUrlApk, downPathApk, "是否下载文件");

//                installProcess(new File(downPathApk));
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

}
