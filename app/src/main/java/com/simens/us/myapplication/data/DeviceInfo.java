package com.simens.us.myapplication.data;

import com.simens.us.myapplication.Constance.Constance;

import java.io.Serializable;

/**
 * Created by Kuma on 2018-02-22.
 */

public class DeviceInfo implements Serializable {
    public String pk = "";
    public String serialNo = "";
    public String productCode = "";
    public String state = "";
    public String makeYear = "";
    public String version = "";
    public String message = "";
    public String insertTime = "";
    public String updateTime = "";

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStringToState(){
        return Constance.changeStringToState(this.state);
    }
}
