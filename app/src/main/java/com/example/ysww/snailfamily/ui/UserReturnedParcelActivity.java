package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 用户退回包裹
 */
public class UserReturnedParcelActivity extends AutoLayoutActivity {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.send_back)
    RelativeLayout sendBack;
    @InjectView(R.id.submit_applications_btn)
    Button submitApplicationsBtn;
    @InjectView(R.id.reason_return_tx)
    TextView reasonReturnTx;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_returned_parcel);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
    }

    @OnClick({R.id.return_arrows, R.id.send_back, R.id.submit_applications_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.send_back:
                break;
            case R.id.submit_applications_btn:
                intent = new Intent(UserReturnedParcelActivity.this, BottomNavigationMenuActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
