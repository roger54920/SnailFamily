package com.example.ysww.snailfamily.mvp.shopping;

/**
 * Created by ysww on 2017/10/16.
 * 根据分类 获取相应的商品信息
 */

public interface GoodsListByCategoryView<T> {
    void onGoodsListByCategoryListFinish(T t);
}
