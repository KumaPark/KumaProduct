package com.simens.us.myapplication.data;

import com.simens.us.myapplication.Constance.Constance;

import java.io.Serializable;

/**
 * Created by Kuma on 2018-02-22.
 */

public class DeviceInfo implements Serializable {
    public String pk = "";
    //제품종류 (M : 장비, P : 프로브 , A : 악세사리)
    public String kind = "";
    //제품번호
    public String serialNo = "";
    //모델번호
    public String modelPk = "";
    //데모번호 - 제품과 연결된 데모고유번호 (진행중이거나 종료 된 데모)
    public String demoPk = "";
    //제품상태
    public String state = "";
    //OS버젼 (장비인 경우에만 해당)
    public String osPk = "";
    //제조일 (장비인경우 : YYYY-MM-DD 형식, 그외 : YYYY형식)
    public String makeDate = "";
    //비고
    public String message = "";
    //모델명
    public String modelName = "";
    //    모델버젼(장비인경우)
    public String modelVersion = "";
    //모델색상 (장비인경우)
    public String modelColor = "";

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getModelPk() {
        return modelPk;
    }

    public void setModelPk(String modelPk) {
        this.modelPk = modelPk;
    }

    public String getDemoPk() {
        return demoPk;
    }

    public void setDemoPk(String demoPk) {
        this.demoPk = demoPk;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOsPk() {
        return osPk;
    }

    public void setOsPk(String osPk) {
        this.osPk = osPk;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelColor() {
        return modelColor;
    }

    public void setModelColor(String modelColor) {
        this.modelColor = modelColor;
    }
}
