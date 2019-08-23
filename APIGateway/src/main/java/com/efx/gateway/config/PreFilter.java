package com.efx.gateway.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
 
public class PreFilter extends ZuulFilter {
 
	ObjectMapper objectMapper =new ObjectMapper();
  @Override
  public String filterType() {
    return "pre";
  }
 
  @Override
  public int filterOrder() {
    return 1;
  }
 
  @Override
  public boolean shouldFilter() {
      RequestContext ctx = RequestContext.getCurrentContext();
      System.out.println("URL "+ctx.getRequest().getRequestURI());
	     if(ctx.getRequest().getRequestURI().contains("/EFXUserManagement/images/")) {
	    	 System.out.println("URL "+ctx.getRequest().getRequestURI().contains("/EFXUserManagement/images/"));
	    	 return false;
	     }
	    return true;
  }
 
  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    
    if(request.getHeader("deviceType")==null) {
    	ctx.setResponseStatusCode(423);
    	try {
			ctx.setResponseBody(objectMapper.writeValueAsString(setDeviceTypeNotFoundResponse()));
		} catch (JsonProcessingException e) {
			ctx.setResponseBody("");
			e.printStackTrace();
		}
        ctx.setSendZuulResponse(false);
    }
    System.out.println("Request Method : " + request.getHeader("Content-Type") + " Request URL : " + request.getRequestURL().toString());
    return null;
  }
  
  private  Map<String, Object> setDeviceTypeNotFoundResponse() {
	  Map<String, Object> map =new HashMap<String,Object>();
	  map.put("message", "Missing device type.");
	    return map;
 }
  
}
