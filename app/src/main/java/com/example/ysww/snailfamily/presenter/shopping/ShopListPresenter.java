package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.ShopListBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.ShopListView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 购物车清单
 */

public class ShopListPresenter implements BasePresenter<ShopListView> {
    private ShopListView view;

    public void postJsonShopListResult(String json, final Activity activity,final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCart/shopList", json, ShopListBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ShopListBean shopList = (ShopListBean) result;
                if (shopList != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if (shopList.getStatus().equals("1")) {
                        if (view != null) {
                            view.onShopListFinish(shopList);
                        }
                    } else if (shopList.getStatus().equals("2") || shopList.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, shopList.getMsg());
                    } else if (shopList.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, shopList.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(ShopListView view) {
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
