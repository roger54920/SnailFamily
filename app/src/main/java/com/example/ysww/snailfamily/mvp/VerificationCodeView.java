package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 验证验证码
 */

public interface VerificationCodeView<T>{
    void onBeanVerificationCodeFinish(T t);//获取验证码
    void onInputBeanFinish(T t);//输入验证码
}
