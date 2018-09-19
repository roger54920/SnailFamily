package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.bean.ChatBean;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.custom.FullPhotoView;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.mvp.ParcelParticularsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;
import com.example.ysww.snailfamily.presenter.ParcelParticularsPresenter;
import com.example.ysww.snailfamily.utils.AcquisitionTimeUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 包裹详情 预设收件时间  确认接收  + 修改预约送
 */

public class ParcelParticularsPredictActivity extends AutoLayoutActivity implements ParcelParticularsView, OnDateSetListener, HistoicFlowView {

    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.chat_recyclerview)
    RecyclerView chatRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.exit_parcel)
    Button exitParcel;
    @InjectView(R.id.postpone_parcel)
    Button postponeParcel;
    @InjectView(R.id.yj)
    TextView yj;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.datetime)
    TextView datetime;
    @InjectView(R.id.affirm_reception)
    Button affirmReception;
    @InjectView(R.id.zoom)
    TextView zoom;

    private List<ChatBean> chatList;

    private Intent intent;

    //时间选择器
    TimePickerDialog mDialogMonthDayHourMinute;
    private ParcelParticularsPresenter parcelParticularsPresenter = new ParcelParticularsPresenter();//收件操作
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_particulars_predict);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);


        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        timelineRecyclerview.setFocusable(false);
        chatRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        if (intent != null) {
            Constants.SOURCE = intent.getStringExtra("source");
            RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
            switch (Constants.SOURCE) {
                case "sendImmediately":
                    yj.setText("预约送时间为:");
                    datetime.setText(Html.fromHtml("<u>" + intent.getStringExtra("time") + "</u>"));
                    affirmReception.setText("立即送");
                    break;
                case "mipca_capture_sendImmediately":
                    yj.setText("预约送时间为:");
                    datetime.setText(Html.fromHtml("<u>" + intent.getStringExtra("time") + "</u>"));
                    affirmReception.setText("立即送");
                    break;
                case "singExpress":
                    yj.setText("预计收件时间为:");
                    yj.setVisibility(View.GONE);
                    datetime.setVisibility(View.GONE);
                    affirmReception.setText("确认接收");
                    break;
                case "mipca_capture_singExpress":
                    yj.setText("预计收件时间为:");
                    yj.setVisibility(View.GONE);
                    datetime.setVisibility(View.GONE);
                    affirmReception.setText("确认接收");
                    break;
            }
            String acceptId = getIntent().getStringExtra("expressAccept_id");
            if (acceptId != null) {
                histoicFlowPresenter.attach(this);
                RequestOperationUtil.setAddresseeHistoicFlowResult(acceptId, this, histoicFlowPresenter);
            }
        }


        //聊天适配器
//        initMsg();
//        chatRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
//        chatRecyclerview.setAdapter(new AddresseeCharAdapter(this, chatList));
    }

    private void initMsg() {
        if (chatList == null) {
            chatList = new ArrayList<>();
            ChatBean msg1 = new ChatBean("请问几点可以送货？", ChatBean.TYPE_RECEIVE);
            chatList.add(msg1);
            ChatBean msg2 = new ChatBean("今天13:30吧！", ChatBean.TYPE_SEND);
            chatList.add(msg2);
            ChatBean msg = new ChatBean("好的！", ChatBean.TYPE_RECEIVE);
            chatList.add(msg);
        }

    }

    /**
     * 更新为立即送
     */
    private void requestSendImmediatelyChoose() {
        if (intent != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("act.procInsId", intent.getStringExtra("act_procInsId"));
            params.put("act.taskId", intent.getStringExtra("act_taskId"));
            params.put("expressParcel.id", intent.getStringExtra("expressParcel_id"));
            params.put("id", intent.getStringExtra("expressAccept_id"));
            params.put("sendImmediately", "true");
            JSONObject jsonObject = new JSONObject(params);

            new OkHttpResolve(this);
            parcelParticularsPresenter.attach(this);
            parcelParticularsPresenter.postSendImmediatelyChooseResult(jsonObject.toString(), this, lazyLoadProgressDialog);
        }

    }

    /**
     * 更新为预约送
     */
    private void requestUpdateTime(String time) {
        if (intent != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("act.procInsId", intent.getStringExtra("act_procInsId"));
            params.put("time", time);
            JSONObject jsonObject = new JSONObject(params);

            new OkHttpResolve(this);
            parcelParticularsPresenter.attach(this);
            parcelParticularsPresenter.postUpdateTimeResult(jsonObject.toString(), this, lazyLoadProgressDialog);
        }

    }

    /**
     * 确认接收
     */
    private void requestConfirmReceive() {
        if (intent != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("act.procInsId", intent.getStringExtra("act_procInsId"));
            params.put("act.taskId", intent.getStringExtra("act_taskId"));
            params.put("expressParcel.id", intent.getStringExtra("expressParcel_id"));
            params.put("id", intent.getStringExtra("expressAccept_id"));
            JSONObject jsonObject = new JSONObject(params);
            new OkHttpResolve(this);
            parcelParticularsPresenter.attach(this);
            parcelParticularsPresenter.postSendConfirmResult(jsonObject.toString(), this, lazyLoadProgressDialog);
        }

    }

    /**
     * 时间选择器
     */
    private void setTimePickerDialog() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogMonthDayHourMinute = new TimePickerDialog.Builder()
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setCallBack(this)
                .setHourText("时")
                .setMonthText("月")
                .setDayText("日")
                .setMinuteText("分")
                .setCancelStringId("关闭")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setWheelItemTextSize(16)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.cl_999999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.cl_333333))
                .setThemeColor(getResources().getColor(R.color.cl_6fd1c8))
                .build();
    }

    @OnClick({R.id.return_arrows, R.id.datetime, R.id.affirm_reception, R.id.zoom, R.id.exit_parcel, R.id.postpone_parcel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
                break;
            case R.id.datetime:
                setTimePickerDialog();
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            case R.id.affirm_reception:
                Constants.SOURCE = getIntent().getStringExtra("source");
                switch (Constants.SOURCE) {
                    case "sendImmediately":
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestSendImmediatelyChoose();
                        break;
                    case "mipca_capture_sendImmediately":
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestSendImmediatelyChoose();
                        break;
                    case "singExpress":
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestConfirmReceive();
                        break;
                    case "mipca_capture_singExpress":
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestConfirmReceive();
                        break;
                }
                break;
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom, timelineRecyclerview);
                break;
            case R.id.postpone_parcel:
                SkipIntentUtil.skipIntent(this, PostponeParcelActivity.class);
                break;
            case R.id.exit_parcel:
                SkipIntentUtil.skipIntent(this, UserReturnedParcelActivity.class);
                break;
        }
    }

    /**
     * 获取时间
     *
     * @param timePickerDialog
     * @param millseconds
     */
    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        try {
            String currentDate = AcquisitionTimeUtil.getCurrentDateTime();
            long currentLong = AcquisitionTimeUtil.stringToLong(currentDate, "yyyy-MM-dd HH:mm:ss") + Constants.THIRTY_LONG;//此刻时间 + 规定时间之后
            if (millseconds >= currentLong) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestUpdateTime(String.valueOf(millseconds));
            } else {
                SkipIntentUtil.toastShow(this, "预约时间不能小于10分钟！");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSendImmediatelyChooseFinish(Object o) {
        returnAddress(o);
    }

    @Override
    public void onUpdateTimeFinish(Object o) {
        returnAddress(o);
    }

    @Override
    public void onConfirmReceiveFinish(Object o) {
        AddressBean addressBean = (AddressBean) o;
        if (addressBean != null) {
            String ifConsumerPickUp = addressBean.getExpressAccept().getIfConsumerPickUp();
            if (ifConsumerPickUp != null && ifConsumerPickUp.equals("1")) {
                returnAddress(o);
            } else {
                intent = new Intent(this, UserAffirmActivity.class);
                intent.putExtra("home_source",getIntent().getStringExtra("home_source"));
                intent.putExtra("businessId", getIntent().getStringExtra("expressAccept_id"));
                intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
                intent.putExtra("shipperCode", getIntent().getStringExtra("shipperCode"));
                intent.putExtra("lgisticCode", getIntent().getStringExtra("lgisticCode"));
                RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
                haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
                expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
                startActivity(intent);
                finish();
            }

        }
    }

    /**
     * 完成返回收件列表
     */
    private void returnAddress(Object o) {
        AddressBean addressBean = (AddressBean) o;
        if (addressBean != null) {
            intent = new Intent(this, AddresseeActivity.class);
            intent.putExtra("source", getIntent().getStringExtra("home_source"));
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onHistoicFlowFinish(Object o) {
        HistoicFlowBean histoicFlowBean = (HistoicFlowBean) o;
        if (histoicFlowBean != null) {
            timelineRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
            timelineRecyclerview.setAdapter(new TimeLineAdapter(this, histoicFlowBean.getListTrajectory()));
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        parcelParticularsPresenter.dettach();
        histoicFlowPresenter.dettach();
    }
}
