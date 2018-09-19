package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 *  获取寄件信息
 */

public interface CommonSiteView<T>{
    void onCommonSiteListFinish(T t); //常用地址列表
    void onSetDefaultCommonSiteFinish(T t);//设置默认地址
    void onDeleteCommonSiteFinish(T t);//删除地址
}
