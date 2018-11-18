package com.smt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.smt.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelecomeActivity extends BaseActivity {

    private TextView welcomeSkip;
    private long mExitTime;
    private Timer timer = new Timer();
    private boolean threadRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welecome);
        int duration = 2;
        welcomeSkip = (TextView) findViewById(R.id.welcome_skip);
        welcomeSkip.setText(duration+"");
        postDelayed(duration);
    }

    //开始倒计时
    private void postDelayed(final int sec) {
        threadRun = true;
        TimerTask task = new TimerTask() {
            int time = sec;
            @Override
            public void run() {
                if (threadRun) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            welcomeSkip.setText(time+"");
                        }
                    });
                    if (--time <= 0) {
                        remorveDelayed();
                        startActivity(new Intent(WelecomeActivity.this, LoginActivity.class));
                        WelecomeActivity.this.finish();
                    }
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }


    private void remorveDelayed() {
        threadRun = false;
        if (timer != null)
            timer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mExitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remorveDelayed();
    }
}
