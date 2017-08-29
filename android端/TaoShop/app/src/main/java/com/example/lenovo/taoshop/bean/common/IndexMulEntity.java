package com.example.lenovo.taoshop.bean.common;

import android.support.annotation.IntDef;

import com.dl7.recycler.entity.MultiItemEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  22  0022.
 */

public class IndexMulEntity extends MultiItemEntity {
    public static final int RECOMMEND_ITEM = 0x1;
    public static final int SPECIAL_ITEM = 0x2;
    public static final int GOOD_OTHER_ITEM = 0x3;
    private List<TbItem> tbItems;
    private List<TbItemResult> itemResults;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TbItemResult> getItemResults() {
        return itemResults;
    }

    public void setItemResults(List<TbItemResult> itemResults) {
        this.itemResults = itemResults;
    }

    public IndexMulEntity(@NewsItemType int itemType) {
        super(itemType);
    }

    public IndexMulEntity(@NewsItemType int itemType, List<TbItem> tbItems, String title) {
        super(itemType);
        this.tbItems = tbItems;
        this.title = title;
    }

    public List<TbItem> getTbItems() {
        return tbItems;
    }

    public void setTbItems(List<TbItem> tbItems) {
        this.tbItems = tbItems;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RECOMMEND_ITEM, SPECIAL_ITEM, GOOD_OTHER_ITEM})
    public @interface NewsItemType {
    }
}
