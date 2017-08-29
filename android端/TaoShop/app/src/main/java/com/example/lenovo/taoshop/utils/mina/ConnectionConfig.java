package com.example.lenovo.taoshop.utils.mina;

import android.content.Context;


/*
* 构建者模式
* */
public class ConnectionConfig {
    private Context context;
    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeOut;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public static class Builder {
        private Context context;
        private String ip;
        private int port;
        private int readBufferSize = 10240;
        private long connectionTimeOut = 10000;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setReadBUfferSize(int readBufferSize) {
            this.readBufferSize = readBufferSize;
            return this;
        }

        public Builder setConnectionTimeOut(int connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
            return this;
        }

        private void applyConfig(ConnectionConfig connectionConfig) {
            connectionConfig.context = this.context;
            connectionConfig.connectionTimeOut = this.connectionTimeOut;
            connectionConfig.ip = this.ip;
            connectionConfig.port = this.port;
            connectionConfig.readBufferSize = this.readBufferSize;
        }

        public ConnectionConfig builder() {
            ConnectionConfig connectionConfig = new ConnectionConfig();
            applyConfig(connectionConfig);
            return connectionConfig;
        }
    }
}
