package com.example.ysww.snailfamily.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.ysww.snailfamily.R;

/**
 * Created by me-jie on 2017/7/7.
 * 支付方式选择弹窗
 */

public class PaymentModePopoverDialog  extends Dialog implements View.OnClickListener{
    private Context mContext;
    private PaymentModeClickListener paymentModeClickListener;
    private PaymentModePopoverDialog paymentModePopoverDialog;
    public PaymentModePopoverDialog(Context context,PaymentModeClickListener onPaymentModeClickListener) {
        super(context);
        this.mContext = context;
        this.paymentModeClickListener = onPaymentModeClickListener;
    }
    public PaymentModePopoverDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }
    public interface PaymentModeClickListener {
        void onClickAliPay(PaymentModePopoverDialog paymentModePopoverDialog);//支付宝
        void onClickWechat(PaymentModePopoverDialog paymentModePopoverDialog);//微信
    }
    public PaymentModePopoverDialog setPaymentModePopoverDialog() {
        paymentModePopoverDialog = new PaymentModePopoverDialog(mContext, R.style.Dialog);
        //填充对话框的布局
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.coverpopup_payment_mode_popover_item, null);
        RelativeLayout wechat = (RelativeLayout) inflate.findViewById(R.id.wechat_rl);
        RelativeLayout ali_pay = (RelativeLayout) inflate.findViewById(R.id.ali_pay_rl);
        //初始化控件
        if(paymentModeClickListener!=null){
            wechat.setOnClickListener(this);
            ali_pay.setOnClickListener(this);
        }

        //将布局设置给Dialog
        paymentModePopoverDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = paymentModePopoverDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width  = lp.MATCH_PARENT;
        lp.height  = lp.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        return paymentModePopoverDialog;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_rl:
                paymentModeClickListener.onClickWechat(paymentModePopoverDialog);
                break;
            case R.id.ali_pay_rl:
                paymentModeClickListener.onClickAliPay(paymentModePopoverDialog);
                break;
        }
    }
}
