package com.example.ysww.snailfamily.presenter.shopping;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.BasePresenter;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 添加删除商品
 */

public class AddReduceGoodsPresenter implements BasePresenter<AddReduceGoodsView> {
    private AddReduceGoodsView view;
    /**
     * 添加商品
     * @param json
     * @param activity
     */
    public void postJsonAddGoodsResult(String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCart/addGoods", json, AddReduceGoodsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddReduceGoodsBean addGoods = (AddReduceGoodsBean) result;
                if (addGoods != null) {
                    if (addGoods.getStatus().equals("1")) {
                        if (view != null) {
                            view.onAddGoodsFinish(addGoods);
                        }
                    } else if (addGoods.getStatus().equals("2") || addGoods.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, addGoods.getMsg());
                    } else if(addGoods.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,addGoods.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    /**
     * 删除商品
     * @param json
     * @param activity
     */
    public void postJsonReduceGoodsResult(String json, final Activity activity) {
        OkHttpResolve.postJsonResult(Constants.TOP + "shop/shopCart/reduceGoods", json, AddReduceGoodsBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                AddReduceGoodsBean reduceGoods = (AddReduceGoodsBean) result;
                if (reduceGoods != null) {
                    if (reduceGoods.getStatus().equals("1")) {
                        if (view != null) {
                            view.onReduceGoodsFinish(reduceGoods);
                        }
                    } else if (reduceGoods.getStatus().equals("2") || reduceGoods.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, reduceGoods.getMsg());
                    } else if(reduceGoods.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,reduceGoods.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(AddReduceGoodsView view) {
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
