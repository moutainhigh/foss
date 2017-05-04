package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class EsbErrorLogging extends BaseEntity {
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	private String id;
     private String methodName;
     private String methodDesc;
     private String paramenter;
     private String errorMessage;
     private long responseTime;
     private String requestSystem;
     public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getParamenter() {
		return paramenter;
	}
	public String getMethodDesc() {
		return methodDesc;
	}
	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}
	public void setParamenter(String paramenter) {
		this.paramenter = paramenter;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public String getRequestSystem() {
		return requestSystem;
	}
	public void setRequestSystem(String requestSystem) {
		this.requestSystem = requestSystem;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	private Date requestTime;
     private Date createTime;
     
}
