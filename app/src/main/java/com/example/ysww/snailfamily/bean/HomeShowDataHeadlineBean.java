package com.example.ysww.snailfamily.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/5/27.
 * 蜗牛头条
 */

public class HomeShowDataHeadlineBean extends BaseBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * content : Bella  蜗牛已收件,到达时间  2017-05-27 12:11
         * id : c75fe4be78b246af9addca2004f68fb3
         * createDate : 1495854794000
         * type : 2
         */

        private String content;
        private String id;
        private long createDate;
        private String type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }



}
