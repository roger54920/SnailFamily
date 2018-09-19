package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 *  寄件操作
 */

public interface SendOperationView<T>{
    void onSendOperationFinish(T t);
    void onUpdateTimeFinish(T t); //更新预约送预约时间
}
