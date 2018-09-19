package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.CommonSiteBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.CommonSiteView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 常用地址
 */

public class CommonSitePresenter implements BasePresenter<CommonSiteView> {
    private CommonSiteView view;

    /**
     * 得到常用列表
     */
    public void getJsonCommonSiteResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/list", json, CommonSiteBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CommonSiteBean commonSiteBean = (CommonSiteBean) result;
                if (commonSiteBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (commonSiteBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onCommonSiteListFinish(commonSiteBean);
                        }
                    } else if (commonSiteBean.getStatus().equals("2") || commonSiteBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, commonSiteBean.getMsg());
                    }else if(commonSiteBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,commonSiteBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    /**
     * 设置默认地址
     */
    public void setDefaultCommonSiteResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/setIfDefault", json, CommonSiteBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CommonSiteBean commonSiteBean = (CommonSiteBean) result;
                if (commonSiteBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (commonSiteBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSetDefaultCommonSiteFinish(commonSiteBean);
                        }
                    } else if (commonSiteBean.getStatus().equals("2") || commonSiteBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, commonSiteBean.getMsg());
                    }else if(commonSiteBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,commonSiteBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    /**
     * 设置删除地址
     */
    public void deleteCommonSiteResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/addressDelete", json, CommonSiteBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CommonSiteBean commonSiteBean = (CommonSiteBean) result;
                if (commonSiteBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (commonSiteBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onDeleteCommonSiteFinish(commonSiteBean);
                        }
                    } else if (commonSiteBean.getStatus().equals("2") || commonSiteBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, commonSiteBean.getMsg());
                    }else if(commonSiteBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,commonSiteBean.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    @Override
    public void attach(CommonSiteView view) {
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
