package com.example.ysww.snailfamily.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.bean.SendDetailsBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;


/**
 * Created by me-jie on 2017/5/18.
 * 封装数据
 */

public class RequestOperationUtil {
    public static void setViewFinishListener(ViewFinishListener viewFinishListener) {
        RequestOperationUtil.viewFinishListener = viewFinishListener;
    }

    private static ViewFinishListener viewFinishListener;

    public interface ViewFinishListener {
        void finishLinstener();
    }

    /**
     * 封装presenter 返回操作
     */
    public static void presenterOperationUtil(BaseBean baseBean, final Activity activity, final LazyLoadProgressDialog lazyLoadProgressDialog) {
        if (baseBean != null) {
            LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
            if (baseBean.getStatus().equals("1")) {
                //干什么事，在另一个类中操作
                if (viewFinishListener != null) {
                    viewFinishListener.finishLinstener();
                }
            } else if (baseBean.getStatus().equals("2") || baseBean.getStatus().equals("3")) {
                SkipIntentUtil.toastShow(activity, baseBean.getMsg());
            } else if (baseBean.getStatus().equals("4")) {
                SkipIntentUtil.returnLogin(activity,baseBean.getMsg());
            } else {
                SkipIntentUtil.toastShow(activity, "系统繁忙，请稍后重试！");
            }

        }
    }

    /**
     * 时间轴寄件
     *
     * @param senderId
     */
    public static void setSendHistoicFlowResult(String senderId, Activity activity, HistoicFlowPresenter histoicFlowPresenter) {
        new OkHttpResolve(activity);
        histoicFlowPresenter.postJsonHistoicFlowResult("business/track/send", "{\"id\":\"" + senderId + "\"}", activity);
    }

    /**
     * 时间轴收件
     *
     * @param acceptId
     */
    public static void setAddresseeHistoicFlowResult(String acceptId, Activity activity, HistoicFlowPresenter histoicFlowPresenter) {
        new OkHttpResolve(activity);
        histoicFlowPresenter.postJsonHistoicFlowResult("business/track/accept", "{\"id\":\"" + acceptId + "\"}", activity);
    }

    /**
     * 设置图片Activity
     */
    public static void setGlide(Activity activity, String img_url, ImageView fullPhotoView) {
        Glide.with(activity)
                .load(img_url)
                .asBitmap()// .asGif() 加载gif图
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                .placeholder(R.mipmap.image_loader)//加载成功之前
                .error(R.mipmap.fail_load)//加载失败
                .into(fullPhotoView);
    }

    /**
     * 收件必须参数
     */
    public static void intentAcceptNecessaryParameters(AddressBean addressBean, Intent intent, String source) {
        AddressBean.ExpressAcceptBean expressAccept = addressBean.getExpressAccept();
        intent.putExtra("source", source);
        if (expressAccept != null) {
            intent.putExtra("shipperCode", expressAccept.getExpressParcel().getShipperCode());
            intent.putExtra("lgisticCode", expressAccept.getExpressParcel().getLgisticCode());
            intent.putExtra("img_url", Constants.UP_LOAD_IMAGE_TOP + expressAccept.getExpressParcel().getPhoto());

            intent.putExtra("expressAccept_id", expressAccept.getId());
            intent.putExtra("expressParcel_id", expressAccept.getExpressParcel().getId());
        }

        intent.putExtra("act_procInsId", addressBean.getAct().getProcInsId());
        intent.putExtra("act_taskId", addressBean.getAct().getTaskId());
    }

    /**
     * 寄件必须参数
     */
    public static void intentSendNecessaryParameters(SendDetailsBean sendDetailsBean, Intent intent, String source) {
        SendDetailsBean.MapsenderBean mapsender = sendDetailsBean.getMapsender();

        intent.putExtra("source", source);
        intent.putExtra("senderId", sendDetailsBean.getMapsender().getSenderId());
        intent.putExtra("taskId", mapsender.getTaskId());
        intent.putExtra("procInsId", mapsender.getProcInsId());

        intent.putExtra("lgisticCode", mapsender.getLgisticCode());
        intent.putExtra("goodsname", mapsender.getGoodsname());
        intent.putExtra("cost", mapsender.getCost());
        intent.putExtra("goodsWeight", mapsender.getGoodsWeight());
        intent.putExtra("shipperCode", mapsender.getShipperCode());
        if (!mapsender.getSenderpro().equals(mapsender.getSendercity())) {
            intent.putExtra("sender", mapsender.getSendername() + " " + mapsender.getSenderpro() + mapsender.getSendercity() + mapsender.getSenderarea() + mapsender.getSenderaddress());
        } else {
            intent.putExtra("sender", mapsender.getSendername() + " " + mapsender.getSendercity() + mapsender.getSenderarea() + mapsender.getSenderaddress());
        }
        if (!mapsender.getRecepro().equals(mapsender.getRececity())) {
            intent.putExtra("recipients", mapsender.getReceivername() + " " + mapsender.getRecepro() + mapsender.getRececity() + mapsender.getRecearea() + mapsender.getReceaddress());
        } else {
            intent.putExtra("recipients", mapsender.getReceivername() + " " + mapsender.getRececity() + mapsender.getRecearea() + mapsender.getReceaddress());
        }
    }

}
