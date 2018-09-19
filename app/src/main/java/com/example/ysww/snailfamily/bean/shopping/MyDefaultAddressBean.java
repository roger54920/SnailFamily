package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

/**
 * Created by ysww on 2017/10/23.
 * 获取用户默认地址
 */

public class MyDefaultAddressBean extends BaseBean{

    /**
     * address : {"id":"6b80e6239da64585b4492fdb27d27261","remarks":"北京市,市辖区,海淀区,测试区1,1号楼,5单元,501室","createDate":"2017-06-27 14:29:06","updateDate":"2017-07-03 17:17:37","owner":{"id":"f16bbfee69564bc8a26249a1718d8039"},"provincialCode":"110000","cityCode":"110100","areaCode":"110108","cellCode":"0e3c1dabe5d548489977005af69c4992","houseNumber":"96a1892a2490408bb365ae6918f7cc34","unit":"ab0036f942b04574afe971a0ebae1509","room":"ce2d150810784b1b8be23cdd36bf5421","ifDefault":"1","type":"1","ifUserParcelNo":"0","parcelNo":"2222228","phone":"17783282913","name":"哈哈哈"}
     */

    private AddressBean address;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * id : 6b80e6239da64585b4492fdb27d27261
         * remarks : 北京市,市辖区,海淀区,测试区1,1号楼,5单元,501室
         * createDate : 2017-06-27 14:29:06
         * updateDate : 2017-07-03 17:17:37
         * owner : {"id":"f16bbfee69564bc8a26249a1718d8039"}
         * provincialCode : 110000
         * cityCode : 110100
         * areaCode : 110108
         * cellCode : 0e3c1dabe5d548489977005af69c4992
         * houseNumber : 96a1892a2490408bb365ae6918f7cc34
         * unit : ab0036f942b04574afe971a0ebae1509
         * room : ce2d150810784b1b8be23cdd36bf5421
         * ifDefault : 1
         * type : 1
         * ifUserParcelNo : 0
         * parcelNo : 2222228
         * phone : 17783282913
         * name : 哈哈哈
         */

        private String id;
        private String remarks;
        private String createDate;
        private String updateDate;
        private OwnerBean owner;
        private String provincialCode;
        private String cityCode;
        private String areaCode;
        private String cellCode;
        private String houseNumber;
        private String unit;
        private String room;
        private String ifDefault;
        private String type;
        private String ifUserParcelNo;
        private String parcelNo;
        private String phone;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
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

        public String getIfDefault() {
            return ifDefault;
        }

        public void setIfDefault(String ifDefault) {
            this.ifDefault = ifDefault;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIfUserParcelNo() {
            return ifUserParcelNo;
        }

        public void setIfUserParcelNo(String ifUserParcelNo) {
            this.ifUserParcelNo = ifUserParcelNo;
        }

        public String getParcelNo() {
            return parcelNo;
        }

        public void setParcelNo(String parcelNo) {
            this.parcelNo = parcelNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class OwnerBean {
            /**
             * id : f16bbfee69564bc8a26249a1718d8039
             */

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
