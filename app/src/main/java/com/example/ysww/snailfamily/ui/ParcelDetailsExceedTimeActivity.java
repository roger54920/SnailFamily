package com.example.ysww.snailfamily.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.dialog.ParcelDetailsExceedTimeDialog;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 包裹详情 退回原因 延期包裹
 */
public class ParcelDetailsExceedTimeActivity extends AutoLayoutActivity implements HistoicFlowView {

    @InjectView(R.id.zoom)
    Button zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_details_exceed_time);
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


    @OnClick(R.id.zoom)
    public void onViewClicked() {
        if (zoom.getText().equals("展开")) {
            zoom.setText("收起");
            timelineRecyclerview.setVisibility(View.VISIBLE);
        } else if (zoom.getText().equals("收起")) {
            zoom.setText("展开");
            timelineRecyclerview.setVisibility(View.GONE);
        }
    }
    /**
     * 弹窗遮掩效果
     */
    public void showAlertDialog() {
        ParcelDetailsExceedTimeDialog.Builder builder = new ParcelDetailsExceedTimeDialog.Builder(this);


        builder.create().show();

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
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        histoicFlowPresenter.dettach();
    }

}
