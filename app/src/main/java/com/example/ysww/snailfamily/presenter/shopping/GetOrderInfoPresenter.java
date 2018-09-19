package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.GetOrderInfoBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.GetOrderInfoView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 电商订单详情
 */

public class GetOrderInfoPresenter implements BasePresenter<GetOrderInfoView> {
    private GetOrderInfoView view;

    public void postJsonGetOrderInfoResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopOrder/getOrderInfo", json, GetOrderInfoBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                GetOrderInfoBean getOrderInfo = (GetOrderInfoBean) result;
                if (getOrderInfo != null) {
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (getOrderInfo.getStatus().equals("1")) {
                        if (view != null) {
                            view.onGetOrderInfoFinish(getOrderInfo);
                        }
                    } else if (getOrderInfo.getStatus().equals("2") || getOrderInfo.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, getOrderInfo.getMsg());
                    } else if(getOrderInfo.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,getOrderInfo.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(GetOrderInfoView view) {
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
