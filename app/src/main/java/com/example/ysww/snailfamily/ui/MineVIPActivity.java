package com.example.ysww.snailfamily.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的vip
 */
public class MineVIPActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.head_portrait)
    ImageView headPortrait;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    @InjectView(R.id.immediately_renewal_btn)
    Button immediatelyRenewalBtn;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.vip_icon)
    ImageView vipIcon;
    @InjectView(R.id.growth_value)
    TextView growthValue;
    @InjectView(R.id.expiration_time)
    TextView expirationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_vip);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }

    @OnClick({R.id.return_arrows, R.id.immediately_renewal_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.immediately_renewal_btn:
                break;
        }
    }
}
