package com.example.ysww.snailfamily.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.NewAddressSaveBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SnailElectronView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.SnailElectronPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 新增地址 设置定制电子包裹编号
 */
public class ElectronParcelNumberActivity extends AutoLayoutActivity implements SnailElectronView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.sr_electron_number)
    EditText srElectronNumber;
    @InjectView(R.id.confirm_btn)
    Button confirmBtn;
    @InjectView(R.id.face)
    ImageView face;
    @InjectView(R.id.dz_number)
    TextView dzNumber;
    @InjectView(R.id.hong_hint)
    TextView hong_hint;
    private SnailElectronPresenter snailElectronPresenter = new SnailElectronPresenter();
    private LazyLoadProgressDialog progressDialog;//延迟加载
    private String electronNumber;//输入的电子包裹箱号
    private Handler handler = new Handler();
    Intent intent;
    private boolean ifSnailElectron = false;//是否通过验证

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electron_parcel_number);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        progressDialog = progressDialog.createDialog(this);
        srElectronNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“放大镜”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    electronNumber = srElectronNumber.getText().toString();
                    if (!TextUtils.isEmpty(electronNumber)) {
                        int length = electronNumber.length();
                        if (length >= 4 && length <= 10) {
                            progressDialog.show();
                            LazyLoadUtil.SetLazyLad(progressDialog);
                            requestDataSnail("{\"parcelNo\":\"" + electronNumber + "\"}");
                        } else {
                            if( SkipIntentUtil.KeyBoard(srElectronNumber)==true){
                                imm.showSoftInput(srElectronNumber, InputMethodManager.SHOW_FORCED);
                            }
                            SkipIntentUtil.toastShow(ElectronParcelNumberActivity.this, "电子包裹箱号长度大于3或小于11！");
                        }
                    } else {
                        if( SkipIntentUtil.KeyBoard(srElectronNumber)==true){
                            imm.showSoftInput(srElectronNumber, InputMethodManager.SHOW_FORCED);
                        }
                        SkipIntentUtil.toastShow(ElectronParcelNumberActivity.this, "电子包裹箱号不能为空！");
                    }
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * 电子包裹箱初始化请求数据
     */
    private void requestDataSnail(String json) {
        new OkHttpResolve(this);
        snailElectronPresenter.attach(this);
        snailElectronPresenter.ifSnailElectronResult(json, this, progressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                intent = new Intent();
                setResult(Constants.SUBSCRIPT_ZER0, intent);
                finish();
                break;
            case R.id.confirm_btn:
                if (!TextUtils.isEmpty(srElectronNumber.getText().toString())) {
                    if (ifSnailElectron == true) {
                        if (electronNumber != null) {
                            intent = new Intent();
                            intent.putExtra("electronNumber", electronNumber);
                            setResult(Constants.SUBSCRIPT_ZER0, intent);
                            finish();
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请先验证电子包裹箱是否有效！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请输入电子包裹箱！");
                }

                break;
        }
    }

    @Override
    public void onIfSnailElectronFinish(Object o) {
        NewAddressSaveBean snailElectronBean = (NewAddressSaveBean) o;
        if (snailElectronBean != null) {
            if (snailElectronBean.isIfParcelBeAbleUsed() == true) {
                ifSnailElectron = true;
                face.setVisibility(View.VISIBLE);
                dzNumber.setText(electronNumber);
                hong_hint.setText("");
            } else {
                ifSnailElectron = false;
                face.setVisibility(View.INVISIBLE);
                dzNumber.setText("");
                hong_hint.setText(snailElectronBean.getMsg());
            }
        } else {
            ifSnailElectron = false;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            intent = new Intent();
            setResult(Constants.SUBSCRIPT_ZER0, intent);
            finish();
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
        snailElectronPresenter.dettach();

    }
}
