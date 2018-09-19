package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.CreateShopOrderBean;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.bean.shopping.ShopListBean;
import com.example.ysww.snailfamily.custom.SwitchButton;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.CreateShopOrderView;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.mvp.shopping.ShopListView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.CreateShopOrderPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopCartCountPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopListPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity implements CreateShopOrderView, ShopListView, AddReduceGoodsView, ShopCartCountView{
    @InjectView(R.id.no_content)
    LinearLayout noContent;
    @InjectView(R.id.confirm_order_rv)
    RecyclerView confirmOrderRv;
    @InjectView(R.id.integral_sb)
    SwitchButton integralSb;
    @InjectView(R.id.total_amount)
    TextView totalAmount;

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private ShopListPresenter shopListPresenter = new ShopListPresenter();//购物车清单
    private ShopCartCountPresenter shopCartCountPresenter = new ShopCartCountPresenter();//购物车清单统计
    private CreateShopOrderPresenter createShopOrderPresenter = new CreateShopOrderPresenter();//电商订单生成
    private CommonAdapter<ShopListBean.ListBean> confirmOrderAdapter;
    private List<ShopListBean.ListBean> shopList;
    private String receiverId;//地址ID
    private String shopGoodsIds;//选择的商品
    private int addReduceGoodsId = -1;//item id
    private int totlammount = 0;//总金额
    private int[] placeOrdrRadioIndex;//是否选中索引
    private boolean[] flag;//把flag数组定义为全局变量
    private int noIndex;//未选中索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initView();
    }
    private void initView() {
        requestShopList();
        requestShopCartCount();
        confirmOrderRv.setLayoutManager(new LinearLayoutManager(this));
    }
    /**
     * 初始化 购物车清单
     */
    private void requestShopList() {
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        new OkHttpResolve(this);
        shopListPresenter.attach(this);
        shopListPresenter.postJsonShopListResult("{\"shopType\":\"2\"}", this,lazyLoadProgressDialog);
    }

    /**
     * 初始化 添加商品
     */
    private void requestAddGoods(String shopCommodityId) {
        JSONObject json = new JSONObject();
        try {
            json.put("quantity", "1");
            json.put("shopCommodityId", shopCommodityId);
            json.put("shopType", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonAddGoodsResult(json.toString(), this);
    }

    /**
     * 初始化 删除商品
     */
    private void requestReduceGoods(String shopCommodityId) {
        JSONObject json = new JSONObject();
        try {
            json.put("quantity", "1");
            json.put("shopCommodityId", shopCommodityId);
            json.put("shopType", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonReduceGoodsResult(json.toString(), this);
    }

    /**
     * 初始化 购物车清单统计
     */
    private void requestShopCartCount() {
        new OkHttpResolve(this);
        shopCartCountPresenter.attach(this);
        shopCartCountPresenter.postJsonShopCartCountResult("{\"shopType\":\"2\"}", this);
    }
    /**
     * 初始化 电商订单生成 站点购
     */
    private void requestSitePurchase() {
        JSONObject json = new JSONObject();
            try {
                json.put("shopGoodsIds", shopGoodsIds);
                json.put("shopType", "2");
                json.put("workstationId",getIntent().getStringExtra("workstationId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new OkHttpResolve(this);
            createShopOrderPresenter.attach(this);
            createShopOrderPresenter.postJsonSitePurchaseResult(json.toString(), this, lazyLoadProgressDialog);
    }
    @OnClick({R.id.go_payment, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_payment:
                if(totlammount>0){
                    if (shopGoodsIds != null) {
                        shopGoodsIds = shopGoodsIds.substring(5, shopGoodsIds.length());
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestSitePurchase();
                    }
                }else{
                    SkipIntentUtil.toastShow(ConfirmOrderActivity.this, "您未选择商品！");
                }

                break;
            case R.id.return_arrows:
                finish();
                break;
        }
    }
    private void initAdapter() {
        confirmOrderRv.setLayoutManager(new LinearLayoutManager(this));
        confirmOrderAdapter = new CommonAdapter<ShopListBean.ListBean>(this, R.layout.place_order_item, shopList) {
            @Override
            protected void convert(ViewHolder holder, ShopListBean.ListBean listBean, final int position) {
                holder.setText(R.id.right_dish_account, listBean.getQuantity() + "");
                final ShopListBean.ListBean.ShopCommodityBean shopCommodity = listBean.getShopCommodity();
                holder.setText(R.id.right_dish_name, shopCommodity.getName() + shopCommodity.getSku());
                holder.setText(R.id.shopping_special_offer, "¥ " + SkipIntentUtil.conversionAmountFormat(shopCommodity.getPresentCost() + ""));
                TextView shoppingOriginalPrice = holder.getView(R.id.shopping_original_price);
                shoppingOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setText(R.id.shopping_original_price, "¥ " + SkipIntentUtil.conversionAmountFormat(shopCommodity.getOriginalCost() + ""));
                ImageView shoppingImg = holder.getView(R.id.shopping_img);
                RequestOperationUtil.setGlide(ConfirmOrderActivity.this, Constants.UP_LOAD_IMAGE_TOP + shopCommodity.getNarrowViewUrl(), shoppingImg);
                final CheckBox placeOrdrCheckBox = holder.getView(R.id.place_ordr_checkbox);
                for (int i = 0; i <shopList.size() ; i++) {
                    if (placeOrdrCheckBox.isChecked() == true) {
                        if(i==position){
                            shopGoodsIds = shopGoodsIds + "," + listBean.getId();
                        }
                    }
                }
                holder.setOnClickListener(R.id.right_dish_remove, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (placeOrdrCheckBox.isChecked() == true) {
                            addReduceGoodsId = position;
                            requestReduceGoods(shopCommodity.getId());
                        } else {
                            SkipIntentUtil.toastShow(ConfirmOrderActivity.this, "您未选择此商品！");
                        }

                    }
                });
                holder.setOnClickListener(R.id.right_dish_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (placeOrdrCheckBox.isChecked() == true) {
                            addReduceGoodsId = position;
                            requestAddGoods(shopCommodity.getId());
                        } else {
                            SkipIntentUtil.toastShow(ConfirmOrderActivity.this, "您未选择此商品！");
                        }
                    }
                });

                placeOrdrCheckBox.setOnCheckedChangeListener(null);
                placeOrdrCheckBox.setChecked(flag[position]);
                placeOrdrCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        flag[position] = b;
                        totlammount = 0;
                        shopGoodsIds = null;
                        if (placeOrdrCheckBox.isChecked() == true) {
                            flag[position] = true;
                            placeOrdrRadioIndex[position] = 1;
                            noIndex--;
                            initTotalAmount();
                            notifyDataSetChanged();

                        } else {
                            flag[position] = false;
                            placeOrdrRadioIndex[position] = 0;
                            noIndex++;
                            if(noIndex>=shopList.size()){
                                totalAmount.setText("¥0.00");
                            }else{
                                initTotalAmount();
                            }
                            notifyDataSetChanged();

                        }
                    }
                });
            }
        };
        confirmOrderRv.setAdapter(confirmOrderAdapter);
    }
    @Override
    public void onShopListFinish(Object o) {
        ShopListBean shopListBean = (ShopListBean) o;
        if (shopListBean != null) {
            shopList = shopListBean.getList();
            if (shopList.size() > 0) {
                initPlaceOrdrRadioIndex();
                flag = new boolean[shopList.size()];
                for (int i = 0; i < placeOrdrRadioIndex.length; i++) {
                    flag[i] = true;
                }
                initAdapter();
                confirmOrderRv.setVisibility(View.VISIBLE);
                noContent.setVisibility(View.GONE);
            } else {
                confirmOrderRv.setVisibility(View.GONE);
                noContent.setVisibility(View.VISIBLE);
            }
        }
    }
    /**
     * 初始化 选中索引
     */
    private void initPlaceOrdrRadioIndex() {
        placeOrdrRadioIndex = new int[shopList.size()];
        for (int i = 0; i < placeOrdrRadioIndex.length; i++) {
            placeOrdrRadioIndex[i] = 1;
        }
    }

    /**
     * 初始化总价
     */
    private void initTotalAmount() {
        totlammount = 0;
        int[] totlammounts = new int[placeOrdrRadioIndex.length];
        for (int i = 0; i < placeOrdrRadioIndex.length; i++) {
            if (placeOrdrRadioIndex[i] != 0) {
                totlammounts[i] = (shopList.get(i).getQuantity() * shopList.get(i).getShopCommodity().getPresentCost());
                totlammount += totlammounts[i];
                totalAmount.setText("¥" + SkipIntentUtil.conversionAmountFormat(totlammount + ""));
            }
        }
    }
    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoods = (AddReduceGoodsBean) o;
        if (addGoods != null) {
            if (addReduceGoodsId > -1) {
                shopList.get(addReduceGoodsId).setQuantity(addGoods.getShopCommodityConsumer().getQuantity());
            }
            confirmOrderAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;
        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoods = (AddReduceGoodsBean) o;
        if (reduceGoods != null) {
            int quantity = reduceGoods.getShopCommodityConsumer().getQuantity();
            if (quantity > 0) {
                if (addReduceGoodsId > -1) {
                    shopList.get(addReduceGoodsId).setQuantity(quantity);
                }
            } else {
                if (addReduceGoodsId > -1) {
                    shopList.remove(addReduceGoodsId);
                    initPlaceOrdrRadioIndex();
                    flag[addReduceGoodsId] = false;
                }
                if (shopList.size() <= 0) {
                    noContent.setVisibility(View.VISIBLE);
                    totlammount=0;
                }
            }
            confirmOrderAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;

        }
    }

    @Override
    public void onShopCartCountFinish(Object o) {
        ShopCartBean shopCartBean = (ShopCartBean) o;
        if (shopCartBean != null) {
            if (shopCartBean.getTotalQuantity() > 0) {
                if (placeOrdrRadioIndex != null) {
                    initTotalAmount();
                } else {
                    totlammount=shopCartBean.getTotlammount();
                    totalAmount.setText("¥" + SkipIntentUtil.conversionAmountFormat(totlammount + ""));
                }
            } else {
                totalAmount.setText("¥0.00");
            }
        }
    }

    @Override
    public void onOnlineShoppingFinish(Object o) {
    }

    @Override
    public void onSitePurchaseFinish(Object o) {
        CreateShopOrderBean createShopOrderBean = (CreateShopOrderBean) o;
        if (createShopOrderBean != null) {
            CreateShopOrderBean.ShopOrderBean shopOrder = createShopOrderBean.getShopOrder();
            Intent intent = new Intent(this, OfflinePaymentOrderActivity.class);
            intent.putExtra("act.procInsId", shopOrder.getAct().getProcInsId());
            intent.putExtra("act.taskDefKey", shopOrder.getAct().getTaskDefKey());
            intent.putExtra("act.taskId", shopOrder.getAct().getTaskId());
            intent.putExtra("id", shopOrder.getId());
            intent.putExtra("limitTime", shopOrder.getLimitTime() + "");
            intent.putExtra("expenditure", totlammount + "");
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestShopList();
        requestShopCartCount();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        addReduceGoodsPresenter.dettach();
        shopListPresenter.dettach();
        shopCartCountPresenter.dettach();
        createShopOrderPresenter.dettach();
    }

}
