package com.example.lenovo.taoshop.bean.common;

import java.util.Date;

import rx.Subscription;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public class BuyGoods {
    private long id;
    private String title;
    private long price;
    private int num;
    private String image;
    private long cid;
    private long muser_id;
    private Long created;
    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getMuser_id() {
        return muser_id;
    }

    public void setMuser_id(long muser_id) {
        this.muser_id = muser_id;
    }

    @Override
    public String toString() {
        return "BuyGoods{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", image='" + image + '\'' +
                ", cid=" + cid +
                ", muser_id=" + muser_id +
                ", created='" + created + '\'' +
                '}';
    }
}
