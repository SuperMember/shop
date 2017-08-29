package com.example.lenovo.taoshop.bean.common;

import java.util.List;

public class TbCommentsResult extends TbComments {
	private List<TbCommentsReply> replylist;

	public List<TbCommentsReply> getReplylist() {
		return replylist;
	}

	public void setReplylist(List<TbCommentsReply> replylist) {
		this.replylist = replylist;
	}
}
