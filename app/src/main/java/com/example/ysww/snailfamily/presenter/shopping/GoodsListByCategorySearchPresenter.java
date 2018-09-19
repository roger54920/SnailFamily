package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.GoodsListByCategorySearchView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 商品搜索接口
 */

public class GoodsListByCategorySearchPresenter implements BasePresenter<GoodsListByCategorySearchView> {
    private GoodsListByCategorySearchView view;

    public void postJsonGoodsListByCategorySearchResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCommodity/goodsListByCategory", json, GoodsListByCategoryBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                GoodsListByCategoryBean goodsListByCategory = (GoodsListByCategoryBean) result;
                if (goodsListByCategory != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if (goodsListByCategory.getStatus().equals("1")) {
                        if (view != null) {
                            view.onGoodsListByCategorySearchFinish(goodsListByCategory);
                        }
                    } else if (goodsListByCategory.getStatus().equals("2") || goodsListByCategory.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, goodsListByCategory.getMsg());
                    } else if(goodsListByCategory.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,goodsListByCategory.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(GoodsListByCategorySearchView view) {
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
