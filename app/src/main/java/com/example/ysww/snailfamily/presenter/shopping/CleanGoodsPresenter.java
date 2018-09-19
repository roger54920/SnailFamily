package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.CleanGoodsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 购物车清空
 */

public class CleanGoodsPresenter implements BasePresenter<CleanGoodsView> {
    private CleanGoodsView view;

    public void postJsonCleanGoodsResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCart/cleanGoods", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean cleanGoodsBean = (BaseBean) result;
                if (cleanGoodsBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (cleanGoodsBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onCleanGoodsFinish(cleanGoodsBean);
                        }
                    } else if (cleanGoodsBean.getStatus().equals("2") || cleanGoodsBean.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, cleanGoodsBean.getMsg());
                    } else if(cleanGoodsBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,cleanGoodsBean.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(CleanGoodsView view) {
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
