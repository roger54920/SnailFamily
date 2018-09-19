package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.ExpressWaybillOperationView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 快递单 操作
 */

public class ExpressWaybillOperationPresenter implements BasePresenter<ExpressWaybillOperationView> {
    private ExpressWaybillOperationView view;

    /**
     * 搜索快递单号
     * @param json
     */
    public void postJsonHomeShowSarchResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/search", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean addresseeDisplayBean = (BaseBean) result;
                if(addresseeDisplayBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(addresseeDisplayBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onCourierNumberSearchFinish(addresseeDisplayBean);
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
    @Override
    public void attach(ExpressWaybillOperationView view) {
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
