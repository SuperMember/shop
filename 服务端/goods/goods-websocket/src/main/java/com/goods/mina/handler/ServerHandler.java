package com.goods.mina.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.goods.mina.session.SessionManager;

public class ServerHandler extends IoHandlerAdapter {
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		System.out.println("sessionCreated");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		System.out.println("sessionOpened");
	}

	// 消息接收
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		System.out.println("mina:" + message.toString());
		// 客户端连接服务端后返回一个userId,方便服务端向特定客户端发送消息
		// 保存session
		String id = (String) message;
		Long userId = Long.parseLong(id);
		SessionManager.getInstance().setSession(session, userId);// 保存
	}

	// 消息发送
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		System.out.println("sessionClosed");

	}
}
