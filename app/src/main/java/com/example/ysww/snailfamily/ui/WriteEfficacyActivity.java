package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.bean.User;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.VerificationCodeView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.VerificationCodePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ysww.snailfamily.R.id.btn_next_step;
import static com.example.ysww.snailfamily.R.id.obtain_code;

/**
 * 填写校验码页面
 */
public class WriteEfficacyActivity extends AutoLayoutActivity implements VerificationCodeView {
    @InjectView(R.id.number)
    TextView number;
    @InjectView(R.id.input_verificationCode)
    EditText inputVerificationCode;
    @InjectView(R.id.obtain_code)
    Button obtainCode;
    @InjectView(R.id.btn_next_step)
    Button btnNextStep;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    private Intent intent;

    private VerificationCodePresenter verificationCodePresenter = new VerificationCodePresenter();
    private String phone;

    public int T = 60; //倒计时时长
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private CountDownTimer timer = null;//定时器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_efficacy);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        initViews();


    }

    /**
     * 初始化页面数据
     */
    private void initViews() {
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        if (intent != null) {
            phone = intent.getStringExtra("phone");
            number.setText("请输入手机号：" + phone + "收到的短信验证码");
        }
    }

    /**
     * 初始化String请求数据
     *
     * @param str
     */
    private void requestverificationCode(String str) {
        new OkHttpResolve(this);
        verificationCodePresenter.attach(this);
        verificationCodePresenter.postJsonCodeResult(str, this, lazyLoadProgressDialog);
    }

    private void requestverificationCodeInput(String str) {
        new OkHttpResolve(this);
        verificationCodePresenter.attach(this);
        verificationCodePresenter.postJsonResult(str, this, lazyLoadProgressDialog);
    }

    @OnClick({obtain_code, btn_next_step, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case obtain_code:
                if (phone != null) {
                    inputVerificationCode.setText("");
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestverificationCode("{\"mobile\":\"" + phone + "\"}");
                }
                break;
            case btn_next_step:
                String code = inputVerificationCode.getText().toString().trim();
                if (!TextUtils.isEmpty(code)) {
                    if (code.length() == 6) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        HashMap<String, String> params = new HashMap<>();
                        params.put("mobile", phone);
                        params.put("checkCode", code);
                        JSONObject jsonObject = new JSONObject(params);
                        requestverificationCodeInput(jsonObject.toString());
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入验证码！");
                }
                break;
            case R.id.return_arrows:
                finish();
                break;
        }
    }
    @Override
    public void onBeanVerificationCodeFinish(Object o) {
        LoginBean verificationCodeBean = (LoginBean) o;
        if (verificationCodeBean != null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    obtainCode.setEnabled(false);
                    obtainCode.setText((millisUntilFinished / 1000) + "秒");
                    obtainCode.setBackgroundResource(R.drawable.verify_btn_shape_nor);
                }

                @Override
                public void onFinish() {
                    obtainCode.setEnabled(true);
                    obtainCode.setText("重新获取");
                    obtainCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
                }
            };
            timer.start();//开始执行
        }
    }

    @Override
    public void onInputBeanFinish(Object o) {
        LoginBean inputVerificationCodeBean = (LoginBean) o;
        if (inputVerificationCodeBean != null) {
            String commitStatus = inputVerificationCodeBean.getStatus();
            User user = inputVerificationCodeBean.getUser();
            if (commitStatus != null) {
                if (user != null) {
                    Intent intent = new Intent(WriteEfficacyActivity.this, PersonalMessageMaintainActivity.class);
                    intent.putExtra("user_id", user.getId());
                    intent.putExtra("mobile", phone);
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            obtainCode.setEnabled(true);
            obtainCode.setText("重新获取");
            obtainCode.setBackgroundResource(R.drawable.verify_btn_shape_sel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        verificationCodePresenter.dettach();
        if (timer != null) {
            timer.cancel();
        }
    }
}
