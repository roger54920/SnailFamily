package com.example.ysww.snailfamily.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * 小区名
 */

public class NewAddressPlotNameBean extends BaseBean{

    /**
     * status : 1
     * community : [{"id":"ac8a0bd56495486484a9e45210976788","isNewRecord":false,"remarks":"","createDate":"2017-04-14 10:21:05","updateDate":"2017-04-14 10:21:05","parentIds":"0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,","name":"益城园","sort":30,"code":"e1267ffd-f385-428b-af79-394aff7ef6cd","type":"5","parentId":"79e7aab3dc964909a720cb2e0c02f3fe"}]
     */

    private List<CommunityBean> community;

    public List<CommunityBean> getCommunity() {
        return community;
    }

    public void setCommunity(List<CommunityBean> community) {
        this.community = community;
    }

    public static class CommunityBean implements IPickerViewData {
        /**
         * id : ac8a0bd56495486484a9e45210976788
         * isNewRecord : false
         * remarks :
         * createDate : 2017-04-14 10:21:05
         * updateDate : 2017-04-14 10:21:05
         * parentIds : 0,2d7682947153480fa1425d516334c1a0,25799e4a916b446e81fc95630d1249c9,1c4f6592e7f443ca92f8b5140b606a39,79e7aab3dc964909a720cb2e0c02f3fe,
         * name : 益城园
         * sort : 30
         * code : e1267ffd-f385-428b-af79-394aff7ef6cd
         * type : 5
         * parentId : 79e7aab3dc964909a720cb2e0c02f3fe
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String updateDate;
        private String parentIds;
        private String name;
        private int sort;
        private String code;
        private String type;
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

        public String getParentIds() {
            return parentIds;
        }

        public void setParentIds(String parentIds) {
            this.parentIds = parentIds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getPickerViewText() {
            return this.name;
        }
    }
}
