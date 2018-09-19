package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.dialog.OverDueCustomDialog;
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
 * 包裹详情  是否进入超期管理
 */
public class OverDueParcelActivity extends AutoLayoutActivity implements HistoicFlowView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;

    private Intent intent;

    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_due_parcel);
        ButterKnife.inject(this);

        StatusBarUtil.SetStatusBar(this );

        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        showAlertDialog();
        String procInsId = getIntent().getStringExtra("procInsId");
        if(procInsId!=null){
            histoicFlowPresenter.attach(this);
            RequestOperationUtil.setSendHistoicFlowResult(procInsId,this,histoicFlowPresenter);
        }
    }

    /**
     * 弹窗遮掩效果
     */
    public void showAlertDialog() {
        OverDueCustomDialog.Builder builder = new OverDueCustomDialog.Builder(this);
        builder.setDialogListener(new OverDueCustomDialog.Builder.OnDialogClickListener() {
            @Override
            public void ReturnClickListener(View view) {
                finish();
            }

            @Override
            public void ConfirmationClickListener(View view) {
                intent = new Intent(OverDueParcelActivity.this, CollectingExceedTimeParcelActivity.class);
                startActivity(intent);
            }
        });
        builder.create().setCanceledOnTouchOutside(true);  //用户选择取消或者是点击屏幕空白部分时让dialog不消失。
        builder.create().show();

    }

    @OnClick({R.id.return_arrows, R.id.zoom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                break;
            case R.id.zoom:
                break;
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
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        histoicFlowPresenter.dettach();
    }

}
