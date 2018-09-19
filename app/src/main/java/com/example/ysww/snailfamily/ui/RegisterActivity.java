package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.IfMobileExitBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.IfMobileExitView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.IfMobileExitPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.example.ysww.snailfamily.utils.TelNumMatch;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册页面
 */
public class RegisterActivity extends AutoLayoutActivity implements IfMobileExitView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.sr_number)
    EditText srNumber;
    @InjectView(R.id.btn_next_step)
    Button btnNextStep;
    @InjectView(R.id.select)
    CheckBox select;

    IfMobileExitPresenter ifMobileExitPresenter = new IfMobileExitPresenter();
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
    }

    /**
     * 初始化请求数据
     *
     * @param str
     */
    private void requestData(String str) {
        new OkHttpResolve(this);
        ifMobileExitPresenter.attach(this);
        ifMobileExitPresenter.postJsonResult(str,this,lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.btn_next_step, R.id.select})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.btn_next_step:
                if(!TextUtils.isEmpty(srNumber.getText().toString())) {
                    if (TelNumMatch.isPhone(srNumber.getText().toString()) == true) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestData("{\"mobile\":\"" + srNumber.getText().toString() + "\"}");
                    }else{
                        SkipIntentUtil.toastShow(this, "请正确输入手机号！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入手机号！");
                }
                break;
            case R.id.select:
                if (select.isChecked() == true) {
                    btnNextStep.setEnabled(true);
                } else {
                    btnNextStep.setEnabled(false);
                }
                break;
        }
    }


    @Override
    public void onBeanFinish(Object o) {
        IfMobileExitBean ifMobileExitBean = (IfMobileExitBean) o;
        if (ifMobileExitBean != null) {
            if (ifMobileExitBean.isIfMobileExit() ==false) {
                Intent intent = new Intent(RegisterActivity.this, WriteEfficacyActivity.class);
                intent.putExtra("phone", srNumber.getText().toString().trim());
                startActivity(intent);
            }else{
                SkipIntentUtil.toastShow(this, "手机号码已注册，请重新输入！");
            }
        }

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
    }
}
