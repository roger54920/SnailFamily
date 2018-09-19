package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ysww on 2017/10/23.
 * 电商订单生成
 */

public class CreateShopOrderBean extends BaseBean{

    /**
     * shopOrder : {"id":"20171023180233","consumerId":"f16bbfee69564bc8a26249a1718d8039","stationmasterId":"01d63d5eec004e87b1d6d03331425153","noneedsend":"1","receiverId":"6b80e6239da64585b4492fdb27d27261","quantity":7,"expenditure":29400,"shopType":"1","ifcancel":"0","ifinworkstation":"1","processInstanceId":"c2ad1330e5e14e9e8485e3273213ab42","processInstanceStatus":"10","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508752953000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508752953000,"delFlag":"0","act":{"taskId":"a187a2dbdd08430a9f19910a8c048252","taskName":"用户支付","taskDefKey":"userPay","procInsId":"c2ad1330e5e14e9e8485e3273213ab42","procDefId":"shop_order:14:82d210bb7442435a867c518d6c6c50c6","procDefKey":"shop_order","status":"todo","vars":{"map":{"shopOrderTimeDuration":"PT30M","ifConsumerPickUp":false,"stationmasterId":"01d63d5eec004e87b1d6d03331425153","sendImmediately":"true","consumerId":"f16bbfee69564bc8a26249a1718d8039"}},"finishTask":false,"todoTask":true},"limitTime":29,"shopCommodityConsumerList":[{"id":"f141983ae07743c49b8ed08712f3f2af","commodityId":"001e260731994577a59518326d141333","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":5,"isorder":"1","shopOrderId":"20171023180233","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508747862000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508752953000,"shopCommodity":{"id":"001e260731994577a59518326d141333","name":"特价商品商品37","categoryId":"4","barcode":"88a14e8f7cd5491289db6e3f7fd031d9","sku":"200-300g/个","originalCost":1000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"895","inventoryCap":"2000","inventoryLimit":"70","sales":4000,"isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":0,"delFlag":"0"},{"id":"6d16cee6d64e4e638b787721577ec8ff","commodityId":"05b406bae24e4d8888dc6c08eab69d08","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":2,"isorder":"1","shopOrderId":"20171023180233","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508750437000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508752953000,"shopCommodity":{"id":"05b406bae24e4d8888dc6c08eab69d08","name":"蔬菜商品3","categoryId":"2","barcode":"97983f45cec641de91c8cd017fe25320","sku":"200-300g/个","originalCost":8000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"898","inventoryCap":"6000","inventoryLimit":"60","sales":0,"isBargainprice":"0","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":0,"delFlag":"0"}]}
     */

    private ShopOrderBean shopOrder;

    public ShopOrderBean getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(ShopOrderBean shopOrder) {
        this.shopOrder = shopOrder;
    }

    public static class ShopOrderBean {
        /**
         * id : 20171023180233
         * consumerId : f16bbfee69564bc8a26249a1718d8039
         * stationmasterId : 01d63d5eec004e87b1d6d03331425153
         * noneedsend : 1
         * receiverId : 6b80e6239da64585b4492fdb27d27261
         * quantity : 7
         * expenditure : 29400
         * shopType : 1
         * ifcancel : 0
         * ifinworkstation : 1
         * processInstanceId : c2ad1330e5e14e9e8485e3273213ab42
         * processInstanceStatus : 10
         * createBy : f16bbfee69564bc8a26249a1718d8039
         * createDate : 1508752953000
         * updateBy : f16bbfee69564bc8a26249a1718d8039
         * updateDate : 1508752953000
         * delFlag : 0
         * act : {"taskId":"a187a2dbdd08430a9f19910a8c048252","taskName":"用户支付","taskDefKey":"userPay","procInsId":"c2ad1330e5e14e9e8485e3273213ab42","procDefId":"shop_order:14:82d210bb7442435a867c518d6c6c50c6","procDefKey":"shop_order","status":"todo","vars":{"map":{"shopOrderTimeDuration":"PT30M","ifConsumerPickUp":false,"stationmasterId":"01d63d5eec004e87b1d6d03331425153","sendImmediately":"true","consumerId":"f16bbfee69564bc8a26249a1718d8039"}},"finishTask":false,"todoTask":true}
         * limitTime : 29
         * shopCommodityConsumerList : [{"id":"f141983ae07743c49b8ed08712f3f2af","commodityId":"001e260731994577a59518326d141333","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":5,"isorder":"1","shopOrderId":"20171023180233","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508747862000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508752953000,"shopCommodity":{"id":"001e260731994577a59518326d141333","name":"特价商品商品37","categoryId":"4","barcode":"88a14e8f7cd5491289db6e3f7fd031d9","sku":"200-300g/个","originalCost":1000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"895","inventoryCap":"2000","inventoryLimit":"70","sales":4000,"isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":0,"delFlag":"0"},{"id":"6d16cee6d64e4e638b787721577ec8ff","commodityId":"05b406bae24e4d8888dc6c08eab69d08","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":2,"isorder":"1","shopOrderId":"20171023180233","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508750437000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508752953000,"shopCommodity":{"id":"05b406bae24e4d8888dc6c08eab69d08","name":"蔬菜商品3","categoryId":"2","barcode":"97983f45cec641de91c8cd017fe25320","sku":"200-300g/个","originalCost":8000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"898","inventoryCap":"6000","inventoryLimit":"60","sales":0,"isBargainprice":"0","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":0,"delFlag":"0"}]
         */

        private String id;
        private String consumerId;
        private String stationmasterId;
        private String noneedsend;
        private String receiverId;
        private int quantity;
        private int expenditure;
        private String shopType;
        private String ifcancel;
        private String ifinworkstation;
        private String processInstanceId;
        private String processInstanceStatus;
        private String createBy;
        private long createDate;
        private String updateBy;
        private long updateDate;
        private String delFlag;
        private ActBean act;
        private int limitTime;
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

        public String getStationmasterId() {
            return stationmasterId;
        }

        public void setStationmasterId(String stationmasterId) {
            this.stationmasterId = stationmasterId;
        }

        public String getNoneedsend() {
            return noneedsend;
        }

        public void setNoneedsend(String noneedsend) {
            this.noneedsend = noneedsend;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
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

        public ActBean getAct() {
            return act;
        }

        public void setAct(ActBean act) {
            this.act = act;
        }

        public int getLimitTime() {
            return limitTime;
        }

        public void setLimitTime(int limitTime) {
            this.limitTime = limitTime;
        }

        public List<ShopCommodityConsumerListBean> getShopCommodityConsumerList() {
            return shopCommodityConsumerList;
        }

        public void setShopCommodityConsumerList(List<ShopCommodityConsumerListBean> shopCommodityConsumerList) {
            this.shopCommodityConsumerList = shopCommodityConsumerList;
        }

        public static class ActBean {
            /**
             * taskId : a187a2dbdd08430a9f19910a8c048252
             * taskName : 用户支付
             * taskDefKey : userPay
             * procInsId : c2ad1330e5e14e9e8485e3273213ab42
             * procDefId : shop_order:14:82d210bb7442435a867c518d6c6c50c6
             * procDefKey : shop_order
             * status : todo
             * vars : {"map":{"shopOrderTimeDuration":"PT30M","ifConsumerPickUp":false,"stationmasterId":"01d63d5eec004e87b1d6d03331425153","sendImmediately":"true","consumerId":"f16bbfee69564bc8a26249a1718d8039"}}
             * finishTask : false
             * todoTask : true
             */

            private String taskId;
            private String taskName;
            private String taskDefKey;
            private String procInsId;
            private String procDefId;
            private String procDefKey;
            @SerializedName("status")
            private String statusX;
            private VarsBean vars;
            private boolean finishTask;
            private boolean todoTask;

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getTaskName() {
                return taskName;
            }

            public void setTaskName(String taskName) {
                this.taskName = taskName;
            }

            public String getTaskDefKey() {
                return taskDefKey;
            }

            public void setTaskDefKey(String taskDefKey) {
                this.taskDefKey = taskDefKey;
            }

            public String getProcInsId() {
                return procInsId;
            }

            public void setProcInsId(String procInsId) {
                this.procInsId = procInsId;
            }

            public String getProcDefId() {
                return procDefId;
            }

            public void setProcDefId(String procDefId) {
                this.procDefId = procDefId;
            }

            public String getProcDefKey() {
                return procDefKey;
            }

            public void setProcDefKey(String procDefKey) {
                this.procDefKey = procDefKey;
            }

            public String getStatusX() {
                return statusX;
            }

            public void setStatusX(String statusX) {
                this.statusX = statusX;
            }

            public VarsBean getVars() {
                return vars;
            }

            public void setVars(VarsBean vars) {
                this.vars = vars;
            }

            public boolean isFinishTask() {
                return finishTask;
            }

            public void setFinishTask(boolean finishTask) {
                this.finishTask = finishTask;
            }

            public boolean isTodoTask() {
                return todoTask;
            }

            public void setTodoTask(boolean todoTask) {
                this.todoTask = todoTask;
            }

            public static class VarsBean {
                /**
                 * map : {"shopOrderTimeDuration":"PT30M","ifConsumerPickUp":false,"stationmasterId":"01d63d5eec004e87b1d6d03331425153","sendImmediately":"true","consumerId":"f16bbfee69564bc8a26249a1718d8039"}
                 */

                private MapBean map;

                public MapBean getMap() {
                    return map;
                }

                public void setMap(MapBean map) {
                    this.map = map;
                }

                public static class MapBean {
                    /**
                     * shopOrderTimeDuration : PT30M
                     * ifConsumerPickUp : false
                     * stationmasterId : 01d63d5eec004e87b1d6d03331425153
                     * sendImmediately : true
                     * consumerId : f16bbfee69564bc8a26249a1718d8039
                     */

                    private String shopOrderTimeDuration;
                    private boolean ifConsumerPickUp;
                    private String stationmasterId;
                    private String sendImmediately;
                    private String consumerId;

                    public String getShopOrderTimeDuration() {
                        return shopOrderTimeDuration;
                    }

                    public void setShopOrderTimeDuration(String shopOrderTimeDuration) {
                        this.shopOrderTimeDuration = shopOrderTimeDuration;
                    }

                    public boolean isIfConsumerPickUp() {
                        return ifConsumerPickUp;
                    }

                    public void setIfConsumerPickUp(boolean ifConsumerPickUp) {
                        this.ifConsumerPickUp = ifConsumerPickUp;
                    }

                    public String getStationmasterId() {
                        return stationmasterId;
                    }

                    public void setStationmasterId(String stationmasterId) {
                        this.stationmasterId = stationmasterId;
                    }

                    public String getSendImmediately() {
                        return sendImmediately;
                    }

                    public void setSendImmediately(String sendImmediately) {
                        this.sendImmediately = sendImmediately;
                    }

                    public String getConsumerId() {
                        return consumerId;
                    }

                    public void setConsumerId(String consumerId) {
                        this.consumerId = consumerId;
                    }
                }
            }
        }

        public static class ShopCommodityConsumerListBean {
            /**
             * id : f141983ae07743c49b8ed08712f3f2af
             * commodityId : 001e260731994577a59518326d141333
             * consumerId : f16bbfee69564bc8a26249a1718d8039
             * quantity : 5
             * isorder : 1
             * shopOrderId : 20171023180233
             * shopType : 1
             * createBy : f16bbfee69564bc8a26249a1718d8039
             * createDate : 1508747862000
             * updateBy : f16bbfee69564bc8a26249a1718d8039
             * updateDate : 1508752953000
             * shopCommodity : {"id":"001e260731994577a59518326d141333","name":"特价商品商品37","categoryId":"4","barcode":"88a14e8f7cd5491289db6e3f7fd031d9","sku":"200-300g/个","originalCost":1000,"presentCost":4200,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"895","inventoryCap":"2000","inventoryLimit":"70","sales":4000,"isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"}
             * amount : 0
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
                 * id : 001e260731994577a59518326d141333
                 * name : 特价商品商品37
                 * categoryId : 4
                 * barcode : 88a14e8f7cd5491289db6e3f7fd031d9
                 * sku : 200-300g/个
                 * originalCost : 1000
                 * presentCost : 4200
                 * narrowViewUrl : imag%5Ccommodity%5C1506663534822.jpg
                 * showViewUrl : imag%5Ccommodity%5C1506663536826.png
                 * detailsViewUrl : imag%5Ccommodity%5C1506663541061.png
                 * brand : 云山
                 * origin : 云山
                 * supplierId : 81e6615323934d1f9ab3083c4f68a10c
                 * inventoryQty : 895
                 * inventoryCap : 2000
                 * inventoryLimit : 70
                 * sales : 4000
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
