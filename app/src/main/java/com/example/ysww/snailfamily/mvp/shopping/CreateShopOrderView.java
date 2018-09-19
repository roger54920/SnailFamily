package com.example.ysww.snailfamily.mvp.shopping;

/**
 * Created by ysww on 2017/10/16.
 * 电商订单生成
 */

public interface CreateShopOrderView<T> {
    void onOnlineShoppingFinish(T t);//网上购
    void onSitePurchaseFinish(T t);//站点购
}
