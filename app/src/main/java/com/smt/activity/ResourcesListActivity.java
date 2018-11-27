package com.smt.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.smt.R;
import com.smt.adapter.ResourcesAdapter;
import com.smt.domain.Factory;
import com.smt.domain.Resources;
import com.smt.http.NetRequest;
import com.smt.http.SMTURL;
import com.smt.utils.ParseUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class ResourcesListActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>{

    @BindView(R.id.pull_to_refresh_listview)
    PullToRefreshListView listView;
    private ResourcesAdapter resourcesAdapter;
    private ArrayList<Resources> arrayList = new ArrayList<Resources>();
    private Resources selectedResources = null;

    Factory factory;
    /** 当前页数 */
    int curPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_list);
        ButterKnife.bind(this);
        showTop("资源列表",true);

        factory = (Factory)getIntent().getParcelableExtra("factory");

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        resourcesAdapter = new ResourcesAdapter(this);
        listView.setAdapter(resourcesAdapter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setRefreshing();
            }
        }, 300);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedResources = arrayList.get(position - 1);
        startActivity(new Intent(this,ResourcesActivity.class).putExtra("resources", selectedResources));
    }

    public static final int MSG_FACTORY_LIST_SUCCESS = 1;
    public static final int MSG_FACTORY_LIST_FAIL = 2;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FACTORY_LIST_SUCCESS:
                    arrayList.addAll(ParseUtils.getResourcesList(msg.obj+""));
                    resourcesAdapter.setData(arrayList);
                    curPage++;
                    if(curPage>ParseUtils.getPageCount(msg.obj+"")){
                        showToast("获取工厂列表成功，已无数据");
                    }else{
                        showToast("获取工厂列表成功");
                    }
                    listView.onRefreshComplete();
                    break;
                case MSG_FACTORY_LIST_FAIL:
                    showToast("获取工厂列表失败："+msg.obj);
                    listView.onRefreshComplete();
                    break;
            }
        }
    };

    /** 获取资源列表 */
    public void factoryList() {
        NetRequest.postFormRequest(SMTURL.RESOURCE_LIST,SMTURL.resourceListParams(factory.id,"",curPage+""), new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                handler.obtainMessage(MSG_FACTORY_LIST_SUCCESS,result).sendToTarget();
                ArrayList<Resources> resources = ParseUtils.getResourcesList(result);
                for (int i=0;i<resources.size();i++){
                    System.out.println("---->"+resources.get(i).toString());
                }
            }
            @Override
            public void requestFailure(Request request, IOException e) {
                handler.obtainMessage(MSG_FACTORY_LIST_SUCCESS,e.getMessage()).sendToTarget();
            }
        });
    }




    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
        curPage = 1;
        arrayList.clear();
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
