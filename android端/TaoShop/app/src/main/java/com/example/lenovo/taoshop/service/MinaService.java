package com.example.lenovo.taoshop.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.utils.mina.ConnectionConfig;
import com.example.lenovo.taoshop.utils.mina.ConnectionManager;
import com.example.lenovo.taoshop.utils.mina.SessionManager;

/**
 * Created by lenovo on 2017  五月  25  0025.
 */

public class MinaService extends Service {
    private ConnectionThread connectionThread;

    @Override
    public void onCreate() {
        super.onCreate();
        connectionThread = new ConnectionThread("mina", MyApplication.getContext());
        connectionThread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connectionThread.disconnection();
    }

    /*
        * 调用ConnectionManager与服务器的连接
        * */
    class ConnectionThread extends HandlerThread {
        private boolean isConnection;
        private ConnectionManager connectionManager;
        private ConnectionConfig connectionConfig;

        public ConnectionThread(String name, Context context) {
            super(name);
            connectionConfig = new ConnectionConfig.Builder(context)
                    .setConnectionTimeOut(10000)
                    .setIp("192.168.0.108")
                    .setPort(9123)
                    .setReadBUfferSize(10240)
                    .builder();
            connectionManager = new ConnectionManager(connectionConfig);
        }

        /*
         * 相当于run方法，连接服务器
         * */
        @Override
        protected void onLooperPrepared() {
            for (; ; ) {
                isConnection = connectionManager.connect();//连接服务器
                if (isConnection) {
                    SessionManager.getInstance().writeToService(SharedPreferencesUtil.getInstance(MyApplication.getContext()).getInfo().getId() + "");
                    break;
                }
                //连接不成功，每隔3秒重新连接
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        public void disconnection() {
            connectionManager.disconnection();//断开连接
        }
    }
}
