package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 收件详情 相关操作
 */

public interface ParcelParticularsView<T>{
    void onSendImmediatelyChooseFinish(T t); //用户选择预约送、立即送
    void onUpdateTimeFinish(T t); //更新预约送预约时间
    void onConfirmReceiveFinish(T t); //确认接收
}
