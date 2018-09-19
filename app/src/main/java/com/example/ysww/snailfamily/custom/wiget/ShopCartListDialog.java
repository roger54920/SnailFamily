package com.example.ysww.snailfamily.custom.wiget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.shopping.ShopListAdapter;
import com.example.ysww.snailfamily.bean.BaseBean;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.bean.shopping.ShopListBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.SystemPromptDialog;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.CleanGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.mvp.shopping.ShopListView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.CleanGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopCartCountPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopListPresenter;
import com.example.ysww.snailfamily.ui.BottomNavigationMenuActivity;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by cheng on 16-12-22.
 */
public class ShopCartListDialog extends Dialog implements CleanGoodsView, AddReduceGoodsView, ShopListView, ShopCartCountView {
    @InjectView(R.id.shopping_cart_popupview_layout)
    RelativeLayout shoppingCartPopupviewLayout;
    @InjectView(R.id.shop_list_rv)
    RecyclerView shopListRv;
    @InjectView(R.id.linearlayout)
    LinearLayout linearlayout;
    @InjectView(R.id.shopping_cart_total_tv)
    TextView totalPriceTextView;
    @InjectView(R.id.shopping_cart)
    ImageView shoppingCartView;
    @InjectView(R.id.shopping_cart_total_num)
    TextView totalPriceNumTextView;
    @InjectView(R.id.freight)
    TextView freight;
    private ShopCartListDialogImp shopCartListDialogImp;
    private ShopListAdapter shopListAdapter;
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private ShopListPresenter shopListPresenter = new ShopListPresenter();//购物车清单
    private ShopCartCountPresenter shopCartCountPresenter = new ShopCartCountPresenter();//购物车清单统计
    private CleanGoodsPresenter cleanGoodsPresenter = new CleanGoodsPresenter();//购物车清空
    private List<ShopListBean.ListBean> shopList;
    private Activity activity;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private int totalQuantity;//购物总数
    private int addReduceGoodsId = -1;//item id
    public ShopCartListDialog(Activity context) {
        super(context);
        this.activity = context;
    }

    public ShopCartListDialog(Activity context, int themeResId) {
        super(context, themeResId);
        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_popupview);
        ButterKnife.inject(this);
        requestShopList();
        requestShopCartCount();
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(activity);
    }

    private void initDialogAdapter() {
        shopListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        shopListAdapter = new ShopListAdapter(getContext(), shopList);
        shopListRv.setAdapter(shopListAdapter);
        shopListAdapter.setShopListItemOnClick(new ShopListAdapter.ShopListItemOnClick() {
            @Override
            public void onAddClick(View view, String shopCommodityId,int position) {
                addReduceGoodsId = position;
                requestAddGoods(shopCommodityId);
            }

            @Override
            public void onReduceClick(View view, String shopCommodityId,int position) {
                addReduceGoodsId = position;
                requestReduceGoods(shopCommodityId);
            }
        });
    }

    /**
     * 初始化 购物车清单
     */
    private void requestShopList() {
        new OkHttpResolve(activity);
        shopListPresenter.attach(this);
        shopListPresenter.postJsonShopListResult("{\"shopType\":\"1\"}", activity,null);
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
        new OkHttpResolve(activity);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonAddGoodsResult(json.toString(), activity);
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
        new OkHttpResolve(activity);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonReduceGoodsResult(json.toString(), activity);
    }

    /**
     * 初始化 购物车清单统计
     */
    private void requestShopCartCount() {
        new OkHttpResolve(activity);
        shopCartCountPresenter.attach(this);
        shopCartCountPresenter.postJsonShopCartCountResult("{\"shopType\":\"1\"}", activity);
    }

    /**
     * 初始化 购物车清空
     */
    private void requestCleanGoodsCount() {
        new OkHttpResolve(activity);
        cleanGoodsPresenter.attach(this);
        cleanGoodsPresenter.postJsonCleanGoodsResult("{\"shopType\":\"1\"}", activity, lazyLoadProgressDialog);
    }

    @Override
    public void show() {
        super.show();
        animationShow(500);
    }

    @Override
    public void dismiss() {
        animationHide(500);
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearlayout, "translationY", 500, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearlayout, "translationY", 0, 500).setDuration(mDuration)
        );
        animatorSet.start();

        if (shopCartListDialogImp != null) {
            shopCartListDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartListDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void setShopCartDialogImp(ShopCartListDialogImp shopCartListDialogImp) {
        this.shopCartListDialogImp = shopCartListDialogImp;
    }

    @OnClick({R.id.llMe, R.id.llHome, R.id.go_accounts, R.id.shopping_cart_bottom, R.id.clear_layout, R.id.shopping_cart_popupview_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llMe:
                skipFragment("fragment_me");
                break;
            case R.id.llHome:
                skipFragment("home");
                break;
            case R.id.go_accounts:
                SkipIntentUtil.goAccounts(totalQuantity, activity);
                break;
            case R.id.shopping_cart_bottom:
                this.dismiss();
                break;
            case R.id.clear_layout:
                SystemPromptDialog.Builder builder = new SystemPromptDialog.Builder(activity, "确定清空购物车吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        requestCleanGoodsCount();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().setCanceledOnTouchOutside(true);  //用户选择取消或者是点击屏幕空白部分时让dialog消失。
                builder.create().show();
                break;
            case R.id.shopping_cart_popupview_layout:
                this.dismiss();
                break;
        }
    }

    /**
     * 首页跳转页面
     */
    private void skipFragment(String source) {
        Intent intent = new Intent(activity, BottomNavigationMenuActivity.class);
        intent.putExtra("source", source);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoods = (AddReduceGoodsBean) o;
        if (addGoods != null) {
            if (addReduceGoodsId > -1) {
                shopList.get(addReduceGoodsId).setQuantity(addGoods.getShopCommodityConsumer().getQuantity());
            }
            shopListAdapter.notifyDataSetChanged();
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
                }
            }
            shopListAdapter.notifyDataSetChanged();
            requestShopCartCount();
            addReduceGoodsId = -1;
        }
    }

    @Override
    public void onShopListFinish(Object o) {
        ShopListBean shopListBean = (ShopListBean) o;
        if (shopListBean != null) {
            if (shopListBean.getList() != null && shopListBean.getList().size() > 0) {
                shopList = shopListBean.getList();
                initDialogAdapter();
                shopListAdapter.notifyDataSetChanged();
            } else {
                this.dismiss();
            }

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
            totalPriceNumTextView.setText(shopCartBean.getTotalQuantity() + "");
            freight.setText("免运费");
        }else{
            this.dismiss();
        }
    }

    @Override
    public void onCleanGoodsFinish(Object o) {
        BaseBean cleanGoodsBean = (BaseBean) o;
        if (cleanGoodsBean != null) {
            this.dismiss();
        }
    }

    public interface ShopCartListDialogImp {
        public void dialogDismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        addReduceGoodsPresenter.dettach();
        shopListPresenter.dettach();
        shopCartCountPresenter.dettach();
        cleanGoodsPresenter.dettach();
    }
}
