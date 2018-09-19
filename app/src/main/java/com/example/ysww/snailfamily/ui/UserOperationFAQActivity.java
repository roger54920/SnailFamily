package com.example.ysww.snailfamily.ui;

import android.os.Bundle;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 用户操作 常见问题
 */
public class UserOperationFAQActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operation_faq);
        StatusBarUtil.SetStatusBar(this);
    }

}
