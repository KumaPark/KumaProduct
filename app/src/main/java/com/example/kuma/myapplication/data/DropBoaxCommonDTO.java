package com.example.kuma.myapplication.data;

import java.io.Serializable;

public class DropBoaxCommonDTO implements Serializable {
    public int pk = -1;
    public String name = "";
//제품종류(M : 장비, P : 프로브 , A : 악세사리)
    public String modelKind = "";
    //제품고유번호
    public int productPk = -1;
    //제품정보 - 모델 버젼 및 시리얼 번호 ex) [NX Elite VA11] 10000001
    public String content = "";


    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelKind() {
        return modelKind;
    }

    public void setModelKind(String modelKind) {
        this.modelKind = modelKind;
    }

    public int getProductPk() {
        return productPk;
    }

    public void setProductPk(int productPk) {
        this.productPk = productPk;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
