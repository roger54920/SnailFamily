package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

import java.util.List;

/**
 * Created by ysww on 2017/10/23.
 */

public class ShopOrderListBean extends BaseBean {
    /**
     * page : {"pageNo":1,"pageSize":10,"count":2,"firstResult":0,"maxResults":10}
     * list : [{"id":"20171025165648","consumerId":"f16bbfee69564bc8a26249a1718d8039","workstationId":"测试站点","noneedsend":"0","quantity":3,"expenditure":12600,"shopType":"2","ifcancel":"0","ifinworkstation":"1","processInstanceId":"82a20cd467ce4758a46fcc91757a1cec","businessStatus":"未支付","processInstanceStatus":"10","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508921809000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508921809000,"delFlag":"0","limitTime":0,"shopCommodityConsumerList":[{"id":"5342f2cf3ee746c79da6b1562276a671","commodityId":"e7753dd302644b86a89abecf117355d8","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":3,"isorder":"1","shopOrderId":"20171025165648","shopType":"2","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508921805000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508921809000,"shopCommodity":{"id":"e7753dd302644b86a89abecf117355d8","name":"零食商品22","categoryId":"6","barcode":"aefa6eb151dd4ecfaedbc61759f7da4b","sku":"200-300g/个","originalCost":8000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"194","inventoryCap":"5000","inventoryLimit":"100","sales":9000,"isBargainprice":"0","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":12600,"delFlag":"0"}]},{"id":"20171025170251","consumerId":"f16bbfee69564bc8a26249a1718d8039","stationmasterId":"6628d6b76542419e85f33453591c15db","workstationId":"站点111","noneedsend":"0","quantity":3,"expenditure":12600,"shopType":"2","ifcancel":"0","ifinworkstation":"1","processInstanceId":"53ff84082c2d472790d136dfad0e4b50","businessStatus":"已支付","processInstanceStatus":"20","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508922172000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508922172000,"delFlag":"0","limitTime":0,"shopCommodityConsumerList":[{"id":"3366fd31bdfd4b25a54ecb829ff84716","commodityId":"e7753dd302644b86a89abecf117355d8","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":3,"isorder":"1","shopOrderId":"20171025170251","shopType":"2","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508922164000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508922172000,"shopCommodity":{"id":"e7753dd302644b86a89abecf117355d8","name":"零食商品22","categoryId":"6","barcode":"aefa6eb151dd4ecfaedbc61759f7da4b","sku":"200-300g/个","originalCost":8000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"194","inventoryCap":"5000","inventoryLimit":"100","sales":9000,"isBargainprice":"0","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":12600,"delFlag":"0"}]}]
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
         * count : 2
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
         * id : 20171025165648
         * consumerId : f16bbfee69564bc8a26249a1718d8039
         * workstationId : 测试站点
         * noneedsend : 0
         * quantity : 3
         * expenditure : 12600
         * shopType : 2
         * ifcancel : 0
         * ifinworkstation : 1
         * processInstanceId : 82a20cd467ce4758a46fcc91757a1cec
         * businessStatus : 未支付
         * processInstanceStatus : 10
         * createBy : f16bbfee69564bc8a26249a1718d8039
         * createDate : 1508921809000
         * updateBy : f16bbfee69564bc8a26249a1718d8039
         * updateDate : 1508921809000
         * delFlag : 0
         * limitTime : 0
         * shopCommodityConsumerList : [{"id":"5342f2cf3ee746c79da6b1562276a671","commodityId":"e7753dd302644b86a89abecf117355d8","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":3,"isorder":"1","shopOrderId":"20171025165648","shopType":"2","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508921805000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508921809000,"shopCommodity":{"id":"e7753dd302644b86a89abecf117355d8","name":"零食商品22","categoryId":"6","barcode":"aefa6eb151dd4ecfaedbc61759f7da4b","sku":"200-300g/个","originalCost":8000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"194","inventoryCap":"5000","inventoryLimit":"100","sales":9000,"isBargainprice":"0","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":12600,"delFlag":"0"}]
         * stationmasterId : 6628d6b76542419e85f33453591c15db
         */

        private String id;
        private String consumerId;
        private String workstationId;
        private String noneedsend;
        private int quantity;
        private int expenditure;
        private String shopType;
        private String ifcancel;
        private String ifinworkstation;
        private String processInstanceId;
        private String businessStatus;
        private String processInstanceStatus;
        private String createBy;
        private long createDate;
        private String updateBy;
        private long updateDate;
        private String delFlag;
        private int limitTime;
        private String stationmasterId;
        private List<ShopCommodityConsumerListBean> shopCommodityConsumerList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(String consumerId) {
            this.consumerId = consumerId;
        }

        public String getWorkstationId() {
            return workstationId;
        }

        public void setWorkstationId(String workstationId) {
            this.workstationId = workstationId;
        }

        public String getNoneedsend() {
            return noneedsend;
        }

        public void setNoneedsend(String noneedsend) {
            this.noneedsend = noneedsend;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getExpenditure() {
            return expenditure;
        }

        public void setExpenditure(int expenditure) {
            this.expenditure = expenditure;
        }

        public String getShopType() {
            return shopType;
        }

        public void setShopType(String shopType) {
            this.shopType = shopType;
        }

        public String getIfcancel() {
            return ifcancel;
        }

        public void setIfcancel(String ifcancel) {
            this.ifcancel = ifcancel;
        }

        public String getIfinworkstation() {
            return ifinworkstation;
        }

        public void setIfinworkstation(String ifinworkstation) {
            this.ifinworkstation = ifinworkstation;
        }

        public String getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getProcessInstanceStatus() {
            return processInstanceStatus;
        }

        public void setProcessInstanceStatus(String processInstanceStatus) {
            this.processInstanceStatus = processInstanceStatus;
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

        public int getLimitTime() {
            return limitTime;
        }

        public void setLimitTime(int limitTime) {
            this.limitTime = limitTime;
        }

        public String getStationmasterId() {
            return stationmasterId;
        }

        public void setStationmasterId(String stationmasterId) {
            this.stationmasterId = stationmasterId;
        }

        public List<ShopCommodityConsumerListBean> getShopCommodityConsumerList() {
            return shopCommodityConsumerList;
        }

        public void setShopCommodityConsumerList(List<ShopCommodityConsumerListBean> shopCommodityConsumerList) {
            this.shopCommodityConsumerList = shopCommodityConsumerList;
        }

        public static class ShopCommodityConsumerListBean {
            /**
             * id : 5342f2cf3ee746c79da6b1562276a671
             * commodityId : e7753dd302644b86a89abecf117355d8
             * consumerId : f16bbfee69564bc8a26249a1718d8039
             * quantity : 3
             * isorder : 1
             * shopOrderId : 20171025165648
             * shopType : 2
             * createBy : f16bbfee69564bc8a26249a1718d8039
             * createDate : 1508921805000
             * updateBy : f16bbfee69564bc8a26249a1718d8039
             * updateDate : 1508921809000
             * shopCommodity : {"id":"e7753dd302644b86a89abecf117355d8","name":"零食商品22","categoryId":"6","barcode":"aefa6eb151dd4ecfaedbc61759f7da4b","sku":"200-300g/个","originalCost":8000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"194","inventoryCap":"5000","inventoryLimit":"100","sales":9000,"isBargainprice":"0","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"}
             * amount : 12600
             * delFlag : 0
             */

            private String id;
            private String commodityId;
            private String consumerId;
            private int quantity;
            private String isorder;
            private String shopOrderId;
            private String shopType;
            private String createBy;
            private long createDate;
            private String updateBy;
            private long updateDate;
            private ShopCommodityBean shopCommodity;
            private int amount;
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

            public String getShopOrderId() {
                return shopOrderId;
            }

            public void setShopOrderId(String shopOrderId) {
                this.shopOrderId = shopOrderId;
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

            public ShopCommodityBean getShopCommodity() {
                return shopCommodity;
            }

            public void setShopCommodity(ShopCommodityBean shopCommodity) {
                this.shopCommodity = shopCommodity;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public static class ShopCommodityBean {
                /**
                 * id : e7753dd302644b86a89abecf117355d8
                 * name : 零食商品22
                 * categoryId : 6
                 * barcode : aefa6eb151dd4ecfaedbc61759f7da4b
                 * sku : 200-300g/个
                 * originalCost : 8000
                 * presentCost : 4200
                 * narrowViewUrl : imag%5Ccommodity%5C1506663534822.jpg
                 * showViewUrl : imag%5Ccommodity%5C1506663536826.png
                 * detailsViewUrl : imag%5Ccommodity%5C1506663541061.png
                 * brand : 云山
                 * origin : 云山
                 * supplierId : 81e6615323934d1f9ab3083c4f68a10c
                 * inventoryQty : 194
                 * inventoryCap : 5000
                 * inventoryLimit : 100
                 * sales : 9000
                 * isBargainprice : 0
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
                private int sales;
                private String isBargainprice;
                private String createBy;
                private long createDate;
                private String updateBy;
                private long updateDate;
                private String remarks;
                private String delFlag;

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

                public int getSales() {
                    return sales;
                }

                public void setSales(int sales) {
                    this.sales = sales;
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
    }
}
