package com.smt.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smt.R;
import com.smt.adapter.AttachmentAdapter;
import com.smt.domain.Attachment;
import com.smt.domain.Resources;
import com.smt.http.NetRequest;
import com.smt.http.SMTURL;
import com.smt.utils.LogUtils;
import com.smt.utils.ParseUtils;
import com.smt.view.ListViewForScrollView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.Request;

public class ResourcesActivity extends BaseActivity {
    @BindView(R.id.jz_video)
    JzvdStd jzVideo;
    @BindView(R.id.note)
    TextView noteTV;
    @BindView(R.id.attachment)
    TextView attachment;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.listview)
    ListViewForScrollView listView;
    private AttachmentAdapter attachmentAdapter;
    private ArrayList<Attachment> arrayList = new ArrayList<Attachment>();

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        ButterKnife.bind(this);
        showTop("资源详情",true);

        resources = (Resources)getIntent().getParcelableExtra("resources");
        LogUtils.println("resources",resources.toString());

        jzVideo.setUp(resources.videoUrl,resources.title, JzvdStd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load(resources.thumbImageUrl).into(jzVideo.thumbImageView);

        attachmentAdapter = new AttachmentAdapter(this);
        listView.setAdapter(attachmentAdapter);

        noteTV.setText(resources.note);
        if("".equals(resources.note)){
            noteTV.setText("暂无说明");
        }

        getResourcesAttachment();
    }


    public static final int MSG_ATTACHMENT_SUCCESS = 1;
    public static final int MSG_ATTACHMENT_FAIL = 2;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ATTACHMENT_SUCCESS:
                    resources = ParseUtils.getResourcesAttachment(msg.obj+"");
                    if(resources.attachments.size() == 0){
                        attachment.setVisibility(View.VISIBLE);
                    }else{
                        attachmentAdapter.setData(resources.attachments);
                        scrollview.smoothScrollTo(0, 0);
                    }
                    showToast("获取资源详情成功");
                    break;
                case MSG_ATTACHMENT_FAIL:
                    showToast("获取资源详情失败："+msg.obj);
                    break;
            }
        }
    };


    /** 获取附件列表 */
    public void getResourcesAttachment() {
        NetRequest.postFormRequest(SMTURL.RESOURCE_INFO, SMTURL.resourceInfoParams(resources.id), new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                handler.obtainMessage(MSG_ATTACHMENT_SUCCESS,result).sendToTarget();
                Resources resources = ParseUtils.getResourcesAttachment(result);
                LogUtils.println("resources",resources.toString());
            }

            @Override
            public void requestFailure(Request request, IOException e) {
            }
        });
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
