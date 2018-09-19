package com.example.ysww.snailfamily.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.bean.AddresseeDisplayBean;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.custom.SwipeMenuLayout;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AcceptPayListView;
import com.example.ysww.snailfamily.mvp.AddresseeView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.net.NetBroadcastReceiver;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AcceptPayListPresenter;
import com.example.ysww.snailfamily.presenter.AddresseePresenter;
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
 * 收件展示
 */
public class AddresseeActivity extends BaseActivity implements AddresseeView, AcceptPayListView {

    @InjectView(R.id.addressee_rv)
    RecyclerView addresseeRv;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.additional_payment_goods_btn)
    Button additionalPaymentGoodsBtn;

    private CommonAdapter<AddresseeDisplayBean.PageBean.ListBean> commonAdapter;
    private List<AddresseeDisplayBean.PageBean.ListBean> addresseeBeanList;
    private AddresseePresenter addresseePresenter = new AddresseePresenter();//收件信息
    private AcceptPayListPresenter acceptPayListPresenter = new AcceptPayListPresenter();//代付货款列表
    private Intent intent;

    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private BroadcastReceiver receiver;//获取广播对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressee);
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
        Constants.SOURCE = getIntent().getStringExtra("source");
        if (!TextUtils.isEmpty(Constants.SOURCE)) {
            switch (Constants.SOURCE) {
                case "accept":
                    titleTv.setText("收件");
                    noContent.setText("暂无收件信息");
                    additionalPaymentGoodsBtn.setVisibility(View.GONE);
                    break;
                case "acceptPay":
                    titleTv.setText("代付包裹");
                    noContent.setText("暂无代付包裹信息");
                    additionalPaymentGoodsBtn.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
        initRequest();
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
        addresseeRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 初始化请求数据
     */
    private void initRequest() {
        if (!TextUtils.isEmpty(Constants.SOURCE)) {
            switch (Constants.SOURCE) {
                case "accept":
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestAddressDisplayResult();
                    break;
                case "acceptPay":
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestAcceptPayListResult();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 初始化收件列表信息
     */
    private void requestAddressDisplayResult() {
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
        addresseePresenter.attach(this);
        addresseePresenter.postJsonDisplayListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 初始化代收货款列表信息
     */
    private void requestAcceptPayListResult() {
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
        acceptPayListPresenter.attach(this);
        acceptPayListPresenter.postJsonAcceptPayListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 初始化代收货款取消
     */
    private void requestAcceptPayCancelResult(String businessId) {
        new OkHttpResolve(this);
        acceptPayListPresenter.attach(this);
        acceptPayListPresenter.postJsonAcceptPayCancelResult("{\"businessId\":\"" + businessId + "\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 初始化代收货款恢复
     */
    private void requestAcceptPayReapplyResult(String reapply) {
        new OkHttpResolve(this);
        acceptPayListPresenter.attach(this);
        acceptPayListPresenter.postJsonAcceptPayReapplyResult("{\"businessId\":\"" + reapply + "\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 初始化收件列表信息Item详情
     */
    private void requestRecipientsMessageResult(String id) {
        new OkHttpResolve(this);
        addresseePresenter.attach(this);
        addresseePresenter.postJsonDisplayResult(id, this, lazyLoadProgressDialog);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        addresseeRv.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new CommonAdapter<AddresseeDisplayBean.PageBean.ListBean>(this, R.layout.addressee_item, addresseeBeanList) {
            @Override
            protected void convert(final ViewHolder holder, final AddresseeDisplayBean.PageBean.ListBean addresseeBean, int position) {
                holder.setText(R.id.state, addresseeBean.getBusinessStatus());
                holder.setText(R.id.express_type, addresseeBean.getExpressParcel().getShipperCode());
                holder.setText(R.id.express_number, addresseeBean.getExpressParcel().getLgisticCode());
                holder.setText(R.id.express_datetime, addresseeBean.getCreateDate());
                holder.setText(R.id.arrive_datetime, addresseeBean.getRemarks());
                holder.setBackgroundRes(R.id.accept_rl, R.drawable.customer_selector);
                String cost = addresseeBean.getExpressParcel().getCost();
                if (Constants.SOURCE.equals("acceptPay")) {
                    final String ifCancel = addresseeBean.getIfCancel();
                    String businessStatusCode = addresseeBean.getBusinessStatusCode();
                    if (ifCancel != null && businessStatusCode == null) {
                        holder.setVisible(R.id.side_menu_ll, true);
                        if (ifCancel.equals("0")) {
                            holder.setBackgroundRes(R.id.btn_cancel, R.color.cl_ff0000).setText(R.id.btn_cancel, "取消");
                        } else {
                            holder.setBackgroundRes(R.id.btn_cancel, R.color.cl_00af0d).setText(R.id.btn_cancel, "恢复");
                        }
                        //根据自己需求设置一些限制条件，比如这里设置了：IOS效果阻塞，item依次是左滑、右滑
                        ((SwipeMenuLayout) LayoutInflater.from(mContext).inflate(R.layout.addressee_item, null)).setIos(true).setLeftSwipe(position % 2 == 0 ? true : false);
                        holder.setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lazyLoadProgressDialog.show();
                                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                if (ifCancel.equals("0")) {
                                    requestAcceptPayCancelResult(addresseeBean.getId());
                                } else {
                                    requestAcceptPayReapplyResult(addresseeBean.getId());
                                }
                            }
                        });
                    } else {
                        holder.setVisible(R.id.side_menu_ll, false);
                    }
                    holder.setText(R.id.cost, "代付金额：" + cost + "元").setVisible(R.id.cost, true);

                } else {
                    holder.setVisible(R.id.cost, false);
                    holder.setVisible(R.id.side_menu_ll, false);
                }
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestRecipientsMessageResult("{\"id\":\"" + addresseeBean.getId() + "\"}");
                    }
                });
                holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
            }
        };
        addresseeRv.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                refreshRV();
                SkipIntentUtil.rvRefreshTimeout(AddresseeActivity.this, refreshLayout, 0);

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                refreshRV();
                SkipIntentUtil.rvRefreshTimeout(AddresseeActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    /**
     * 刷新列表
     */
    private void refreshRV() {
        if (!TextUtils.isEmpty(Constants.SOURCE)) {
            switch (Constants.SOURCE) {
                case "accept":
                    requestAddressDisplayResult();
                    break;
                case "acceptPay":
                    requestAcceptPayListResult();
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.return_arrows, R.id.additional_payment_goods_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                SkipIntentUtil.returnHomeFinish(this);
                break;
            case R.id.additional_payment_goods_btn:
                SkipIntentUtil.skipIntent(this, CollectingPaymentActivity.class);
                break;
        }
    }

    @Override
    public void onARecipientsDisplayListFinish(Object o) {
        setRVFinish((AddresseeDisplayBean) o);
    }

    @Override
    public void onARecipientsDisplayBeanFinish(Object o) {
        AddressBean addressBean = (AddressBean) o;
        if (addressBean != null) {
            String taskDefKey = addressBean.getAct().getTaskDefKey();
            if (taskDefKey != null) {
                switch (taskDefKey) {
                    case "sendImmediatelyChoose":
                        intent = new Intent(this, ParcelParticularsActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "sendImmediatelyChoose");
                        break;
                    case "sendImmediately":
                        intent = new Intent(this, ParcelParticularsPredictActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "sendImmediately");
                        try {
                            String time = AcquisitionTimeUtil.longToString(addressBean.getAct().getVars().getMap().getTime(), "yyyy-MM-dd HH:mm:ss");
                            intent.putExtra("time", time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "singExpress":
                        intent = new Intent(this, ParcelParticularsPredictActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "singExpress");
                        try {
                            String time = AcquisitionTimeUtil.longToString(addressBean.getAct().getVars().getMap().getTime(), "yyyy-MM-dd HH:mm:ss");
                            intent.putExtra("time", time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        intent = new Intent(this, ParcelParticularsActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "addressee_dispaly");
                        break;
                }
                intent.putExtra("home_source", Constants.SOURCE);
                startActivity(intent);
            } else {
                intent = new Intent(this, ParcelParticularsActivity.class);
                RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "addressee_dispaly");
                intent.putExtra("home_source", Constants.SOURCE);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAcceptPayListFinish(Object o) {
        setRVFinish((AddresseeDisplayBean) o);
    }

    @Override
    public void onAcceptPayCancelFinish(Object o) {
        BaseBean acceptPayCancel = (BaseBean) o;
        if (acceptPayCancel != null && acceptPayCancel.getStatus().equals("1")) {
            refreshRV();
        }
    }

    @Override
    public void onAcceptPayReapplyFinish(Object o) {
        BaseBean acceptPayReapply = (BaseBean) o;
        if (acceptPayReapply != null && acceptPayReapply.getStatus().equals("1")) {
            refreshRV();
        }
    }

    /**
     * 请求完成设置列表参数
     *
     * @param addresseeDisplayBean
     */
    private void setRVFinish(AddresseeDisplayBean addresseeDisplayBean) {
        if (addresseeDisplayBean != null) {
            if (addresseeBeanList == null) {
                addresseeBeanList = new ArrayList<>();
            }
            List<AddresseeDisplayBean.PageBean.ListBean> list = addresseeDisplayBean.getPage().getList();
            if (addresseeDisplayBean.getPage().getPageNo() == 1) {
                if (addresseeDisplayBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    addresseeBeanList.clear();
                    addresseeBeanList = list;
                    setUpParameters();
                    if (addresseeDisplayBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, addresseeRv, addresseeBeanList.size());
                    for (int i = 0; i < list.size(); i++) {
                        addresseeBeanList.add(list.get(i));
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
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(AddresseeActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                initRequest();
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
        addresseePresenter.dettach();
        acceptPayListPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

}
