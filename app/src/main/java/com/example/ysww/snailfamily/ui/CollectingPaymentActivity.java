package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.SearchCourierNumberBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SearchCourierNumberView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.SearchCourierNumberPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 代付货款 查询快递单号
 */
public class CollectingPaymentActivity extends AutoLayoutActivity implements SearchCourierNumberView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.search)
    EditText search;
    @InjectView(R.id.next_step_btn)
    Button nextStepBtn;

    private SearchCourierNumberPresenter searchCourierNumberPresenter = new SearchCourierNumberPresenter();//搜索快递单号
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_payment);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }
    private void initViews(){
        /**
         * 限制只能输入字母和数字，默认弹出数字输入法
         */
        search.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_PHONE;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData(R.string.express_number_only_can_input).toCharArray();
                return data;
            }
        });
    }
    public String getStringData(int id) {
        return getResources().getString(id);
    }
    /**
     * 初始化快递单号搜索
     */
    private void requesSearchCourierNumber(String json) {
        new OkHttpResolve(this);
        searchCourierNumberPresenter.attach(this);
        searchCourierNumberPresenter.postJsonSearchCourierNumberResult(json, this,lazyLoadProgressDialog);
    }
    @OnClick({R.id.return_arrows, R.id.next_step_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.next_step_btn:
                String seek = search.getText().toString();
                if (!TextUtils.isEmpty(seek)) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requesSearchCourierNumber("{\"search\":\"" + seek + "\"}");
                } else {
                    SkipIntentUtil.toastShow(this, "快递单号不能为空！");
                }
                break;
        }
    }

    @Override
    public void onSearchCourierNumberViewFinish(Object o) {
        SkipIntentUtil.searchCourierNumberSkip((SearchCourierNumberBean) o, this,intent, lazyLoadProgressDialog);
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
        searchCourierNumberPresenter.dettach();
    }
}
