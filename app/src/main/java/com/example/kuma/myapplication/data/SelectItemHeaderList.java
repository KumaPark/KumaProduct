package com.example.kuma.myapplication.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectItemHeaderList implements Serializable {
    public String categoryName = "";
    public int type;

    public ArrayList<SelectItemInfo> invisibleChildren;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setInvisibleChildren(ArrayList<SelectItemInfo> invisibleChildren) {
        this.invisibleChildren = invisibleChildren;
    }
}
