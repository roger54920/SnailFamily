package com.example.ysww.snailfamily.bean;

import java.util.List;

/**
 * Created by me-jie on 2017/7/7.
 * 我的余额 累计消费/充值
 */

public class MyCccountGrandTotalBean extends BaseBean {

    /**
     * page : {"pageNo":1,"pageSize":3,"count":2,"list":[{"id":"38c44a14e6da46b399d0e2e245f1a80a","transactionType":"1","transactionChannel":"支付宝","transactionTime":1499421676000,"transactionAmount":100},{"id":"1b2665ab95b64c00859fb19aecaa4565","transactionType":"1","transactionChannel":"支付宝","transactionTime":1499421490000,"transactionAmount":100}],"firstResult":0,"maxResults":3}
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
         * pageSize : 3
         * count : 2
         * list : [{"id":"38c44a14e6da46b399d0e2e245f1a80a","transactionType":"1","transactionChannel":"支付宝","transactionTime":1499421676000,"transactionAmount":100},{"id":"1b2665ab95b64c00859fb19aecaa4565","transactionType":"1","transactionChannel":"支付宝","transactionTime":1499421490000,"transactionAmount":100}]
         * firstResult : 0
         * maxResults : 3
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private int firstResult;
        private int maxResults;
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

        public int getFirstResult() {
            return firstResult;
        }

        public void setFirstResult(int firstResult) {
            this.firstResult = firstResult;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 38c44a14e6da46b399d0e2e245f1a80a
             * transactionType : 1
             * transactionChannel : 支付宝
             * transactionTime : 1499421676000
             * transactionAmount : 100.0
             */

            private String id;
            private String transactionType;
            private String transactionChannel;
            private long transactionTime;
            private double transactionAmount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTransactionType() {
                return transactionType;
            }

            public void setTransactionType(String transactionType) {
                this.transactionType = transactionType;
            }

            public String getTransactionChannel() {
                return transactionChannel;
            }

            public void setTransactionChannel(String transactionChannel) {
                this.transactionChannel = transactionChannel;
            }

            public long getTransactionTime() {
                return transactionTime;
            }

            public void setTransactionTime(long transactionTime) {
                this.transactionTime = transactionTime;
            }

            public double getTransactionAmount() {
                return transactionAmount;
            }

            public void setTransactionAmount(double transactionAmount) {
                this.transactionAmount = transactionAmount;
            }
        }
    }
}
