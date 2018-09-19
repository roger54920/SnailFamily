package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.bean.SearchCourierNumberBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AcceptPayApplayView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AcceptPayApplayPresenter;
import com.example.ysww.snailfamily.utils.GadgetUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 代付货款  收快递
 */
public class PaidPaymentGoodsAcceptDeliveryActivity extends AutoLayoutActivity implements AcceptPayApplayView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.apply_pay_btn)
    Button applyPayBtn;
    @InjectView(R.id.paid_amount)
    EditText paidAmount;
    @InjectView(R.id.paid_amount_rl)
    RelativeLayout paidAmountRl;
    @InjectView(R.id.bottom_ll)
    LinearLayout bottomLl;

    private Intent intent;
    private List<HistoicFlowBean.ListTrajectoryBean> listTrajectory;//物流信息
    private AcceptPayApplayPresenter acceptPayApplayPresenter = new AcceptPayApplayPresenter();//申请到付
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_payment_goods_accept_delivery);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }

    private void initViews() {
        GadgetUtil.setPricePoint(paidAmount);
        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        intent = getIntent();
        if (intent != null) {
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
            String type = intent.getStringExtra("type");
            if (TextUtils.isEmpty(type) || !type.equals("2")) {
                bottomLl.setVisibility(View.VISIBLE);
            } else {
                bottomLl.setVisibility(View.GONE);
            }
            List<SearchCourierNumberBean.TrackBean> track = (List<SearchCourierNumberBean.TrackBean>) intent.getSerializableExtra("track");
            if (track != null && track.size() > 0) {
                listTrajectory = new ArrayList<>();
                for (int i = 0; i < track.size(); i++) {
                    HistoicFlowBean.ListTrajectoryBean trajectory = new HistoicFlowBean.ListTrajectoryBean();
                    trajectory.setPContent(track.get(i).getPContent());
                    trajectory.setPDate(track.get(i).getPDate());
                    listTrajectory.add(trajectory);
                }
                timelineRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
                timelineRecyclerview.setAdapter(new TimeLineAdapter(this, listTrajectory));
            }

        }
    }

    /**
     * 初始化申请到付
     */
    private void requestAcceptPayApplayResult(String json) {
        new OkHttpResolve(this);
        acceptPayApplayPresenter.attach(this);
        acceptPayApplayPresenter.postJsonAcceptPayApplayResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.apply_pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.apply_pay_btn:
                String paid = paidAmount.getText().toString();
                String type = getIntent().getStringExtra("type");
                if (TextUtils.isEmpty(type) || !type.equals("2")) {
                    if (!TextUtils.isEmpty(paid)) {
                        String lastStr = paid.substring(paid.length() - 1, paid.length());
                        if (!lastStr.equals(".") && !paid.equals("0.0") && !paid.equals("0.00") && !paid.equals("0.")) {
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            requestAcceptPayApplayResult("{\"lgisticCode\":\"" + getIntent().getStringExtra("lgisticCode") + "\",\"cost\":\"" + paid + "\"}");
                        } else {
                            SkipIntentUtil.toastShow(this, "请正确输入金额！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入代付金额！");
                    }
                }
                break;
        }
    }

    @Override
    public void onAcceptPayApplayFinish(Object o) {
        BaseBean acceptPayApplay = (BaseBean) o;
        if (acceptPayApplay != null) {
            SkipIntentUtil.skipIntent(this, BottomNavigationMenuActivity.class);
            finish();
        }
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
        acceptPayApplayPresenter.dettach();
    }
}
