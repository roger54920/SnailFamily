package com.example.ysww.snailfamily.adapter.shopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.ShopListBean;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ysww on 2017/10/16.
 * 购物车清单
 */

public class ShopListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ShopListBean.ListBean> mDatas;
    private ShopListItemOnClick shopListItemOnClick;
    private ShopListItemViewHolder shopListItemViewHolder;
    public ShopListAdapter(Context context, List<ShopListBean.ListBean> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    public void setShopListItemOnClick(ShopListItemOnClick shopListItemOnClick) {
        this.shopListItemOnClick = shopListItemOnClick;
    }

    public interface ShopListItemOnClick {
        void onAddClick(View view, String shopCommodityId,int position);

        void onReduceClick(View view, String shopCommodityId,int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shopping_cart_popupview_item, null, false);
        return new ShopListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        shopListItemViewHolder = (ShopListItemViewHolder) holder;
        final ShopListBean.ListBean.ShopCommodityBean shopCommodity = mDatas.get(position).getShopCommodity();
        shopListItemViewHolder.shoppingSpecialOffer.setText("¥ " + SkipIntentUtil.conversionAmountFormat(shopCommodity.getPresentCost() + ""));
        shopListItemViewHolder.rightDishName.setText(shopCommodity.getName());
        shopListItemViewHolder.rightDishAccount.setText(mDatas.get(position).getQuantity()+"");
        if (shopListItemOnClick != null) {
            shopListItemViewHolder.rightDishAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shopListItemOnClick.onAddClick(view, shopCommodity.getId(),position);
                }
            });
            shopListItemViewHolder.rightDishRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shopListItemOnClick.onReduceClick(view, shopCommodity.getId(),position);
                }
            });
        }
        shopListItemViewHolder.rightDishItem.setBackgroundResource(R.drawable.customer_selector);
    }
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ShopListItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.right_dish_name)
        TextView rightDishName;
        @InjectView(R.id.shopping_special_offer)
        TextView shoppingSpecialOffer;
        @InjectView(R.id.right_dish_remove)
        ImageView rightDishRemove;
        @InjectView(R.id.right_dish_account)
        TextView rightDishAccount;
        @InjectView(R.id.right_dish_add)
        ImageView rightDishAdd;
        @InjectView(R.id.right_dish_item)
        LinearLayout rightDishItem;
        public ShopListItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
