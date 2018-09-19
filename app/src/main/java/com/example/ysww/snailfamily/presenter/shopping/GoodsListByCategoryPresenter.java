package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.GoodsListByCategoryView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 根据分类 获取相应的商品信息
 */

public class GoodsListByCategoryPresenter implements BasePresenter<GoodsListByCategoryView> {
    private GoodsListByCategoryView view;

    public void postJsonGoodsListByCategoryResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
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
                            view.onGoodsListByCategoryListFinish(goodsListByCategory);

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
    public void attach(GoodsListByCategoryView view) {
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
