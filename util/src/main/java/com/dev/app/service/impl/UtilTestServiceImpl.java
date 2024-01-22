package com.dev.app.service.impl;

import org.springframework.stereotype.Service;

import com.dev.app.service.UtilTestService;
import com.dev.app.util.ContextUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UtilTestServiceImpl implements UtilTestService {
    @Override
    public void setThreadLocal(String key, String val) {
	// TODO Auto-generated method stub
	ContextUtil.setThreadLocal(key, val);

    }

    @Override
    public String getThreadLocal(String key) {
	// TODO Auto-generated method stub
	return ContextUtil.getThreadLocal(key);
    }

}
