package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 获取地址
 */

public interface LoginView<T>{
    void onBeanLoginFinish(T t); //登录
    void onBeanWriteOffFinish(T t); //注销
}
