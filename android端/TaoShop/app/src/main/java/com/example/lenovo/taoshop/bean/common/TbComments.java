package com.example.lenovo.taoshop.bean.common;

import java.util.Date;

public class TbComments {
    private Long id;

    private Long muserId;

    private String comments;

    private Long time;

    private String degree;

    private String buyername;

    private String goodname;

    private Long goodprice;

    private Long created;

    private Long updated;

    private String pic;

    private Long itemid;

    private Long soldid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMuserId() {
        return muserId;
    }

    public void setMuserId(Long muserId) {
        this.muserId = muserId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername == null ? null : buyername.trim();
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname == null ? null : goodname.trim();
    }

    public Long getGoodprice() {
        return goodprice;
    }

    public void setGoodprice(Long goodprice) {
        this.goodprice = goodprice;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Long getSoldid() {
        return soldid;
    }

    public void setSoldid(Long soldid) {
        this.soldid = soldid;
    }

    @Override
    public String toString() {
        return "TbComments{" +
                "id=" + id +
                ", muserId=" + muserId +
                ", comments='" + comments + '\'' +
                ", time=" + time +
                ", degree='" + degree + '\'' +
                ", buyername='" + buyername + '\'' +
                ", goodname='" + goodname + '\'' +
                ", goodprice=" + goodprice +
                ", created=" + created +
                ", updated=" + updated +
                ", pic='" + pic + '\'' +
                ", itemid=" + itemid +
                ", soldid=" + soldid +
                '}';
    }
}