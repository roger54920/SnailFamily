package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ysww on 2017/10/25.
 * 电商订单详情
 */

public class GetOrderInfoBean extends BaseBean implements Serializable{

    /**
     * shopOrder : {"id":"20171025182145","consumerId":"f16bbfee69564bc8a26249a1718d8039","workstationId":"91c1d09e5ed7438bb7111e67b7ea517c","noneedsend":"1","receiverId":"ce1bc35cf2774a679aad6bfd3f350a1c","quantity":1,"expenditure":8000,"shopType":"1","ifcancel":"0","ifinworkstation":"1","processInstanceId":"d678cee67e034e02b376ebbdd92f7fa4","businessStatus":"未支付","processInstanceStatus":"10","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508926906000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508926906000,"delFlag":"0","act":{"taskId":"9e72a4ebfe704db2af279a75444ec554","taskName":"用户支付","taskDefKey":"userPay","procInsId":"d678cee67e034e02b376ebbdd92f7fa4","procDefId":"shop_order:14:82d210bb7442435a867c518d6c6c50c6","procDefKey":"shop_order","status":"todo","vars":{"map":{"consumerId":"f16bbfee69564bc8a26249a1718d8039","ifConsumerPickUp":false,"sendImmediately":true,"shopOrderTimeDuration":"PT30M","stationmasterId":""}},"todoTask":true,"finishTask":false},"limitTime":29,"shopCommodityConsumerList":[{"id":"64fad7a0c0464515a0e8d9f35ca6aa3d","commodityId":"0806baf67e9f4f35b17a922e4c4f38c7","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":1,"isorder":"1","shopOrderId":"20171025182145","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508926901000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508926906000,"shopCommodity":{"id":"0806baf67e9f4f35b17a922e4c4f38c7","name":"特价商品商品61","categoryId":"2","barcode":"d0da79fba5014a9b928d7a37f61e04d1","sku":"200-300g/个","originalCost":8000,"presentCost":8000,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"598","inventoryCap":"2000","inventoryLimit":"60","sales":2000,"isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":8000,"delFlag":"0"}]}
     * workstation : {"id":"91c1d09e5ed7438bb7111e67b7ea517c","createDate":"2017-06-30 12:33:12","updateDate":"2017-06-30 15:18:47","stationName":"测试站点","workstationCode":"1498797192230","provincialCode":"110000","cityCode":"110100","areaCode":"110108","cellCode":"1a841dccf8204be08ac3b683144e43b9","houseNumber":"4fcf6f6c98d540a0b977dfb3cce0e7f1","unit":"87574df435da429facf6e95366bd4e30","room":"1f8d8f5313e74b6a80b01e53ea5f3946","ifAvailabe":"1"}
     */

    private ShopOrderBean shopOrder;
    private WorkstationBean workstation;

    public ShopOrderBean getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(ShopOrderBean shopOrder) {
        this.shopOrder = shopOrder;
    }

    public WorkstationBean getWorkstation() {
        return workstation;
    }

    public void setWorkstation(WorkstationBean workstation) {
        this.workstation = workstation;
    }

    public static class ShopOrderBean  implements Serializable{
        /**
         * id : 20171025182145
         * consumerId : f16bbfee69564bc8a26249a1718d8039
         * workstationId : 91c1d09e5ed7438bb7111e67b7ea517c
         * noneedsend : 1
         * receiverId : ce1bc35cf2774a679aad6bfd3f350a1c
         * quantity : 1
         * expenditure : 8000
         * shopType : 1
         * ifcancel : 0
         * ifinworkstation : 1
         * processInstanceId : d678cee67e034e02b376ebbdd92f7fa4
         * businessStatus : 未支付
         * processInstanceStatus : 10
         * createBy : f16bbfee69564bc8a26249a1718d8039
         * createDate : 1508926906000
         * updateBy : f16bbfee69564bc8a26249a1718d8039
         * updateDate : 1508926906000
         * delFlag : 0
         * act : {"taskId":"9e72a4ebfe704db2af279a75444ec554","taskName":"用户支付","taskDefKey":"userPay","procInsId":"d678cee67e034e02b376ebbdd92f7fa4","procDefId":"shop_order:14:82d210bb7442435a867c518d6c6c50c6","procDefKey":"shop_order","status":"todo","vars":{"map":{"consumerId":"f16bbfee69564bc8a26249a1718d8039","ifConsumerPickUp":false,"sendImmediately":true,"shopOrderTimeDuration":"PT30M","stationmasterId":""}},"todoTask":true,"finishTask":false}
         * limitTime : 29
         * shopCommodityConsumerList : [{"id":"64fad7a0c0464515a0e8d9f35ca6aa3d","commodityId":"0806baf67e9f4f35b17a922e4c4f38c7","consumerId":"f16bbfee69564bc8a26249a1718d8039","quantity":1,"isorder":"1","shopOrderId":"20171025182145","shopType":"1","createBy":"f16bbfee69564bc8a26249a1718d8039","createDate":1508926901000,"updateBy":"f16bbfee69564bc8a26249a1718d8039","updateDate":1508926906000,"shopCommodity":{"id":"0806baf67e9f4f35b17a922e4c4f38c7","name":"特价商品商品61","categoryId":"2","barcode":"d0da79fba5014a9b928d7a37f61e04d1","sku":"200-300g/个","originalCost":8000,"presentCost":8000,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"598","inventoryCap":"2000","inventoryLimit":"60","sales":2000,"isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"},"amount":8000,"delFlag":"0"}]
         */

        private String id;
        private String consumerId;
        private String workstationId;
        private String noneedsend;
        private String receiverId;
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

        public static class ActBean implements Serializable{
            /**
             * taskId : 9e72a4ebfe704db2af279a75444ec554
             * taskName : 用户支付
             * taskDefKey : userPay
             * procInsId : d678cee67e034e02b376ebbdd92f7fa4
             * procDefId : shop_order:14:82d210bb7442435a867c518d6c6c50c6
             * procDefKey : shop_order
             * status : todo
             * vars : {"map":{"consumerId":"f16bbfee69564bc8a26249a1718d8039","ifConsumerPickUp":false,"sendImmediately":true,"shopOrderTimeDuration":"PT30M","stationmasterId":""}}
             * todoTask : true
             * finishTask : false
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
            private boolean todoTask;
            private boolean finishTask;

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

            public boolean isTodoTask() {
                return todoTask;
            }

            public void setTodoTask(boolean todoTask) {
                this.todoTask = todoTask;
            }

            public boolean isFinishTask() {
                return finishTask;
            }

            public void setFinishTask(boolean finishTask) {
                this.finishTask = finishTask;
            }

            public static class VarsBean implements Serializable{
                /**
                 * map : {"consumerId":"f16bbfee69564bc8a26249a1718d8039","ifConsumerPickUp":false,"sendImmediately":true,"shopOrderTimeDuration":"PT30M","stationmasterId":""}
                 */

                private MapBean map;

                public MapBean getMap() {
                    return map;
                }

                public void setMap(MapBean map) {
                    this.map = map;
                }

                public static class MapBean implements Serializable{
                    /**
                     * consumerId : f16bbfee69564bc8a26249a1718d8039
                     * ifConsumerPickUp : false
                     * sendImmediately : true
                     * shopOrderTimeDuration : PT30M
                     * stationmasterId :
                     */

                    private String consumerId;
                    private boolean ifConsumerPickUp;
                    private boolean sendImmediately;
                    private String shopOrderTimeDuration;
                    private String stationmasterId;

                    public String getConsumerId() {
                        return consumerId;
                    }

                    public void setConsumerId(String consumerId) {
                        this.consumerId = consumerId;
                    }

                    public boolean isIfConsumerPickUp() {
                        return ifConsumerPickUp;
                    }

                    public void setIfConsumerPickUp(boolean ifConsumerPickUp) {
                        this.ifConsumerPickUp = ifConsumerPickUp;
                    }

                    public boolean isSendImmediately() {
                        return sendImmediately;
                    }

                    public void setSendImmediately(boolean sendImmediately) {
                        this.sendImmediately = sendImmediately;
                    }

                    public String getShopOrderTimeDuration() {
                        return shopOrderTimeDuration;
                    }

                    public void setShopOrderTimeDuration(String shopOrderTimeDuration) {
                        this.shopOrderTimeDuration = shopOrderTimeDuration;
                    }

                    public String getStationmasterId() {
                        return stationmasterId;
                    }

                    public void setStationmasterId(String stationmasterId) {
                        this.stationmasterId = stationmasterId;
                    }
                }
            }
        }

        public static class ShopCommodityConsumerListBean implements Serializable{
            /**
             * id : 64fad7a0c0464515a0e8d9f35ca6aa3d
             * commodityId : 0806baf67e9f4f35b17a922e4c4f38c7
             * consumerId : f16bbfee69564bc8a26249a1718d8039
             * quantity : 1
             * isorder : 1
             * shopOrderId : 20171025182145
             * shopType : 1
             * createBy : f16bbfee69564bc8a26249a1718d8039
             * createDate : 1508926901000
             * updateBy : f16bbfee69564bc8a26249a1718d8039
             * updateDate : 1508926906000
             * shopCommodity : {"id":"0806baf67e9f4f35b17a922e4c4f38c7","name":"特价商品商品61","categoryId":"2","barcode":"d0da79fba5014a9b928d7a37f61e04d1","sku":"200-300g/个","originalCost":8000,"presentCost":8000,"narrowViewUrl":"imag%5Ccommodity%5C1506663534822.jpg","showViewUrl":"imag%5Ccommodity%5C1506663536826.png","detailsViewUrl":"imag%5Ccommodity%5C1506663541061.png","brand":"云山","origin":"云山","supplierId":"81e6615323934d1f9ab3083c4f68a10c","inventoryQty":"598","inventoryCap":"2000","inventoryLimit":"60","sales":2000,"isBargainprice":"1","createBy":"1","createDate":1506662191000,"updateBy":"1","updateDate":1507803554000,"remarks":"","delFlag":"0"}
             * amount : 8000
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

            public static class ShopCommodityBean  implements Serializable{
                /**
                 * id : 0806baf67e9f4f35b17a922e4c4f38c7
                 * name : 特价商品商品61
                 * categoryId : 2
                 * barcode : d0da79fba5014a9b928d7a37f61e04d1
                 * sku : 200-300g/个
                 * originalCost : 8000
                 * presentCost : 8000
                 * narrowViewUrl : imag%5Ccommodity%5C1506663534822.jpg
                 * showViewUrl : imag%5Ccommodity%5C1506663536826.png
                 * detailsViewUrl : imag%5Ccommodity%5C1506663541061.png
                 * brand : 云山
                 * origin : 云山
                 * supplierId : 81e6615323934d1f9ab3083c4f68a10c
                 * inventoryQty : 598
                 * inventoryCap : 2000
                 * inventoryLimit : 60
                 * sales : 2000
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

    public static class WorkstationBean  implements Serializable{
        /**
         * id : 91c1d09e5ed7438bb7111e67b7ea517c
         * createDate : 2017-06-30 12:33:12
         * updateDate : 2017-06-30 15:18:47
         * stationName : 测试站点
         * workstationCode : 1498797192230
         * provincialCode : 110000
         * cityCode : 110100
         * areaCode : 110108
         * cellCode : 1a841dccf8204be08ac3b683144e43b9
         * houseNumber : 4fcf6f6c98d540a0b977dfb3cce0e7f1
         * unit : 87574df435da429facf6e95366bd4e30
         * room : 1f8d8f5313e74b6a80b01e53ea5f3946
         * ifAvailabe : 1
         */

        private String id;
        private String createDate;
        private String updateDate;
        private String stationName;
        private String workstationCode;
        private String provincialCode;
        private String cityCode;
        private String areaCode;
        private String cellCode;
        private String houseNumber;
        private String unit;
        private String room;
        private String ifAvailabe;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getWorkstationCode() {
            return workstationCode;
        }

        public void setWorkstationCode(String workstationCode) {
            this.workstationCode = workstationCode;
        }

        public String getProvincialCode() {
            return provincialCode;
        }

        public void setProvincialCode(String provincialCode) {
            this.provincialCode = provincialCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getCellCode() {
            return cellCode;
        }

        public void setCellCode(String cellCode) {
            this.cellCode = cellCode;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getIfAvailabe() {
            return ifAvailabe;
        }

        public void setIfAvailabe(String ifAvailabe) {
            this.ifAvailabe = ifAvailabe;
        }
    }
}
