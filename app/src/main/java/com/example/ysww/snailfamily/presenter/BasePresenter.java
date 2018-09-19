package com.example.ysww.snailfamily.presenter;

/**
 * Created by me-jie on 2016/12/7.
 */

public interface BasePresenter <T>{
    public void attach(T t);
    public void dettach();
}
