package com.dev.app.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.dev.app.common.context.ApplicationContextProvider;
import com.dev.app.common.context.request.RequestContextThreadLocalHolder;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ContextUtil {

    private static ApplicationContext getContext() {
	return ApplicationContextProvider.getContext();
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
	if (getContext() == null) {
	    return null;
	}

	try {
	    return getContext().getBean(beanName, clazz);
	} catch (BeansException e) {
	    log.error("[GetBean] Exception [{}]{}", e.getMessage(), e.toString());
	    return null;
	}
    }

    public static Object getBean(String beanName) {
	if (getContext() == null) {
	    return null;
	}

	try {
	    return getContext().getBean(beanName);
	} catch (BeansException e) {
	    log.error("[GetBean] Exception [{}]{}", e.getMessage(), e.toString());
	    return null;
	}
    }

    public static <T> T getBean(Class<T> clazz) {
	if (getContext() == null) {
	    return null;
	}

	try {
	    return getContext().getBean(clazz);
	} catch (BeansException e) {
	    log.error("[GetBean] Exception [{}]{}", e.getMessage(), e.toString());
	    return null;
	}
    }

    public static void setThreadLocal(String key, String val) {
	RequestContextThreadLocalHolder.setValue(key, val);
    }

    public static String getThreadLocal(String key) {
	return RequestContextThreadLocalHolder.getValue(key);
    }
}
