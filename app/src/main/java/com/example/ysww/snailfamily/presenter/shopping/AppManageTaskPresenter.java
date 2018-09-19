package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.AppManageTaskView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 电商订单支付
 */

public class AppManageTaskPresenter implements BasePresenter<AppManageTaskView> {
    private AppManageTaskView view;

    public void postJsonAppManageTaskResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopOrder/appManageTask", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean appManageTask = (BaseBean) result;
                if (appManageTask != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (appManageTask.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAppManageTaskFinish(appManageTask);
                        }
                    } else if (appManageTask.getStatus().equals("2") || appManageTask.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, appManageTask.getMsg());
                    } else if (appManageTask.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, appManageTask.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(AppManageTaskView view) {
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
