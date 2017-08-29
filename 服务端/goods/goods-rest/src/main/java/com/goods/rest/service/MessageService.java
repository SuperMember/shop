package com.goods.rest.service;

import com.goods.tools.common.util.TaotaoResult;

public interface MessageService {
	TaotaoResult getMessage(long userId, Integer page, int type);
}
