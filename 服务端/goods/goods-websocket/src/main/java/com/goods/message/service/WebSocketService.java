package com.goods.message.service;

import com.goods.manager.pojo.TbMessage;
import com.goods.tools.common.util.TaotaoResult;

public interface WebSocketService {
	TaotaoResult save(TbMessage tbMessage);

	TbMessage get(Long userId);

	TaotaoResult delete(Long userId);

	TaotaoResult update(TbMessage tbMessage);
}
