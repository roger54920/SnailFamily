package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.ParcelParticularsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 收件地址
 */

public class ParcelParticularsPresenter implements BasePresenter<ParcelParticularsView> {
    private ParcelParticularsView view;

    /**
     * 用户选择预约送、立即送
     * @param json
     */
    public void postSendImmediatelyChooseResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/appManageTask", json, AddressBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddressBean addressBean = (AddressBean) result;
                if(addressBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addressBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSendImmediatelyChooseFinish(addressBean);
                        }
                    }else if(addressBean.getStatus().equals("2") || addressBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, addressBean.getMsg());
                    }else if(addressBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,addressBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 更新预约送预约时间
     * @param json
     */
    public void postUpdateTimeResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/updateTime", json, AddressBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddressBean addressBean = (AddressBean) result;
                if(addressBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addressBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onUpdateTimeFinish(addressBean);
                        }
                    }else if(addressBean.getStatus().equals("2") || addressBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, addressBean.getMsg());
                    }else if(addressBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,addressBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 用户确认签收
     * @param json
     */
    public void postSendConfirmResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/appManageTask", json, AddressBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddressBean addressBean = (AddressBean) result;
                if(addressBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addressBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onConfirmReceiveFinish(addressBean);
                        }
                    }else if(addressBean.getStatus().equals("2") || addressBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, addressBean.getMsg());
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
    public void attach(ParcelParticularsView view) {
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
