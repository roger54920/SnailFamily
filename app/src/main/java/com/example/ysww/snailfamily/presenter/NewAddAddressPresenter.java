package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.NewAddressPlotNameBean;
import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.example.ysww.snailfamily.bean.NewAddressSaveBean;
import com.example.ysww.snailfamily.bean.NewAddressTabletBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.NewAddAddressView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

import java.util.List;


/**
 * Created by me-jie on 2017/4/17.
 * 获取地址信息
 */

public class NewAddAddressPresenter implements BasePresenter<NewAddAddressView> {
    private NewAddAddressView view;
    //获取地区信息
    public void postRegionJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/area/getArea", json, NewAddressRegionBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) result;
                if (newAddressRegionBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(newAddressRegionBean.getStatus().equals("1")) {
                        List<NewAddressRegionBean.AreaBean> area = newAddressRegionBean.getArea();
                        if (area != null && area.size()>0) {
                            if (area.get(0).getNodes() != null) {
                                if (area.get(0).getNodes().get(0).getNodes() != null) {
                                    if (view != null) {
                                        view.onRegionListFinish(newAddressRegionBean);
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

    //获取小区名信息
    public void postPlotNameJsonResult(String json,final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/area/getCommunity", json, NewAddressPlotNameBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressPlotNameBean newAddressPlotNameBean = (NewAddressPlotNameBean) result;
                if (newAddressPlotNameBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(newAddressPlotNameBean.getStatus().equals("1")) {
                        if (newAddressPlotNameBean.getCommunity() != null) {
                            if (view != null) {
                                view.onPlotNameListFinish(newAddressPlotNameBean);
                            }
                        }
                    }else if(newAddressPlotNameBean.getStatus().equals("2") || newAddressPlotNameBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,newAddressPlotNameBean.getMsg());
                    }else if(newAddressPlotNameBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,newAddressPlotNameBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    //获取门牌号信息
    public void postTabletJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/area/getHouse", json, NewAddressTabletBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressTabletBean newAddressTabletBean = (NewAddressTabletBean) result;
                if (newAddressTabletBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(newAddressTabletBean.getStatus().equals("1")) {
                        if (newAddressTabletBean.getHouse() != null && newAddressTabletBean.getHouse().size()>0) {
                            if (newAddressTabletBean.getHouse().get(0).getNodes() != null) {
                                if (newAddressTabletBean.getHouse().get(0).getNodes().get(0).getNodes() != null) {
                                    if (view != null) {
                                        view.onTabletListFinish(newAddressTabletBean);
                                    }
                                }
                            }
                        }
                    }else if(newAddressTabletBean.getStatus().equals("2") || newAddressTabletBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,newAddressTabletBean.getMsg());
                    }else if(newAddressTabletBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,newAddressTabletBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    //保存地址
    public void postSaveJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/saveOrUpadteAddress", json, NewAddressPlotNameBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressPlotNameBean newAddressSaveBean = (NewAddressPlotNameBean) result;
                if (newAddressSaveBean != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(newAddressSaveBean.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSaveFinish(newAddressSaveBean);
                        }
                    }else if(newAddressSaveBean.getStatus().equals("2") || newAddressSaveBean.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,newAddressSaveBean.getMsg());
                    }else if(newAddressSaveBean.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,newAddressSaveBean.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }

    //获取蜗牛电子包裹箱
    public void getSnailElectronResult(final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.getResult(Constants.TOP + "sys/address/getParcelNo", "snailElectron", NewAddressSaveBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressSaveBean snailElectron = (NewAddressSaveBean) result;
                if (snailElectron != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(snailElectron.getStatus().equals("1")) {
                        if (view != null) {
                            view.onSnailElectronFinish(snailElectron);
                        }
                    }else if(snailElectron.getStatus().equals("2") || snailElectron.getStatus().equals("3")){
                        SkipIntentUtil.toastShow(activity,snailElectron.getMsg());
                    }else if(snailElectron.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,snailElectron.getMsg());
                    }else{
                        SkipIntentUtil.toastShow(activity,"系统繁忙，请稍后重试！");
                    }
                }
            }
        });
    }


    @Override
    public void attach(NewAddAddressView view) {
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
