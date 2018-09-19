package com.example.ysww.snailfamily.ui;

import android.os.Bundle;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 代付我的余额
 */
public class CollectingMeBalanceActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_me_balance);
        StatusBarUtil.SetStatusBar(this);
    }
}
