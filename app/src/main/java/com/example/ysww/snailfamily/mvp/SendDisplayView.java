package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 *  获取寄件信息
 */

public interface SendDisplayView<T>{
    void onSendParticularsListFinish(T t); //寄件列表信息
    void onSendParticularsFinish(T t); //寄件详情
}
