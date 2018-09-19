package com.example.ysww.snailfamily.ui.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.GetCommodityInfo;
import com.example.ysww.snailfamily.bean.shopping.ShopCartBean;
import com.example.ysww.snailfamily.custom.wiget.ShopCartSearchDialog;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.ShoppingScanQRCodeDialog;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.GetCommodityInfoView;
import com.example.ysww.snailfamily.mvp.shopping.ShopCartCountView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GetCommodityInfoPresenter;
import com.example.ysww.snailfamily.presenter.shopping.ShopCartCountPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.NetUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 商品扫描二维码
 */
public class ShoppingScanQRCodeActivity extends BaseActivity implements ShopCartSearchDialog.ShopCartSearchDialogImp, ShopCartCountView, AddReduceGoodsView, GetCommodityInfoView, QRCodeView.Delegate {

    @InjectView(R.id.preview_view)
    SurfaceView previewView;
    @InjectView(R.id.zxingview)
    ZXingView mQRCodeView;
    @InjectView(R.id.shopping_cart_layout)
    FrameLayout shoppingCartLayout;
    @InjectView(R.id.shopping_cart_total_tv)
    TextView totalPriceTextView;
    @InjectView(R.id.shopping_cart)
    ImageView shoppingCartView;
    @InjectView(R.id.shopping_cart_total_num)
    TextView totalPriceNumTextView;
    @InjectView(R.id.freight)
    TextView freight;
    private static final String TAG = "ShoppingScanQRCodeActiv";
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private Intent intent;
    private GetCommodityInfoPresenter getCommodityInfoPresenter = new GetCommodityInfoPresenter();//商品详情接口
    private ShopCartCountPresenter shopCartCountPresenter = new ShopCartCountPresenter();//购物车清单统计
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private String shopCommodityId;//商品ID
    private int totalQuantity;//购物总数
    private ShoppingScanQRCodeDialog.Builder builder;
    private TextView accounTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_scan_qrcode);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        requestShopCartCount();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        mQRCodeView.startSpot();
        scanQRCodeSuccess(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    /**
     * 初始化 查询商品详情
     */
    private void requestGetCommodityInfo(String barcode) {
        new OkHttpResolve(this);
        getCommodityInfoPresenter.attach(this);
        getCommodityInfoPresenter.postJsonGetCommodityInfoResult("{\"barcode\":\"" + barcode + "\"}", this, lazyLoadProgressDialog);
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
     * 初始化 添加商品
     */
    private void requestAddGoods() {
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
    private void requestReduceGoods() {
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
     * 扫码成功
     *
     * @param result
     */
    private void scanQRCodeSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
            SkipIntentUtil.toastShow(this, "未发现二维码！");
        } else {
            //启动时判断网络状态
            Constants.NETCONNECT = this.isNetConnect();
            if (Constants.NETCONNECT == false) {
                SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
                Constants.NETCONNECT = false;
            } else {
                Constants.NETCONNECT = true;
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestGetCommodityInfo(result);
            }

        }
    }

    /**
     * 站点选择弹窗
     *
     * @param commodityName
     * @param commodityMoney
     */
    private void selectSiteDialog(String commodityName, String commodityMoney, String account) {
        builder = new ShoppingScanQRCodeDialog.Builder(ShoppingScanQRCodeActivity.this, commodityName, commodityMoney, account);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mQRCodeView.startSpot();
                //跳转
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mQRCodeView.startSpot();
                    }
                });
        builder.setAddReduceGoodsImpl(new ShoppingScanQRCodeDialog.Builder.AddReduceGoodsImpl() {
            @Override
            public void onAddGoodsClick(ShoppingScanQRCodeDialog dialog, TextView right_dish_account) {
                accounTv = right_dish_account;
                requestAddGoods();
            }

            @Override
            public void onReduceGoodsClick(ShoppingScanQRCodeDialog dialog, TextView right_dish_account) {
                accounTv = right_dish_account;
                requestReduceGoods();
            }
        });
        builder.create().setCanceledOnTouchOutside(true);  //用户选择取消或者是点击屏幕空白部分时让dialog消失。
        builder.create().show();
    }

    @OnClick({R.id.shopping_cart_layout, R.id.return_arrows, R.id.manual_input, R.id.go_accounts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.manual_input:
                //跳转到输入页面
                intent = new Intent(this, InputNumberFindActivity.class);
                startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                break;
            case R.id.go_accounts:
                if (totalQuantity > 0) {
                    Intent intent = new Intent(this, ConfirmOrderActivity.class);
                    intent.putExtra("workstationId", getIntent().getStringExtra("workstationId"));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "您未添加商品！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shopping_cart_layout:
                if (totalQuantity > 0) {
                    showCart();
                }
                break;
        }
    }

    private void showCart() {
        ShopCartSearchDialog dialog = new ShopCartSearchDialog(this, R.style.cartdialog, "2");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQRCodeView.startSpot();
        if (requestCode == Constants.SUBSCRIPT_ZER0 && resultCode == Constants.SUBSCRIPT_ZER0) {
            String name = data.getStringExtra("name");
            String presentCost = data.getStringExtra("presentCost");
            String orderQuantity = data.getStringExtra("orderQuantity");
            shopCommodityId = data.getStringExtra("shopCommodityId");
            selectSiteDialog(name, "¥" + SkipIntentUtil.conversionAmountFormat(presentCost), orderQuantity);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
        }
    }

    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    public void onGetCommodityInfoFinish(Object o) {
        GetCommodityInfo getCommodityInfo = (GetCommodityInfo) o;
        if (getCommodityInfo != null) {
            GetCommodityInfo.ShopCommodityBean shopCommodity = getCommodityInfo.getShopCommodity();
            shopCommodityId = shopCommodity.getId();
            selectSiteDialog(shopCommodity.getName(), "¥" + SkipIntentUtil.conversionAmountFormat(shopCommodity.getPresentCost() + ""), shopCommodity.getOrderQuantity() + "");
        }
    }

    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoodsBean = (AddReduceGoodsBean) o;
        if (addGoodsBean != null) {
            accounTv.setText(addGoodsBean.getShopCommodityConsumer().getQuantity() + "");
            requestShopCartCount();
        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoodsBean = (AddReduceGoodsBean) o;
        if (reduceGoodsBean != null) {
            int quantity = reduceGoodsBean.getShopCommodityConsumer().getQuantity();
            accounTv.setText(quantity + "");
            requestShopCartCount();
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
    public void dialogDismiss() {
        mQRCodeView.startSpot();
        requestShopCartCount();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        getCommodityInfoPresenter.dettach();
        addReduceGoodsPresenter.dettach();
        shopCartCountPresenter.dettach();
    }
}
