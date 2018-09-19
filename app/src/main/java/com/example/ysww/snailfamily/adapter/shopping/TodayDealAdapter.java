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
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ysww on 2017/10/16.
 * 今日特价
 */

public class TodayDealAdapter extends RecyclerView.Adapter {

    private Activity mContext;
    private List<GoodsListByCategoryBean.ListBean> mDatas;
    private TodayDealItemOnClick todayDealItemOnClick;
    public TodayDealAdapter(Activity context, List<GoodsListByCategoryBean.ListBean> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    public void setTodayDealItemOnClick(TodayDealItemOnClick todayDealItemOnClick) {
        this.todayDealItemOnClick = todayDealItemOnClick;
    }

    public interface TodayDealItemOnClick{
        void onItemClick(View view,String  shopCommodityId);
        void onAddClick(View view,String  shopCommodityId);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shopping_deal_item, null, false);
        return new TodayDealItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TodayDealItemViewHolder todayDealItemViewHolder = (TodayDealItemViewHolder) holder;
        final GoodsListByCategoryBean.ListBean listBean = mDatas.get(position);
        RequestOperationUtil.setGlide(mContext, Constants.UP_LOAD_IMAGE_TOP + listBean.getNarrowViewUrl(), todayDealItemViewHolder.shoppingImg);
        todayDealItemViewHolder.shoppingName.setText(listBean.getName() + listBean.getSku());

        todayDealItemViewHolder.shoppingSpecialOffer.setText("¥ " + SkipIntentUtil.conversionAmountFormat(listBean.getPresentCost() + ""));
        todayDealItemViewHolder.shoppingOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        todayDealItemViewHolder.shoppingOriginalPrice.setText("¥ " + SkipIntentUtil.conversionAmountFormat(listBean.getOriginalCost() + ""));
        if(todayDealItemOnClick!=null){
            todayDealItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todayDealItemOnClick.onItemClick(view,listBean.getId());
                }
            });
            todayDealItemViewHolder.shoppingAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todayDealItemOnClick.onAddClick(view,listBean.getId());
                }
            });
        }
        todayDealItemViewHolder.itemView.setBackgroundResource(R.drawable.customer_selector);

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class TodayDealItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.shopping_img)
        ImageView shoppingImg;
        @InjectView(R.id.shopping_name)
        TextView shoppingName;
        @InjectView(R.id.shopping_special_offer)
        TextView shoppingSpecialOffer;
        @InjectView(R.id.shopping_original_price)
        TextView shoppingOriginalPrice;
        @InjectView(R.id.shopping_add)
        ImageView shoppingAdd;
        @InjectView(R.id.ll_content)
        LinearLayout llContent;
        public TodayDealItemViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
