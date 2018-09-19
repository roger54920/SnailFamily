package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.RechargeView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 充值
 */

public class RechargePresenter implements BasePresenter<RechargeView> {
    private RechargeView view;

    public void postRechargeResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/account/recharge", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean recharge = (BaseBean) result;
                if (recharge != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (recharge.getStatus().equals("1")) {
                        if (view != null) {
                            view.onRechargeFinish(recharge);
                        }
                    } else if (recharge.getStatus().equals("2") || recharge.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, recharge.getMsg());
                    } else if (recharge.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, recharge.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(RechargeView view) {
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
