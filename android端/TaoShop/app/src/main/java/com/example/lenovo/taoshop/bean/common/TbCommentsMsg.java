package com.example.lenovo.taoshop.bean.common;

import java.util.Date;

public class TbCommentsMsg {
    private Integer id;

    private String comment;

    private String pic;

    private String username;

    private Long userid;

    private String selfcomment;

    private Long itemid;

    private Date time;

    private Long muserid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getSelfcomment() {
        return selfcomment;
    }

    public void setSelfcomment(String selfcomment) {
        this.selfcomment = selfcomment == null ? null : selfcomment.trim();
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getMuserid() {
        return muserid;
    }

    public void setMuserid(Long muserid) {
        this.muserid = muserid;
    }
}