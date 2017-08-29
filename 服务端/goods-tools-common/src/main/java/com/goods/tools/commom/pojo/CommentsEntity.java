package com.goods.tools.commom.pojo;


//买家评论实体
//包含买家的评论，买家部分信息和商品信息
public class CommentsEntity {
	String comments;//评论内容
	String time;//评论时间
	String buyername;//买家名称
	String goodname;//货物名称
	String goodprice;//货物价格
	String degree;//评论等级:好评，中评，差评
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBuyername() {
		return buyername;
	}
	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}
	public String getGoodname() {
		return goodname;
	}
	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}
	public String getGoodprice() {
		return goodprice;
	}
	public void setGoodprice(String goodprice) {
		this.goodprice = goodprice;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	

}
