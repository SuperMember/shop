package com.example.lenovo.taoshop.bean.common;

import java.util.List;


public class UserEntity extends TbUser{
	List<TbAddr> tbAddrs;

	public List<TbAddr> getTbAddrs() {
		return tbAddrs;
	}

	public void setTbAddrs(List<TbAddr> tbAddrs) {
		this.tbAddrs = tbAddrs;
	}

}
