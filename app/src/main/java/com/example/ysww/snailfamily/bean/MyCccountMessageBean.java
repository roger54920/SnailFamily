package com.example.ysww.snailfamily.bean;

/**
 * Created by me-jie on 2017/7/7.
 */

public class MyCccountMessageBean extends BaseBean {

    /**
     * account : {"id":"cec206e1183242c0b975c09f2d109c7e","amount":0,"income":0,"pay":0}
     */

    private AccountBean account;

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public static class AccountBean {
        /**
         * id : cec206e1183242c0b975c09f2d109c7e
         * amount : 0.0
         * income : 0.0
         * pay : 0.0
         */

        private String id;
        private double amount;
        private double income;
        private double pay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public double getPay() {
            return pay;
        }

        public void setPay(double pay) {
            this.pay = pay;
        }
    }
}
