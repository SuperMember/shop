package com.goods.manager.pojo;

import java.util.List;

public class IndexDataEntity {
	private int ordernum;// 订单总数量
	private int finish;// 订单完成数量
	private int close;// 订单关闭数量
	private long income;// 总收入
	private int lastmoneyorder;// 近一个月的订单数
	private List<TbCommentsResult> list;// 最新评论的前十条
	private List<TbGoodsRank> ranks;// 畅销榜

	public List<TbGoodsRank> getRanks() {
		return ranks;
	}

	public void setRanks(List<TbGoodsRank> ranks) {
		this.ranks = ranks;
	}

	public int getLastmoneyorder() {
		return lastmoneyorder;
	}

	public void setLastmoneyorder(int lastmoneyorder) {
		this.lastmoneyorder = lastmoneyorder;
	}

	public int getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public int getClose() {
		return close;
	}

	public void setClose(int close) {
		this.close = close;
	}

	public long getIncome() {
		return income;
	}

	public void setIncome(long income) {
		this.income = income;
	}

	public List<TbCommentsResult> getList() {
		return list;
	}

	public void setList(List<TbCommentsResult> list) {
		this.list = list;
	}

}
