package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.AddressBean;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 包裹详情  预约 设置收件时间
 */
public class ParcelParticularsPromptlyActivity extends AutoLayoutActivity implements OnDateSetListener, ParcelParticularsView,HistoicFlowView {

    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.set_addressee_time)
    Button setAddresseeTime;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    //时间选择器
    TimePickerDialog mDialogMonthDayHourMinute;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Intent intent;
    private ParcelParticularsPresenter parcelParticularsPresenter = new ParcelParticularsPresenter();//收件操作
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_particulars_promptly);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        if (intent!= null) {
            Constants.SOURCE = intent.getStringExtra("source");
            if (intent.getStringExtra("img_url") != null) {
                Glide.with(this)
                        .load(intent.getStringExtra("img_url"))
                        .asBitmap()// .asGif() 加载gif图
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                        .placeholder(R.mipmap.ic_launcher)//加载成功之前
                        .error(R.mipmap.ic_launcher)//加载失败
                        .into(fullPhotoView);
            }
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
            String procInsId = getIntent().getStringExtra("act_procInsId");
            if(procInsId!=null){
                histoicFlowPresenter.attach(this);
                RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
            }

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


    @OnClick({R.id.zoom, R.id.set_addressee_time,R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                if (zoom.getText().equals("展开")) {
                    zoom.setText("收起");
                    timelineRecyclerview.setVisibility(View.VISIBLE);
                } else if (zoom.getText().equals("收起")) {
                    zoom.setText("展开");
                    timelineRecyclerview.setVisibility(View.GONE);
                }
                break;
            case R.id.set_addressee_time:
                setTimePickerDialog();
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            case R.id.return_arrows:
                finish();
                break;
        }

    }

    /**
     * 预约送
     */
    private void requestSendImmediatelyChoose(String time) {
        if (intent != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("act.procInsId", intent.getStringExtra("act_procInsId"));
            params.put("act.taskId", intent.getStringExtra("act_taskId"));
            params.put("expressParcel.id", intent.getStringExtra("expressParcel_id"));
            params.put("id", intent.getStringExtra("expressAccept_id"));
            params.put("sendImmediately", "false");
            params.put("time", time);
            JSONObject jsonObject = new JSONObject(params);

            new OkHttpResolve(this);
            parcelParticularsPresenter.attach(this);
            parcelParticularsPresenter.postSendImmediatelyChooseResult(jsonObject.toString(), this, lazyLoadProgressDialog);
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
            long currentLong = AcquisitionTimeUtil.stringToLong(currentDate, "yyyy-MM-dd HH:mm:ss")+Constants.THIRTY_LONG;//此刻时间 + 规定时间之后
            if(millseconds >=currentLong){
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestSendImmediatelyChoose(String.valueOf(millseconds));
            }else{
                SkipIntentUtil.toastShow(this,"预约时间不能小于10分钟！");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onSendImmediatelyChooseFinish(Object o) {
        AddressBean addressBean = (AddressBean) o;
        if (addressBean != null) {
            intent = new Intent(this, AddresseeActivity.class);
            intent.putExtra("source", getIntent().getStringExtra("home_source"));
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onUpdateTimeFinish(Object o) {

    }

    @Override
    public void onConfirmReceiveFinish(Object o) {

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
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
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
