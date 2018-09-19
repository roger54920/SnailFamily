package com.example.ysww.snailfamily.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.CategoryListBean;
import com.example.ysww.snailfamily.bean.shopping.GetCommodityInfo;
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.custom.RadioGroup;
import com.example.ysww.snailfamily.custom.wiget.ShopCartListDialog;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.CategoryListView;
import com.example.ysww.snailfamily.mvp.shopping.GetCommodityInfoView;
import com.example.ysww.snailfamily.mvp.shopping.GoodsListByCategoryView;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.CategoryListPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GetCommodityInfoPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GoodsListByCategoryPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopCartCountPresenter;
import com.example.ysww.snailfamily.ui.shopping.CommodityDetailsPageActivity;
import com.example.ysww.snailfamily.ui.shopping.CommoditySearchPageActivity;
import com.example.ysww.snailfamily.ui.shopping.SelectSiteActivity;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.MoveLocationUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
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
 * Created by me-jie on 2017/4/8.
 */

public class ShappingFragment extends Fragment implements AddReduceGoodsView, ShopCartCountView, GetCommodityInfoView, GoodsListByCategoryView, CategoryListView, ShopCartListDialog.ShopCartListDialogImp {

    @InjectView(R.id.shopping_rg)
    RadioGroup shoppingRg;
    @InjectView(R.id.left_menu)
    RecyclerView leftMenu;
    @InjectView(R.id.right_menu)
    RecyclerView rightMenu;
    @InjectView(R.id.all_goods_rl)
    RelativeLayout allGoodsRl;
    @InjectView(R.id.scan_code_shopping_rl)
    RelativeLayout scanCodeShoppingRl;
    @InjectView(R.id.offline_shopping_rl)
    RelativeLayout offlineShoppingRl;
    @InjectView(R.id.refresh)
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
    private CategoryListPresenter categoryListPresenter = new CategoryListPresenter();//获取分类列表
    private GoodsListByCategoryPresenter goodsListByCategoryPresenter = new GoodsListByCategoryPresenter();//根据分类 获取相应的商品信息
    private GetCommodityInfoPresenter getCommodityInfoPresenter = new GetCommodityInfoPresenter();//商品详情接口
    private ShopCartCountPresenter shopCartCountPresenter = new ShopCartCountPresenter();//购物车清单统计
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private CommonAdapter<CategoryListBean.ListBean> categoryListBeanCommonAdapter;
    private CommonAdapter<GoodsListByCategoryBean.ListBean> goodsListByCategoryAdapter;
    private List<GoodsListByCategoryBean.ListBean> goodsListByCategoryBeanList;
    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数
    private int[] leftIndex; // 分类索引
    private String shopCommodityId;
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private int totalQuantity;//购物总数
    private int addReduceGoodsId = -1;//item id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        ButterKnife.inject(this, view);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(getActivity());
        initData();
        initView();
        return view;
    }

    private void initView() {
        requestShopCartCount();
        shoppingRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.all_goods_rb:
                        allGoodsRl.setVisibility(View.VISIBLE);
                        offlineShoppingRl.setVisibility(View.GONE);
                        break;
                    case R.id.offline_shopping_rb:
                        allGoodsRl.setVisibility(View.GONE);
                        offlineShoppingRl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //添加头部
        SinaRefreshView headerView = new SinaRefreshView(getActivity());
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setHeaderHeight(50);
        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rightMenu.setLayoutManager(linearLayoutManager);
    }

    /**
     * 初始化 分类列表
     */
    private void requestCategoryList() {
        new OkHttpResolve(getActivity());
        categoryListPresenter.attach(this);
        categoryListPresenter.postJsonCategoryListResult("", getActivity(), lazyLoadProgressDialog);
    }

    /**
     * 初始化 获取相应的商品信息
     */
    private void requesGoodsListByCategory(String categoryId) {
        JSONObject json = new JSONObject();
        try {
            json.put("categoryId", categoryId);
            json.put("pageNo", pageno);
            json.put("pageSize", pagesize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(getActivity());
        goodsListByCategoryPresenter.attach(this);
        goodsListByCategoryPresenter.postJsonGoodsListByCategoryResult(json.toString(), getActivity(), lazyLoadProgressDialog);
    }

    /**
     * 初始化 查询商品详情
     */
    private void requestGetCommodityInfo(String id) {
        new OkHttpResolve(getActivity());
        getCommodityInfoPresenter.attach(this);
        getCommodityInfoPresenter.postJsonGetCommodityInfoResult("{\"id\":\"" + id + "\"}", getActivity(), lazyLoadProgressDialog);
    }

    /**
     * 初始化 购物车清单统计
     */
    private void requestShopCartCount() {
        new OkHttpResolve(getActivity());
        shopCartCountPresenter.attach(this);
        shopCartCountPresenter.postJsonShopCartCountResult("{\"shopType\":\"1\"}", getActivity());
    }

    /**
     * 初始化 添加商品
     */
    private void requestAddGoods(String shopCommodityId) {
        JSONObject json = new JSONObject();
        try {
            json.put("quantity", "1");
            json.put("shopCommodityId", shopCommodityId);
            json.put("shopType", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(getActivity());
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonAddGoodsResult(json.toString(), getActivity());
    }

    /**
     * 初始化 删除商品
     */
    private void requestReduceGoods(String shopCommodityId) {
        JSONObject json = new JSONObject();
        try {
            json.put("quantity", "1");
            json.put("shopCommodityId", shopCommodityId);
            json.put("shopType", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(getActivity());
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonReduceGoodsResult(json.toString(), getActivity());
    }

    private void initData() {
        requestCategoryList();
    }

    private void showTotalPrice() {
        pageno = 1;
        requesGoodsListByCategory(shopCommodityId);
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        goodsListByCategoryAdapter.notifyDataSetChanged();
        requestShopCartCount();
    }

    @OnClick({R.id.go_accounts, R.id.shopping_seach, R.id.scan_code_shopping_rl, R.id.shopping_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_accounts:
                SkipIntentUtil.goAccounts(totalQuantity, getActivity());
                break;
            case R.id.shopping_seach:
                SkipIntentUtil.skipIntent(getActivity(), CommoditySearchPageActivity.class);
                break;
            case R.id.scan_code_shopping_rl:
                SkipIntentUtil.skipIntent(getActivity(), SelectSiteActivity.class);
                break;
            case R.id.shopping_cart:
                if (totalQuantity > 0) {
                    showCart();
                }
                break;
        }
    }

    private void showCart() {
        ShopCartListDialog dialog = new ShopCartListDialog(getActivity(), R.style.cartdialog);
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
    public void onCategoryListFinish(Object o) {
        CategoryListBean categoryListBean = (CategoryListBean) o;
        if (categoryListBean != null) {
            final List<CategoryListBean.ListBean> list = categoryListBean.getList();
            leftMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
            initLeftIndex(list);
            leftIndex[0] = 0;
            categoryListBeanCommonAdapter = new CommonAdapter<CategoryListBean.ListBean>(getActivity(), R.layout.shopping_left_menu_item, list) {
                @Override
                protected void convert(final ViewHolder holder, final CategoryListBean.ListBean categoryListBean, final int position) {
                    holder.setText(R.id.left_menu_rb, categoryListBean.getName());
                    if (leftIndex[position] == 0) {
                        holder.setChecked(R.id.left_menu_rb, true);
                        shopCommodityId = categoryListBean.getId();
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requesGoodsListByCategory(shopCommodityId);
                    } else {
                        holder.setChecked(R.id.left_menu_rb, false);
                    }
                    holder.setOnClickListener(R.id.left_menu_rb, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            initLeftIndex(list);
                            leftIndex[position] = 0;
                            notifyDataSetChanged();
                        }
                    });
                }
            };
            leftMenu.setAdapter(categoryListBeanCommonAdapter);
        }
    }

    /**
     * 初始化索引
     */
    private void initLeftIndex(List<CategoryListBean.ListBean> list) {
        if (leftIndex == null) {
            leftIndex = new int[list.size()];
        }
        for (int i = 0; i < list.size(); i++) {
            leftIndex[i] = i + 1;
        }

    }

    @Override
    public void onGoodsListByCategoryListFinish(Object o) {
        GoodsListByCategoryBean goodsListByCategoryBean = (GoodsListByCategoryBean) o;
        if (goodsListByCategoryBean != null) {
            if (goodsListByCategoryBeanList == null) {
                goodsListByCategoryBeanList = new ArrayList<>();
            }
            List<GoodsListByCategoryBean.ListBean> list = goodsListByCategoryBean.getList();
            if (goodsListByCategoryBean.getPage().getPageNo() == 1) {
                if (goodsListByCategoryBean.getPage().getCount() > 0 && list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    goodsListByCategoryBeanList.clear();
                    goodsListByCategoryBeanList = list;
                    setUpParameters();
                    if (goodsListByCategoryBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, rightMenu, goodsListByCategoryBeanList.size());
                    for (int i = 0; i < list.size(); i++) {
                        goodsListByCategoryBeanList.add(list.get(i));
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
                    SkipIntentUtil.toastShow(getActivity(), "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);
                }
            }
        }
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rightMenu.setLayoutManager(linearLayoutManager);
        goodsListByCategoryAdapter = new CommonAdapter<GoodsListByCategoryBean.ListBean>(getActivity(), R.layout.shopping_right_dish_item, goodsListByCategoryBeanList) {
            @Override
            protected void convert(ViewHolder holder, final GoodsListByCategoryBean.ListBean listBean, final int position) {
                ImageView shoppingImg = holder.getView(R.id.shopping_img);
                TextView shoppingOriginalPrice = holder.getView(R.id.shopping_original_price);
                RequestOperationUtil.setGlide(getActivity(), Constants.UP_LOAD_IMAGE_TOP + listBean.getNarrowViewUrl(), shoppingImg);
                holder.setText(R.id.right_dish_name, listBean.getName() + listBean.getSku());
                int orderQuantity = listBean.getOrderQuantity();
                if (orderQuantity > 0) {
                    holder.setVisible(R.id.right_dish_remove, true);
                    holder.setVisible(R.id.right_dish_account, true);
                    holder.setText(R.id.right_dish_account, orderQuantity + "");
                } else {
                    holder.setVisible(R.id.right_dish_remove, false);
                    holder.setVisible(R.id.right_dish_account, false);
                }

                holder.setText(R.id.shopping_special_offer, "¥" + SkipIntentUtil.conversionAmountFormat(listBean.getPresentCost() + ""));
                shoppingOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setText(R.id.shopping_original_price, "¥ " + SkipIntentUtil.conversionAmountFormat(listBean.getOriginalCost() + ""));

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
                holder.setBackgroundRes(R.id.right_dish_item, R.drawable.customer_selector);
            }
        };
        rightMenu.setAdapter(goodsListByCategoryAdapter);
        goodsListByCategoryAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestGetCommodityInfo(goodsListByCategoryBeanList.get(position).getId());
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
                requesGoodsListByCategory(shopCommodityId);
                SkipIntentUtil.rvRefreshTimeout(getActivity(), refreshLayout, 0);
            }
        });
        if (goodsListByCategoryAdapter != null) {
            goodsListByCategoryAdapter.notifyDataSetChanged();
            refreshLayout.finishRefreshing();
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onGetCommodityInfoFinish(Object o) {
        GetCommodityInfo getCommodityInfo = (GetCommodityInfo) o;
        if (getCommodityInfo != null) {
            Intent intent = new Intent(getActivity(), CommodityDetailsPageActivity.class);
            intent.putExtra("GetCommodityInfo", (Serializable) getCommodityInfo.getShopCommodity());
            startActivity(intent);
        }
    }

    @Override
    public void onShopCartCountFinish(Object o) {
        ShopCartBean shopCartBean = (ShopCartBean) o;
        totalQuantity = shopCartBean.getTotalQuantity();
        if (shopCartBean != null && shopCartBean.getTotalQuantity() > 0) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //execute the task
                    shoppingCartView.setImageResource(R.drawable.shopping_cart_sel);
                }
            }, 100);
            shoppingCartView.setImageResource(R.drawable.shopping_cart_sel);

            totalPriceTextView.setText("¥" + SkipIntentUtil.conversionAmountFormat(shopCartBean.getTotlammount() + ""));
            totalPriceTextView.setTextColor(Color.parseColor("#d7123d"));
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText(totalQuantity + "");
            freight.setText("免运费");
        } else {
            shoppingCartView.setImageResource(R.drawable.shopping_cart_nor);
            totalPriceTextView.setText("¥ 0.00");
            totalPriceTextView.setTextColor(Color.parseColor("#999999"));
            totalPriceNumTextView.setVisibility(View.GONE);
            freight.setText("配送费：¥0.00");
        }
    }

    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoodsBean = (AddReduceGoodsBean) o;
        if (addGoodsBean != null) {
            if (addReduceGoodsId > -1) {
                goodsListByCategoryBeanList.get(addReduceGoodsId).setOrderQuantity(addGoodsBean.getShopCommodityConsumer().getQuantity());
            }
            goodsListByCategoryAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;
        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoodsBean = (AddReduceGoodsBean) o;
        if (reduceGoodsBean != null) {
            int quantity = reduceGoodsBean.getShopCommodityConsumer().getQuantity();
            if (addReduceGoodsId > -1) {
                goodsListByCategoryBeanList.get(addReduceGoodsId).setOrderQuantity(quantity);
            }
            goodsListByCategoryAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;
        }
    }

    /**
     * 刷新列表
     */
    private void refreshList() {
        pageno = 1;
        requesGoodsListByCategory(shopCommodityId);
        SkipIntentUtil.rvRefreshTimeout(getActivity(), refreshLayout, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(goodsListByCategoryAdapter!=null){
            requesGoodsListByCategory(shopCommodityId);
            goodsListByCategoryAdapter.notifyDataSetChanged();
            requestShopCartCount();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryListPresenter.dettach();
        goodsListByCategoryPresenter.dettach();
        getCommodityInfoPresenter.dettach();
        shopCartCountPresenter.dettach();
        addReduceGoodsPresenter.dettach();
    }
}
