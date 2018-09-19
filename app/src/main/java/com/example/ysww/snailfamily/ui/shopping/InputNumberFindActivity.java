package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.GetCommodityInfo;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.GetCommodityInfoView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.GetCommodityInfoPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 输入编号查询
 */
public class InputNumberFindActivity extends AutoLayoutActivity implements GetCommodityInfoView {

    @InjectView(R.id.input_commodity_number)
    EditText inputCommodityNumber;
    private Intent intent;
    private GetCommodityInfoPresenter getCommodityInfoPresenter = new GetCommodityInfoPresenter();//商品详情接口
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_number_find);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        intent = getIntent();
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
    }
    /**
     * 初始化 查询商品详情
     */
    private void requestGetCommodityInfo(String barcode) {
        new OkHttpResolve(this);
        getCommodityInfoPresenter.attach(this);
        getCommodityInfoPresenter.postJsonGetCommodityInfoResult("{\"barcode\":\"" + barcode + "\"}", this, lazyLoadProgressDialog);
    }
    @OnClick({R.id.return_arrows, R.id.the_library})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                setResult(Constants.SUBSCRIPT_ONE,intent);
                finish();
                break;
            case R.id.the_library:
               String commodityNumber= inputCommodityNumber.getText().toString();
                if(!TextUtils.isEmpty(commodityNumber)){
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestGetCommodityInfo(commodityNumber);
                }else{
                    SkipIntentUtil.toastShow(this,"商品编号不能为空！");
                }
                break;
        }
    }

    @Override
    public void onGetCommodityInfoFinish(Object o) {
        GetCommodityInfo getCommodityInfo = (GetCommodityInfo) o;
        if(getCommodityInfo!=null){
            GetCommodityInfo.ShopCommodityBean shopCommodity = getCommodityInfo.getShopCommodity();
            intent.putExtra("name",shopCommodity.getName());
            intent.putExtra("presentCost",shopCommodity.getPresentCost()+"");
            intent.putExtra("orderQuantity",shopCommodity.getOrderQuantity()+"");
            intent.putExtra("shopCommodityId",shopCommodity.getId());
            setResult(Constants.SUBSCRIPT_ZER0,intent);
            finish();
        }else{
            SkipIntentUtil.toastShow(this,"暂无次商品信息！");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            setResult(Constants.SUBSCRIPT_ONE,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        getCommodityInfoPresenter.dettach();
    }
}
