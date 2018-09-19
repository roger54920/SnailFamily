package com.example.ysww.snailfamily.ui;

import android.os.Bundle;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 用户操作意见反馈
 */
public class UserOperationOpinionFeedbackActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operation_opinion_feedback);
        StatusBarUtil.SetStatusBar(this);
    }

}
