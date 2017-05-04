package com.deppon.foss.module.transfer.agent.server.service;

import java.io.Serializable;

public class AgentException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 8737144098356611844L;

	private String bizMessage;
	
	private ExceptionTypeEnum exceptionTypeEnum;
	
	public AgentException() {
	}

	public AgentException(String arg0) {
		super(arg0);
	}
	
	public AgentException(String bizMessage, ExceptionTypeEnum exceptionTypeEnum) {
		this.bizMessage = bizMessage;
		this.exceptionTypeEnum = exceptionTypeEnum;
	}

	public AgentException(Throwable arg0) {
		super(arg0);
	}

	public AgentException(String arg0, Throwable throwable) {
		super(arg0, throwable);
	}

	public String getBizMessage() {
		return bizMessage;
	}

	public void setBizMessage(String bizMessage) {
		this.bizMessage = bizMessage;
	}

	public ExceptionTypeEnum getExceptionTypeEnum() {
		return exceptionTypeEnum;
	}

	public void setExceptionTypeEnum(ExceptionTypeEnum exceptionTypeEnum) {
		this.exceptionTypeEnum = exceptionTypeEnum;
	}
}
