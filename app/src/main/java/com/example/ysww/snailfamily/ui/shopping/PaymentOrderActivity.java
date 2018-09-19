package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ysww.snailfamily.R.id.time_1;

/**
 * 支付订单计时器
 */
public class PaymentOrderActivity extends BaseActivity implements AppManageTaskView {
    @InjectView(time_1)
    TextView time1;
    @InjectView(R.id.time_2)
    TextView time2;
    @InjectView(R.id.second_1)
    TextView second1;
    @InjectView(R.id.second_2)
    TextView second2;
    @InjectView(R.id.amount_money)
    TextView amountMoney;
    @InjectView(R.id.payment_alipay_rb)
    RadioButton paymentAlipayRb;
    @InjectView(R.id.payment_wechat_rb)
    RadioButton paymentWechatRb;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    //倒计时
    private static int minute = -1;
    private static int second = -1;
    private Timer timer;
    private TimerTask timerTask;
    Handler timeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (minute == 0) {
                if (second == 0) {
//                    tvTimer.setText("倒计时结束");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        countdown();
                    } else {
                        countdown();
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        countdown();
                    } else {
                        countdown();
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            countdown();
                        } else {
                            countdown();
                        }
                    } else {
                        if (minute >= 10) {
                            countdown();
                        } else {
                            countdown();
                        }
                    }
                }
            }
        }
    };
    private Intent intent;
    private AppManageTaskPresenter appManageTaskPresenter = new AppManageTaskPresenter();//电商订单支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_order);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initView();
    }

    private void initView() {
        intent = getIntent();
        if (intent != null) {
            startTime(Integer.parseInt(intent.getStringExtra("limitTime")));
            amountMoney.setText("¥ " + SkipIntentUtil.conversionAmountFormat(intent.getStringExtra("expenditure")));
        }
    }

    private void startTime(int time) {
        minute = time;
        second = 00;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                timeHandler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

    }

    /**
     * 封装倒计时方法样式
     */
    private void countdown() {
        String minutStr = minute + "";
        String secondStr = second + "";
        if(minutStr.length()==2 && secondStr.length()==2) {
            time1.setText(minutStr.substring(0, 1));
            time2.setText(minutStr.substring(1, 2));
            second1.setText(secondStr.substring(0, 1));
            second2.setText(secondStr.substring(1, 2));
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

    @OnClick({R.id.confirm_payment_btn,R.id.return_arrows, R.id.payment_alipay_rl, R.id.payment_wechat_rl, R.id.payment_alipay_rb, R.id.payment_wechat_rb})
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
            intent.putExtra("source","paymentOrder");
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        appManageTaskPresenter.dettach();
        timer.cancel();
    }
}
