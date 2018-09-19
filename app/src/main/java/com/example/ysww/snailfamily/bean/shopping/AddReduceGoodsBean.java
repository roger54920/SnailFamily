package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

/**
 * Created by ysww on 2017/10/16.
 * 添加删除商品
 */

public class AddReduceGoodsBean extends BaseBean{

    /**
     * shopCommodityConsumer : {"id":"7e83afbd81184cd3b66d4a69856124bd","commodityId":"236c289526b94d05a169e09e3608f7dd","amount":0,"consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":10,"isorder":"0","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1507799264000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508147754621,"delFlag":"0"}
     */

    private ShopCommodityConsumerBean shopCommodityConsumer;

    public ShopCommodityConsumerBean getShopCommodityConsumer() {
        return shopCommodityConsumer;
    }

    public void setShopCommodityConsumer(ShopCommodityConsumerBean shopCommodityConsumer) {
        this.shopCommodityConsumer = shopCommodityConsumer;
    }

    public static class ShopCommodityConsumerBean {
        /**
         * id : 7e83afbd81184cd3b66d4a69856124bd
         * commodityId : 236c289526b94d05a169e09e3608f7dd
         * amount : 0
         * consumerId : f16bbfee69564bc8a26249a1718d8039
         * quantity : 10
         * isorder : 0
         * shopType : 1
         * createBy : f16bbfee69564bc8a26249a1718d8039
         * createDate : 1507799264000
         * updateBy : f16bbfee69564bc8a26249a1718d8039
         * updateDate : 1508147754621
         * delFlag : 0
         */

        private String id;
        private String commodityId;
        private int amount;
        private String consumerId;
        private int quantity;
        private String isorder;
        private String shopType;
        private String createBy;
        private long createDate;
        private String updateBy;
        private long updateDate;
        private String delFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(String consumerId) {
            this.consumerId = consumerId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getIsorder() {
            return isorder;
        }

        public void setIsorder(String isorder) {
            this.isorder = isorder;
        }

        public String getShopType() {
            return shopType;
        }

        public void setShopType(String shopType) {
            this.shopType = shopType;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }
}
