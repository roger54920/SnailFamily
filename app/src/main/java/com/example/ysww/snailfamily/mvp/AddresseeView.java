package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 用户收件列表
 */

public interface AddresseeView<T>{
    void onARecipientsDisplayListFinish(T t); //收件列表展示
    void onARecipientsDisplayBeanFinish(T t);//Item收件详情
}
