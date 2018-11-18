package com.smt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.smt.R;
import com.smt.config.SMTApplication;
import com.smt.utils.PermisionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_btn)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PermisionUtils.verifyStoragePermissions(this);
        ButterKnife.bind(this);
        login.setOnClickListener(this);
    }

    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
