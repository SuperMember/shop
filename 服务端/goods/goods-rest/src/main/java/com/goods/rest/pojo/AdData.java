package com.goods.rest.pojo;

import java.util.List;

import com.goods.manager.pojo.TbItem;
import com.goods.manager.pojo.TbItemResult;

public class AdData {
	private int total;
	private List<AdItem> ads;
	private List<TbItem> phones;
	private List<TbItem> cps;
	private List<TbItemResult> news;
	private List<TbItem> hometool;
	private List<TbItemResult> others;
	public List<TbItemResult> getOthers() {
		return others;
	}
	public void setOthers(List<TbItemResult> others) {
		this.others = others;
	}
	public List<TbItem> getHometool() {
		return hometool;
	}
	public void setHometool(List<TbItem> hometool) {
		this.hometool = hometool;
	}
	public List<TbItemResult> getNews() {
		return news;
	}
	public void setNews(List<TbItemResult> news) {
		this.news = news;
	}
	public List<TbItem> getPhones() {
		return phones;
	}
	public void setPhones(List<TbItem> phones) {
		this.phones = phones;
	}
	public List<TbItem> getCps() {
		return cps;
	}
	public void setCps(List<TbItem> cps) {
		this.cps = cps;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<AdItem> getAds() {
		return ads;
	}
	public void setAds(List<AdItem> ads) {
		this.ads = ads;
	}
}
