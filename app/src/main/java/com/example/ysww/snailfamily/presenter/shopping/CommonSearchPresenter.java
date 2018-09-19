package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.CommonSearchBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.CommonSearchView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 热门搜索
 */

public class CommonSearchPresenter implements BasePresenter<CommonSearchView> {
    private CommonSearchView view;

    public void postJsonCommonSearchResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCommodity/commonSearch", json, CommonSearchBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CommonSearchBean commonSearchBean = (CommonSearchBean) result;
                if (commonSearchBean != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if (commonSearchBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onCommonSearchFinish(commonSearchBean);

                        }
                    } else if (commonSearchBean.getStatus().equals("2") || commonSearchBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, commonSearchBean.getMsg());
                    } else if(commonSearchBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,commonSearchBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(CommonSearchView view) {
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
