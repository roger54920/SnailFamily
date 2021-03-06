package com.example.ysww.snailfamily.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/5/3.
 */

public class CommonSiteBean extends BaseBean {


    /**
     * page : {"pageNo":1,"pageSize":10,"count":3,"list":[{"id":"aaff0cc38b8c4f91bc8639a233b76828","isNewRecord":false,"remarks":"北京海淀区倒座庙小区西区1楼1单元101","createDate":"2017-05-08 18:23:53","updateDate":"2017-05-08 20:16:00","owner":{"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""},"provincialCode":"25799e4a916b446e81fc95630d1249c9","cityCode":"1c4f6592e7f443ca92f8b5140b606a39","areaCode":"522f930143a7498dbc17dfa7281888d4","cellCode":"7c2a5e3b3c774cfa97f00ef0d92eac95","houseNumber":"45e8a7996d5b4c2c97e2554def6f5e35","unit":"2ad78d13a4734cfca2e8aa4b4e4d60f9","room":"0996ce241f964bf2983ea59c02d9130c","ifDefault":"1","type":"1","ifUserParcelNo":"\u0000","phone":"17555888889","name":"1212","place":"默默哦","parcelNo":"581920"},{"id":"e8927c886c314f3996385e8d46c458dc","isNewRecord":false,"remarks":"湖北北京海淀区默默哦","createDate":"2017-05-08 18:36:43","updateDate":"2017-05-08 18:36:43","owner":{"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""},"provincialCode":"4c9ac06c70824622a94392cf9f58e887","cityCode":"1c4f6592e7f443ca92f8b5140b606a39","areaCode":"522f930143a7498dbc17dfa7281888d4","ifDefault":"1","type":"2","ifUserParcelNo":"\u0000","phone":"13520151910","place":"默默哦","name":"考虑考虑"},{"id":"1d09ff47611b4da6b12de119bb31011a","isNewRecord":false,"remarks":"北京海淀区倒座庙小区西区1楼1单元101","createDate":"2017-05-08 18:20:21","updateDate":"2017-05-08 18:20:21","owner":{"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""},"provincialCode":"25799e4a916b446e81fc95630d1249c9","cityCode":"1c4f6592e7f443ca92f8b5140b606a39","areaCode":"522f930143a7498dbc17dfa7281888d4","cellCode":"7c2a5e3b3c774cfa97f00ef0d92eac95","houseNumber":"45e8a7996d5b4c2c97e2554def6f5e35","unit":"2ad78d13a4734cfca2e8aa4b4e4d60f9","room":"0996ce241f964bf2983ea59c02d9130c","ifDefault":"0","type":"1","ifUserParcelNo":"0","parcelNo":"581920","phone":"14555899666","name":"1212"}],"maxResults":10,"firstResult":0}
     */

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * count : 3
         * list : [{"id":"aaff0cc38b8c4f91bc8639a233b76828","isNewRecord":false,"remarks":"北京海淀区倒座庙小区西区1楼1单元101","createDate":"2017-05-08 18:23:53","updateDate":"2017-05-08 20:16:00","owner":{"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""},"provincialCode":"25799e4a916b446e81fc95630d1249c9","cityCode":"1c4f6592e7f443ca92f8b5140b606a39","areaCode":"522f930143a7498dbc17dfa7281888d4","cellCode":"7c2a5e3b3c774cfa97f00ef0d92eac95","houseNumber":"45e8a7996d5b4c2c97e2554def6f5e35","unit":"2ad78d13a4734cfca2e8aa4b4e4d60f9","room":"0996ce241f964bf2983ea59c02d9130c","ifDefault":"1","type":"1","ifUserParcelNo":"\u0000","phone":"17555888889","name":"1212"},{"id":"e8927c886c314f3996385e8d46c458dc","isNewRecord":false,"remarks":"湖北北京海淀区默默哦","createDate":"2017-05-08 18:36:43","updateDate":"2017-05-08 18:36:43","owner":{"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""},"provincialCode":"4c9ac06c70824622a94392cf9f58e887","cityCode":"1c4f6592e7f443ca92f8b5140b606a39","areaCode":"522f930143a7498dbc17dfa7281888d4","ifDefault":"1","type":"2","ifUserParcelNo":"\u0000","phone":"13520151910","place":"默默哦","name":"考虑考虑"},{"id":"1d09ff47611b4da6b12de119bb31011a","isNewRecord":false,"remarks":"北京海淀区倒座庙小区西区1楼1单元101","createDate":"2017-05-08 18:20:21","updateDate":"2017-05-08 18:20:21","owner":{"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""},"provincialCode":"25799e4a916b446e81fc95630d1249c9","cityCode":"1c4f6592e7f443ca92f8b5140b606a39","areaCode":"522f930143a7498dbc17dfa7281888d4","cellCode":"7c2a5e3b3c774cfa97f00ef0d92eac95","houseNumber":"45e8a7996d5b4c2c97e2554def6f5e35","unit":"2ad78d13a4734cfca2e8aa4b4e4d60f9","room":"0996ce241f964bf2983ea59c02d9130c","ifDefault":"0","type":"1","ifUserParcelNo":"0","parcelNo":"581920","phone":"14555899666","name":"1212"}]
         * maxResults : 10
         * firstResult : 0
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private int maxResults;
        private int firstResult;
        private List<ListBean> list;

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

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : aaff0cc38b8c4f91bc8639a233b76828
             * isNewRecord : false
             * remarks : 北京海淀区倒座庙小区西区1楼1单元101
             * createDate : 2017-05-08 18:23:53
             * updateDate : 2017-05-08 20:16:00
             * owner : {"id":"2","isNewRecord":false,"loginFlag":"1","roleNames":""}
             * provincialCode : 25799e4a916b446e81fc95630d1249c9
             * cityCode : 1c4f6592e7f443ca92f8b5140b606a39
             * areaCode : 522f930143a7498dbc17dfa7281888d4
             * cellCode : 7c2a5e3b3c774cfa97f00ef0d92eac95
             * houseNumber : 45e8a7996d5b4c2c97e2554def6f5e35
             * unit : 2ad78d13a4734cfca2e8aa4b4e4d60f9
             * room : 0996ce241f964bf2983ea59c02d9130c
             * ifDefault : 1
             * type : 1
             * ifUserParcelNo :  
             * phone : 17555888889
             * name : 1212
             * place : 默默哦
             * parcelNo : 581920
             */

            private String id;
            private boolean isNewRecord;
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
            private String phone;
            private String name;
            private String place;
            private String parcelNo;

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

            public String getPlace() {
                return place;
            }

            public void setPlace(String place) {
                this.place = place;
            }

            public String getParcelNo() {
                return parcelNo;
            }

            public void setParcelNo(String parcelNo) {
                this.parcelNo = parcelNo;
            }

            public static class OwnerBean {
                /**
                 * id : 2
                 * isNewRecord : false
                 * loginFlag : 1
                 * roleNames :
                 */

                private String id;
                private boolean isNewRecord;
                private String loginFlag;
                private String roleNames;

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

                public String getLoginFlag() {
                    return loginFlag;
                }

                public void setLoginFlag(String loginFlag) {
                    this.loginFlag = loginFlag;
                }

                public String getRoleNames() {
                    return roleNames;
                }

                public void setRoleNames(String roleNames) {
                    this.roleNames = roleNames;
                }
            }
        }
    }
}
