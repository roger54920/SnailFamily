package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.bean.AddresseeDisplayBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AddresseeView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 收件地址
 */

public class AddresseePresenter implements BasePresenter<AddresseeView> {
    private AddresseeView view;

    /**
     * 收件列表
     * @param json
     */
    public void postJsonDisplayListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/list", json, AddresseeDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddresseeDisplayBean addresseeDisplayBean = (AddresseeDisplayBean) result;
                if(addresseeDisplayBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addresseeDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onARecipientsDisplayListFinish(addresseeDisplayBean);
                        }
                    }else if(addresseeDisplayBean.getStatus().equals("2") || addresseeDisplayBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, addresseeDisplayBean.getMsg());
                    }else if(addresseeDisplayBean.getStatus().equals("4")){
                      SkipIntentUtil.returnLogin(activity,addresseeDisplayBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 收件列表Item详情
     * @param json
     */
    public void postJsonDisplayResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/getExpressAcceptById", json, AddressBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddressBean addressBean = (AddressBean) result;
                if(addressBean!=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addressBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onARecipientsDisplayBeanFinish(addressBean);
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
    public void attach(AddresseeView view) {
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
