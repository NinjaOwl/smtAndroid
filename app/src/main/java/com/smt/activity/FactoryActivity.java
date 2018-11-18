package com.smt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.smt.R;
import com.smt.adapter.ItemAdapter;
import com.smt.domain.Item;
import com.smt.utils.PermisionUtils;

import java.util.ArrayList;

public class FactoryActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    private PullToRefreshListView listView;
    private ItemAdapter historyAdapter;
    private ArrayList<Item> arrayList = new ArrayList<Item>();
    private Item selectedRecord = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);


        PermisionUtils.verifyStoragePermissions(this);
        listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        listView.setMode(PullToRefreshBase.Mode.BOTH);//设置只能上拉获取
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        historyAdapter = new ItemAdapter(this);
        listView.setAdapter(historyAdapter);

        arrayList.add(new Item("1","1"));
        arrayList.add(new Item("1","2"));
        arrayList.add(new Item("1","3"));
        arrayList.add(new Item("1","4"));
        historyAdapter.setData(arrayList);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedRecord = arrayList.get(position - 1);
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
        System.out.println("---->"+selectedRecord.getName());

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        System.out.println("---->onPullDownToRefresh");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.onRefreshComplete();
                        }
                    });
                }catch(Exception e){
                    System.out.println("---->onPullDownToRefresh   "+e.getMessage());
                }
            }
        }).start();

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        System.out.println("---->onPullUpToRefresh");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    listView.onRefreshComplete();
                }catch(Exception e){
                    System.out.println("---->onPullUpToRefresh   "+e.getMessage());
                }
            }
        }).start();

    }
}
