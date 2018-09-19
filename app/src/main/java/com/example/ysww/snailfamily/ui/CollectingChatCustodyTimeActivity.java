package com.example.ysww.snailfamily.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.custom.FullPhotoView;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.dialog.CollectingChatCustodyTimeDialog;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 超期包裹 聊天设定包裹保管到期时间
 */
public class CollectingChatCustodyTimeActivity extends AutoLayoutActivity implements OnDateSetListener,HistoicFlowView {

    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;

    private TextView date;
    TimePickerDialog mDialogYearMonthDay;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecting_chat_custody_time);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);


        showAlertDialog();
        String procInsId = getIntent().getStringExtra("procInsId");
        if(procInsId!=null){
            histoicFlowPresenter.attach(this);
            RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
        }
    }


    @OnClick({R.id.zoom})
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
        }

    }

    /**
     * 弹窗遮掩效果
     */
    public void showAlertDialog() {
        setTimePickerDialog();
        CollectingChatCustodyTimeDialog.Builder builder = new CollectingChatCustodyTimeDialog.Builder(this);
        builder.setDialogListener(new CollectingChatCustodyTimeDialog.Builder.OnDialogClickListener() {
            @Override
            public void ReturnClickListener(View view) {

            }

            @Override
            public void ConfirmationClickListener(View view) {

            }

            @Override
            public void DateClickListener(View view, TextView date) {
                CollectingChatCustodyTimeActivity.this.date = date;
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");

            }
        });
        builder.create().show();

    }

    /**
     * 日期选择器
     */
    private void setTimePickerDialog() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCancelStringId("关闭")
                .setSureStringId("确定")
                .setTitleStringId("选择日期")
                .setWheelItemTextSize(16)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.cl_999999))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.cl_333333))
                .setThemeColor(getResources().getColor(R.color.cl_6fd1c8))
                .build();
    }

    /**
     * 获取时间
     *
     * @param timePickerDialog
     * @param millseconds
     */
    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        String text = getDateToString(millseconds);
        date.setText(text);
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
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
        histoicFlowPresenter.dettach();
    }
}
