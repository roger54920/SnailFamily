package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.bean.SendDetailsBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.mvp.SendOperationView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;
import com.example.ysww.snailfamily.presenter.SendOperationPresenter;
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
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 揽件 设置预约时间
 */
public class SendSetTimeActivity extends AutoLayoutActivity implements SendOperationView, OnDateSetListener, HistoicFlowView {
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.set_send_time)
    Button setSendTime;
    @InjectView(R.id.send_cases_person)
    TextView sendCasesPerson;
    @InjectView(R.id.addressee_person)
    TextView addresseePerson;
    @InjectView(R.id.prcal_state)
    TextView prcalState;
    @InjectView(R.id.goodsname)
    TextView goodsname;
    @InjectView(R.id.set_time)
    RelativeLayout setTime;
    @InjectView(R.id.update_set_time)
    RelativeLayout updateSetTime;
    @InjectView(R.id.datetime)
    TextView datetime;
    @InjectView(R.id.immediately_send)
    Button immediatelySend;
    @InjectView(R.id.transaction_number)
    TextView transactionNumber;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    private SendOperationPresenter sendOperationPresenter = new SendOperationPresenter();//揽件预约取
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private Intent intent;

    private HashMap<String, String> params;//json参数
    //时间选择器
    TimePickerDialog mDialogMonthDayHourMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_set_time);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        Constants.SOURCE = intent.getStringExtra("source");
        if (intent != null) {
            switch (Constants.SOURCE) {
                case "appalyUser":
                    setTime.setVisibility(View.VISIBLE);
                    updateSetTime.setVisibility(View.GONE);
                    break;
                case "mipca_capture_appalyUser":
                    setTime.setVisibility(View.VISIBLE);
                    updateSetTime.setVisibility(View.GONE);
                    break;
                case "sendImmediately":
                    setTime.setVisibility(View.GONE);
                    updateSetTime.setVisibility(View.VISIBLE);
                    datetime.setText(Html.fromHtml("<u>" + intent.getStringExtra("time") + "</u>"));
                    break;
                case "mipca_capture_sendImmediately":
                    setTime.setVisibility(View.GONE);
                    updateSetTime.setVisibility(View.VISIBLE);
                    datetime.setText(Html.fromHtml("<u>" + intent.getStringExtra("time") + "</u>"));
                    break;
            }
            goodsname.setText(intent.getStringExtra("goodsname"));
            sendCasesPerson.setText(intent.getStringExtra("sender"));
            addresseePerson.setText(intent.getStringExtra("recipients"));
            String lgisticCode = intent.getStringExtra("lgisticCode");
            if (!lgisticCode.equals("0")) {
                transactionNumber.setVisibility(View.VISIBLE);
                transactionNumber.setText("订单编号：" + lgisticCode);
            } else {
                transactionNumber.setVisibility(View.GONE);
            }
            String senderId = getIntent().getStringExtra("senderId");
            if(senderId!=null){
                histoicFlowPresenter.attach(this);
                RequestOperationUtil.setSendHistoicFlowResult(senderId,this,histoicFlowPresenter);
            }
            params = new HashMap<>();
            params.put("senderId", getIntent().getStringExtra("senderId"));
            params.put("taskId", getIntent().getStringExtra("taskId"));
            params.put("procInsId", getIntent().getStringExtra("procInsId"));
        }
    }

    /**
     * 设置预约寄件时间
     *
     * @param time
     */
    private void getSendSetTimeResult(String time) {
        params.put("sendImmediately", "false");
        params.put("time", time);
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postJsonSendOperationResult(new JSONObject(params).toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 设置立即寄件
     */
    private void getSendImmediatelySendResult() {
        params.put("sendImmediately", "true");
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postJsonSendOperationResult(new JSONObject(params).toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 修改揽件时间
     *
     * @param time
     */
    private void postUpdateTimeResult(String time) {
        params.put("time", time);
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postUpdateTimeResult(new JSONObject(params).toString(), this, lazyLoadProgressDialog);
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

    @Override
    public void onSendOperationFinish(Object o) {
        finishSkip(o);
    }

    @Override
    public void onUpdateTimeFinish(Object o) {
        finishSkip(o);
    }

    /**
     * 成功跳转页面
     *
     * @param o
     */
    private void finishSkip(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            SkipIntentUtil.skipIntent(this, SendCasesDisplayActivity.class);
            finish();
        }
    }

    @OnClick({R.id.return_arrows, R.id.set_send_time, R.id.datetime, R.id.immediately_send,R.id.zoom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
                break;
            case R.id.set_send_time:
                setTimePickerDialog();
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            case R.id.datetime:
                setTimePickerDialog();
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            case R.id.immediately_send:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                getSendImmediatelySendResult();
                break;
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom,timelineRecyclerview);
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
                if (Constants.SOURCE != null) {
                    switch (Constants.SOURCE) {
                        case "appalyUser"://设置时间
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            getSendSetTimeResult(String.valueOf(millseconds));
                            break;
                        case "mipca_capture_appalyUser"://设置时间
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            getSendSetTimeResult(String.valueOf(millseconds));
                            break;
                        case "sendImmediately": //更新时间
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            postUpdateTimeResult(String.valueOf(millseconds));
                            break;
                        case "mipca_capture_sendImmediately": //更新时间
                            lazyLoadProgressDialog.show();
                            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                            postUpdateTimeResult(String.valueOf(millseconds));
                            break;
                    }
                }
            } else {
                SkipIntentUtil.toastShow(this, "预约时间不能小于10分钟！");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onHistoicFlowFinish(Object o) {
        HistoicFlowBean histoicFlowBean = (HistoicFlowBean) o;
        if(histoicFlowBean!=null){
            timelineRecyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
            timelineRecyclerview.setAdapter(new TimeLineAdapter(this, histoicFlowBean.getListTrajectory()));
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
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
        sendOperationPresenter.dettach();
        histoicFlowPresenter.dettach();
    }
}
