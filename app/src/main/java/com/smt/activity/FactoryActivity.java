package com.smt.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.smt.R;
import com.smt.adapter.FactoryAdapter;
import com.smt.config.Preference;
import com.smt.domain.Factory;
import com.smt.domain.UserInfo;
import com.smt.http.NetRequest;
import com.smt.http.SMTURL;
import com.smt.utils.LogUtils;
import com.smt.utils.ParseUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class FactoryActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.pull_to_refresh_listview)
    PullToRefreshListView listView;
    private FactoryAdapter factoryAdapter;
    private ArrayList<Factory> arrayList = new ArrayList<Factory>();
    private Factory selectedFactory = null;
    UserInfo userInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        ButterKnife.bind(this);

        showTop("工厂列表",false);
        //是否从Welcome界面过来
        boolean fromWelcome = getIntent().getBooleanExtra("fromWelcome",false);
        if(fromWelcome)
            LogUtils.println("","fromWelcome");

        userInfo = Preference.getUser();
        name.setText(userInfo.name);
        username.setText(userInfo.username);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        factoryAdapter = new FactoryAdapter(this);
        listView.setAdapter(factoryAdapter);

//        arrayList.add(new Factory("1","江阴兴澄特钢","江阴"));
//        arrayList.add(new Factory("2","靖江华菱钢铁","靖江"));
//        arrayList.add(new Factory("3","苏州钢厂","苏州"));
//        arrayList.add(new Factory("4","中信泰富江都特钢厂","中信"));
//        factoryAdapter.setData(arrayList);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setRefreshing();
            }
        }, 300);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedFactory = arrayList.get(position - 1);
        startActivity(new Intent(this,ResourcesListActivity.class).putExtra("factory", selectedFactory));
    }

    public static final int MSG_LOGIN_SUCCESS = 1;
    public static final int MSG_LOGIN_FAIL = 2;
    public static final int MSG_FACTORY_LIST_SUCCESS = 3;
    public static final int MSG_FACTORY_LIST_FAIL = 4;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOGIN_SUCCESS:
                    break;
                case MSG_LOGIN_FAIL:
                    showToast("登录工厂列表失败，请重新登录"+msg.obj);
                    startActivity(new Intent(FactoryActivity.this, LoginActivity.class));
                    FactoryActivity.this.finish();
                    break;
                case MSG_FACTORY_LIST_SUCCESS:
                    arrayList.clear();
                    arrayList = ParseUtils.getFactorys(msg.obj+"");
                    factoryAdapter.setData(arrayList);
                    showToast("获取工厂列表成功");
                    listView.onRefreshComplete();
                    break;
                case MSG_FACTORY_LIST_FAIL:
                    showToast("获取工厂列表失败："+msg.obj);
                    listView.onRefreshComplete();
                    break;
            }
        }
    };

    /** 获取工厂列表 */
    public void factoryList() {
        NetRequest.postFormRequest(SMTURL.FACTORY_LIST,null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                handler.obtainMessage(MSG_FACTORY_LIST_SUCCESS,result).sendToTarget();
                ArrayList<Factory> factories = ParseUtils.getFactorys(result);
                for (int i=0;i<factories.size();i++){
                    System.out.println("---->"+factories.get(i).toString());
                }
            }
            @Override
            public void requestFailure(Request request, IOException e) {
                handler.obtainMessage(MSG_FACTORY_LIST_SUCCESS,e.getMessage()).sendToTarget();
            }
        });
    }




    /** 登录操作 */
    public void login() {
        NetRequest.postFormRequest(SMTURL.LOGIN, SMTURL.loginParams(Preference.getString(Preference.USERNAME),
                Preference.getString(Preference.PASSWORD)), new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                UserInfo userInfo = ParseUtils.getUser(result);
                Preference.setUser(userInfo);
                Preference.putString(Preference.TOKEN,ParseUtils.getToken(result));
                LogUtils.println("userInfo",userInfo.toString());
                handler.obtainMessage(MSG_LOGIN_SUCCESS,result).sendToTarget();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                handler.obtainMessage(MSG_LOGIN_FAIL,e.getMessage()).sendToTarget();
            }
        });
    }




    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        factoryList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        factoryList();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listView.onRefreshComplete();
//            }
//        }, 2000);
    }
}
