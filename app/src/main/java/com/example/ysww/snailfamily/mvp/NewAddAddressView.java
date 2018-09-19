package com.example.ysww.snailfamily.mvp;

/**
 * Created by me-jie on 2016/12/7.
 * 获取地址
 */

public interface NewAddAddressView<T>{
    void onRegionListFinish(T t); //地区
    void onPlotNameListFinish(T t); //小区名
    void onTabletListFinish(T t); //门牌号
    void onSnailElectronFinish(T t);//电子包裹箱
    void onSaveFinish(T t); //保存地址
}
