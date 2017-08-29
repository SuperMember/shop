package com.example.lenovo.taoshop.utils.mina;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

/*
* 封装mina连接api
* */
public class ConnectionManager {
    private ConnectionConfig connectionConfig;
    private WeakReference<Context> mContext;
    private NioSocketConnector nioSocketConnector;
    private IoSession session;
    private InetSocketAddress inetSocketAddress;

    public ConnectionManager(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
        this.mContext = new WeakReference<Context>(connectionConfig.getContext());
        init();
    }

    //初始化参数
    private void init() {
        // inetSocketAddress = new InetSocketAddress(connectionConfig.getIp(), connectionConfig.getPort());
        nioSocketConnector = new NioSocketConnector();
        nioSocketConnector.getSessionConfig().setReadBufferSize(connectionConfig.getReadBufferSize());
        nioSocketConnector.getFilterChain().addLast("logging", new LoggingFilter());
        nioSocketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        nioSocketConnector.setHandler(new DefaultHandler(mContext.get()));
    }

    //连接
    public boolean connect() {
        try {
            ConnectFuture future = nioSocketConnector.connect(new InetSocketAddress(connectionConfig.getIp(), connectionConfig.getPort()));
            future.awaitUninterruptibly();
            session = future.getSession();
        } catch (Exception e) {
            return false;
        }
        return session != null ? true : false;
    }

    //关闭连接
    public void disconnection() {
        nioSocketConnector.dispose();
        nioSocketConnector = null;
        session = null;
        mContext = null;
        inetSocketAddress = null;
    }

    private static class DefaultHandler extends IoHandlerAdapter {
        private static final String BROADCAST_ACTION = "com.example.lenovo.taoshop.utils.mina";
        private Context context;

        public DefaultHandler(Context context) {
            this.context = context;
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
            //保存session，从而可以发送消息给服务器
            SessionManager.getInstance().setSession(session);
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            super.sessionClosed(session);
            //发送一个消息给服务端，方便服务端断开与该客户端的连接
        }

        @Override
        public void sessionCreated(IoSession session) throws Exception {
            super.sessionCreated(session);
        }

        @Override
        public void messageSent(IoSession session, Object message) throws Exception {
            super.messageSent(session, message);
        }

        //接收消息
        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session, message);
            //利用局部广播发送消息
            Intent intent = new Intent(BROADCAST_ACTION);
            intent.putExtra("message", message.toString());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

        @Override
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
            super.exceptionCaught(session, cause);
        }
    }
}
