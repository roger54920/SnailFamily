package com.example.ysww.snailfamily.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.PaymentModePopoverDialog;
import com.example.ysww.snailfamily.mvp.RechargeView;
import com.example.ysww.snailfamily.mvp.WithdrawalsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.RechargePresenter;
import com.example.ysww.snailfamily.presenter.WithdrawalsPresenter;
import com.example.ysww.snailfamily.utils.GadgetUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的账户充值
 */
public class UserOperationBalanceRechargeActivity extends AutoLayoutActivity implements RechargeView, WithdrawalsView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.input_money)
    EditText inputMoney;
    @InjectView(R.id.recharge_btn)
    Button rechargeBtn;
    @InjectView(R.id.display_credit)
    TextView displayCredit;
    @InjectView(R.id.all_withdrawal)
    TextView allWithdrawal;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.money_hint)
    TextView moneyHint;
    private double credit;//账户余额
    private RechargePresenter rechargePresenter = new RechargePresenter();//充值
    private WithdrawalsPresenter withdrawalsPresenter = new WithdrawalsPresenter();//提现
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private PaymentModePopoverDialog paymentModePopoverDialog;//弹窗

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operation_balance_recharge);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }

    private void initViews() {
        GadgetUtil.setPricePoint(inputMoney);
        Constants.SOURCE = getIntent().getStringExtra("source");
        rechargeBtn.setEnabled(false);
        if (Constants.SOURCE.equals("recharge")) {
            titleTv.setText(R.string.my_account);
            moneyHint.setText(R.string.enter_top_up_amount);
            inputMoney.setHint(R.string.enter_top_up_amount);
            displayCredit.setVisibility(View.GONE);
            allWithdrawal.setVisibility(View.GONE);
            rechargeBtn.setText(R.string.my_recharge);
            inputMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String moneyStr = inputMoney.getText().toString();
                    if (TextUtils.isEmpty(moneyStr)) {
                        rechargeBtn.setEnabled(false);
                    }else{
                        rechargeBtn.setEnabled(true);
                    }
                }
            });
        } else {
            titleTv.setText(R.string.balance_withdraw);
            moneyHint.setText(R.string.enter_top_up_withdrawals);
            inputMoney.setHint(R.string.enter_top_up_withdrawals);
            displayCredit.setVisibility(View.VISIBLE);
            allWithdrawal.setVisibility(View.VISIBLE);
            credit = Double.parseDouble(getIntent().getStringExtra("displayCredit"));
            displayCredit.setText("账户余额：¥" + credit);
            rechargeBtn.setText(R.string.withdraw);
            inputMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String moneyStr = inputMoney.getText().toString();
                    if (!TextUtils.isEmpty(moneyStr)) {
                        double money = Double.parseDouble(moneyStr);
                        if (money <= credit) {
                            allWithdrawal.setVisibility(View.VISIBLE);
                            displayCredit.setText("账户余额：¥" + credit);
                            displayCredit.setTextColor(Color.parseColor("#666666"));
                            rechargeBtn.setEnabled(true);
                        } else {
                            rechargeBtn.setEnabled(false);
                            displayCredit.setText("输入金额超过账户余额");
                            displayCredit.setTextColor(Color.parseColor("#f30000"));
                            allWithdrawal.setVisibility(View.GONE);
                        }
                    } else {
                        rechargeBtn.setEnabled(false);
                        allWithdrawal.setVisibility(View.VISIBLE);
                        displayCredit.setTextColor(Color.parseColor("#666666"));
                        displayCredit.setText("账户余额：¥" + credit);
                    }
                }
            });
        }
    }

    /**
     * 初始化充值
     */
    private void requestRechargeResult(String json) {
        new OkHttpResolve(this);
        rechargePresenter.attach(this);
        rechargePresenter.postRechargeResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化提现
     */
    private void requestWithdrawalsResult(String json) {
        new OkHttpResolve(this);
        withdrawalsPresenter.attach(this);
        withdrawalsPresenter.postWithdrawalsResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.recharge_btn, R.id.all_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.recharge_btn:
                String paid = inputMoney.getText().toString();
                String lastStr = paid.substring(paid.length() - 1, paid.length());
                if (!lastStr.equals(".") && !paid.equals("0.0") && !paid.equals("0.00") && !paid.equals("0.")) {
                    //弹窗充值
                    paymentModePopoverDialog();
                } else {
                    SkipIntentUtil.toastShow(this, "请正确输入金额！");
                }

                break;
            case R.id.all_withdrawal:
                inputMoney.setText(credit + "");
                break;
        }
    }

    /**
     * 支付方式弹窗
     */
    private void paymentModePopoverDialog() {
        paymentModePopoverDialog = new PaymentModePopoverDialog(this, new PaymentModePopoverDialog.PaymentModeClickListener() {
            @Override
            public void onClickAliPay(PaymentModePopoverDialog paymentModePopoverDialog) {
                submitRequest("10");
            }

            @Override
            public void onClickWechat(PaymentModePopoverDialog paymentModePopoverDialog) {
                submitRequest("20");
            }
        }).setPaymentModePopoverDialog();
        paymentModePopoverDialog.show();
    }

    /**
     * 提交请求
     *
     * @param transactionChannel
     */
    private void submitRequest(String transactionChannel) {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        String money = inputMoney.getText().toString();
        if (Constants.SOURCE.equals("recharge")) {
            requestRechargeResult("{\"transactionAmount\":\"" + money + "\",\"transactionChannel\":\"" + transactionChannel + "\"}");
        } else {
            requestWithdrawalsResult("{\"transactionAmount\":\"" + money + "\",\"transactionChannel\":\"" + transactionChannel + "\"}");
        }
    }

    @Override
    public void onRechargeFinish(Object o) {
        requestSuccess(o);
    }

    @Override
    public void onWithdrawalsFinish(Object o) {
        requestSuccess(o);
    }

    /**
     * 请求成功
     */
    private void requestSuccess(Object o) {
        BaseBean success = (BaseBean) o;
        if (success.getStatus().equals("1")) {
            paymentModePopoverDialog.dismiss();
            SkipIntentUtil.skipIntent(this, UserOperationBalanceActivity.class);
            finish();
        }
    }

    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        rechargePresenter.dettach();
        withdrawalsPresenter.dettach();
    }
}
