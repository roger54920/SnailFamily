package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.MyDefaultAddressBean;
import com.example.ysww.snailfamily.mvp.shopping.MyDefaultAddressView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取用户默认地址
 */

public class MyDefaultAddressPresenter implements BasePresenter<MyDefaultAddressView> {
    private MyDefaultAddressView view;

    public void postJsonMyDefaultAddressResult(String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/myDefaultAddress", json, MyDefaultAddressBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                MyDefaultAddressBean myDefaultAddressBean = (MyDefaultAddressBean) result;
                if (myDefaultAddressBean != null) {
                    if (myDefaultAddressBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onMyDefaultAddressFinish(myDefaultAddressBean);
                        }
                    } else if (myDefaultAddressBean.getStatus().equals("2") || myDefaultAddressBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, myDefaultAddressBean.getMsg());
                    } else if(myDefaultAddressBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,myDefaultAddressBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(MyDefaultAddressView view) {
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
