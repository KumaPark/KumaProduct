package com.simens.us.myapplication.data;

import com.simens.us.myapplication.Constance.Constance;

import java.io.Serializable;

/**
 * Created by Kuma on 2018-12-12.
 */

public class DeviceSummary implements Serializable {

    public String R = "";
    public String N = "";
    public String MO = "";
    public String DM = "";
    public String MH = "";
    public String T = "";
    public String F = "";
    public String D = "";
    public String S = "";

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getMO() {
        return MO;
    }

    public void setMO(String MO) {
        this.MO = MO;
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getMH() {
        return MH;
    }

    public void setMH(String MH) {
        this.MH = MH;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }
}
