package com.smt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smt.R;
import com.smt.config.Preference;
import com.smt.config.SMTApplication;
import com.smt.domain.BaseResult;
import com.smt.domain.UserInfo;
import com.smt.http.NetRequest;
import com.smt.http.SMTURL;
import com.smt.utils.LogUtils;
import com.smt.utils.ParseUtils;
import com.smt.utils.PermisionUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_btn)
    Button login;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_pwd)
    EditText editPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PermisionUtils.verifyStoragePermissions(this);
        ButterKnife.bind(this);
        login.setOnClickListener(this);

        editPhone.setText("admin");
        editPwd.setText("123456");
    }


    String userName = "";
    String password = "";
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_btn:
                userName = editPhone.getText().toString();
                password = editPwd.getText().toString();
                if(!checkValue(userName,password)){
                    login();
                }
                break;
        }
    }
    /** 登录操作 */
    public void login() {
        NetRequest.postFormRequest(SMTURL.LOGIN, SMTURL.loginParams(userName,password), new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                UserInfo userInfo = ParseUtils.getUser(result);
                Preference.setUser(userInfo);
                Preference.putString(Preference.TOKEN,ParseUtils.getToken(result));
                Preference.putString(Preference.USERNAME,userName);
                Preference.putString(Preference.PASSWORD,password);
                LogUtils.println("userInfo",userInfo.toString());
                showToast("登录成功");
                startActivity(new Intent(LoginActivity.this,FactoryActivity.class));
                LoginActivity.this.finish();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                showToast("登录失败"+e.getMessage());
            }
        });
    }
    /** 返回true的时候说明有空 */
    public boolean checkValue(String userName,String password) {
        if(isEmpty(userName)){
            showToast("请输入手机号");
            return true;
        }
        if(isEmpty(password)){
            showToast("请输入密码");
            return true;
        }
        return false;
    }
}
