package com.example.lenovo.taoshop.bean.common;


import java.util.List;

public class CommentsResult {
	private long itemId;// 商品id
	private String itemPic;// 商品图片
	private String sellpoint;// 商品卖点
	private String title;// 商品标题
	private List<TbCommentsResult> userlist;// 第一条评论的内容


	public List<TbCommentsResult> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<TbCommentsResult> userlist) {
		this.userlist = userlist;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemPic() {
		return itemPic;
	}

	public void setItemPic(String itemPic) {
		this.itemPic = itemPic;
	}

	public String getSellpoint() {
		return sellpoint;
	}

	public void setSellpoint(String sellpoint) {
		this.sellpoint = sellpoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



}
