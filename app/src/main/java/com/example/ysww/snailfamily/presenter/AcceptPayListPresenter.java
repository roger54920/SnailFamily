package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.AddresseeDisplayBean;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AcceptPayListView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 代付货款
 */

public class AcceptPayListPresenter implements BasePresenter<AcceptPayListView> {
    private AcceptPayListView view;

    /**
     * 代付列表
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonAcceptPayListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/acceptPayList", json, AddresseeDisplayBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddresseeDisplayBean addresseeDisplayBean = (AddresseeDisplayBean) result;
                if(addresseeDisplayBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addresseeDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAcceptPayListFinish(addresseeDisplayBean);
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
     * 取消
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonAcceptPayCancelResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/cancel", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean acceptPayCancel = (BaseBean) result;
                if(acceptPayCancel !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(acceptPayCancel.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAcceptPayCancelFinish(acceptPayCancel);
                        }
                    }else if(acceptPayCancel.getStatus().equals("2") || acceptPayCancel.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, acceptPayCancel.getMsg());
                    }else if(acceptPayCancel.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,acceptPayCancel.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 恢复
     * @param json
     * @param activity
     * @param lazyLoadProgressDialog
     */
    public void postJsonAcceptPayReapplyResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/reapply", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean acceptPayCancel = (BaseBean) result;
                if(acceptPayCancel !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(acceptPayCancel.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAcceptPayCancelFinish(acceptPayCancel);
                        }
                    }else if(acceptPayCancel.getStatus().equals("2") || acceptPayCancel.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, acceptPayCancel.getMsg());
                    }else if(acceptPayCancel.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,acceptPayCancel.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(AcceptPayListView view) {
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
