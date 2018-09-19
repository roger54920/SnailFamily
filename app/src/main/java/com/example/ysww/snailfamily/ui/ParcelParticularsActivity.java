package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 包裹详情 立即送与预约送
 */
public class ParcelParticularsActivity extends AutoLayoutActivity implements ParcelParticularsView, HistoicFlowView {

    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.chat_recyclerview)
    RecyclerView chatRecyclerview;
    @InjectView(R.id.fullPhotoView)
    FullPhotoView fullPhotoView;
    @InjectView(R.id.promptly_send)
    Button promptlySend;
    @InjectView(R.id.ordr_send)
    Button ordrSend;
    @InjectView(R.id.postpone_parcel)
    Button postponeParcel;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.exit_parcel)
    Button exitParcel;
    @InjectView(R.id.present_rl)
    RelativeLayout presentRl;
    @InjectView(R.id.haulier_source)
    TextView haulierSource;
    @InjectView(R.id.express_number)
    TextView expressNumber;
    @InjectView(R.id.exception_ll)
    LinearLayout exceptionLl;
    private List<ChatBean> chatList;

    private Intent intent;
    private String source;//页面来源
    private ParcelParticularsPresenter parcelParticularsPresenter = new ParcelParticularsPresenter();//收件操作
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_particulars);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        timelineRecyclerview.setFocusable(false);
        chatRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);

        intent = getIntent();
        if (intent != null) {
            source = intent.getStringExtra("source");
            exceptionLl.setVisibility(View.GONE);
            RequestOperationUtil.setGlide(this, getIntent().getStringExtra("img_url"), fullPhotoView);
            haulierSource.setText("承运来源：" + intent.getStringExtra("shipperCode"));
            expressNumber.setText("快递单号：" + intent.getStringExtra("lgisticCode"));
            switch (source) {
                case "sendImmediatelyChoose":
                    presentRl.setVisibility(View.VISIBLE);
                    break;
                case "mipca_capture_sendImmediatelyChoose":
                    presentRl.setVisibility(View.VISIBLE);
                    break;
                case "addressee_dispaly":
                    presentRl.setVisibility(View.GONE);
                    break;
                case "mipca_capture":
                    presentRl.setVisibility(View.GONE);
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

    /**
     * 立即送
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

    @OnClick({R.id.zoom, R.id.promptly_send, R.id.ordr_send, R.id.postpone_parcel, R.id.return_arrows, R.id.exit_parcel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom, timelineRecyclerview);
                break;
            case R.id.promptly_send:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestSendImmediatelyChoose();
                break;
            case R.id.ordr_send:
                if (getIntent().getStringExtra("source").contains("sendImmediatelyChoose")) {
                    intent = new Intent(this, ParcelParticularsPromptlyActivity.class);
                    intent.putExtra("source", "sendImmediatelyChoose");
                    intent.putExtra("home_source",getIntent().getStringExtra("home_source"));
                    intent.putExtra("expressAccept_id", getIntent().getStringExtra("expressAccept_id"));
                    intent.putExtra("expressParcel_id", getIntent().getStringExtra("expressParcel_id"));
                    intent.putExtra("act_taskId", getIntent().getStringExtra("act_taskId"));
                    intent.putExtra("act_procInsId", getIntent().getStringExtra("act_procInsId"));
                    intent.putExtra("shipperCode", getIntent().getStringExtra("shipperCode"));
                    intent.putExtra("lgisticCode", getIntent().getStringExtra("lgisticCode"));
                    intent.putExtra("img_url", getIntent().getStringExtra("img_url"));
                    startActivity(intent);
                }
                break;
            case R.id.postpone_parcel:
//                skipIntent(PostponeParcelActivity.class);
                break;
            case R.id.exit_parcel:
//                skipIntent(UserReturnedParcelActivity.class);
                break;
            case R.id.return_arrows:
                SkipIntentUtil.sourceAcceptReturn(this, getIntent().getStringExtra("source"));
                break;
        }
    }

    @Override
    public void onSendImmediatelyChooseFinish(Object o) {
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
        AddressBean addressBean = (AddressBean) o;
        if (addressBean != null) {
            intent = new Intent(this, AddresseeActivity.class);
            intent.putExtra("source", getIntent().getStringExtra("home_source"));
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConfirmReceiveFinish(Object o) {

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
