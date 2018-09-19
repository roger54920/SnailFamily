package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.SearchCourierNumberBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SearchCourierNumberView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 搜索快递单号
 */

public class SearchCourierNumberPresenter implements BasePresenter<SearchCourierNumberView>{
    private SearchCourierNumberView view;
    /**
     * 搜索快递单号
     * @param json
     */
    public void postJsonSearchCourierNumberResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/expressAccept/search", json, SearchCourierNumberBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                SearchCourierNumberBean searchCourierNumberBean = (SearchCourierNumberBean) result;
                if(searchCourierNumberBean !=null){
                    if(searchCourierNumberBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSearchCourierNumberViewFinish(searchCourierNumberBean);
                        }
                    }else if(searchCourierNumberBean.getStatus().equals("2") || searchCourierNumberBean.getStatus().equals("3")){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                        SkipIntentUtil.toastShow(activity, searchCourierNumberBean.getMsg());
                    }else if(searchCourierNumberBean.getStatus().equals("4")){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                        SkipIntentUtil.returnLogin(activity,searchCourierNumberBean.getMsg());
                    }else{
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(SearchCourierNumberView view) {
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
