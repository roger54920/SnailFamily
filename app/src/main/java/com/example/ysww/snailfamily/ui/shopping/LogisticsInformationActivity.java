package com.example.ysww.snailfamily.ui.shopping;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 物流信息列表页
 */
public class LogisticsInformationActivity extends BaseActivity {

    @InjectView(R.id.logistics_information_rv)
    RecyclerView logisticsInformationRv;
    private CommonAdapter<String> logisticsInformationAdapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_information);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        initData();
        initViews();
    }
    private void initViews(){
        logisticsInformationRv.setLayoutManager(new LinearLayoutManager(this));
        logisticsInformationAdapter = new CommonAdapter<String>(this,R.layout.logistics_information_item,list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        logisticsInformationRv.setAdapter(logisticsInformationAdapter);
    }
    private void initData(){
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("A"+i);
        }
    }
    @OnClick(R.id.return_arrows)
    public void onViewClicked() {
        finish();
    }
}
