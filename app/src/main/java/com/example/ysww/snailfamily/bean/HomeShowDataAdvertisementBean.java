package com.example.ysww.snailfamily.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/7/5.
 * 广告图
 */

public class HomeShowDataAdvertisementBean extends BaseBean{

    private List<AdvertisementListBean> advertisementList;

    public List<AdvertisementListBean> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<AdvertisementListBean> advertisementList) {
        this.advertisementList = advertisementList;
    }

    public static class AdvertisementListBean {
        /**
         * id : 8a55551958174dee96f7dd6769ed3aa7
         * name : 111
         * sort : 111
         * filePath : advertisement%5C1499219795418.jpg
         * fileName : ios1.jpg
         */

        private String id;
        private String name;
        private int sort;
        private String filePath;
        private String fileName;

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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}
