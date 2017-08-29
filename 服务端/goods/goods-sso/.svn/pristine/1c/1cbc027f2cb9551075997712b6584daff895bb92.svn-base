package com.goods.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.manager.pojo.Muser;
import com.goods.manager.pojo.TbAddr;
import com.goods.manager.pojo.TbUser;
import com.goods.tools.common.util.TaotaoResult;

public interface UserService {
	TaotaoResult login(String username,String password,HttpServletRequest request, HttpServletResponse response,Integer user);//type������Һ�����
	TaotaoResult register(TbUser tbUser);
	TaotaoResult checkDatas(String datas,int type);
	
	//修改地址
	TaotaoResult editAddr(TbAddr tbAddr);
	//删除地址
	TaotaoResult deleteAddr(Long id);
	//新增地址
	TaotaoResult addAddr(TbAddr tbAddr);
}
