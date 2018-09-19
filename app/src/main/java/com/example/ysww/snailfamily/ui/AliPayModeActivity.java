package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.custom.FullPhotoView;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 支付方式
 */
public class AliPayModeActivity extends AutoLayoutActivity {
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.recharge_btn)
    Button rechargeBtn;
    @InjectView(R.id.cos_btn)
    Button cosBtn;
    @InjectView(R.id.sender_ll)
    LinearLayout senderLl;
    @InjectView(R.id.accept_rl)
    RelativeLayout acceptRl;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay_mode);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        initViews();
    }

    private void initViews() {
        intent = getIntent();
        if (intent != null) {
            Constants.SOURCE = intent.getStringExtra("source");
            switch (Constants.SOURCE) {
                case "userAffirm":
                    senderLl.setVisibility(View.GONE);
                    acceptRl.setVisibility(View.VISIBLE);
                    RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
                    haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
                    expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
                    break;
            }

        }

    }

    @OnClick({R.id.return_arrows, R.id.recharge_btn, R.id.cos_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.recharge_btn:
                break;
            case R.id.cos_btn:
                break;
        }
    }
    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }
}
