package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.TimeLineAdapter;
import com.example.ysww.snailfamily.bean.ChatBean;
import com.example.ysww.snailfamily.bean.HistoicFlowBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.mvp.HistoicFlowView;
import com.example.ysww.snailfamily.presenter.HistoicFlowPresenter;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
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
 * 寄件订单   已称重
 */
public class SendCasesOrderNumberActivity extends AutoLayoutActivity implements HistoicFlowView {

    @InjectView(R.id.zoom)
    TextView zoom;
    @InjectView(R.id.send_sases_chat_rv)
    RecyclerView sendSasesChatRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.goodsname)
    TextView goodsname;
    @InjectView(R.id.transaction_number)
    TextView transactionNumber;
    @InjectView(R.id.send_cases_person)
    TextView sendCasesPerson;
    @InjectView(R.id.addressee_person)
    TextView addresseePerson;
    @InjectView(R.id.weight)
    TextView weight;
    @InjectView(R.id.cost)
    TextView cost;
    @InjectView(R.id.timeline_recyclerview)
    RecyclerView timelineRecyclerview;

    private List<ChatBean> chatList;
    private Intent intent;
    private HistoicFlowPresenter histoicFlowPresenter = new HistoicFlowPresenter();//时间轴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cases_order_number);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);


        timelineRecyclerview.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);

        intent = getIntent();
        if (intent != null) {
            goodsname.setText(intent.getStringExtra("goodsname"));
            String goodsWeight = intent.getStringExtra("goodsWeight");
            String cost = intent.getStringExtra("cost");
            if (goodsWeight.equals("0") && cost.equals("0")) {
                this.cost.setVisibility(View.GONE);
                weight.setText("您的包裹未称重");
            } else {
                this.cost.setText("您的包裹快递费为：" + cost + "元");
                weight.setText("您的包裹重量为：" + goodsWeight + "kg");
            }

            sendCasesPerson.setText("寄件人：" + intent.getStringExtra("sender"));
            addresseePerson.setText("收件人：" + intent.getStringExtra("recipients"));
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

    @OnClick({R.id.zoom, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zoom:
                SkipIntentUtil.zoomTimerShaft(zoom,timelineRecyclerview);
                break;
            case R.id.return_arrows:
                SkipIntentUtil.sourceSenderReturn(this,getIntent().getStringExtra("source"));
                break;
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
        histoicFlowPresenter.dettach();
    }


}
