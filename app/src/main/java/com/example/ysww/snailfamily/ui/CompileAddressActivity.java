package com.example.ysww.snailfamily.ui;

import android.os.Bundle;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 编辑用户地址
 */
public class CompileAddressActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compile_address);
        StatusBarUtil.SetStatusBar(this);
    }
}
