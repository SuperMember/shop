package com.goods.mina.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

//mina框架的session保存和管理
public class SessionManager {
	private static SessionManager sessionManager;

	private SessionManager() {
	}

	private Map<Long, IoSession> map = new HashMap<Long, IoSession>();// 保存所有连接的客户端的session

	public static SessionManager getInstance() {
		if (sessionManager == null) {
			synchronized (SessionManager.class) {
				if (sessionManager == null) {
					sessionManager = new SessionManager();
				}
			}
		}
		return sessionManager;
	}

	public void setSession(IoSession ioSession, long userId) {
		map.put(userId, ioSession);
	}

	// 向客户端写信息
	public void writeToClient(Object o, long userId) {
		if (map.get(userId) == null) {
			// 保存至数据库中
		} else {
			map.get(userId).write(o);
		}
	}

	// 移出session
	public void deleteSession(long userId) {
		map.remove(userId);
	}
}
