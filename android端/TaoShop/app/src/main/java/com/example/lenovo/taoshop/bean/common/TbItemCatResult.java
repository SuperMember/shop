package com.example.lenovo.taoshop.bean.common;

import java.util.List;

public class TbItemCatResult{
	private TbItemCat tbItemCat;
	public TbItemCat getTbItemCat() {
		return tbItemCat;
	}

	public void setTbItemCat(TbItemCat tbItemCat) {
		this.tbItemCat = tbItemCat;
	}

	private List<TbItemCat> list;

	public List<TbItemCat> getList() {
		return list;
	}

	public void setList(List<TbItemCat> list) {
		this.list = list;
	}

}
