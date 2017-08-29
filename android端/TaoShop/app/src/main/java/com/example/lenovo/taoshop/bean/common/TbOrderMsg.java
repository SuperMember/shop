package com.example.lenovo.taoshop.bean.common;

import java.util.Date;

public class TbOrderMsg {
    private Integer id;

    private Long userid;

    private String msg;

    private Long orderid;

    private String pic;

    private String title;

    private Long logisticsid;

    private Long muserid;

    private Date time;

    private String company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getLogisticsid() {
        return logisticsid;
    }

    public void setLogisticsid(Long logisticsid) {
        this.logisticsid = logisticsid;
    }

    public Long getMuserid() {
        return muserid;
    }

    public void setMuserid(Long muserid) {
        this.muserid = muserid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }
}