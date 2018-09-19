package com.example.ysww.snailfamily.bean;

/**
 * Created by me-jie on 2017/4/20.
 * 用户地址保存
 */

public class NewAddressSaveBean extends BaseBean{
    private String provincialCode;// 省份编码
    private String cityCode;// 城市编码
    private String areaCode;// 区域编码
    private String cellCode;// 小区编码
    private String houseNumber;// 楼号
    private String unit;// 单元
    private String room;// 室
    private boolean ifParcelBeAbleUsed;//电子包裹箱是否使用
    private String receiverprovincename;//收件地址省份
    private String receivercityname;//收件地址城市
    private String receiverexpareaname;//收件地址区

    public String getReceiverprovincename() {
        return receiverprovincename;
    }

    public void setReceiverprovincename(String receiverprovincename) {
        this.receiverprovincename = receiverprovincename;
    }

    public String getReceivercityname() {
        return receivercityname;
    }

    public void setReceivercityname(String receivercityname) {
        this.receivercityname = receivercityname;
    }

    public String getReceiverexpareaname() {
        return receiverexpareaname;
    }

    public void setReceiverexpareaname(String receiverexpareaname) {
        this.receiverexpareaname = receiverexpareaname;
    }

    public boolean isIfParcelBeAbleUsed() {
        return ifParcelBeAbleUsed;
    }

    public void setIfParcelBeAbleUsed(boolean ifParcelBeAbleUsed) {
        this.ifParcelBeAbleUsed = ifParcelBeAbleUsed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getIfDefault() {
        return ifDefault;
    }

    public void setIfDefault(char ifDefault) {
        this.ifDefault = ifDefault;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getIfUserParcelNo() {
        return ifUserParcelNo;
    }

    public void setIfUserParcelNo(char ifUserParcelNo) {
        this.ifUserParcelNo = ifUserParcelNo;
    }

    public String getParcelNo() {
        return parcelNo;
    }

    public void setParcelNo(String parcelNo) {
        this.parcelNo = parcelNo;
    }

    private String id;
    private char ifDefault;   //是否默认地址，1：是；0 否
    private char type;     //地址类型  1：收件 2：寄件
    private char ifUserParcelNo;   //是否使用电子包裹箱 1：是；0否
    private String parcelNo;   //电子包裹箱号


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

}
