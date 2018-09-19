package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.HomeShowDataAdvertisementBean;
import com.example.ysww.snailfamily.bean.HomeShowDataHeadlineBean;
import com.example.ysww.snailfamily.bean.HomeShowDataMessageBean;
import com.example.ysww.snailfamily.mvp.HomeShowDataView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 首页展示数据
 */

public class HomeShowDataPresenter implements BasePresenter<HomeShowDataView>{
    private HomeShowDataView view;

    /**
     * 蜗牛头条
     * @param activity
     */
    public void postJsonHomeShowDataHeadlineResult(final Activity activity){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/showHeadlines", "{\"id\":\"\"}" , HomeShowDataHeadlineBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                HomeShowDataHeadlineBean homeShowDataHeadlineBean = (HomeShowDataHeadlineBean) result;
                if(homeShowDataHeadlineBean !=null){
                    if(homeShowDataHeadlineBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onHomeShowDataHeadlineFinish(homeShowDataHeadlineBean);
                        }
                    }else if(homeShowDataHeadlineBean.getStatus().equals("2") || homeShowDataHeadlineBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, homeShowDataHeadlineBean.getMsg());
                    }else if(homeShowDataHeadlineBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,homeShowDataHeadlineBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    /**
     * 蜗牛信息展示
     * @param activity
     */
    public void postJsonHomeShowDataMessageResult(final Activity activity){
        OkHttpResolve.postJsonResult(Constants.TOP + "business/index/showConsumerList",  "{\"id\":\"\"}" , HomeShowDataMessageBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                HomeShowDataMessageBean homeShowDataMessageBean = (HomeShowDataMessageBean) result;
                if(homeShowDataMessageBean !=null){
                    if(homeShowDataMessageBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onHomeShowDataMessageFinish(homeShowDataMessageBean);
                        }
                    }else if(homeShowDataMessageBean.getStatus().equals("2") || homeShowDataMessageBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, homeShowDataMessageBean.getMsg());
                    }else if(homeShowDataMessageBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,homeShowDataMessageBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    /**
     * 用户首页广告图
     * @param activity
     */
    public void postJsonHomeShowDataAdvertisementResult(final Activity activity){
        OkHttpResolve.getResult(Constants.TOP + "business/index/getAdvertisement",  "advertisement" , HomeShowDataAdvertisementBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                HomeShowDataAdvertisementBean homeShowDataAdvertisementBean = (HomeShowDataAdvertisementBean) result;
                if(homeShowDataAdvertisementBean !=null){
                    if(homeShowDataAdvertisementBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onHomeShowDataAdvertisementFinish(homeShowDataAdvertisementBean);
                        }
                    }else if(homeShowDataAdvertisementBean.getStatus().equals("2") || homeShowDataAdvertisementBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity, homeShowDataAdvertisementBean.getMsg());
                    }else if(homeShowDataAdvertisementBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,homeShowDataAdvertisementBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }
    @Override
    public void attach(HomeShowDataView view) {
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
