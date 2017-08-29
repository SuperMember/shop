package com.goods.manager.message.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

//activemq接受类
public class CustomListener implements MessageListener {

	public void onMessage(Message message) {
		ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
		try {
			System.out.println(activeMQTextMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
