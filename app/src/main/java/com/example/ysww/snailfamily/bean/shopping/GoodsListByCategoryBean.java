package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

import java.util.List;

/**
 * Created by ysww on 2017/10/16.
 * 购物分类列表
 */

public class GoodsListByCategoryBean extends BaseBean{
    /**
     * page : {"pageNo":1,"pageSize":10,"count":1,"firstResult":0,"maxResults":10}
     * list : [{"id":"236c289526b94d05a169e09e3608f7dd","name":"柠檬","categoryId":"2","barcode":"15","sku":"200-300g/个","originalCost":700,"presentCost":800,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"591","inventoryCap":"800","inventoryLimit":"50","isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"}]
     */
    private PageBean page;
    private List<ListBean> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 1
         * firstResult : 0
         * maxResults : 10
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private int firstResult;
        private int maxResults;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }
    }

    public static class ListBean {
        /**
         * id : 236c289526b94d05a169e09e3608f7dd
         * name : 柠檬
         * categoryId : 2
         * barcode : 15
         * sku : 200-300g/个
         * originalCost : 700
         * presentCost : 800
         * narrowViewUrl : imag%5Ccommodity%5C1506663534822.jpg
         * showViewUrl : imag%5Ccommodity%5C1506663536826.png
         * detailsViewUrl : imag%5Ccommodity%5C1506663541061.png
         * brand : 云山
         * origin : 云山
         * supplierId : 81e6615323934d1f9ab3083c4f68a10c
         * inventoryQty : 591
         * inventoryCap : 800
         * inventoryLimit : 50
         * isBargainprice : 1
         * createBy : 1
         * createDate : 1506662191000
         * updateBy : 1
         * updateDate : 1507803554000
         * remarks :
         * delFlag : 0
         */

        private String id;
        private String name;
        private String categoryId;
        private String barcode;
        private String sku;
        private int originalCost;
        private int presentCost;
        private String narrowViewUrl;
        private String showViewUrl;
        private String detailsViewUrl;
        private String brand;
        private String origin;
        private String supplierId;
        private String inventoryQty;
        private String inventoryCap;
        private String inventoryLimit;
        private String isBargainprice;
        private String createBy;
        private long createDate;
        private String updateBy;
        private long updateDate;
        private String remarks;
        private String delFlag;
        private int orderQuantity;

        public int getOrderQuantity() {
            return orderQuantity;
        }

        public void setOrderQuantity(int orderQuantity) {
            this.orderQuantity = orderQuantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public int getOriginalCost() {
            return originalCost;
        }

        public void setOriginalCost(int originalCost) {
            this.originalCost = originalCost;
        }

        public int getPresentCost() {
            return presentCost;
        }

        public void setPresentCost(int presentCost) {
            this.presentCost = presentCost;
        }

        public String getNarrowViewUrl() {
            return narrowViewUrl;
        }

        public void setNarrowViewUrl(String narrowViewUrl) {
            this.narrowViewUrl = narrowViewUrl;
        }

        public String getShowViewUrl() {
            return showViewUrl;
        }

        public void setShowViewUrl(String showViewUrl) {
            this.showViewUrl = showViewUrl;
        }

        public String getDetailsViewUrl() {
            return detailsViewUrl;
        }

        public void setDetailsViewUrl(String detailsViewUrl) {
            this.detailsViewUrl = detailsViewUrl;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getInventoryQty() {
            return inventoryQty;
        }

        public void setInventoryQty(String inventoryQty) {
            this.inventoryQty = inventoryQty;
        }

        public String getInventoryCap() {
            return inventoryCap;
        }

        public void setInventoryCap(String inventoryCap) {
            this.inventoryCap = inventoryCap;
        }

        public String getInventoryLimit() {
            return inventoryLimit;
        }

        public void setInventoryLimit(String inventoryLimit) {
            this.inventoryLimit = inventoryLimit;
        }

        public String getIsBargainprice() {
            return isBargainprice;
        }

        public void setIsBargainprice(String isBargainprice) {
            this.isBargainprice = isBargainprice;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }
}
