package com.example.lenovo.taoshop.rxbus.event;

/**
 * Created by lenovo on 2017  五月  20  0020.
 */

public class AddrEvent {
    private String phone;
    private String username;
    private String area;
    private Status status;

    public enum Status {
        PAY, SEND, UNSEND, FINISH;//付款，未发货，已发货，完成交易
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AddrEvent() {
    }

    public AddrEvent(Status status) {
        this.status = status;
    }

    public AddrEvent(String phone, String username, String area) {
        this.phone = phone;
        this.username = username;
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
