package com.example.ysww.snailfamily.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysww.snailfamily.bean.SearchCourierNumberBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.NoNetworkDialog;
import com.example.ysww.snailfamily.ui.BottomNavigationMenuActivity;
import com.example.ysww.snailfamily.ui.LoginActivity;
import com.example.ysww.snailfamily.ui.MipcaActivityCapture;
import com.example.ysww.snailfamily.ui.PaidPaymentGoodsAcceptDeliveryActivity;
import com.example.ysww.snailfamily.ui.shopping.PlaceOrderActivity;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.io.Serializable;


/**
 * Created by me-jie on 2017/4/27.
 * 页面小工具
 */

public class SkipIntentUtil {
    /**
     * 跳转页面
     *
     * @param activity
     * @param cl
     */
    public static void skipIntent(Activity activity, Class cl) {
        Intent intent = new Intent(activity, cl);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
    }

    /**
     * 对toast进行一个简单的封装
     *
     * @param activity
     * @param msg
     */
    private static Toast mToast;

    public static void toastShow(Activity activity, String msg) {
        if (null == mToast) {
            mToast = Toast.makeText(activity, msg,
                    Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.CENTER|Gravity.BOTTOM, 40, 0);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    //取消Toast
    public static void toastStop() {
        if (null != mToast) {
            mToast.cancel();
        }
    }

    /**
     * 返回登录页面
     *
     * @param activity
     */
    public static void returnLogin(Activity activity, String msg) {
        SkipIntentUtil.toastShow(activity, msg);
        SharedPreferences sp = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("login");
        editor.clear();
        editor.commit();
        SkipIntentUtil.skipIntent(activity, LoginActivity.class);
        MyActivityManager.getInstance().finishAllActivity();
    }

    /**
     * 无网络时弹窗
     *
     * @param activity
     */
    public static void noNetworkPopUpWindows(Activity activity, LazyLoadProgressDialog dialog) {
        if (dialog != null) {
            LazyLoadUtil.SetLazyLadResult(dialog);
            final NoNetworkDialog noNetworkDialog = new NoNetworkDialog(activity).setNoNetworkDialog();
            if (!activity.isFinishing()) {
                noNetworkDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noNetworkDialog.dismiss();
                    }
                }, 1000);
            }
        }

    }

    /**
     * 隐藏和显示时间轴
     *
     * @param zoom
     * @param rv
     */
    public static void zoomTimerShaft(TextView zoom, RecyclerView rv) {
        String zoomVal = zoom.getText().toString();
        if (zoomVal.equals("收起")) {
            zoom.setText("展开");
            rv.setVisibility(View.GONE);
        } else if (zoomVal.equals("展开")) {
            zoom.setText("收起");
            rv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 返回来源页面 首页点击扫二维码 收件
     */
    public static void sourceAcceptReturn(Activity activity, String source) {
        if (source.contains("mipca_capture")) {
            SkipIntentUtil.skipIntent(activity, MipcaActivityCapture.class);
            activity.finish();
        } else {
            activity.finish();
        }
    }

    /**
     * 返回来源页面 首页点击扫二维码 寄件
     */
    public static void sourceSenderReturn(Activity activity, String source) {
        if (source.contains("mipca_capture")) {
            SkipIntentUtil.skipIntent(activity, MipcaActivityCapture.class);
            activity.finish();
        } else {
            activity.finish();
        }
    }

    /**
     * RV列表刷新超时
     *
     * @param activity
     */
    public static void rvRefreshTimeout(final Activity activity, final TwinklingRefreshLayout refreshLayout, int state) {
        if (state == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final NoNetworkDialog noNetworkDialog = new NoNetworkDialog(activity).setNoNetworkDialog();
                    if (!activity.isFinishing()) {
                        noNetworkDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!activity.isFinishing()) {
                                    noNetworkDialog.dismiss();
                                }
                            }
                        }, 1000);
                    }
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            }, 30000);
        } else {
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }

    }

    /**
     * RV刷新列表成功
     *
     * @param commonAdapter
     * @param refreshLayout
     */
    public static void rvRefreshSuccess(CommonAdapter commonAdapter, TwinklingRefreshLayout refreshLayout) {
        if (commonAdapter != null) {
            commonAdapter.notifyDataSetChanged();
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 输入法是否显示着
     *
     * @param edittext
     * @return
     */
    public static boolean KeyBoard(EditText edittext) {
        boolean bool = false;
        InputMethodManager imm = (InputMethodManager) edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            bool = true;
        }
        return bool;

    }

    /**
     * 搜索快递单号跳转页面
     *
     * @param searchCourierNumberBean
     * @param activity
     * @param intent
     * @param lazyLoadProgressDialog
     */
    public static void searchCourierNumberSkip(SearchCourierNumberBean searchCourierNumberBean, Activity activity, Intent intent, LazyLoadProgressDialog lazyLoadProgressDialog) {
        if (searchCourierNumberBean != null) {
            intent = new Intent(activity, PaidPaymentGoodsAcceptDeliveryActivity.class);
            intent.putExtra("shipperCode", searchCourierNumberBean.getShipperCode());
            intent.putExtra("lgisticCode", searchCourierNumberBean.getLgisticCode());
            intent.putExtra("type", searchCourierNumberBean.getType());
            intent.putExtra("track", (Serializable) searchCourierNumberBean.getTrack());
            activity.startActivity(intent);
            LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
        }
    }

    /**
     * 关闭AsyncTask
     *
     * @param task
     */
    public static void closeAsyncTask(AsyncTask task) {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    /**
     * 返回首页
     * @param activity
     */
    public static void returnHomeFinish(Activity activity) {
        SkipIntentUtil.skipIntent(activity, BottomNavigationMenuActivity.class);
        activity.finish();
    }

    /**
     * 转换金额格式
     * @param conversionAmount
     */
    public static String conversionAmountFormat(String conversionAmount){
        if(!TextUtils.isEmpty(conversionAmount)) {
            String after = conversionAmount.substring(conversionAmount.length() - 2, conversionAmount.length());
            String front = conversionAmount.substring(0, conversionAmount.length() - 2);
            return front + "." + after;
        }
        return null;
    }
    /**
     * 提交订单
     * @param totalQuantity
     * @param activity
     */
    public static void goAccounts(int totalQuantity,Activity activity){
        if(totalQuantity>0){
            SkipIntentUtil.skipIntent(activity, PlaceOrderActivity.class);
        }else{
            Toast.makeText(activity, "您未添加商品！", Toast.LENGTH_SHORT).show();
        }
    }
}
