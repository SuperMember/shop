package com.goods.rest.pojo;

import java.util.List;

import com.goods.manager.pojo.TbComments;

public class ItemDetail {
	private String content;//详细内容
	private TbComments tbComment;//第一条最新评论
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public TbComments getTbComment() {
		return tbComment;
	}
	public void setTbComment(TbComments tbComment) {
		this.tbComment = tbComment;
	}
	
}
