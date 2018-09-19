package com.example.ysww.snailfamily.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 超期包裹展示
 */
public class CollectingExceedTimeParcelActivity extends AutoLayoutActivity {

    @InjectView(R.id.collection_exceed_time_rv)
    RecyclerView collectionExceedTimeRv;
    private CommonAdapter commonRVAdapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_exceed_time_parcel);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        collectionExceedTimeRv.setLayoutManager(new LinearLayoutManager(this));
        addList();
        commonRVAdapter = new CommonAdapter<String>(this,R.layout.collecting_exceed_time_item,list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        collectionExceedTimeRv.setAdapter(commonRVAdapter);
    }
    private void addList(){
        if(list==null){
            list = new ArrayList<>();
            for (int i = 0; i <10 ; i++) {
                list.add("A"+i);
            }
        }
    }
}
