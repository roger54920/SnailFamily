package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.MyCccountGrandTotalBean;
import com.example.ysww.snailfamily.bean.MyCccountMessageBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.MyCccountOperationView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 我的账户操作
 */

public class MyCccountOperationPresenter implements BasePresenter<MyCccountOperationView> {
    private MyCccountOperationView view;

    /**
     * 我的账户信息
     *
     * @param activity
     */
    public void getAccountResult(final Activity activity) {
        OkHttpResolve.getResult(Constants.TOP + "sys/account/getAccount", "getAccount", MyCccountMessageBean.class, new OkHttpResolve.HttpCallBack() {
                    @Override
                    public void finish(Object result) {
                        MyCccountMessageBean myCccountMessageBean = (MyCccountMessageBean) result;
                        if (myCccountMessageBean != null) {
                            if (myCccountMessageBean.getStatus().equals("1")) {
                                if (view != null) {
                                    view.onGetAccountFinish(myCccountMessageBean);
                                }
                            } else if (myCccountMessageBean.getStatus().equals("2") || myCccountMessageBean.getStatus().equals("3")) {
                                SkipIntentUtil.toastShow(activity, myCccountMessageBean.getMsg());
                            } else if (myCccountMessageBean.getStatus().equals("4")) {
                                SkipIntentUtil.returnLogin(activity, myCccountMessageBean.getMsg());
                            } else {
                                SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                            }

                        }
                    }
                }
        );
    }
    /**
     * 充值记录
     *
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postRechargeDetailListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/account/getRechargeDetailList", json, MyCccountGrandTotalBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                MyCccountGrandTotalBean myCccountGrandTotalBean = (MyCccountGrandTotalBean) result;
                if (myCccountGrandTotalBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (myCccountGrandTotalBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onGetRechargeDetailListFinish(myCccountGrandTotalBean);
                        }
                    } else if (myCccountGrandTotalBean.getStatus().equals("2") || myCccountGrandTotalBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, myCccountGrandTotalBean.getMsg());
                    } else if (myCccountGrandTotalBean.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, myCccountGrandTotalBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    /**
     * 消费记录
     *
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postConsumeDetailListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/account/getConsumeDetailList", json, MyCccountGrandTotalBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                MyCccountGrandTotalBean myCccountGrandTotalBean = (MyCccountGrandTotalBean) result;
                if (myCccountGrandTotalBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (myCccountGrandTotalBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onGetConsumeDetailListFinish(myCccountGrandTotalBean);
                        }
                    } else if (myCccountGrandTotalBean.getStatus().equals("2") || myCccountGrandTotalBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, myCccountGrandTotalBean.getMsg());
                    } else if (myCccountGrandTotalBean.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, myCccountGrandTotalBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(MyCccountOperationView view) {
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
