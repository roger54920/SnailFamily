package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AcceptPayApplayView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 到付申请
 */

public class AcceptPayApplayPresenter implements BasePresenter<AcceptPayApplayView> {
    private AcceptPayApplayView view;

    /**
     * 收件列表
     * @param json
     */
    public void postJsonAcceptPayApplayResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/acceptPayApplay", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean acceptPayApplay = (BaseBean) result;
                if(acceptPayApplay !=null){
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(acceptPayApplay.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAcceptPayApplayFinish(acceptPayApplay);
                        }
                    }else if(acceptPayApplay.getStatus().equals("2") || acceptPayApplay.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, acceptPayApplay.getMsg());
                    }else if(acceptPayApplay.getStatus().equals("4")){
                      SkipIntentUtil.returnLogin(activity,acceptPayApplay.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(AcceptPayApplayView view) {
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
