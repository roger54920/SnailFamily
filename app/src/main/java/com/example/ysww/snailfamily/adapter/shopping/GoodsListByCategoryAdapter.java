package com.example.ysww.snailfamily.adapter.shopping;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ysww on 2017/10/16.
 * 商品分类详情
 */

public class GoodsListByCategoryAdapter extends RecyclerView.Adapter implements AddReduceGoodsView{
    private Activity mContext;
    private List<GoodsListByCategoryBean.ListBean> mDatas;
    private GoodsListByCategoryItemOnClick goodsListByCategoryItemOnClick;
    private GoodsListByCategoryItemViewHolder goodsListByCategoryItemViewHolder;
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    public GoodsListByCategoryAdapter(Activity context, List<GoodsListByCategoryBean.ListBean> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    public void setGoodsListByCategoryItemOnClick(GoodsListByCategoryItemOnClick goodsListByCategoryItemOnClick) {
        this.goodsListByCategoryItemOnClick = goodsListByCategoryItemOnClick;
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
        new OkHttpResolve(mContext);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonAddGoodsResult(json.toString(),mContext);
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
        new OkHttpResolve(mContext);
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonReduceGoodsResult(json.toString(),mContext);
    }
    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoodsBean = (AddReduceGoodsBean) o;
        if(addGoodsBean!=null){
            AddReduceGoodsBean.ShopCommodityConsumerBean shopCommodityConsumer = addGoodsBean.getShopCommodityConsumer();
            goodsListByCategoryItemViewHolder.rightDishAccount.setText(shopCommodityConsumer.getQuantity()+"");
            goodsListByCategoryItemViewHolder.rightDishRemove.setVisibility(View.VISIBLE);
            goodsListByCategoryItemViewHolder.rightDishAccount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {
        AddReduceGoodsBean reduceGoodsBean = (AddReduceGoodsBean) o;
        if(reduceGoodsBean!=null){
            AddReduceGoodsBean.ShopCommodityConsumerBean shopCommodityConsumer = reduceGoodsBean.getShopCommodityConsumer();
            if (shopCommodityConsumer.getQuantity() <= 0) {
                goodsListByCategoryItemViewHolder.rightDishRemove.setVisibility(View.GONE);
                goodsListByCategoryItemViewHolder.rightDishAccount.setVisibility(View.GONE);
            } else {
                goodsListByCategoryItemViewHolder.rightDishAccount.setText(shopCommodityConsumer.getQuantity() + "");
                goodsListByCategoryItemViewHolder.rightDishRemove.setVisibility(View.VISIBLE);
                goodsListByCategoryItemViewHolder.rightDishAccount.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface GoodsListByCategoryItemOnClick {
        void onItemClick(View view, String shopCommodityId);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shopping_right_dish_item, null, false);
        return new GoodsListByCategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        goodsListByCategoryItemViewHolder = (GoodsListByCategoryItemViewHolder) holder;
        final GoodsListByCategoryBean.ListBean listBean = mDatas.get(position);
        RequestOperationUtil.setGlide(mContext, Constants.UP_LOAD_IMAGE_TOP + listBean.getNarrowViewUrl(), goodsListByCategoryItemViewHolder.shoppingImg);
        goodsListByCategoryItemViewHolder.rightDishName.setText(listBean.getName() + listBean.getSku());

        goodsListByCategoryItemViewHolder.rightDishRemove.setVisibility(View.GONE);
        goodsListByCategoryItemViewHolder.rightDishAccount.setVisibility(View.GONE);
        goodsListByCategoryItemViewHolder.shoppingSpecialOffer.setText("¥ " + SkipIntentUtil.conversionAmountFormat(listBean.getPresentCost() + ""));
        goodsListByCategoryItemViewHolder.shoppingOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        goodsListByCategoryItemViewHolder.shoppingOriginalPrice.setText("¥ " + SkipIntentUtil.conversionAmountFormat(listBean.getOriginalCost() + ""));
        goodsListByCategoryItemViewHolder.rightDishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAddGoods(listBean.getId());
            }
        });
        goodsListByCategoryItemViewHolder.rightDishRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReduceGoods(listBean.getId());
            }
        });


        if (goodsListByCategoryItemOnClick != null) {
            goodsListByCategoryItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goodsListByCategoryItemOnClick.onItemClick(view, listBean.getId());

                }
            });
        }
        goodsListByCategoryItemViewHolder.itemView.setBackgroundResource(R.drawable.customer_selector);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class GoodsListByCategoryItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.shopping_img)
        ImageView shoppingImg;
        @InjectView(R.id.right_dish_name)
        TextView rightDishName;
        @InjectView(R.id.shopping_special_offer)
        TextView shoppingSpecialOffer;
        @InjectView(R.id.shopping_original_price)
        TextView shoppingOriginalPrice;
        @InjectView(R.id.right_dish_remove)
        ImageView rightDishRemove;
        @InjectView(R.id.right_dish_account)
        TextView rightDishAccount;
        @InjectView(R.id.right_dish_add)
        ImageView rightDishAdd;
        @InjectView(R.id.right_dish_item)
        LinearLayout rightDishItem;
        public GoodsListByCategoryItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        addReduceGoodsPresenter.dettach();
    }
}
