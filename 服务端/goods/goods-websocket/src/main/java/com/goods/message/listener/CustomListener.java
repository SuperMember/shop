package com.goods.message.listener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import com.goods.websocket.handler.MyWebSocketHandler;

public class CustomListener implements MessageListener {

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	public void onMessage(Message message) {
		ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
		// 0表示新评论，1表示回复，2表示新订单
		try {
			TextMessage textMessage = null;
			String text = activeMQTextMessage.getText();
			// 截取出卖家id
			String[] userId = text.split(":");
			if (userId[1].equals("0")) {
				textMessage = new TextMessage("评论");
			} else if (userId[1].equals("1")) {
				textMessage = new TextMessage("回复");
			} else {
				textMessage = new TextMessage("订单");
			}
			try {
				webSocketHandler.broadcaseTopersonal(textMessage, userId[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JMSException e1) {
			e1.printStackTrace();
		}

	}

}
