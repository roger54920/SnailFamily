package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.SendSaveBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SendSaveView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 新建寄件信息
 */

public class SendSavePresenter implements BasePresenter<SendSaveView> {
    private SendSaveView view;

    /**
     * 得到寄件相关信息
     */
    public void getJsonSendResult(final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.getResult(Constants.TOP + "business/expressSend/userSendApply", "send_message", SendSaveBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendSaveBean sendSaveBean = (SendSaveBean) result;
                if (sendSaveBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(sendSaveBean.getStatus().equals("1")) {
                        if (sendSaveBean.getWeightprice() != null && sendSaveBean.getCacompany() != null && sendSaveBean.getDelMethodsList() != null && sendSaveBean.getGoodsTypeList() != null && sendSaveBean.getWorksStr() != null) {
                            if (view != null) {
                                view.onGetSendFinish(sendSaveBean);
                            }
                        }
                    }else if(sendSaveBean.getStatus().equals("2") || sendSaveBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,sendSaveBean.getMsg());
                    }else if(sendSaveBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,sendSaveBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    /**
     * 保存寄件信息
     *
     * @param json
     */
    public void postJsonSendSaveResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressSend/saveSender", json, SendSaveBean.ResultSendBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SendSaveBean.ResultSendBean resultSendBean = (SendSaveBean.ResultSendBean) result;
                if (resultSendBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(resultSendBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAffirmSendSaveFinish(resultSendBean);
                        }
                    }else if(resultSendBean.getStatus().equals("2") || resultSendBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,resultSendBean.getMsg());
                    }else if(resultSendBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,resultSendBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    @Override
    public void attach(SendSaveView view) {
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
