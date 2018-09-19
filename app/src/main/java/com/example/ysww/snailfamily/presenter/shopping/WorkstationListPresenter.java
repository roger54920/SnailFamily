package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.WorkstationListBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.WorkstationListView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取蜗牛站列表
 */

public class WorkstationListPresenter implements BasePresenter<WorkstationListView> {
    private WorkstationListView view;

    public void postJsonWorkstationListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/workstation/workstationList", json, WorkstationListBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                WorkstationListBean workstationListBean = (WorkstationListBean) result;
                if (workstationListBean != null) {
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (workstationListBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onWorkstationListFinish(workstationListBean);
                        }
                    } else if (workstationListBean.getStatus().equals("2") || workstationListBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, workstationListBean.getMsg());
                    } else if(workstationListBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,workstationListBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(WorkstationListView view) {
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
