package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 购物车清单统计
 */

public class ShopCartCountPresenter implements BasePresenter<ShopCartCountView> {
    private ShopCartCountView view;

    public void postJsonShopCartCountResult(String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCart/shopCartCount", json, ShopCartBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ShopCartBean shopCartBean = (ShopCartBean) result;
                if (shopCartBean != null) {
                    if (shopCartBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onShopCartCountFinish(shopCartBean);
                        }
                    } else if (shopCartBean.getStatus().equals("2") || shopCartBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, shopCartBean.getMsg());
                    } else if(shopCartBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,shopCartBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(ShopCartCountView view) {
        this.view = view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if (view != null) {
            view = null;
        }
    }
}
