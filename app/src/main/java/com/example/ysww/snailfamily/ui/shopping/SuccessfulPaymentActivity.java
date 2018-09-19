package com.example.ysww.snailfamily.ui.shopping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.utils.EncodingUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 支付成功
 */
public class SuccessfulPaymentActivity extends AutoLayoutActivity {

    @InjectView(R.id.qr_code)
    ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_payment);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

    }
    private void initViews(){
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_icon);
        Bitmap bitmap;
        bitmap = EncodingUtil.createQRCode("123123123", 350, 350, logoBitmap);
        qrCode.setImageBitmap(bitmap);
    }
    @OnClick(R.id.return_arrows)
    public void onViewClicked() {
        finish();
    }
}
