package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2017/4/21.
 * 编辑寄件信息
 */

public interface SendSaveView<T> {
    void onGetSendFinish(T t);//得到寄件相关信息
    void onAffirmSendSaveFinish(T t);//确认信息
}
