package com.smt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smt.R;
import com.smt.dialog.WaitDialog;

public class BaseActivity extends Activity implements View.OnClickListener {

    /** 等待提示框 */
    protected WaitDialog waitDialog;
    /** 等待提示框是否被取消 */
    protected boolean progressShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        waitDialog = new WaitDialog(this, "正在请求。。。");
        waitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    /**
     * 显示title
     *
     * @param title
     * @param isShowBack
     */
    public void showTop(String title, boolean isShowBack) {
        TextView top_title = (TextView) findViewById(R.id.top_title);
        top_title.setText(title);
        if (isShowBack) {
            View backBtn = findViewById(R.id.back);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onBackPressed();
                    } catch (Exception e) {
                        finish();
                    }
                }
            });
            backBtn.setVisibility(View.VISIBLE);
        }
    }


    protected boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    public void showToast(int messageId) {
        Toast.makeText(this,messageId,Toast.LENGTH_LONG).show();
    }

    @SuppressLint("HandlerLeak")
    public Handler baseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
        }
    };
}
