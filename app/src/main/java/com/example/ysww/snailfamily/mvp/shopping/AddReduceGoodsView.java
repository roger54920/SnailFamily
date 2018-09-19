package com.example.ysww.snailfamily.mvp.shopping;

/**
 * Created by ysww on 2017/10/16.
 * 添加删除商品
 */

public interface AddReduceGoodsView<T> {
    void onAddGoodsFinish(T t);//添加
    void onReduceGoodsFinish(T t);//删除
}
