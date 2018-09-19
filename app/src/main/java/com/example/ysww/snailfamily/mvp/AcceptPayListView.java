package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 代付货款
 */

public interface AcceptPayListView<T>{
    void onAcceptPayListFinish(T t); //代付货款
    void onAcceptPayCancelFinish(T t);//取消
    void onAcceptPayReapplyFinish(T t);//恢复
}
