package com.goods.search.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.goods.search.service.ItemService;

//activemq接受类
public class CustomListener implements MessageListener {

	@Autowired
	private ItemService itemService;

	public void onMessage(Message message) {
		ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
		try {
			String result = activeMQTextMessage.getText();
			if (result != null && !StringUtils.isEmpty(result)) {
				String[] strings = result.split(":");
				if (strings[0].equals("up")) {
					// 上架
					// 从搜索中添加
					itemService.addItem(Long.parseLong(strings[1]));
				} else {
					// 下架
					// 从搜索中删除
					itemService.removeItem(Long.parseLong(strings[1]));
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
