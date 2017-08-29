package com.example.lenovo.taoshop.utils.mina;

import org.apache.mina.core.session.IoSession;

/**
 * Created by lenovo on 2017  五月  25  0025.
 */

public class SessionManager {
    public static SessionManager manager;
    private IoSession session;

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        if (manager == null) {
            synchronized (SessionManager.class) {
                manager = new SessionManager();
            }
        }
        return manager;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public void writeToService(Object o) {
        if (session != null) {
            session.write(o);
        }
    }

    public void disconnect() {
        if (session != null) {
            session = null;
        }
    }
}
