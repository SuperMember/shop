package com.example.lenovo.taoshop.bean.common;

import java.io.Serializable;

public class TbAddr implements Serializable{
    private Long addrid;

    private Long muserid;

    private String name;

    private String tel;

    private String addr;

    private Integer defaultaddr;

    private String area;

    public Long getAddrid() {
        return addrid;
    }

    public void setAddrid(Long addrid) {
        this.addrid = addrid;
    }

    public Long getMuserid() {
        return muserid;
    }

    public void setMuserid(Long muserid) {
        this.muserid = muserid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Integer getDefaultaddr() {
        return defaultaddr;
    }

    public void setDefaultaddr(Integer defaultaddr) {
        this.defaultaddr = defaultaddr;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    @Override
    public String toString() {
        return "TbAddr{" +
                "addrid=" + addrid +
                ", muserid=" + muserid +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", defaultaddr=" + defaultaddr +
                ", area='" + area + '\'' +
                '}';
    }
}