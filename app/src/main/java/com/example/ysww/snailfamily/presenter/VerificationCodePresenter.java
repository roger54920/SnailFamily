package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.VerificationCodeView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取验证码
 */

public class VerificationCodePresenter implements BasePresenter<VerificationCodeView>{
    private VerificationCodeView view;
    //请求验证码
    public void postJsonCodeResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/user/getPhoneVerificationCode", json, LoginBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                LoginBean verificationCodeBean = (LoginBean) result;
                if(verificationCodeBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(verificationCodeBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onBeanVerificationCodeFinish(verificationCodeBean);
                        }
                    }else if(verificationCodeBean.getStatus().equals("2") || verificationCodeBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,verificationCodeBean.getMsg());
                    }else if(verificationCodeBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,verificationCodeBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    //输入验证码
    public void postJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/user/verificationcode", json, LoginBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                LoginBean verificationCodeBean = (LoginBean) result;
                if(verificationCodeBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(verificationCodeBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onInputBeanFinish(verificationCodeBean);
                        }
                    }else if(verificationCodeBean.getStatus().equals("2") || verificationCodeBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,verificationCodeBean.getMsg());
                    }else if(verificationCodeBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,verificationCodeBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    @Override
    public void attach(VerificationCodeView view) {
        this.view =  view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if(view!=null){
            view = null;
        }
    }
}
