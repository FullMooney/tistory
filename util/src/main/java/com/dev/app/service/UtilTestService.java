package com.dev.app.service;

public interface UtilTestService {

    void setThreadLocal(String key, String val);

    String getThreadLocal(String key);
}
