package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.UserEvaluateView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 用户评价
 */

public class UserEvaluatePresenter implements BasePresenter<UserEvaluateView>{
    private UserEvaluateView view;
    public void postJsonUserEvaluateResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/performance/save", json, BaseBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                BaseBean baseBean = (BaseBean) result;
                if(baseBean!=null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (baseBean.getStatus().equals("1")) {
                        if (baseBean != null) {
                            if (view != null) {
                                view.onUserEvaluateFinish(baseBean);
                            }
                        }
                    }else if(baseBean.getStatus().equals("2") || baseBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,baseBean.getMsg());
                    }else if(baseBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,baseBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(UserEvaluateView view) {
        this.view =  view;
    }

    /**
     * 不调用的时候进行 清空
     */
    @Override
    public void dettach() {
        if(view!=null){
            view = null;
        }
    }
}
