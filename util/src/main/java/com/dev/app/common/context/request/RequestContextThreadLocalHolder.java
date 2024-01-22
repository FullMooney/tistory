package com.dev.app.common.context.request;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestContextThreadLocalHolder {
    private static final ThreadLocal<RequestContext> REQUEST_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setRequestContext(RequestContext requestContext) {
	REQUEST_CONTEXT_THREAD_LOCAL.set(requestContext);
    }

    public static RequestContext getRequestContext() {
	return REQUEST_CONTEXT_THREAD_LOCAL.get();
    }

    public static void remove() {
	REQUEST_CONTEXT_THREAD_LOCAL.remove();
    }

    public static void createAndSetRequestContext(HttpServletRequest request) {
	createAndSetRequestContext(request, null);
    }

    public static void createAndSetRequestContext(HttpServletRequest request, HttpServletResponse response) {
	RequestContext requestContext = new RequestContext(request, response);
	setRequestContext(requestContext);
    }

    public static HttpServletRequest getRequest() {
	RequestContext requestContext = getRequestContext();
	if (requestContext != null && requestContext.getRequest() != null) {
	    return requestContext.getRequest();
	}

	return getRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
	RequestContext requestContext = getRequestContext();
	if (requestContext != null && requestContext.getResponse() != null) {
	    return requestContext.getResponse();
	}

	return getRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes() {
	ServletRequestAttributes servletReqAttr = (ServletRequestAttributes) RequestContextHolder
		.getRequestAttributes();
	return servletReqAttr;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String key) {
	RequestContext rc = REQUEST_CONTEXT_THREAD_LOCAL.get();

	if (rc == null) {
	    createAndSetRequestContext(null, null);
	    rc = REQUEST_CONTEXT_THREAD_LOCAL.get();
	}

	Map<String, Object> map = rc.getMap();
	return (T) map.get(key);

    }

    public static <T> void setValue(String key, T val) {
	RequestContext rc = REQUEST_CONTEXT_THREAD_LOCAL.get();

	if (rc == null) {
	    createAndSetRequestContext(null, null);
	    rc = REQUEST_CONTEXT_THREAD_LOCAL.get();
	}

	Map<String, Object> map = rc.getMap();
	map.put(key, val);
    }
}
