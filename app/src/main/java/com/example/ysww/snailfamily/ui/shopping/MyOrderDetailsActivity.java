package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.GetOrderInfoBean;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的订单详情页面
 */
public class MyOrderDetailsActivity extends AutoLayoutActivity {

    @InjectView(R.id.site_img)
    ImageView siteImg;
    @InjectView(R.id.site_name)
    TextView siteName;
    @InjectView(R.id.order_number)
    TextView orderNumber;
    @InjectView(R.id.payment_method)
    TextView paymentMethod;
    @InjectView(R.id.payment_time)
    TextView paymentTime;
    @InjectView(R.id.my_order_details_rv)
    RecyclerView myOrderDetailsRv;
    @InjectView(R.id.paid_money)
    TextView paidMoney;
    @InjectView(R.id.title_tv)
    TextView titleTv;
    @InjectView(R.id.go_pay_btn)
    Button goPayBtn;
    @InjectView(R.id.button_rl)
    RelativeLayout buttonRl;
    private CommonAdapter<GetOrderInfoBean.ShopOrderBean.ShopCommodityConsumerListBean> myOrderDeatailsAdapter;
    private Intent intent;
    private GetOrderInfoBean getOrderInfoBean;
    private GetOrderInfoBean.WorkstationBean workstation;
    private GetOrderInfoBean.ShopOrderBean shopOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        initViews();
    }

    private void initViews() {
        intent = getIntent();
        if (intent != null) {
            getOrderInfoBean = (GetOrderInfoBean) intent.getSerializableExtra("getOrderInfoBean");
            workstation = getOrderInfoBean.getWorkstation();
            shopOrder = getOrderInfoBean.getShopOrder();
            siteName.setText(workstation.getStationName());
            orderNumber.setText("订单编号：" + shopOrder.getId());
            String businessStatus = shopOrder.getBusinessStatus();
            if (businessStatus.equals("未支付")) {
                buttonRl.setVisibility(View.VISIBLE);
            } else {
                buttonRl.setVisibility(View.GONE);
            }
            titleTv.setText(shopOrder.getBusinessStatus());

            myOrderDetailsRv.setLayoutManager(new LinearLayoutManager(this));
            myOrderDeatailsAdapter = new CommonAdapter<GetOrderInfoBean.ShopOrderBean.ShopCommodityConsumerListBean>(this, R.layout.shopping_my_order_deatails_item, getOrderInfoBean.getShopOrder().getShopCommodityConsumerList()) {
                @Override
                protected void convert(ViewHolder holder, GetOrderInfoBean.ShopOrderBean.ShopCommodityConsumerListBean commodityConsumerListBean, int position) {
                    GetOrderInfoBean.ShopOrderBean.ShopCommodityConsumerListBean.ShopCommodityBean shopCommodity = commodityConsumerListBean.getShopCommodity();
                    holder.setText(R.id.commodity_name, shopCommodity.getName() + shopCommodity.getSku());
                    int quantity = commodityConsumerListBean.getQuantity();
                    holder.setText(R.id.commodity_num, quantity + "");
                    holder.setText(R.id.commodity_money, "¥" + SkipIntentUtil.conversionAmountFormat((quantity * shopCommodity.getPresentCost()) + ""));
                }
            };
            myOrderDetailsRv.setAdapter(myOrderDeatailsAdapter);
            paidMoney.setText("¥" + SkipIntentUtil.conversionAmountFormat(shopOrder.getExpenditure() + ""));
        }

    }

    @OnClick({R.id.return_arrows, R.id.go_pay_btn, R.id.delete_order_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.go_pay_btn:
                if (shopOrder.getShopType().equals("1")) {
                    intent = new Intent(this, PaymentOrderActivity.class);
                } else {
                    intent = new Intent(this, OfflinePaymentOrderActivity.class);
                }
                if(shopOrder!=null) {
                    intent.putExtra("act.procInsId", shopOrder.getAct().getProcInsId());
                    intent.putExtra("act.taskDefKey", shopOrder.getAct().getTaskDefKey());
                    intent.putExtra("act.taskId", shopOrder.getAct().getTaskId());
                    intent.putExtra("id", shopOrder.getId());
                    intent.putExtra("limitTime", shopOrder.getLimitTime() + "");
                    intent.putExtra("expenditure", shopOrder.getExpenditure() + "");
                    startActivity(intent);
                }
                break;
            case R.id.delete_order_btn:
                break;
        }
    }
}
