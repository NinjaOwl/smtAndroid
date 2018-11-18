package com.smt.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.smt.R;
import com.smt.config.SMTApplication;
import com.smt.domain.Resources;
import com.smt.http.NetRequest;
import com.smt.inter.DownManager;
import com.smt.utils.OpenFiles;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.Request;

public class ResourcesActivity extends BaseActivity {
    @BindView(R.id.jz_video)
    JzvdStd jzVideo;
    @BindView(R.id.getRequest)
    Button getRequest;
    @BindView(R.id.postRequest)
    Button postRequest;
    @BindView(R.id.download)
    Button download;

    String URL = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";

    @BindView(R.id.PDF)
    Button PDF;
    @BindView(R.id.docx)
    Button docx;
    @BindView(R.id.doc)
    Button doc;
    @BindView(R.id.xlsx)
    Button xlsx;
    @BindView(R.id.apk)
    Button apk;
    @BindView(R.id.ppt)
    Button ppt;
    @BindView(R.id.text)
    Button text;

    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        ButterKnife.bind(this);

        resources = new Resources("123","视频Title","http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4",
                "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png",
                "附件标题","http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png","备注说明");

        jzVideo.setUp("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4"
                , "视频", JzvdStd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(jzVideo.thumbImageView);


        getRequest.setOnClickListener(this);
        postRequest.setOnClickListener(this);
        download.setOnClickListener(this);



        PDF.setOnClickListener(this);
        docx.setOnClickListener(this);
        doc.setOnClickListener(this);
        xlsx.setOnClickListener(this);
        apk.setOnClickListener(this);
        ppt.setOnClickListener(this);
        text.setOnClickListener(this);
    }

    public void openFile(String path){
        try {
            System.out.println("文件名---->"+path);
            Intent intent = OpenFiles.getFileIntent(this,path);
            startActivity(intent);
        }catch (ActivityNotFoundException e) {
            //没有安装第三方的软件会提示
            System.out.println("---->没有找到打开该文件的应用程序");
        } catch (Exception e) {
            //没有安装第三方的软件会提示
            System.out.println("---->其他问题");
        }
    }
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.PDF:
                openFile(SMTApplication.getRootDir()+"步态分析与训练.pdf");
                break;
            case R.id.docx:
                openFile(SMTApplication.getRootDir()+"产品经理题.docx");
                break;
            case R.id.doc:
                openFile(SMTApplication.getRootDir()+"健康平台.xmind");
                break;
            case R.id.xlsx:
                openFile(SMTApplication.getRootDir()+"健康平台.xlsx");
                break;
            case R.id.apk:
                openFile(SMTApplication.getRootDir()+"6分钟步行.apk");
                break;
            case R.id.ppt:
                openFile(SMTApplication.getRootDir()+"上肢康复系统宣传资料.ppt");
                break;
            case R.id.text:
                openFile(SMTApplication.getRootDir()+"2018.10.22故障解决.txt");
                break;
            case R.id.getRequest:
                get();
//                String url = "http://wwww.baidu.com";
//                OkHttpClient okHttpClient = new OkHttpClient();
//                final Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//                final Call call = okHttpClient.newCall(request);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Response response = call.execute();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();

                break;
            case R.id.postRequest:
                post();
                break;
            case R.id.download:
                String rootDir = "";
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {   // 创建一个文件夹对象，赋值为外部存储器的目录
                    rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                } else {
                    rootDir = getCacheDir().getAbsolutePath();
                }
                rootDir = rootDir + "/mhealth365/snapecguser/";
                String downUrl = "http://58.63.233.48/app.znds.com/down/20170712/ystjg_2.6.0.1059_dangbei.apk";
                String downPath = rootDir + "/tengxun.apk";
                DownManager downManager = new DownManager(ResourcesActivity.this);
                downManager.downSatrt(downUrl, downPath, "是否下载《腾讯视频》");
                break;
        }
    }

    public void get() {
        String url = "http://wwww.baidu.com";
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("key", "value");
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                System.out.println("----> " + "run: " + result);
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                System.out.println("----> " + "onFailure: " + e.getMessage());
            }
        });
    }

    public void post() {

        String url = "http://47.98.205.118/docs/#api-auth-_v1_auth_login";
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("user_name", "123");
        params.put("password", "123");
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                System.out.println("----> " + "run: " + result);
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                System.out.println("----> " + "onFailure: " + e.getMessage());
            }
        });


//        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = new FormBody.Builder()
//                .add("search", "Jurassic Park")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://en.wikipedia.org/w/index.php")
//                .post(requestBody)
//                .build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("onResponse---->" + response.protocol() + " " +response.code() + " " + response.message());
//                Headers headers = response.headers();
//                for (int i = 0; i < headers.size(); i++) {
//                    System.out.println("----> "+headers.name(i) + ":" + headers.value(i));
//                }
//
//                System.out.println("----> onResponse: " + response.body().string());
//            }
//        });

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
