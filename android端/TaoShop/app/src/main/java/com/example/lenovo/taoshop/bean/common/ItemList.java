package com.example.lenovo.taoshop.bean.common;

import java.io.Serializable;

/**
 * Created by lenovo on 2017  五月  07  0007.
 */

public class ItemList implements Serializable {
    private String id;

    private String title;

    private String sellPoint;

    private int price;

    private String image;

    private String cidname;

    private String item_des;

    private int num;

    private int status;

    private Long muser_id;

    public Long getMuser_id() {
        return muser_id;
    }

    public void setMuser_id(Long muser_id) {
        this.muser_id = muser_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getSellPoint() {
        return this.sellPoint;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setCidname(String cidname) {
        this.cidname = cidname;
    }

    public String getCidname() {
        return this.cidname;
    }

    public void setItem_des(String item_des) {
        this.item_des = item_des;
    }

    public String getItem_des() {
        return this.item_des;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", sellPoint='" + sellPoint + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", cidname='" + cidname + '\'' +
                ", item_des='" + item_des + '\'' +
                ", num=" + num +
                ", status=" + status +
                ", muserId=" + muser_id +
                '}';
    }
}
