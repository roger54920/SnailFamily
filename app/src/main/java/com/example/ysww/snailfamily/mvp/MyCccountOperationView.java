package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 我的账户操作
 */

public interface MyCccountOperationView<T>{
    void onGetAccountFinish(T t); //我的账户信息
    void onGetRechargeDetailListFinish(T t);//充值记录
    void onGetConsumeDetailListFinish(T t);//消费记录
}
