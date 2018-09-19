package com.example.ysww.snailfamily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by me-jie on 2017/6/28.
 */

public class SearchCourierNumberBean extends BaseBean {

    /**
     * track : [{"pDate":1497353624000,"pContent":"【浙江省杭州市秋涛路公司】 已打包"},{"pDate":1497353624000,"pContent":"【浙江省杭州市秋涛路公司】 已收件"},{"pDate":1497354083000,"pContent":"【浙江省杭州市秋涛路公司】 已发出 下一站 【杭州转运中心】"},{"pDate":1497357634000,"pContent":"【杭州转运中心】 已收入"},{"pDate":1497357949000,"pContent":"【杭州转运中心】 已发出 下一站 【北京转运中心】"},{"pDate":1497451563000,"pContent":"【萧山机场公司】 已收入"},{"pDate":1497485358000,"pContent":"【北京转运中心】 已收入"},{"pDate":1497486094000,"pContent":"【北京转运中心】 已发出 下一站 【北京市海淀区苏州街公司】"},{"pDate":1497503067000,"pContent":"【北京市海淀区苏州街公司】 已收入"},{"pDate":1497503677000,"pContent":"【北京市海淀区苏州街公司】 派件人: 刘蓬 派件中 派件员电话"},{"pDate":1497512431000,"pContent":"客户 签收人: 本人签收 已签收 感谢使用圆通速递，期待再次为您服务"}]
     * shipperCode : YTO
     * type : 1
     * lgisticCode : 885406170650335614
     */

    private String shipperCode;
    private String type;
    private String lgisticCode;
    private List<TrackBean> track;

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLgisticCode() {
        return lgisticCode;
    }

    public void setLgisticCode(String lgisticCode) {
        this.lgisticCode = lgisticCode;
    }

    public List<TrackBean> getTrack() {
        return track;
    }

    public void setTrack(List<TrackBean> track) {
        this.track = track;
    }

    public static class TrackBean implements Serializable {
        /**
         * pDate : 1497353624000
         * pContent : 【浙江省杭州市秋涛路公司】 已打包
         */

        private long pDate;
        private String pContent;

        public long getPDate() {
            return pDate;
        }

        public void setPDate(long pDate) {
            this.pDate = pDate;
        }

        public String getPContent() {
            return pContent;
        }

        public void setPContent(String pContent) {
            this.pContent = pContent;
        }
    }
}
