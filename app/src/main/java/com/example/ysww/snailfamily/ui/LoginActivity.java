package com.example.ysww.snailfamily.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.IfMobileExitBean;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.example.ysww.snailfamily.custom.GlideCircleTransform;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AllRegionView;
import com.example.ysww.snailfamily.mvp.IfMobileExitView;
import com.example.ysww.snailfamily.mvp.LoginView;
import com.example.ysww.snailfamily.mvp.VerificationCodeView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AllRegionPresenter;
import com.example.ysww.snailfamily.presenter.IfMobileExitPresenter;
import com.example.ysww.snailfamily.presenter.LoginPresenter;
import com.example.ysww.snailfamily.presenter.VerificationCodePresenter;
import com.example.ysww.snailfamily.utils.BitmapUtil;
import com.example.ysww.snailfamily.utils.CacheUtils;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.example.ysww.snailfamily.utils.TelNumMatch;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ysww.snailfamily.utils.BitmapUtil.fileIsExists;


/**
 * 登录页面
 */
public class LoginActivity extends AutoLayoutActivity implements LoginView, IfMobileExitView, VerificationCodeView, AllRegionView {
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.btn_register)
    Button btnRegister;
    @InjectView(R.id.obtain_code)
    Button obtainCode;
    @InjectView(R.id.sr_number)
    EditText srNumber;
    @InjectView(R.id.sr_verification)
    EditText srVerification;
    @InjectView(R.id.circle)
    ImageView circle;

    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler();

    private IfMobileExitPresenter ifMobileExitPresenter = new IfMobileExitPresenter();//是否注册手机号
    private VerificationCodePresenter verificationCodePresenter = new VerificationCodePresenter(); //获取验证码
    private LoginPresenter loginPresenter = new LoginPresenter();//登录
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private String phone = "";//手机号

    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;
    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();//全国地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();

    }

    /**
     * 初始化布局
     */
    private void initViews() {
        btnLogin.setClickable(true);
        btnRegister.setClickable(true);
        srNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String srphone = srNumber.getText().toString();
                if (TelNumMatch.isPhone(srphone)) {
                    String sdPath = BitmapUtil.getSDPath();
                    if (!TextUtils.isEmpty(sdPath)) {
                        sdPath = sdPath + "/thesnailfamily/" + srphone + ".jpg";
                        boolean b = fileIsExists(sdPath);
                        if (b == true) {
                            Bitmap diskBitmap = BitmapUtil.getDiskBitmap(sdPath);
                            if(diskBitmap!=null){
                                circle.setImageBitmap(BitmapUtil.toRoundBitmap(diskBitmap));
                            }else{
                                circle.setImageResource(R.drawable.circle_shape);
                            }
                        } else {
                            circle.setImageResource(R.drawable.circle_shape);
                        }
                    } else {
                        circle.setImageResource(R.drawable.circle_shape);
                    }
                } else {
                    circle.setImageResource(R.drawable.circle_shape);
                }
            }
        });
        if (getIntent().getStringExtra("mobile") != null) {
            srNumber.setText(getIntent().getStringExtra("mobile"));
        }
    }

    /**
     * 初始化登录
     *
     * @param str
     */
    private void requestLoginVerification(String str) {
        new OkHttpResolve(this);
        loginPresenter.attach(this);
        loginPresenter.postJsonLoginVerificationResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化是否注册手机号
     *
     * @param str
     */
    private void requestifMobileExit(String str) {
        new OkHttpResolve(this);
        ifMobileExitPresenter.attach(this);
        ifMobileExitPresenter.postJsonResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化请求验证码
     *
     * @param str
     */
    private void requestverificationCode(String str) {
        new OkHttpResolve(this);
        verificationCodePresenter.attach(this);
        verificationCodePresenter.postJsonCodeResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 全国地区初始化请求数据
     */

    private void requestDataAllRegion() {
        new OkHttpResolve(this);
        allRegionPresenter.attach(this);
        allRegionPresenter.postAllRegionJsonResult("{\"id\":\" \"}", this, null);
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.obtain_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.obtain_code:
                phone = srNumber.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (TelNumMatch.isPhone(phone) == true) {
                        srVerification.setText("");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestifMobileExit("{\"mobile\":\"" + phone + "\",\"roleName\":\"consumer\"}");
                    } else {
                        SkipIntentUtil.toastShow(this, "请正确输入手机号！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入手机号！");
                }
                break;
            case R.id.btn_login:
                phone = srNumber.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    if (TelNumMatch.isPhone(phone) == true) {
                        String srverification = srVerification.getText().toString().trim();
                        if (srverification.length() == 6) {
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            HashMap<String, String> params = new HashMap<>();
                            params.put("username", phone);
                            params.put("rolename", "consumer");
                            params.put("appLogin", "true");
                            params.put("validateCode", srverification);
                            JSONObject jsonObject = new JSONObject(params);
                            requestLoginVerification(jsonObject.toString());
                        } else {
                            SkipIntentUtil.toastShow(this, "请正确输入验证码！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请正确输入手机号！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入手机号！");
                }

                break;
            case R.id.btn_register:
                SkipIntentUtil.skipIntent(this, RegisterActivity.class);
                break;
        }
    }


    @Override
    public void onBeanLoginFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            sp = getSharedPreferences("login", Context.MODE_PRIVATE);
            editor = sp.edit();
            if (loginBean.getUser().getRoleName() != null) {
                btnLogin.setClickable(false);
                btnRegister.setClickable(false);
                String roleName = loginBean.getUser().getRoleName();
                long sessionTimeout = loginBean.getSessionTimeout();
                final String mobile = loginBean.getUser().getMobile();
                String photo = loginBean.getUser().getPhoto();
                if (roleName.equals("consumer")) {
                    editor.putString("roleName", roleName);
                    editor.putLong("timeout", System.currentTimeMillis() + sessionTimeout);
                    editor.putString("photo", photo);
                    editor.putString("mobile", mobile);
                    editor.commit();

                    Glide.with(this).load(Constants.UP_LOAD_IMAGE_TOP + photo)
                            .asBitmap()
                            .transform(new GlideCircleTransform(this))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                            .placeholder(circle.getDrawable())//加载成功之前
                            .error(circle.getDrawable())//加载失败
                            .crossFade()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    BitmapUtil.saveImageToGallery(resource, mobile);
                                }
                            });

                    SkipIntentUtil.skipIntent(this, BottomNavigationMenuActivity.class);
                    requestDataAllRegion();
                }
            }
        }
    }

    @Override
    public void onBeanWriteOffFinish(Object o) {
    }

    @Override
    public void onBeanFinish(Object o) {
        IfMobileExitBean ifMobileExitBean = (IfMobileExitBean) o;
        if (ifMobileExitBean != null) {
            if (ifMobileExitBean.isIfMobileExit() == true) {
                requestverificationCode("{\"mobile\":\"" + phone + "\"}");
            } else {
                SkipIntentUtil.toastShow(this, "账号不存在，请检查后登录！");
            }
        }

    }

    @Override
    public void onBeanVerificationCodeFinish(Object o) {
        LoginBean verificationCodeBean = (LoginBean) o;
        if (verificationCodeBean != null) {
            new Thread(new MyCountDownTimer()).start();//开始执行
        }
    }

    @Override
    public void onInputBeanFinish(Object o) {

    }

    @Override
    public void onAllRegionListFinish(Object o) {
        final NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) o;
        if (newAddressRegionBean != null) {
            new AsyncTask<String, Void, NewAddressRegionBean>() {
                @Override
                protected NewAddressRegionBean doInBackground(String... params) {
                    CacheUtils.writeJson(LoginActivity.this, newAddressRegionBean, "nationalregions.txt", false,this);
                    return null;
                }
            }.execute();
        }
    }

    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class MyCountDownTimer implements Runnable {

        @Override
        public void run() {

            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        obtainCode.setBackgroundResource(R.drawable.verify_btn_shape_nor);
                        obtainCode.setClickable(false);
                        obtainCode.setText(T + "秒");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    obtainCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
                    obtainCode.setClickable(true);
                    obtainCode.setText("重新获取");
                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.skipIntent(this, NotHomeActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        ifMobileExitPresenter.dettach();
        verificationCodePresenter.dettach();
        loginPresenter.dettach();
        allRegionPresenter.dettach();
        if (mHandler != null) {
            mHandler.removeCallbacks(new MyCountDownTimer());
        }
    }
}
