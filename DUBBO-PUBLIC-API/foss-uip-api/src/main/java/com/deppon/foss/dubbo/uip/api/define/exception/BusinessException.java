package com.deppon.foss.dubbo.uip.api.define.exception;

import java.io.Serializable;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:业务异常基类</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2011-4-9 steven.cheng 新增
* </div>  
********************************************
 */
public class BusinessException extends RuntimeException implements Serializable, IException {
	
	private static final long serialVersionUID = 1937263904748419090L;
	
	protected String errCode;
	
	private String natvieMsg;
	
	private Object[] arguments;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public BusinessException(String code, String msg) {
		super(msg);
		this.errCode = code;
	}
	
	public BusinessException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = code;
	}
	
	public BusinessException(String code, String msg, String natvieMsg) {
		super(msg);
		this.errCode = code;
		this.natvieMsg = natvieMsg;
	}
	
	public BusinessException(String code, String msg,String natvieMsg, Throwable cause) {
		super(msg, cause);
		this.errCode = code;
		this.natvieMsg = natvieMsg;
	}
	
	public BusinessException(String code,Object... args) {
		super();
		this.errCode = code;
		this.arguments = args;
	}
	
	public BusinessException(String code,String msg, Object... args) {
		super(msg);
		this.errCode = code;
		this.arguments = args;
	}
	
	@Override
	public void setErrorArguments(Object... args) {
		this.arguments = args;
	}
	
	@Override
	public Object[] getErrorArguments() {
		return this.arguments;
	}
	
	@Override
	public String getErrorCode() {
		return this.errCode;
	}
	
	@Override
	public String getNativeMessage() {
		// TODO
		return natvieMsg;
	}
	
}
