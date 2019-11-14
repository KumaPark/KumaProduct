package com.simens.us.myapplication.data;

import java.io.Serializable;
import java.util.List;

public class SelectItemInfo implements Serializable {
    public String categoryName = "";
    public String categoryCode = "";
    public String serialNo = "";
    public String state = "";
    public boolean checked = false;
    public int type;
    public int position = 0;
    public List<SelectItemInfo> invisibleChildren;

    public SelectItemInfo() {
    }

    public SelectItemInfo(int type, String category, String serialNo) {
        this.type = type;
        this.categoryName = category;
        this.serialNo = serialNo;

    }
}
