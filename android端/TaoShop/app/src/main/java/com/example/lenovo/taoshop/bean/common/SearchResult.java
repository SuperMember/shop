package com.example.lenovo.taoshop.bean.common;

import java.util.List;

public class SearchResult {

	//商品列表
	private List<?> itemList;
	//总记录数
	private long total;
	//总页数
	private long pageCount;
	//当前页
	private long curPage;
	
	
	public List<?> getItemList() {
		return itemList;
	}
	public void setItemList(List<?> itemList) {
		this.itemList = itemList;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
	
}
