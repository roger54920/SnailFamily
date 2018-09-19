package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.custom.FullPhotoView;
import com.example.ysww.snailfamily.custom.RatingBar;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.UserEvaluateView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.UserEvaluatePresenter;
import com.example.ysww.snailfamily.utils.GadgetUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static java.lang.Integer.parseInt;

/**
 * 用户确认  评价
 */
public class UserAffirmActivity extends AutoLayoutActivity implements UserEvaluateView {

    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;
    @InjectView(R.id.submit_evaluate_btn)
    Button submitEvaluateBtn;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.service_radiogroup)
    RadioGroup serviceRadiogroup;
    @InjectView(R.id.delivery_radiogroup)
    RadioGroup deliveryRadiogroup;
    @InjectView(R.id.running_errands_fee)
    EditText runningErrandsFee;
    @InjectView(R.id.one_money)
    CheckBox oneMoney;
    @InjectView(R.id.five_money)
    CheckBox fiveMoney;
    @InjectView(R.id.ten_money)
    CheckBox tenMoney;

    private RadioButton service_rb;//服务
    private RadioButton delivery_rb;//送货速度

    private String service_tv;//服务
    private String delivery_tv;//送货速度
    private String giveReward_tv;//打赏

    private String ratingCount;//评价星星的个数
    private Intent intent;

    private UserEvaluatePresenter userEvaluatePresenter = new UserEvaluatePresenter();//用户评价
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_affirm);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }

    private void initViews() {
        GadgetUtil.setPricePoint(runningErrandsFee);
        intent = getIntent();
        if (intent != null) {
            RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
        }

        ratingBar.setClickable(true);//设置可否点击
        ratingBar.setStar(0);//默认设置显示的星星个数
        ratingBar.setStepSize(RatingBar.StepSize.Half);//设置每次点击增加一颗星还是半颗星
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float count) {//点击星星变化后选中的个数
                ratingCount = count + "";
            }
        });
        //服务
        serviceRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                service_rb = (RadioButton) findViewById(checkedId);
                service_tv = service_rb.getTag().toString();
            }
        });
        //送货速度
        deliveryRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                delivery_rb = (RadioButton) findViewById(checkedId);
                delivery_tv = delivery_rb.getTag().toString();
            }
        });

        isGiveRewardChecked();
    }

    /**
     * 是否选中打赏
     */
    private void isGiveRewardChecked() {
        //监听器 取消选中
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!((CheckBox) v).isChecked()) {
                    giveReward_tv = null;
                }
            }
        };
        //选中
        CompoundButton.OnCheckedChangeListener occl = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    oneMoney.setChecked(false);
                    fiveMoney.setChecked(false);
                    tenMoney.setChecked(false);
                    buttonView.setChecked(true);
                    giveReward_tv = buttonView.getTag().toString();
                }
            }
        };

        oneMoney.setOnClickListener(ocl);
        fiveMoney.setOnClickListener(ocl);
        tenMoney.setOnClickListener(ocl);
        oneMoney.setOnCheckedChangeListener(occl);
        fiveMoney.setOnCheckedChangeListener(occl);
        tenMoney.setOnCheckedChangeListener(occl);

    }

    /**
     * 初始化用户评价信息
     */
    private void requestRecipientsMessageResult(String json) {

        new OkHttpResolve(this);
        userEvaluatePresenter.attach(this);
        userEvaluatePresenter.postJsonUserEvaluateResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.submit_evaluate_btn, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_evaluate_btn:
                if (ratingCount != null) {
                    if (service_tv != null) {
                        if (delivery_tv != null) {
                            if (giveReward_tv == null && TextUtils.isEmpty(runningErrandsFee.getText())) {
                                //直接提交
                                lazyLoadProgressDialog.show();
                                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                HashMap<String, Object> params = new HashMap<>();
                                params.put("businessId", getIntent().getStringExtra("businessId"));
                                params.put("deliverySpeed", parseInt(delivery_tv));
                                params.put("serviceAttitude", parseInt(service_tv));
                                params.put("score", Double.parseDouble(ratingCount));
                                JSONObject jsonObject = new JSONObject(params);
                                requestRecipientsMessageResult(jsonObject.toString());
                            } else {
                                String paid = runningErrandsFee.getText().toString();
                                String lastStr = paid.substring(paid.length() - 1, paid.length());
                                if (!lastStr.equals(".") && !paid.equals("0.0") && !paid.equals("0.00") && !paid.equals("0.")) {
                                    //跳转支付
                                    intent = new Intent(this, AliPayModeActivity.class);
                                    intent.putExtra("source", "userAffirm");
                                    intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
                                    intent.putExtra("shipperCode", getIntent().getStringExtra("shipperCode"));
                                    intent.putExtra("lgisticCode", getIntent().getStringExtra("lgisticCode"));
                                    //金额使用 Double.parseDouble(金额);
                                    startActivity(intent);
                                } else {
                                    SkipIntentUtil.toastShow(this, "请正确输入金额");
                                }
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请评价蜗牛小哥送货速度！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请评价蜗牛小哥服务态度！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请为蜗牛顾家打分！");
                }
                break;
            case R.id.return_arrows:
                returnAddress();
                break;
        }
    }


    @Override
    public void onUserEvaluateFinish(Object o) {
        BaseBean baseBean = (BaseBean) o;
        if (baseBean != null) {
            returnAddress();
        }
    }

    /**
     * 完成返回收件列表
     */
    private void returnAddress() {
        intent = new Intent(this, AddresseeActivity.class);
        intent.putExtra("source", getIntent().getStringExtra("home_source"));
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            returnAddress();
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
        userEvaluatePresenter.dettach();
    }
}
