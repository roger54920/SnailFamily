package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.WithdrawalsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 提现
 */

public class WithdrawalsPresenter implements BasePresenter<WithdrawalsView> {
    private WithdrawalsView view;
    /**
     * 提现
     *
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postWithdrawalsResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/account/withdrawals", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean withdrawals = (BaseBean) result;
                if (withdrawals != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (withdrawals.getStatus().equals("1")) {
                        if (view != null) {
                            view.onWithdrawalsFinish(withdrawals);
                        }
                    } else if (withdrawals.getStatus().equals("2") || withdrawals.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, withdrawals.getMsg());
                    } else if (withdrawals.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, withdrawals.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(WithdrawalsView view) {
        this.view = view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if (view != null) {
            view = null;
        }
    }
}
