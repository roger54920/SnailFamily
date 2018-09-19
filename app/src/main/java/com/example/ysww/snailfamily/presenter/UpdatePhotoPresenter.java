package com.example.ysww.snailfamily.presenter;

import android.app.Activity;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.bean.UpLoadImageBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.UpdatePhotoView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;

/**
 * Created by me-jie on 2017/4/17.
 * 更新头像
 */

public class UpdatePhotoPresenter implements BasePresenter<UpdatePhotoView>{
    private UpdatePhotoView view;
    public void postJsonResult(String json, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog){
            OkHttpResolve.postJsonResult(Constants.TOP + "sys/user/updatePhoto",json, UpLoadImageBean.class, new OkHttpResolve.HttpCallBack() {
            @Override
            public void finish(Object result) {
                UpLoadImageBean updatePhoto = (UpLoadImageBean) result;
                if (updatePhoto != null) {
                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                    if (updatePhoto.getStatus().equals("1")) {
                        if (view != null) {
                            view.onUpdatePhotoFinish(updatePhoto);
                        }
                    } else if (updatePhoto.getStatus().equals("2") || updatePhoto.getStatus().equals("3")) {
                        SkipIntentUtil.toastShow(activity, updatePhoto.getMsg());
                    } else if(updatePhoto.getStatus().equals("4")){
                        SkipIntentUtil.returnLogin(activity,updatePhoto.getMsg());
                    }else {
                        SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
                    }

                }
            }
        });
    }
    @Override
    public void attach(UpdatePhotoView view) {
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
