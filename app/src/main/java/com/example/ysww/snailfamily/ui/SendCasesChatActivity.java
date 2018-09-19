package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.ChatBean;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.bean.SendDetailsBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.mvp.SendOperationView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;
import com.example.ysww.snailfamily.presenter.SendOperationPresenter;
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
 * 寄件聊天 未称重 立即取和预约取
 */

public class SendCasesChatActivity extends AutoLayoutActivity implements SendOperationView, HistoicFlowView {

    @InjectView(R.id.send_sases_chat_rv)
    RecyclerView sendSasesChatRv;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.promptly_send)
    Button promptlySend;
    @InjectView(R.id.ordr_send)
    Button ordrSend;
    @InjectView(R.id.goodsname)
    TextView goodsname;
    @InjectView(R.id.send_cases_person)
    TextView sendCasesPerson;
    @InjectView(R.id.addressee_person)
    TextView addresseePerson;
    @InjectView(R.id.transaction_number)
    TextView transactionNumber;
    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;
    private List<ChatBean> chatList;

    private SendOperationPresenter sendOperationPresenter = new SendOperationPresenter();//寄件立即/预约取
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private Intent intent;

    private HashMap<String, String> params;//json参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cases_chat);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        intent = getIntent();
        if (intent != null) {
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
            if (senderId != null) {
                histoicFlowPresenter.attach(this);
                RequestOperationUtil.setSendHistoicFlowResult(senderId, this, histoicFlowPresenter);
            }
        }

        //聊天适配器
//        initMsg();
//        sendSasesChatRv.setLayoutManager(new FullyLinearLayoutManager(this));
//        sendSasesChatRv.setAdapter(new SendCasesCharAdapter(this, chatList));
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
     * 寄件立即取预约取
     *
     * @param json
     */
    private void getSendImmediatelyTakeBookingResult(String json) {
        new OkHttpResolve(this);
        sendOperationPresenter.attach(this);
        sendOperationPresenter.postJsonSendOperationResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.promptly_send, R.id.ordr_send,R.id.zoom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
                break;
            case R.id.promptly_send:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                params = new HashMap<>();
                params.put("sendImmediately", "true");
                params.put("senderId", getIntent().getStringExtra("senderId"));
                params.put("taskId", getIntent().getStringExtra("taskId"));
                params.put("procInsId", getIntent().getStringExtra("procInsId"));
                getSendImmediatelyTakeBookingResult(new JSONObject(params).toString());
                break;
            case R.id.ordr_send:
                intent = new Intent(this, SendSetTimeActivity.class);
                intent.putExtra("lgisticCode", getIntent().getStringExtra("lgisticCode"));
                intent.putExtra("goodsname", getIntent().getStringExtra("goodsname"));
                intent.putExtra("sender", getIntent().getStringExtra("sender"));
                intent.putExtra("recipients", getIntent().getStringExtra("recipients"));
                intent.putExtra("source", getIntent().getStringExtra("source"));
                intent.putExtra("senderId", getIntent().getStringExtra("senderId"));
                intent.putExtra("taskId", getIntent().getStringExtra("taskId"));
                intent.putExtra("procInsId", getIntent().getStringExtra("procInsId"));
                startActivity(intent);
                break;
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom,timelineRecyclerview);
                break;
        }
    }

    @Override
    public void onSendOperationFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            SkipIntentUtil.skipIntent(this, SendCasesDisplayActivity.class);
            finish();
        }
    }

    @Override
    public void onUpdateTimeFinish(Object o) {

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
            SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
