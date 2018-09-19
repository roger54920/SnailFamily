package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.LoginView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 用户登录
 */

public class LoginPresenter implements BasePresenter<LoginView> {
    private LoginView view;
    /**
     * 登录
     *
     * @param json
     */
    public void postJsonLoginVerificationResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "login", json, LoginBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                final LoginBean loginBean = (LoginBean) result;
                if (loginBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (loginBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onBeanLoginFinish(loginBean);
                        }
                    } else if (loginBean.getStatus().equals("2") || loginBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, loginBean.getMsg());
                    } else if(loginBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,loginBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    /**
     * 注销
     */
    public void userWriteOffResult(final Activity activity) {
        OkHttpResolve.getResult(Constants.TOP + "loginOut", "writeOff", LoginBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                LoginBean loginBean = (LoginBean) result;
                if (loginBean != null) {
                    if(loginBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onBeanWriteOffFinish(loginBean);
                        }
                    }else if(loginBean.getStatus().equals("2") || loginBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,loginBean.getMsg());
                    }else if(loginBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,loginBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(LoginView view) {
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
