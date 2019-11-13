package com.example.kuma.myapplication.data;

import java.io.Serializable;

/**
 * Created by Kuma on 2018-03-03.
 * 데모 정보
 */

public class ScheduleInfo implements Serializable {
    public String id = "";
    public String start = "";
    public String end = "";
    public String description = "";
    public String memberPk = "";
    public String memberName = "";
    public String title = "";
    public String color = "";

//    id : 스케쥴 고유번호
//  - start : 시작일
//  - end : 종료일
//  - title : 데모명
//  - description : 비고

    public int pk = -1;
    public String kind = "";
    public String state = "";
    public String productPk = "";
    public String startDate = "";
    public String endDate = "";
    public int destinationPk = -1;
    public int agencyPk = -1;
    public int receiver = -1;
    public String message = "";
    public String serialNo = "";

//    - pk : 데모고유번호
//  - kind : 종류
//  - state : 상태
//  - productPk : 제품고유번호
//  - startDate : 시작일
//  - endDate : 종료일
//  - agencyPk : 수신자 대리점번호(0 : 본사)
//  - receiver : 수신자고유번호
//  - message : 비고


    public int getDestinationPk() {
        return destinationPk;
    }

    public void setDestinationPk(int destinationPk) {
        this.destinationPk = destinationPk;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductPk() {
        return productPk;
    }

    public void setProductPk(String productPk) {
        this.productPk = productPk;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAgencyPk() {
        return agencyPk;
    }

    public void setAgencyPk(int agencyPk) {
        this.agencyPk = agencyPk;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemberPk() {
        return memberPk;
    }

    public void setMemberPk(String memberPk) {
        this.memberPk = memberPk;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
