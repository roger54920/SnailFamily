package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AllRegionView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

import java.util.List;

/**
 * Created by me-jie on 2017/4/17.
 * 获取地址信息
 */

public class AllRegionPresenter implements BasePresenter<AllRegionView> {
    private AllRegionView view;

    //获取全国地区信息
    public void postAllRegionJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/area/getAllArea", json, NewAddressRegionBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) result;
                if (newAddressRegionBean != null) {
                    if(lazyLoadProgressDialog!=null){
                        LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    }
                    if(newAddressRegionBean.getStatus().equals("1")) {
                        List<NewAddressRegionBean.AreaBean> area = newAddressRegionBean.getArea();
                        if (area != null && area.size()>0) {
                            if (area.get(0).getNodes() != null) {
                                if (area.get(0).getNodes().get(0).getNodes() != null) {
                                    if (view != null) {
                                        view.onAllRegionListFinish(newAddressRegionBean);
                                    }
                                }
                            }
                        }
                    }else if(newAddressRegionBean.getStatus().equals("2") || newAddressRegionBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,newAddressRegionBean.getMsg());
                    }else if(newAddressRegionBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,newAddressRegionBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }

    @Override
    public void attach(AllRegionView view) {
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
