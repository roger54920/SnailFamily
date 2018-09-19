package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.NewAddressSaveBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SnailElectronView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;


/**
 * Created by me-jie on 2017/4/17.
 * 获取地址信息
 */

public class SnailElectronPresenter implements BasePresenter<SnailElectronView> {
    private SnailElectronView view;

    //是否注册蜗牛电子包裹箱
    public void ifSnailElectronResult(String json,final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        OkHttpResolve.postJsonResult(Constants.TOP + "sys/address/checkParcelNo", json, NewAddressSaveBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                NewAddressSaveBean snailElectron = (NewAddressSaveBean) result;
                if (snailElectron != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if(snailElectron.getStatus().equals("1")) {
                        if (view != null) {
                            view.onIfSnailElectronFinish(snailElectron);
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
    public void attach(SnailElectronView view) {
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
