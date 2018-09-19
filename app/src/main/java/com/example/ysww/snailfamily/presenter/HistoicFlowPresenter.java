package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
  时间轴
 */

public class HistoicFlowPresenter implements BasePresenter<HistoicFlowView> {
    private HistoicFlowView view;
    /**
     * 时间轴
     *
     * @param json
     */
    public void postJsonHistoicFlowResult(String request_url,String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + request_url, json, HistoicFlowBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                HistoicFlowBean histoicFlowBean = (HistoicFlowBean) result;
                if (histoicFlowBean != null) {
                    if (histoicFlowBean.getStatus().equals("1") && histoicFlowBean.getListTrajectory()!=null) {
                        if (view != null) {
                            view.onHistoicFlowFinish(histoicFlowBean);
                        }
                    } else if (histoicFlowBean.getStatus().equals("2") || histoicFlowBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, histoicFlowBean.getMsg());
                    } else if(histoicFlowBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,histoicFlowBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(HistoicFlowView view) {
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
