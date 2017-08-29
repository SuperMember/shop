package com.example.lenovo.taoshop.bean.common;

/**
 * Created by lenovo on 2017  五月  08  0008.
 */

public class ItemDetail {
    private String content;//商品详细的图片
    private TbComments tbComment;//有关的评论

    public TbComments getTbComment() {
        return tbComment;
    }

    public void setTbComment(TbComments tbComment) {
        this.tbComment = tbComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
