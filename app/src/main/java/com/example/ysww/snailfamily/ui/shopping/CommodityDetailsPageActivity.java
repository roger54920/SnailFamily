package com.example.ysww.snailfamily.ui.shopping;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.GetCommodityInfo;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.custom.wiget.ShopCartSearchDialog;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopCartCountPresenter;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商品详情页
 */
public class CommodityDetailsPageActivity extends AutoLayoutActivity implements ShopCartCountView, AddReduceGoodsView, ShopCartSearchDialog.ShopCartSearchDialogImp {

    @InjectView(R.id.right_dish_name)
    TextView right_dish_name_tv;
    @InjectView(R.id.shopping_special_offer)
    TextView right_dish_price_tv;
    @InjectView(R.id.shopping_original_price)
    TextView shoppingOriginalPrice;
    @InjectView(R.id.right_dish_remove)
    ImageView right_dish_remove_iv;
    @InjectView(R.id.right_dish_add)
    ImageView right_dish_add_iv;
    @InjectView(R.id.shopping_cart_total_tv)
    TextView totalPriceTextView;
    @InjectView(R.id.shopping_cart)
    ImageView shoppingCartView;
    @InjectView(R.id.shopping_cart_layout)
    FrameLayout shopingCartLayout;
    @InjectView(R.id.shopping_cart_total_num)
    TextView totalPriceNumTextView;
    @InjectView(R.id.right_dish_account)
    TextView right_dish_account_tv;
    @InjectView(R.id.design_seartch_img)
    ImageView designSeartchImg;
    @InjectView(R.id.details_img1)
    ImageView detailsImg1;
    @InjectView(R.id.details_img2)
    ImageView detailsImg2;
    @InjectView(R.id.activity_commodity_details_page)
    RelativeLayout activityCommodityDetailsPage;
    @InjectView(R.id.freight)
    TextView freight;
    private GetCommodityInfo.ShopCommodityBean getCommodityInfo;
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private ShopCartCountPresenter shopCartCountPresenter = new ShopCartCountPresenter();//购物车清单统计
    private int totalQuantity;//购物总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details_page);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        initViews();
    }

    private void initViews() {
        requestShopCartCount();
        shoppingOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        getCommodityInfo = (GetCommodityInfo.ShopCommodityBean) getIntent().getSerializableExtra("GetCommodityInfo");
        if (getCommodityInfo != null) {
            RequestOperationUtil.setGlide(this, Constants.UP_LOAD_IMAGE_TOP + getCommodityInfo.getNarrowViewUrl(), designSeartchImg);
            RequestOperationUtil.setGlide(this, Constants.UP_LOAD_IMAGE_TOP + getCommodityInfo.getShowViewUrl(), detailsImg1);
            RequestOperationUtil.setGlide(this, Constants.UP_LOAD_IMAGE_TOP + getCommodityInfo.getDetailsViewUrl(), detailsImg2);
            right_dish_name_tv.setText(getCommodityInfo.getName() + getCommodityInfo.getSku());
            right_dish_price_tv.setText("¥ " + SkipIntentUtil.conversionAmountFormat(getCommodityInfo.getPresentCost() + ""));
            shoppingOriginalPrice.setText("¥ " + SkipIntentUtil.conversionAmountFormat(getCommodityInfo.getOriginalCost() + ""));
            right_dish_account_tv.setText(getCommodityInfo.getOrderQuantity()+"");
            if(getCommodityInfo.getOrderQuantity()>0){
                right_dish_remove_iv.setVisibility(View.VISIBLE);
                right_dish_account_tv.setVisibility(View.VISIBLE);
            }else{
                right_dish_remove_iv.setVisibility(View.GONE);
                right_dish_account_tv.setVisibility(View.GONE);
            }
        }

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
            json.put("shopType", "1");
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
        shopCartCountPresenter.postJsonShopCartCountResult("{\"shopType\":\"1\"}", this);
    }

    @OnClick({R.id.details_return, R.id.details_share, R.id.go_accounts, R.id.right_dish_add, R.id.right_dish_remove, R.id.shopping_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.details_return:
                finish();
                break;
            case R.id.details_share:
                break;
            case R.id.go_accounts:
                SkipIntentUtil.goAccounts(totalQuantity,this);
                break;
            case R.id.right_dish_add:
                requestAddGoods(getCommodityInfo.getId());
                break;
            case R.id.right_dish_remove:
                requestReduceGoods(getCommodityInfo.getId());
                break;
            case R.id.shopping_cart:
                if (totalQuantity > 0) {
                    showCart();
                }
                break;
        }
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
        requestShopCartCount();
    }
    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoodsBean = (AddReduceGoodsBean) o;
        if (addGoodsBean != null) {
            AddReduceGoodsBean.ShopCommodityConsumerBean shopCommodityConsumer = addGoodsBean.getShopCommodityConsumer();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //execute the task
                    shoppingCartView.setImageResource(R.drawable.shopping_cart_sel);
                }
            }, 300);
            requestShopCartCount();
            right_dish_account_tv.setText(shopCommodityConsumer.getQuantity() + "");
            right_dish_remove_iv.setVisibility(View.VISIBLE);
            right_dish_account_tv.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoodsBean = (AddReduceGoodsBean) o;
        if (reduceGoodsBean != null) {
            AddReduceGoodsBean.ShopCommodityConsumerBean shopCommodityConsumer = reduceGoodsBean.getShopCommodityConsumer();
            if (shopCommodityConsumer.getQuantity() <= 0) {
                right_dish_remove_iv.setVisibility(View.GONE);
                right_dish_account_tv.setVisibility(View.GONE);
            } else {
                requestShopCartCount();
                right_dish_account_tv.setText(shopCommodityConsumer.getQuantity() + "");
                right_dish_remove_iv.setVisibility(View.VISIBLE);
                right_dish_account_tv.setVisibility(View.VISIBLE);
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
            totalPriceNumTextView.setText(totalQuantity + "");
            freight.setText("免运费");
        } else {
            right_dish_remove_iv.setVisibility(View.GONE);
            right_dish_account_tv.setVisibility(View.GONE);
            shoppingCartView.setImageResource(R.drawable.shopping_cart_nor);
            totalPriceTextView.setText("¥ 0.00");
            totalPriceTextView.setTextColor(Color.parseColor("#999999"));
            totalPriceNumTextView.setVisibility(View.GONE);
            freight.setText("配送费：¥0.00");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        addReduceGoodsPresenter.dettach();
        shopCartCountPresenter.dettach();
    }

}
