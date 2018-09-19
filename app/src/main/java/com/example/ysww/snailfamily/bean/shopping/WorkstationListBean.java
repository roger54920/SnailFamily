package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

import java.util.List;

/**
 * Created by ysww on 2017/10/24.
 * 获取蜗牛站列表
 */

public class WorkstationListBean extends BaseBean {

    private List<WorkstationList> workstationList;

    public List<WorkstationList> getWorkstationList() {
        return workstationList;
    }

    public void setWorkstationList(List<WorkstationList> workstationList) {
        this.workstationList = workstationList;
    }

    public static class WorkstationList {
        /**
         * id : d30f0cff52a543ce91c296afeb8befe7
         * remarks : 北京市,市辖区,海淀区,测试区1,1号楼,5单元,502室
         * createDate : 2017-06-27 12:10:37
         * updateDate : 2017-06-27 12:13:20
         * stationName : 站点111
         * workstationCode : 1498536636827
         * provincialCode : 110000
         * cityCode : 110100
         * areaCode : 110108
         * cellCode : 0e3c1dabe5d548489977005af69c4992
         * houseNumber : 96a1892a2490408bb365ae6918f7cc34
         * unit : 8bdd428194fb4e478ac4e6b2d9f565e4
         * room : 099c4d770bc64bf4aa973d2c6a9bdee9
         * ifAvailabe : 1
         */

        private String id;
        private String remarks;
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
