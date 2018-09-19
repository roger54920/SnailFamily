package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AScanCodeView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 站长扫码
 */

public class AScanCodePresenter implements BasePresenter<AScanCodeView>{
    private AScanCodeView view;
    public void postJsonScanCodeResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/appScanCode", json, AddressBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddressBean addressBean = (AddressBean) result;
                if(addressBean!=null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (addressBean.getStatus().equals("1")) {
                        if (addressBean != null) {
                            if (view != null) {
                                view.onAScanCodeFinish(addressBean);
                            }
                        }
                    }else if(addressBean.getStatus().equals("2") || addressBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,addressBean.getMsg());
                    }else if(addressBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,addressBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(AScanCodeView view) {
        this.view =  view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if(view!=null){
            view = null;
        }
    }
}
