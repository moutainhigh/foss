package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseMsg")
public class ReturnGoodsResponseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1703322714880354377L;
	
	private boolean ifSuccess;
	
	private String errorMsg;

	public boolean isIfSuccess() {
		return ifSuccess;
	}

	public void setIfSuccess(boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
