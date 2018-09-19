package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.CreateShopOrderBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.CreateShopOrderView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 电商订单生成
 */

public class CreateShopOrderPresenter implements BasePresenter<CreateShopOrderView> {
    private CreateShopOrderView view;

    /**
     * 网上购
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonOnlineShoppingResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopOrder/createShopOrder", json, CreateShopOrderBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CreateShopOrderBean onlineShoppong = (CreateShopOrderBean) result;
                if (onlineShoppong != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if (onlineShoppong.getStatus().equals("1")) {
                        if (view != null) {
                            view.onOnlineShoppingFinish(onlineShoppong);
                        }
                    } else if (onlineShoppong.getStatus().equals("2") || onlineShoppong.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, onlineShoppong.getMsg());
                    } else if(onlineShoppong.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,onlineShoppong.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    /**
     * 站点购
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonSitePurchaseResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopOrder/createShopOrder", json, CreateShopOrderBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CreateShopOrderBean SitePurchase = (CreateShopOrderBean) result;
                if (SitePurchase != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if (SitePurchase.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSitePurchaseFinish(SitePurchase);
                        }
                    } else if (SitePurchase.getStatus().equals("2") || SitePurchase.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, SitePurchase.getMsg());
                    } else if(SitePurchase.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,SitePurchase.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(CreateShopOrderView view) {
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
