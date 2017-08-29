package com.example.lenovo.taoshop.bean.common;

import java.util.Date;

public class TbCommentsReply {
    private Long id;

    private String replycomments;

    private Date replytime;

    private Long parentid;

    private Date updated;

    private String pic;

    private Long itemId;

    private Long replyid;

    private Long userid;

    private String name;

    private String replyname;

    private Long muserid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReplycomments() {
        return replycomments;
    }

    public void setReplycomments(String replycomments) {
        this.replycomments = replycomments == null ? null : replycomments.trim();
    }

    public Date getReplytime() {
        return replytime;
    }

    public void setReplytime(Date replytime) {
        this.replytime = replytime;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getReplyid() {
        return replyid;
    }

    public void setReplyid(Long replyid) {
        this.replyid = replyid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getReplyname() {
        return replyname;
    }

    public void setReplyname(String replyname) {
        this.replyname = replyname == null ? null : replyname.trim();
    }

    public Long getMuserid() {
        return muserid;
    }

    public void setMuserid(Long muserid) {
        this.muserid = muserid;
    }
}