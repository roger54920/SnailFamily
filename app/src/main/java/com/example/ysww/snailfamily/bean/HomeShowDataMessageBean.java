package com.example.ysww.snailfamily.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/5/27.
 * 首页信息展示
 */

public class HomeShowDataMessageBean extends BaseBean{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : c75fe4be78b246af9addca2004f68fb3
         * expressParcel : {"id":"c0f8df138e6445d98e1550390ea727af","isNewRecord":false,"receiver":{"id":"b6d3acf8e6b541eb98b7f4128cf49350","isNewRecord":false,"createDate":"2017-05-27 12:11:23","updateDate":"2017-05-27 12:11:23","name":"Bella","mobile":"15811478673","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"城南益城园1楼1单元302"},"shipperCode":"STO","lgisticCode":"3330118230232"}
         * remarks : 到达时间  2017-05-27 12:11
         * createDate : 1495854794000
         * type : 2
         * businessStatus : 蜗牛已收件
         */

        private String id;
        private ExpressParcelBean expressParcel;
        private String remarks;
        private long createDate;
        private String type;
        private String businessStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ExpressParcelBean getExpressParcel() {
            return expressParcel;
        }

        public void setExpressParcel(ExpressParcelBean expressParcel) {
            this.expressParcel = expressParcel;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public static class ExpressParcelBean {
            /**
             * id : c0f8df138e6445d98e1550390ea727af
             * isNewRecord : false
             * receiver : {"id":"b6d3acf8e6b541eb98b7f4128cf49350","isNewRecord":false,"createDate":"2017-05-27 12:11:23","updateDate":"2017-05-27 12:11:23","name":"Bella","mobile":"15811478673","provinceName":"北京","cityName":"北京","expAreaName":"丰台区","address":"城南益城园1楼1单元302"}
             * shipperCode : STO
             * lgisticCode : 3330118230232
             */

            private String id;
            private boolean isNewRecord;
            private ReceiverBean receiver;
            private String shipperCode;
            private String lgisticCode;

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

            public ReceiverBean getReceiver() {
                return receiver;
            }

            public void setReceiver(ReceiverBean receiver) {
                this.receiver = receiver;
            }

            public String getShipperCode() {
                return shipperCode;
            }

            public void setShipperCode(String shipperCode) {
                this.shipperCode = shipperCode;
            }

            public String getLgisticCode() {
                return lgisticCode;
            }

            public void setLgisticCode(String lgisticCode) {
                this.lgisticCode = lgisticCode;
            }

            public static class ReceiverBean {
                /**
                 * id : b6d3acf8e6b541eb98b7f4128cf49350
                 * isNewRecord : false
                 * createDate : 2017-05-27 12:11:23
                 * updateDate : 2017-05-27 12:11:23
                 * name : Bella
                 * mobile : 15811478673
                 * provinceName : 北京
                 * cityName : 北京
                 * expAreaName : 丰台区
                 * address : 城南益城园1楼1单元302
                 */

                private String id;
                private boolean isNewRecord;
                private String createDate;
                private String updateDate;
                private String name;
                private String mobile;
                private String provinceName;
                private String cityName;
                private String expAreaName;
                private String address;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getProvinceName() {
                    return provinceName;
                }

                public void setProvinceName(String provinceName) {
                    this.provinceName = provinceName;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public String getExpAreaName() {
                    return expAreaName;
                }

                public void setExpAreaName(String expAreaName) {
                    this.expAreaName = expAreaName;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }
            }
        }
    }
}
