package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.SendDetailsBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SendOperationView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 寄件操作
 */

public class SendOperationPresenter implements BasePresenter<SendOperationView> {
    private SendOperationView view;

    public void postJsonSendOperationResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressSend/appManageTaskSend", json, SendDetailsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendDetailsBean sendDetailsBean = (SendDetailsBean) result;
                if (sendDetailsBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (sendDetailsBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSendOperationFinish(sendDetailsBean);
                        }
                    } else if (sendDetailsBean.getStatus().equals("2") || sendDetailsBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, sendDetailsBean.getMsg());
                    } else if(sendDetailsBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,sendDetailsBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
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
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressSend/updateTime", json, SendDetailsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendDetailsBean sendDetailsBean = (SendDetailsBean) result;
                if(sendDetailsBean !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(sendDetailsBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onUpdateTimeFinish(sendDetailsBean);
                        }
                    }else if(sendDetailsBean.getStatus().equals("2") || sendDetailsBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, sendDetailsBean.getMsg());
                    }else if(sendDetailsBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,sendDetailsBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(SendOperationView view) {
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
