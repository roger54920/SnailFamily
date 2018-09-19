package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.AppManageTaskView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AppManageTaskPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 无倒计时支付页面
 */
public class OfflinePaymentOrderActivity extends BaseActivity implements AppManageTaskView {
    @InjectView(R.id.amount_money)
    TextView amountMoney;
    @InjectView(R.id.payment_alipay_rb)
    RadioButton paymentAlipayRb;
    @InjectView(R.id.payment_wechat_rb)
    RadioButton paymentWechatRb;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private Intent intent;
    private AppManageTaskPresenter appManageTaskPresenter = new AppManageTaskPresenter();//电商订单支付
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_payment_order);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initView();
    }
    private void initView() {
        intent = getIntent();
        if (intent != null) {
            amountMoney.setText("¥ " + SkipIntentUtil.conversionAmountFormat(intent.getStringExtra("expenditure")));
        }
    }
    /**
     * 初始化 购物车清单统计
     */
    private void requestAppManageTask() {
        if (intent != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("act.procInsId", intent.getStringExtra("act.procInsId"));
                json.put("act.taskDefKey", intent.getStringExtra("act.taskDefKey"));
                json.put("act.taskId", intent.getStringExtra("act.taskId"));
                json.put("id", intent.getStringExtra("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new OkHttpResolve(this);
            appManageTaskPresenter.attach(this);
            appManageTaskPresenter.postJsonAppManageTaskResult(json.toString(), this, lazyLoadProgressDialog);
        }
    }
    @OnClick({R.id.confirm_payment_btn,R.id.return_arrows, R.id.payment_alipay_rb, R.id.payment_alipay_rl, R.id.payment_wechat_rb, R.id.payment_wechat_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.payment_alipay_rl:
                if (paymentAlipayRb.isChecked() == false) {
                    paymentAlipayRb.setChecked(true);
                    paymentWechatRb.setChecked(false);
                }
                break;
            case R.id.payment_wechat_rl:
                if (paymentWechatRb.isChecked() == false) {
                    paymentWechatRb.setChecked(true);
                    paymentAlipayRb.setChecked(false);
                }
                break;
            case R.id.payment_alipay_rb:
                paymentAlipayRb.setChecked(true);
                paymentWechatRb.setChecked(false);
                break;
            case R.id.payment_wechat_rb:
                paymentWechatRb.setChecked(true);
                paymentAlipayRb.setChecked(false);
                break;
            case R.id.confirm_payment_btn:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestAppManageTask();
                break;
        }
    }

    @Override
    public void onAppManageTaskFinish(Object o) {
        BaseBean baseBean = (BaseBean) o;
        if(baseBean!=null){
            Intent intent=new Intent(this,MyOrderActivity.class);
            intent.putExtra("source","offlinePaymentOrder");
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        appManageTaskPresenter.dettach();
    }
}
