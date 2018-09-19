package com.example.ysww.snailfamily.mvp;

/**
 * 首页展示数据
 */

public interface HomeShowDataView<T>{
    void onHomeShowDataHeadlineFinish(T t);//蜗牛头条
    void onHomeShowDataMessageFinish(T t);//蜗牛信息展示
    void onHomeShowDataAdvertisementFinish(T t);//广告图
}
