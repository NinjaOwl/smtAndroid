package com.smt.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()){

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
