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
import cn.bingoogolapple.badgeview.BGABadgeRadioButton;

/**
 * 用户操作 信息中心
 */
public class UserOperationMessageCenterActivity extends AutoLayoutActivity {
    @InjectView(R.id.user_operation_message_center_rv)
    RecyclerView userOperationMessageCenterRv;
    @InjectView(R.id.parcel_message_rb)
    BGABadgeRadioButton parcelMessageRb;
    @InjectView(R.id.chat_message_rb)
    BGABadgeRadioButton chatMessageRb;
    @InjectView(R.id.system_message_rb)
    BGABadgeRadioButton systemMessageRb;
    private CommonAdapter<String> commonRVAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operation_message_center);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        userOperationMessageCenterRv.setLayoutManager(new LinearLayoutManager(this));
        addList();
        commonRVAdapter = new CommonAdapter<String>(this, R.layout.user_operation_message_center_item, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        userOperationMessageCenterRv.setAdapter(commonRVAdapter);

        parcelMessageRb.showTextBadge("3");
        chatMessageRb.showTextBadge("2");
        systemMessageRb.showTextBadge("3");
    }

    private void addList() {
        if (list == null) {
            list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add("A" + i);
            }
        }
    }

}
