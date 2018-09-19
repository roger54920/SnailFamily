package com.example.ysww.snailfamily.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.SendDetailsBean;
import com.example.ysww.snailfamily.bean.SendDisplayBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SendDisplayView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.net.NetBroadcastReceiver;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.SendDisplayPresenter;
import com.example.ysww.snailfamily.utils.AcquisitionTimeUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.MoveLocationUtil;
import com.example.ysww.snailfamily.utils.NetUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 寄件 展示首页
 */
public class SendCasesDisplayActivity extends BaseActivity implements SendDisplayView {

    @InjectView(R.id.send_cases_rv)
    RecyclerView sendCasesRv;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.me_send_btn)
    Button meSendBtn;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    private CommonAdapter<SendDisplayBean.PageBean.ListBean> commonAdapter;
    private List<SendDisplayBean.PageBean.ListBean> sendDisplayList;//寄件列表数据

    private SendDisplayPresenter sendDisplayPresenter = new SendDisplayPresenter();//寄件列表
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载


    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数
    private LinearLayoutManager linearLayoutManager;

    private Intent intent;
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cases_display);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        } else {
            Constants.NETCONNECT = true;
        }
        initViews();
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        getSendDisplayListResult();

        //添加头部
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setHeaderHeight(50);
        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        linearLayoutManager = new LinearLayoutManager(this);
        sendCasesRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        refreshLayout.setVisibility(View.VISIBLE);
        noContent.setVisibility(View.GONE);
        commonAdapter = new CommonAdapter<SendDisplayBean.PageBean.ListBean>(this, R.layout.send_cases_item, sendDisplayList) {
            @Override
            protected void convert(ViewHolder holder, SendDisplayBean.PageBean.ListBean sendShowBean, int position) {
                holder.setText(R.id.express_type_tv, "承运来源：" + sendShowBean.getExpressParcel().getShipperCode());
                holder.setText(R.id.express_datetime_tv, sendShowBean.getCreateDate());
                String lgisticcode = sendShowBean.getExpressParcel().getLgisticCode();
                if (!lgisticcode.equals("0")) {
                    holder.setText(R.id.tracking_number_tv, "快递单号：" + lgisticcode).setVisible(R.id.tracking_number_tv, true);
                } else {
                    holder.setVisible(R.id.tracking_number_tv, false);
                }
                holder.setText(R.id.send_state_tv, sendShowBean.getBusinessStatus());
                holder.setText(R.id.arrive_date_tv, sendShowBean.getRemarks());

                SendDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = sendShowBean.getExpressParcel().getReceiver();
                if (receiver != null) {
                    holder.setText(R.id.sir_name, receiver.getName());
                    holder.setText(R.id.accept_phone, receiver.getMobile());
                    String provinceName = receiver.getProvinceName();
                    String cityName = receiver.getCityName();
                    String expAreaName = receiver.getExpAreaName();
                    String address = receiver.getAddress();
                    if (provinceName != null && provinceName.equals(cityName)) {
                        holder.setText(R.id.sir_address, cityName + expAreaName + address);
                    } else {
                        holder.setText(R.id.sir_address, provinceName + cityName + expAreaName + address);
                    }
                }
                holder.setBackgroundRes(R.id.sender_ll, R.drawable.customer_selector);
            }
        };
        sendCasesRv.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                postSendparticularsResult(sendDisplayList.get(position).getId());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                getSendDisplayListResult();
                SkipIntentUtil.rvRefreshTimeout(SendCasesDisplayActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                getSendDisplayListResult();
                SkipIntentUtil.rvRefreshTimeout(SendCasesDisplayActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    /**
     * 得到寄件列表数据
     */
    private void getSendDisplayListResult() {

        JSONObject json = new JSONObject();
        try {
            JSONObject page = new JSONObject();
            page.put("pageNo", pageno);
            page.put("pageSize", pagesize);
            json.put("pageInfo", page);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.getJsonSendResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 得到寄件列表
     */
    private void postSendparticularsResult(String id) {
        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.postJsonSendResult("{\"senderId\":\"" + id + "\"}", this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.me_send_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.returnHomeFinish(this);
                break;
            case R.id.me_send_btn:
                SkipIntentUtil.skipIntent(this, SendCasesAffirmActivity.class);
                break;
        }
    }

    @Override
    public void onSendParticularsListFinish(Object o) {
        SendDisplayBean sendDisplayBean = (SendDisplayBean) o;
        if (sendDisplayBean != null) {
            if (sendDisplayList == null) {
                sendDisplayList = new ArrayList<>();
            }
            List<SendDisplayBean.PageBean.ListBean> list = sendDisplayBean.getPage().getList();
            if (sendDisplayBean.getPage().getPageNo() == 1) {
                if (sendDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    sendDisplayList.clear();
                    sendDisplayList = list;
                    setUpParameters();
                    if (sendDisplayBean.getPage().getCount() > 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    noContent.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, sendCasesRv, sendDisplayList.size());
                    for (int i = 0; i < list.size(); i++) {
                        sendDisplayList.add(list.get(i));
                    }
                    setUpParameters();
                    if (list.size() == 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);

                }
            }
        }
    }

    @Override
    public void onSendParticularsFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            String taskKey = sendDetailsBean.getMapsender().getTaskKey();
            switch (taskKey) {
                case "appalyUser":
                    intent = new Intent(this, SendCasesChatActivity.class);
                    RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "appalyUser");
                    startActivity(intent);
                    break;
                case "sendImmediately":
                    intent = new Intent(this, SendSetTimeActivity.class);
                    RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "sendImmediately");
                    try {
                        String time = AcquisitionTimeUtil.longToString(sendDetailsBean.getMapsender().getTime(), "yyyy-MM-dd HH:mm:ss");
                        intent.putExtra("time", time);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                    break;
                case "userPay":
                    intent = new Intent(this, SendCasesChatAliPayActivity.class);
                    RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "userPay");
                    startActivity(intent);
                    break;
                case "0":
                    intent = new Intent(this, SendCasesOrderNumberActivity.class);
                    RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "0");
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(SendCasesDisplayActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                getSendDisplayListResult();
            }
        }
    }

    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            SkipIntentUtil.returnHomeFinish(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        sendDisplayPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
