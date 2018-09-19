package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

/**
 * Created by ysww on 2017/10/23.
 * 购物车清单统计
 */

public class ShopCartBean extends BaseBean{

    /**
     * totalQuantity : 13
     * totlammount : 10400
     */

    private int totalQuantity;
    private int totlammount;

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotlammount() {
        return totlammount;
    }

    public void setTotlammount(int totlammount) {
        this.totlammount = totlammount;
    }
}
