package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.ShopOrderListBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.ShopOrderListView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 电商订单列表
 */

public class ShopOrderListPresenter implements BasePresenter<ShopOrderListView> {
    private ShopOrderListView view;

    public void postJsonShopOrderListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopOrder/shopOrderList", json, ShopOrderListBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                ShopOrderListBean shopOrderList = (ShopOrderListBean) result;
                if (shopOrderList != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if (shopOrderList.getStatus().equals("1")) {
                        if (view != null) {
                            view.onShopOrderListFinish(shopOrderList);
                        }
                    } else if (shopOrderList.getStatus().equals("2") || shopOrderList.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, shopOrderList.getMsg());
                    } else if(shopOrderList.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,shopOrderList.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(ShopOrderListView view) {
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
