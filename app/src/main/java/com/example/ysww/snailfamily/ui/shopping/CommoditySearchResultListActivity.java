package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.GetCommodityInfo;
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.custom.wiget.ShopCartSearchDialog;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.GetCommodityInfoView;
import com.example.ysww.snailfamily.mvp.shopping.GoodsListByCategorySearchView;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GetCommodityInfoPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GoodsListByCategorySearchPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopCartCountPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.MoveLocationUtil;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 收拾商品详情页
 */
public class CommoditySearchResultListActivity extends BaseActivity implements AddReduceGoodsView,ShopCartCountView,GetCommodityInfoView,GoodsListByCategorySearchView, ShopCartSearchDialog.ShopCartSearchDialogImp {
    @InjectView(R.id.input_commodity_name)
    EditText inputCommodityName;
    @InjectView(R.id.commodity_search_list_rv)
    RecyclerView commoditySearchListRv;
    @InjectView(R.id.activity_commodity_search_result_list)
    LinearLayout mainLayout;
    @InjectView(R.id.comprehensive_cb)
    CheckBox comprehensiveCb;
    @InjectView(R.id.sales_volume_cb)
    CheckBox salesVolumeCb;
    @InjectView(R.id.price_cb)
    CheckBox priceCb;
    @InjectView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.shopping_cart_total_tv)
    TextView totalPriceTextView;
    @InjectView(R.id.shopping_cart)
    ImageView shoppingCartView;
    @InjectView(R.id.shopping_cart_total_num)
    TextView totalPriceNumTextView;
    @InjectView(R.id.freight)
    TextView freight;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private Intent intent;
    private String search;//搜索

    private GoodsListByCategorySearchPresenter goodsListByCategorySearchPresenter = new GoodsListByCategorySearchPresenter();//商品搜索
    private GetCommodityInfoPresenter getCommodityInfoPresenter = new GetCommodityInfoPresenter();//商品详情接口
    private ShopCartCountPresenter shopCartCountPresenter = new ShopCartCountPresenter();//购物车清单统计
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private CommonAdapter<GoodsListByCategoryBean.ListBean> goodsListByCategorySearchAdapter;
    private List<GoodsListByCategoryBean.ListBean> goodsListByCategorySearchBeanList;
    private GridLayoutManager gridLayoutManager;
    private String orderBy;//排序类型
    private String sort;//排序
    private int totalQuantity;//购物总数
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数
    private int addReduceGoodsId = -1;//item id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_search_result_list);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initView();
    }

    private void initView() {
        requestShopCartCount();
        intent = getIntent();
        search = intent.getStringExtra("search");
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        if (!TextUtils.isEmpty(search)) {
            inputCommodityName.setText(search);
            requesGoodsListByCategorySearch(search);
        }else{
            requesGoodsListByCategorySearch("");
        }
        inputCommodityName.setSelection(inputCommodityName.getText().length());
        //添加头部
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setHeaderHeight(50);
        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        commoditySearchListRv.setLayoutManager(gridLayoutManager);
    }


    /**
     * 初始化 获取相应的商品信息
     */
    private void requesGoodsListByCategorySearch(String search) {
        JSONObject json = new JSONObject();
        try {
            if(!TextUtils.isEmpty(orderBy) && !orderBy.equals("comprehensive")){
                json.put("orderBy", orderBy);
                json.put("sort", sort);
            }
            json.put("search", search);
            json.put("pageNo", pageno);
            json.put("pageSize", pagesize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        goodsListByCategorySearchPresenter.attach(this);
        goodsListByCategorySearchPresenter.postJsonGoodsListByCategorySearchResult(json.toString(), this, lazyLoadProgressDialog);
    }
    /**
     * 初始化 查询商品详情
     */
    private void requestGetCommodityInfo(String id) {
        new OkHttpResolve(this);
        getCommodityInfoPresenter.attach(this);
        getCommodityInfoPresenter.postJsonGetCommodityInfoResult("{\"id\":\"" + id + "\"}", this, lazyLoadProgressDialog);
    }
    /**
     * 初始化 购物车清单统计
     */
    private void requestShopCartCount() {
        new OkHttpResolve(this);
        shopCartCountPresenter.attach(this);
        shopCartCountPresenter.postJsonShopCartCountResult("{\"shopType\":\"1\"}", this);
    }
    /**
     * 初始化 添加商品
     */
    private void requestAddGoods(String shopCommodityId) {
        JSONObject json= new JSONObject();
        try {
            json.put("quantity","1");
            json.put("shopCommodityId",shopCommodityId);
            json.put("shopType","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonAddGoodsResult(json.toString(),this);
    }
    /**
     * 初始化 删除商品
     */
    private void requestReduceGoods(String shopCommodityId) {
        JSONObject json= new JSONObject();
        try {
            json.put("quantity","1");
            json.put("shopCommodityId",shopCommodityId);
            json.put("shopType","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonReduceGoodsResult(json.toString(),this);
    }
    private void showTotalPrice() {
        requestShopCartCount();
    }
    private void showCart() {
        ShopCartSearchDialog dialog = new ShopCartSearchDialog(this, R.style.cartdialog,"1");
        Window window = dialog.getWindow();
        dialog.setShopCartDialogImp(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.dimAmount = 0.5f;
        window.setAttributes(params);
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
    }

    @OnClick({R.id.shopping_cart_layout,R.id.return_arrows, R.id.shopping_seach_btn, R.id.go_accounts, R.id.comprehensive_cb, R.id.sales_volume_cb, R.id.price_cb})
    public void onViewClicked(View view) {
        Drawable rightAscendingDrawable = getResources().getDrawable(R.drawable.ascending_icon);
        rightAscendingDrawable.setBounds(0, 0, rightAscendingDrawable.getMinimumWidth(), rightAscendingDrawable.getMinimumHeight());
        Drawable rightDescendingDrawable = getResources().getDrawable(R.drawable.descending_icon);
        rightDescendingDrawable.setBounds(0, 0, rightDescendingDrawable.getMinimumWidth(), rightDescendingDrawable.getMinimumHeight());
        switch (view.getId()) {
            case R.id.shopping_cart_layout:
                if(totalQuantity>0){
                    showCart();
                }
                break;
            case R.id.return_arrows:
                finish();
                break;
            case R.id.shopping_seach_btn:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                orderBy = "";
                search=inputCommodityName.getText().toString();
                requesGoodsListByCategorySearch(search);
                break;
            case R.id.go_accounts:
                SkipIntentUtil.goAccounts(totalQuantity,this);
                break;
            case R.id.comprehensive_cb:
                orderBy = "comprehensive";
                requesGoodsListByCategorySearch(search);
                salesVolumeCb.setTextColor(Color.parseColor("#666666"));
                priceCb.setTextColor(Color.parseColor("#666666"));
                comprehensiveCb.setTextColor(Color.parseColor("#6fd1c8"));
                if (comprehensiveCb.isChecked() || comprehensiveCb.isChecked() == false || salesVolumeCb.isChecked() || priceCb.isChecked()) {
                    comprehensiveCb.setChecked(true);
                    salesVolumeCb.setChecked(false);
                    priceCb.setChecked(false);
                }
                break;
            case R.id.sales_volume_cb:
                salesVolumeCb.setTextColor(Color.parseColor("#6fd1c8"));
                comprehensiveCb.setTextColor(Color.parseColor("#666666"));
                priceCb.setTextColor(Color.parseColor("#666666"));
                if (comprehensiveCb.isChecked() || salesVolumeCb.isChecked() || priceCb.isChecked()) {
                    comprehensiveCb.setChecked(false);
                    salesVolumeCb.setChecked(true);
                    priceCb.setChecked(false);
                }
                orderBy = "sales";
                if (salesVolumeCb.isChecked() == false) {
                    salesVolumeCb.setCompoundDrawables(null, null, rightDescendingDrawable, null);
                    sort = "desc";
                    requesGoodsListByCategorySearch(search);
                } else {
                    salesVolumeCb.setCompoundDrawables(null, null, rightAscendingDrawable, null);
                    sort = "asc";
                    requesGoodsListByCategorySearch(search);
                }
                break;
            case R.id.price_cb:
                priceCb.setTextColor(Color.parseColor("#6fd1c8"));
                comprehensiveCb.setTextColor(Color.parseColor("#666666"));
                salesVolumeCb.setTextColor(Color.parseColor("#666666"));
                if (comprehensiveCb.isChecked() || salesVolumeCb.isChecked() || priceCb.isChecked()) {
                    comprehensiveCb.setChecked(false);
                    salesVolumeCb.setChecked(false);
                    priceCb.setChecked(true);

                }
                orderBy = "present_cost";
                if (priceCb.isChecked() == false) {
                    priceCb.setCompoundDrawables(null, null, rightDescendingDrawable, null);
                    sort = "desc";
                    requesGoodsListByCategorySearch(search);
                } else {
                    priceCb.setCompoundDrawables(null, null, rightAscendingDrawable, null);
                    sort = "asc";
                    requesGoodsListByCategorySearch(search);
                }
                break;
        }
    }

    @Override
    public void onGoodsListByCategorySearchFinish(Object o) {
        GoodsListByCategoryBean goodsListByCategorySearchBean = (GoodsListByCategoryBean) o;
        if (goodsListByCategorySearchBean != null) {
            if (goodsListByCategorySearchBeanList == null) {
                goodsListByCategorySearchBeanList = new ArrayList<>();
            }
            List<GoodsListByCategoryBean.ListBean> list = goodsListByCategorySearchBean.getList();
            if (goodsListByCategorySearchBean.getPage().getPageNo() == 1) {
                if (goodsListByCategorySearchBean.getPage().getCount() > 0 && list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    goodsListByCategorySearchBeanList.clear();
                    goodsListByCategorySearchBeanList = list;
                    setUpParameters();
                    if (goodsListByCategorySearchBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(gridLayoutManager, commoditySearchListRv, goodsListByCategorySearchBeanList.size());
                    for (int i = 0; i < list.size(); i++) {
                        goodsListByCategorySearchBeanList.add(list.get(i));
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
    public void onGetCommodityInfoFinish(Object o) {
        GetCommodityInfo getCommodityInfo = (GetCommodityInfo) o;
        if (getCommodityInfo != null) {
            Intent intent = new Intent(this, CommodityDetailsPageActivity.class);
            intent.putExtra("GetCommodityInfo", (Serializable) getCommodityInfo.getShopCommodity());
            startActivity(intent);
        }
    }
    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        gridLayoutManager = new GridLayoutManager(this, 2);
        commoditySearchListRv.setLayoutManager(gridLayoutManager);
        goodsListByCategorySearchAdapter = new CommonAdapter<GoodsListByCategoryBean.ListBean>(this,R.layout.commodity_search_result_list_item,goodsListByCategorySearchBeanList) {
            @Override
            protected void convert(ViewHolder holder, final GoodsListByCategoryBean.ListBean listBean, final int position) {
                ImageView shoppingImg = holder.getView(R.id.item_img);
                TextView shoppingOriginalPrice=holder.getView(R.id.shopping_original_price);
                RequestOperationUtil.setGlide(CommoditySearchResultListActivity.this, Constants.UP_LOAD_IMAGE_TOP + listBean.getNarrowViewUrl(), shoppingImg);
                holder.setText(R.id.right_dish_name,listBean.getName() + listBean.getSku());
                int orderQuantity = listBean.getOrderQuantity();
                if(orderQuantity>0){
                    holder.setVisible(R.id.right_dish_remove,true);
                    holder.setVisible(R.id.right_dish_account,true);
                    holder.setText(R.id.right_dish_account,orderQuantity+"");
                }else{
                    holder.setVisible(R.id.right_dish_remove,false);
                    holder.setVisible(R.id.right_dish_account,false);
                }

                holder.setText(R.id.shopping_special_offer,"¥" + SkipIntentUtil.conversionAmountFormat(listBean.getPresentCost() + ""));
                shoppingOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setText(R.id.shopping_original_price,"¥ " + SkipIntentUtil.conversionAmountFormat(listBean.getOriginalCost() + ""));

                holder.setOnClickListener(R.id.right_dish_remove, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addReduceGoodsId = position;
                        requestReduceGoods(listBean.getId());
                    }
                });
                holder.setOnClickListener(R.id.right_dish_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addReduceGoodsId = position;
                        requestAddGoods(listBean.getId());
                    }
                });
                holder.setBackgroundRes(R.id.right_dish_item,R.drawable.customer_selector);
            }
        };
        commoditySearchListRv.setAdapter(goodsListByCategorySearchAdapter);
        goodsListByCategorySearchAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestGetCommodityInfo(goodsListByCategorySearchBeanList.get(position).getId());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshList();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                requesGoodsListByCategorySearch(search);
                SkipIntentUtil.rvRefreshTimeout(CommoditySearchResultListActivity.this, refreshLayout, 0);
            }
        });
        if (goodsListByCategorySearchAdapter != null) {
            goodsListByCategorySearchAdapter.notifyDataSetChanged();
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }
    @Override
    public void onShopCartCountFinish(Object o) {
        ShopCartBean shopCartBean = (ShopCartBean) o;
        totalQuantity=shopCartBean.getTotalQuantity();
        if (shopCartBean != null && shopCartBean.getTotalQuantity() > 0) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    shoppingCartView.setImageResource(R.drawable.shopping_cart_sel);
                }
            }, 100);
            shoppingCartView.setImageResource(R.drawable.shopping_cart_sel);

            totalPriceTextView.setText("¥"+ SkipIntentUtil.conversionAmountFormat(shopCartBean.getTotlammount()+""));
            totalPriceTextView.setTextColor(Color.parseColor("#d7123d"));
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText(totalQuantity+"");
            freight.setText("免运费");
        }else{
            shoppingCartView.setImageResource(R.drawable.shopping_cart_nor);
            totalPriceTextView.setText("¥ 0.00");
            totalPriceTextView.setTextColor(Color.parseColor("#999999"));
            totalPriceNumTextView.setVisibility(View.GONE);
            freight.setText("配送费：¥0.00");
        }
    }

    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoodsBean = (AddReduceGoodsBean) o;
        if(reduceGoodsBean!=null){
            if (addReduceGoodsId > -1) {
                goodsListByCategorySearchBeanList.get(addReduceGoodsId).setOrderQuantity(reduceGoodsBean.getShopCommodityConsumer().getQuantity());
            }
            goodsListByCategorySearchAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;
        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoodsBean = (AddReduceGoodsBean) o;
        if(reduceGoodsBean!=null){
            int quantity = reduceGoodsBean.getShopCommodityConsumer().getQuantity();
            if (addReduceGoodsId > -1) {
                goodsListByCategorySearchBeanList.get(addReduceGoodsId).setOrderQuantity(quantity);
            }
            goodsListByCategorySearchAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;
        }
    }
    /**
     * 刷新列表
     */
    private void refreshList(){
        pageno = 1;
        requesGoodsListByCategorySearch(search);
        SkipIntentUtil.rvRefreshTimeout(CommoditySearchResultListActivity.this, refreshLayout, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(goodsListByCategorySearchAdapter!=null){
            pageno = 1;
            requesGoodsListByCategorySearch(search);
            goodsListByCategorySearchAdapter.notifyDataSetChanged();
            requestShopCartCount();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        goodsListByCategorySearchPresenter.dettach();
        shopCartCountPresenter.dettach();
        addReduceGoodsPresenter.dettach();
    }


}
