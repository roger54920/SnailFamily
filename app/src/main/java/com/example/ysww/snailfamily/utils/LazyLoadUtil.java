package com.example.ysww.snailfamily.utils;

import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.lzy.okgo.OkGo;

/**
 * Created by me-jie on 2017/4/19.
 * 休眠Button 按钮
 */

public class LazyLoadUtil {
    /**
     * 15秒LazyLoadProgressDialog
     * @param dialog
     */
    public static void SetLazyLad(final LazyLoadProgressDialog dialog){
        final LazyLoadProgressDialog lazyLoadProgressDialog = dialog;
        //15秒内加载没有反应，直接关闭
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(30000);//休眠15秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
               if(lazyLoadProgressDialog!=null){
                    lazyLoadProgressDialog.cancel();
                }
            }
        }.start();
    }

    /**
     * 成功返回关闭LazyLoadProgressDialog
     * @param dialog
     */
    public static void SetLazyLadResult(LazyLoadProgressDialog dialog){
        final LazyLoadProgressDialog lazyLoadProgressDialog = dialog;
        //15秒内加载没有反应，直接关闭
       if(lazyLoadProgressDialog!=null){
            lazyLoadProgressDialog.cancel();
        }
    }

    /**
     * 1分钟以后关闭LazyLoadProgressDialog
     * @param dialog
     */
    public static void SetLazyLad0neMinute(LazyLoadProgressDialog dialog){
        final LazyLoadProgressDialog lazyLoadProgressDialog = dialog;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(OkGo.DEFAULT_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
                if(lazyLoadProgressDialog!=null){
                    lazyLoadProgressDialog.cancel();
                }
            }
        }.start();
    }

}
