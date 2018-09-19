package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.GetOrderInfoBean;
import com.example.ysww.snailfamily.bean.shopping.ShopOrderListBean;
import com.example.ysww.snailfamily.custom.RadioGroup;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.GetOrderInfoView;
import com.example.ysww.snailfamily.mvp.shopping.ShopOrderListView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.GetOrderInfoPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopOrderListPresenter;
import com.example.ysww.snailfamily.ui.BottomNavigationMenuActivity;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.MoveLocationUtil;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity implements ShopOrderListView, GetOrderInfoView {

    @InjectView(R.id.shopping_rg)
    RadioGroup shoppingRg;
    @InjectView(R.id.my_order_rv)
    RecyclerView myOrderRv;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.buy_online_rb)
    RadioButton buyOnlineRb;
    @InjectView(R.id.offline_shopping_rb)
    RadioButton offlineShoppingRb;
    private CommonAdapter<ShopOrderListBean.ListBean> myOrderAdapter;
    private CommonAdapter<ShopOrderListBean.ListBean.ShopCommodityConsumerListBean> commodityAdapter;
    private ShopOrderListPresenter shopOrderListPresenter = new ShopOrderListPresenter();//电商订单列表
    private GetOrderInfoPresenter getOrderInfoPresenter = new GetOrderInfoPresenter();//电商订单详情
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数
    private String shopType;//类型
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private List<ShopOrderListBean.ListBean> shopOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();

    }
    private void initViews() {
        Constants.SOURCE = getIntent().getStringExtra("source");
        if(!TextUtils.isEmpty(Constants.SOURCE)){
            if (Constants.SOURCE.equals("offlinePaymentOrder")) {
                offlineShoppingRb.setChecked(true);
                shopType = "2";
            }else{
                buyOnlineRb.setChecked(true);
                shopType = "1";
            }
        }else{
            buyOnlineRb.setChecked(true);
            shopType = "1";
        }

        requestShopOrderList();
        shoppingRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buy_online_rb:
                        shopType = "1";
                        requestShopOrderList();
                        break;
                    case R.id.offline_shopping_rb:
                        shopType = "2";
                        requestShopOrderList();
                        break;
                }
            }
        });
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
        myOrderRv.setLayoutManager(linearLayoutManager);
    }
    /**
     * 电商订单列表
     */
    private void requestShopOrderList() {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        JSONObject json = new JSONObject();
        try {
            json.put("pageNo", pageno);
            json.put("pageSize", pagesize);
            json.put("shopType", shopType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new OkHttpResolve(this);
        shopOrderListPresenter.attach(this);
        shopOrderListPresenter.postJsonShopOrderListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 电商订单详情
     */
    private void requestGetOrderInfo(String id) {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        new OkHttpResolve(this);
        getOrderInfoPresenter.attach(this);
        getOrderInfoPresenter.postJsonGetOrderInfoResult("{\"id\":\"" + id + "\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        linearLayoutManager = new LinearLayoutManager(this);
        myOrderRv.setLayoutManager(linearLayoutManager);
        myOrderAdapter = new CommonAdapter<ShopOrderListBean.ListBean>(this, R.layout.shopping_my_order_item, shopOrderList) {
            @Override
            protected void convert(ViewHolder holder, ShopOrderListBean.ListBean listBean, int position) {
                holder.setText(R.id.site_name, listBean.getWorkstationId());
                holder.setText(R.id.site_payment_state, listBean.getBusinessStatus());
                holder.setText(R.id.order_number, "订单号：" + listBean.getId());
                holder.setText(R.id.commodity_total_num, "共" + listBean.getQuantity() + "件");
                holder.setText(R.id.cope_with, "应付¥" + SkipIntentUtil.conversionAmountFormat(listBean.getExpenditure() + ""));


                ShopOrderListBean.ListBean.ShopCommodityConsumerListBean shopCommodityConsumerListBean = listBean.getShopCommodityConsumerList().get(0);
                ShopOrderListBean.ListBean.ShopCommodityConsumerListBean.ShopCommodityBean shopCommodity = shopCommodityConsumerListBean.getShopCommodity();
                holder.setText(R.id.commodity_name, shopCommodity.getName() + shopCommodity.getSku());
                int quantity = shopCommodityConsumerListBean.getQuantity();
                holder.setText(R.id.commodity_num, quantity + "");
                holder.setText(R.id.commodity_money, "¥" + SkipIntentUtil.conversionAmountFormat((quantity * shopCommodity.getPresentCost()) + ""));
                if (listBean.getShopCommodityConsumerList().size() >= 2) {
                    holder.setVisible(R.id.commodity_rl_1, true);
                    ShopOrderListBean.ListBean.ShopCommodityConsumerListBean shopCommodityConsumerListBean1 = listBean.getShopCommodityConsumerList().get(1);
                    ShopOrderListBean.ListBean.ShopCommodityConsumerListBean.ShopCommodityBean shopCommodit1 = shopCommodityConsumerListBean1.getShopCommodity();
                    holder.setText(R.id.commodity_name_1, shopCommodit1.getName() + shopCommodit1.getSku());
                    int quantity1 = shopCommodityConsumerListBean1.getQuantity();
                    holder.setText(R.id.commodity_num_1, quantity1 + "");
                    holder.setText(R.id.commodity_money_1, "¥" + SkipIntentUtil.conversionAmountFormat((quantity1 * shopCommodit1.getPresentCost()) + ""));
                } else {
                    holder.setVisible(R.id.commodity_rl_1, false);
                }
                holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
            }
        };
        myOrderRv.setAdapter(myOrderAdapter);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                requestShopOrderList();
                SkipIntentUtil.rvRefreshTimeout(MyOrderActivity.this, refreshLayout, 0);

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requestShopOrderList();
                SkipIntentUtil.rvRefreshTimeout(MyOrderActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(myOrderAdapter, refreshLayout);
        myOrderAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                requestGetOrderInfo(shopOrderList.get(position).getId());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick(R.id.return_arrows)
    public void onViewClicked() {
        returnShopping();
    }

    private void returnShopping() {
        if (TextUtils.isEmpty(getIntent().getStringExtra("source"))) {
            finish();
        } else {
            Intent intent = new Intent(this, BottomNavigationMenuActivity.class);
            intent.putExtra("source", "shopping");
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onShopOrderListFinish(Object o) {
        ShopOrderListBean shopOrderListBean = (ShopOrderListBean) o;
        if (shopOrderListBean != null) {
            if (shopOrderList == null) {
                shopOrderList = new ArrayList<>();
            }
            List<ShopOrderListBean.ListBean> list = shopOrderListBean.getList();
            if (shopOrderListBean.getPage().getPageNo() == 1) {
                if (shopOrderListBean.getPage().getCount() > 0 && list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    shopOrderList.clear();
                    shopOrderList = list;
                    setUpParameters();
                    if (shopOrderListBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, myOrderRv, shopOrderList.size());
                    for (int i = 0; i < list.size(); i++) {
                        shopOrderList.add(list.get(i));
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
    public void onGetOrderInfoFinish(Object o) {
        GetOrderInfoBean getOrderInfoBean = (GetOrderInfoBean) o;
        if (getOrderInfoBean != null) {
            Intent intent = new Intent(this, MyOrderDetailsActivity.class);
            intent.putExtra("getOrderInfoBean",(Serializable)getOrderInfoBean);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            returnShopping();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        shopOrderListPresenter.dettach();
        getOrderInfoPresenter.dettach();
    }


}
