package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author 310970
 */
//@XmlRootElement(name = "responseMsg")
public class ReturnGreenHandWrapEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

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
