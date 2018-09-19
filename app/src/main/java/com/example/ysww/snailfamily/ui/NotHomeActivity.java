package com.example.ysww.snailfamily.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.HomeShowDataAdvertisementBean;
import com.example.ysww.snailfamily.bean.HomeShowDataHeadlineBean;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.custom.GlideImageLoader;
import com.example.ysww.snailfamily.custom.UPMarqueeView;
import com.example.ysww.snailfamily.mvp.HomeShowDataView;
import com.example.ysww.snailfamily.mvp.LoginView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.net.NetBroadcastReceiver;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.HomeShowDataPresenter;
import com.example.ysww.snailfamily.presenter.LoginPresenter;
import com.example.ysww.snailfamily.utils.MyActivityManager;
import com.example.ysww.snailfamily.utils.NetUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 未登录首页
 */
public class NotHomeActivity extends BaseActivity implements LoginView, HomeShowDataView {
    private static final String TAG = "NotHomeActivity";
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.not_home)
    LinearLayout notHome;
    @InjectView(R.id.up_marquee_view)
    UPMarqueeView upMarqueeView;
    @InjectView(R.id.title)
    RelativeLayout title;
    @InjectView(R.id.banner)
    Banner banner;

    private LoginPresenter loginPresenter = new LoginPresenter();//登录
    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;
    private HomeShowDataPresenter homeShowDataPresenter = new HomeShowDataPresenter();//蜗牛头条
    private List<View> views = new ArrayList<>();  //头条布局
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_home);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            SkipIntentUtil.noNetworkPopUpWindows(this, null);
        } else {
            Constants.NETCONNECT = true;
        }
        initViews();
        if (Constants.VERSION >= 19) {
            title.setPadding(0, 70, 0, 0);
        } else {
            title.setPadding(0, 20, 0, 0);
        }


    }

    /**
     * 初始化页面
     */
    private void initViews() {
        requestHomeShowDataHeadline();
        requestHomeShowDataAdvertisement();

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sp.edit();
        String roleName = sp.getString("roleName", null);
        long timeout = sp.getLong("timeout", 0);
        if (roleName != null && timeout != 0) {
            if (System.currentTimeMillis() >= timeout) {
                editor.remove("login");
                editor.clear();
                editor.commit();
                SkipIntentUtil.skipIntent(this, LoginActivity.class);
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                MyActivityManager.getInstance().finishAllActivity();
                requestWriteOff();
            } else {
                if (roleName.equals("consumer")) {
                    SkipIntentUtil.skipIntent(this, BottomNavigationMenuActivity.class);
                    finish();
                }
            }
        }
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView(List<HomeShowDataHeadlineBean.ListBean> data) {
        for (int i = 0; i < data.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_top_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.top_tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.top_tv2);
            //进行对控件赋值
            tv1.setText(data.get(i).getContent());
            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).getContent());
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    @OnClick({R.id.btn_login, R.id.not_home})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                SkipIntentUtil.skipIntent(this, LoginActivity.class);
                finish();
                break;
            case R.id.not_home:
                SkipIntentUtil.toastShow(this, "请登录后查看！");
                break;
        }
    }

    /**
     * 初始化注销
     */
    private void requestWriteOff() {
        new OkHttpResolve(this);
        loginPresenter.attach(this);
        loginPresenter.userWriteOffResult(this);
    }

    /**
     * 初始化蜗牛头条
     */
    private void requestHomeShowDataHeadline() {
        new OkHttpResolve(this);
        homeShowDataPresenter.attach(this);
        homeShowDataPresenter.postJsonHomeShowDataHeadlineResult(this);
    }

    /**
     * 初始化 用户首页广告图
     */
    private void requestHomeShowDataAdvertisement() {
        new OkHttpResolve(this);
        homeShowDataPresenter.attach(this);
        homeShowDataPresenter.postJsonHomeShowDataAdvertisementResult(this);
    }
    @Override
    public void onBeanLoginFinish(Object o) {
    }

    @Override
    public void onBeanWriteOffFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            Log.e(TAG, "onBeanWriteOffFinish: 注销成功！");
        }
    }

    @Override
    public void onHomeShowDataHeadlineFinish(Object o) {
        HomeShowDataHeadlineBean homeShowDataHeadlineBean = (HomeShowDataHeadlineBean) o;
        if (homeShowDataHeadlineBean != null) {
            //设置头条
            setView(homeShowDataHeadlineBean.getList());
            upMarqueeView.setViews(views);
        }
    }

    @Override
    public void onHomeShowDataMessageFinish(Object o) {

    }

    @Override
    public void onHomeShowDataAdvertisementFinish(Object o) {
        HomeShowDataAdvertisementBean homeShowDataAdvertisementBean = (HomeShowDataAdvertisementBean) o;
        if(homeShowDataAdvertisementBean!=null){
            List<HomeShowDataAdvertisementBean.AdvertisementListBean> advertisementList = homeShowDataAdvertisementBean.getAdvertisementList();
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i <advertisementList.size() ; i++) {
                imagesUrl.add(Constants.UP_LOAD_IMAGE_TOP+advertisementList.get(i).getFilePath());
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(imagesUrl);
            banner.start();
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, null);
        }
    }
    /**
     * Android中的“再按一次返回键退出程序”实现
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出蜗牛快递！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyActivityManager.getInstance().finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        loginPresenter.dettach();
        homeShowDataPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        //结束轮播
        banner.stopAutoPlay();
    }
}
