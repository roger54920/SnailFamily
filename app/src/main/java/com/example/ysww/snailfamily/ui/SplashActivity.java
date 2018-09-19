package com.example.ysww.snailfamily.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by me-jie on 2017/4/7.
 * 起始页
 */

public class SplashActivity extends Activity {

    @InjectView(R.id.sp_bg)
    ImageView spBg;
    @InjectView(R.id.sp_jump_btn)
    Button spJumpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);
        startClock();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(3200, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            spJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            spJumpBtn.setText("跳过(" + 0 + "s)");
            SkipIntentUtil.skipIntent(SplashActivity.this, NotHomeActivity.class);
            finish();
        }
    };

    private void startClock() {
        spJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    @OnClick({R.id.sp_bg, R.id.sp_jump_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sp_bg:
//                gotoWebActivity();
                break;
            case R.id.sp_jump_btn:
                if (countDownTimer != null)
                    countDownTimer.cancel();
                SkipIntentUtil.skipIntent(SplashActivity.this, NotHomeActivity.class);
                finish();
                break;
        }
    }

    //    private Splash mSplash;
//    private void gotoWebActivity() {
//        if (mSplash != null && mSplash.click_url != null) {
//            Intent intent = new Intent(this, BannerActivity.class);
//            intent.putExtra("url", mSplash.click_url);
//            intent.putExtra("title", mSplash.title);
//            intent.putExtra("fromSplash", true);
//            intent.putExtra("needShare", false);
//            startActivity(intent);
//            finish();
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
