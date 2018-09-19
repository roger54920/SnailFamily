package com.example.ysww.snailfamily.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by me-jie on 2017/5/3.
 */

public class SendSaveBean extends BaseBean {


    private List<CacompanyBean> cacompany;
    private List<WeightpriceBean> weightprice;
    private List<DelMethodsListBean> delMethodsList;
    private List<WorksStrBean> worksStr;
    private List<GoodsTypeListBean> goodsTypeList;

    public List<CacompanyBean> getCacompany() {
        return cacompany;
    }

    public void setCacompany(List<CacompanyBean> cacompany) {
        this.cacompany = cacompany;
    }

    public List<WeightpriceBean> getWeightprice() {
        return weightprice;
    }

    public void setWeightprice(List<WeightpriceBean> weightprice) {
        this.weightprice = weightprice;
    }

    public List<DelMethodsListBean> getDelMethodsList() {
        return delMethodsList;
    }

    public void setDelMethodsList(List<DelMethodsListBean> delMethodsList) {
        this.delMethodsList = delMethodsList;
    }

    public List<WorksStrBean> getWorksStr() {
        return worksStr;
    }

    public void setWorksStr(List<WorksStrBean> worksStr) {
        this.worksStr = worksStr;
    }

    public List<GoodsTypeListBean> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<GoodsTypeListBean> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }

    public static class CacompanyBean implements IPickerViewData {
        /**
         * id : ab8563dcdd8340fea1f691b7cbcdd9cd
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-30 09:08:45
         * updateDate : 2017-04-30 09:08:45
         * value : 3
         * label : 顺丰快递
         * type : sender_carrier_company
         * description : 承运公司
         * sort : 10
         * parentId : 0
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getPickerViewText() {
            return this.getLabel();
        }
    }

    public static class WeightpriceBean {
        /**
         * id : 3d5ef6e1ffce49e3b5e944d60500b935
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-27 16:57:36
         * updateDate : 2017-04-27 16:59:42
         * value : one
         * label : 0.7
         * type : sender_weight_price
         * description : 称重首次价格
         * sort : 10
         * parentId : 0
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }

    public static class DelMethodsListBean implements IPickerViewData {
        /**
         * id : 1876aecdebc1495e9a186046dd1f9080
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-27 16:41:26
         * updateDate : 2017-04-27 16:41:26
         * value : 0
         * label : 保安保洁上门
         * type : sender_delivery_methods
         * description : 投递方式
         * sort : 10
         * parentId : 0
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getPickerViewText() {
            return this.getLabel();
        }
    }

    public static class WorksStrBean implements IPickerViewData {
        /**
         * id : a07a9224689f468495f0f56a5ebd280e
         * isNewRecord : false
         * stationName : 河南安阳汤阴瓦岗寨人民社区
         */

        private String id;
        private boolean isNewRecord;
        private String stationName;
        private String remarks;

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        @Override
        public String getPickerViewText() {
            return this.getRemarks().replace(",","");
        }
    }

    public static class GoodsTypeListBean implements IPickerViewData {
        /**
         * id : 3fad0e7ba974452faef48452df49f046
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-27 15:27:52
         * updateDate : 2017-04-27 15:27:52
         * value : 1
         * label : 日用品
         * type : sender_goods_type
         * description : 物品类型
         * sort : 10
         * parentId : 0
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String value;
        private String label;
        private String type;
        private String description;
        private int sort;
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getPickerViewText() {
            return this.getLabel();
        }
    }
    /**
     * 生成新寄件订单返回的参数
     */
    public static class ResultSendBean extends BaseBean{

        /**
         * mapsend : {"sender":"丰台区益城园1楼1单元101","recepro":"北京","sendercity":"北京","sendername":"张三","receivername":"","senderpro":"北京","receiver":"丰台区益城园1楼1单元101","rececity":"北京","goodname":"日用品"}
         */

        private MapsendBean mapsend;

        public MapsendBean getMapsend() {
            return mapsend;
        }

        public void setMapsend(MapsendBean mapsend) {
            this.mapsend = mapsend;
        }

        public static class MapsendBean {
            /**
             * sender : 丰台区益城园1楼1单元101
             * recepro : 北京
             * sendercity : 北京
             * sendername : 张三
             * receivername :
             * senderpro : 北京
             * receiver : 丰台区益城园1楼1单元101
             * rececity : 北京
             * goodname : 日用品
             */

            private String sender;
            private String recepro;
            private String sendercity;
            private String sendername;
            private String receivername;
            private String senderpro;
            private String receiver;
            private String rececity;
            private String goodname;

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
            }

            public String getRecepro() {
                return recepro;
            }

            public void setRecepro(String recepro) {
                this.recepro = recepro;
            }

            public String getSendercity() {
                return sendercity;
            }

            public void setSendercity(String sendercity) {
                this.sendercity = sendercity;
            }

            public String getSendername() {
                return sendername;
            }

            public void setSendername(String sendername) {
                this.sendername = sendername;
            }

            public String getReceivername() {
                return receivername;
            }

            public void setReceivername(String receivername) {
                this.receivername = receivername;
            }

            public String getSenderpro() {
                return senderpro;
            }

            public void setSenderpro(String senderpro) {
                this.senderpro = senderpro;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getRececity() {
                return rececity;
            }

            public void setRececity(String rececity) {
                this.rececity = rececity;
            }

            public String getGoodname() {
                return goodname;
            }

            public void setGoodname(String goodname) {
                this.goodname = goodname;
            }
        }
    }
    /**
     * 保存的参数
     */
    public static class SendSaveParameter{
        private String worksId;// 站的ID
        private String  receivername ;//收件人名称
        private String sendername;//  寄件人名称
        private String  receivermoblie;// 收件手机号码
        private String sendermoblie;//  寄件人手机号码
        private String  receiverprovincename;// 收件地址省份
        private String receivercityname;//  收件地址城市
        private String receiverexpareaname;// 收件地址区
        private String receiveraddress;//  收件详细地址
        private String senderprovincename;// 寄件地址省份
        private String sendercityname;//  寄件地址城市
        private String senderexpareaname;// 寄件地址区
        private String senderaddress;//  寄件详细地址
        private String  goodsname;// 商品类型
        private String isnotice;// 投递方式
        private String shippercode;// 承运公司
        private String card;//身份证
        private String receiverprovincode;// 收件省份地址编码
        private String receivercitycode;//  收件城市地址编码
        private String receiverexpareacode;//  收件区域地址编码
        private String isAddress;//是否为常用地址 1->为常用地址


        public String getIsAddress() {
            return isAddress;
        }

        public void setIsAddress(String isAddress) {
            this.isAddress = isAddress;
        }

        public String getReceiverexpareacode() {
            return receiverexpareacode;
        }

        public void setReceiverexpareacode(String receiverexpareacode) {
            this.receiverexpareacode = receiverexpareacode;
        }

        public String getReceiverprovincode() {
            return receiverprovincode;
        }

        public void setReceiverprovincode(String receiverprovincode) {
            this.receiverprovincode = receiverprovincode;
        }

        public String getReceivercitycode() {
            return receivercitycode;
        }

        public void setReceivercitycode(String receivercitycode) {
            this.receivercitycode = receivercitycode;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getWorksId() {
            return worksId;
        }

        public void setWorksId(String worksId) {
            this.worksId = worksId;
        }

        public String getReceivername() {
            return receivername;
        }

        public void setReceivername(String receivername) {
            this.receivername = receivername;
        }

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getReceivermoblie() {
            return receivermoblie;
        }

        public void setReceivermoblie(String receivermoblie) {
            this.receivermoblie = receivermoblie;
        }

        public String getSendermoblie() {
            return sendermoblie;
        }

        public void setSendermoblie(String sendermoblie) {
            this.sendermoblie = sendermoblie;
        }

        public String getReceiverprovincename() {
            return receiverprovincename;
        }

        public void setReceiverprovincename(String receiverprovincename) {
            this.receiverprovincename = receiverprovincename;
        }

        public String getReceivercityname() {
            return receivercityname;
        }

        public void setReceivercityname(String receivercityname) {
            this.receivercityname = receivercityname;
        }

        public String getReceiverexpareaname() {
            return receiverexpareaname;
        }

        public void setReceiverexpareaname(String receiverexpareaname) {
            this.receiverexpareaname = receiverexpareaname;
        }

        public String getReceiveraddress() {
            return receiveraddress;
        }

        public void setReceiveraddress(String receiveraddress) {
            this.receiveraddress = receiveraddress;
        }

        public String getSenderprovincename() {
            return senderprovincename;
        }

        public void setSenderprovincename(String senderprovincename) {
            this.senderprovincename = senderprovincename;
        }

        public String getSendercityname() {
            return sendercityname;
        }

        public void setSendercityname(String sendercityname) {
            this.sendercityname = sendercityname;
        }

        public String getSenderexpareaname() {
            return senderexpareaname;
        }

        public void setSenderexpareaname(String senderexpareaname) {
            this.senderexpareaname = senderexpareaname;
        }

        public String getSenderaddress() {
            return senderaddress;
        }

        public void setSenderaddress(String senderaddress) {
            this.senderaddress = senderaddress;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getIsnotice() {
            return isnotice;
        }

        public void setIsnotice(String isnotice) {
            this.isnotice = isnotice;
        }

        public String getShippercode() {
            return shippercode;
        }

        public void setShippercode(String shippercode) {
            this.shippercode = shippercode;
        }
    }
}
