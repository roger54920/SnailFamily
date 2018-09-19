package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 新寄件订单 展示
 */
public class SendCasesTransactionActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.send_message)
    RelativeLayout sendMessage;
    @InjectView(R.id.state)
    TextView state;
    @InjectView(R.id.cyly)
    TextView cyly;
    @InjectView(R.id.send_cases_person)
    TextView sendCasesPerson;
    @InjectView(R.id.addressee_person)
    TextView addresseePerson;
    @InjectView(R.id.prcal_state)
    TextView prcalState;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cases_transaction);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        intent =getIntent();
        if(intent!=null){
            sendCasesPerson.setText("寄件人："+intent.getStringExtra("sender"));
            addresseePerson.setText("收件人："+intent.getStringExtra("receiver"));
            cyly.setText(intent.getStringExtra("goodname"));
        }
    }


    @OnClick({R.id.return_arrows, R.id.send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.skipIntent(this, SendCasesDisplayActivity.class);
                finish();
                break;
            case R.id.send_message:
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
