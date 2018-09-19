package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.CategoryListBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.CategoryListView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取分类列表
 */

public class CategoryListPresenter implements BasePresenter<CategoryListView> {
    private CategoryListView view;

    public void postJsonCategoryListResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCommodity/categoryList", json, CategoryListBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                CategoryListBean getCommodityInfo = (CategoryListBean) result;
                if (getCommodityInfo != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (getCommodityInfo.getStatus().equals("1")) {
                        if (view != null) {
                            view.onCategoryListFinish(getCommodityInfo);
                        }
                    } else if (getCommodityInfo.getStatus().equals("2") || getCommodityInfo.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, getCommodityInfo.getMsg());
                    } else if (getCommodityInfo.getStatus().equals("4")) {
                        SkipIntentUtil.returnLogin(activity, getCommodityInfo.getMsg());
                    } else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(CategoryListView view) {
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
