package com.example.ysww.snailfamily.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.custom.SwipeMenuLayout;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 代付包裹展示
 */
public class CollectingParcelDisplayActivity extends AutoLayoutActivity {

    @InjectView(R.id.collecting_rv)
    RecyclerView collectingRv;
    private CommonAdapter commonRVAdapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_parcel_display);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        collectingRv.setLayoutManager(new LinearLayoutManager(this));
        addList();
        commonRVAdapter = new CommonAdapter<String>(this,R.layout.collecting_parcel_display_item,list) {

            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setOnClickListener(R.id.btn_deselect, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                //根据自己需求设置一些限制条件，比如这里设置了：IOS效果阻塞，item依次是左滑、右滑
                ((SwipeMenuLayout) LayoutInflater.from(mContext).inflate(R.layout.collecting_parcel_display_item, null)).setIos(true).setLeftSwipe(position % 2 == 0 ? true : false);
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CollectingParcelDisplayActivity.this, "Item"+position, Toast.LENGTH_SHORT).show();
                    }});
            }
        };
       collectingRv.setAdapter(commonRVAdapter);
    }
    private void addList(){
        if(list==null){
            list = new ArrayList<>();
            for (int i = 0; i <6 ; i++) {
                list.add("A"+i);
            }
        }
    }


}
