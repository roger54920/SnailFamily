package com.example.ysww.snailfamily.mvp;

import android.view.View;

/**
 * 商品接口
 */
public interface ShopCartImp {
    void add(View view, int postion);
    void remove(View view, int postion);
}
