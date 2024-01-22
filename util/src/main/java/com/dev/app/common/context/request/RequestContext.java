package com.dev.app.common.context.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContext {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> map;

    public RequestContext(HttpServletRequest request, HttpServletResponse response) {

	this.request = request;
	this.response = response;
	this.map = new HashMap<>();
    }

    public HttpServletRequest getRequest() {
	return request;
    }

    public HttpServletResponse getResponse() {
	return response;
    }

    public Map<String, Object> getMap() {
	return map;
    }

}
