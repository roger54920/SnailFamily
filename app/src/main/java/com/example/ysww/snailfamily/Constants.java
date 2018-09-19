package com.example.ysww.snailfamily;

import android.os.Build;


/**
 * Created by me-jie on 2016/11/16.
 */
public class Constants {
    public static final int VERSION = Build.VERSION.SDK_INT; //android版本
    public static final int SUBSCRIPT_ZER0 = 0; //下标标注  0
    public static final int SUBSCRIPT_ONE = 1; //下标标注  1
    public static String SOURCE; //来源页面
    public static final long THIRTY_LONG = 600000;//long 预约时间
    public static boolean NETCONNECT;//网络是否连接
//        public static final String TOP = "http://10.2.0.25:8080/snaildak-dubbo-consumer/"; //链接头 电脑
//    public static final String TOP = "http://120.92.33.172:8080/app/a/";//链接头 开发服务器
//    public static final String TOP = "http://120.92.33.172:8082/app/a/";//链接头 测试服务器
    public static final String TOP = "http://www.snaildak.com:8002/snaildak-dubbo-app/";
//    public static final String TOP = "http://10.2.0.25:8080/snaildak-dubbo-app/";
    public static final String UP_LOAD_IMAGE_TOP = TOP + "download?relativePath=";//链接头 上传图片
}
