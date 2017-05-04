package com.deppon.uums2foss.inteface.domian.usermanagementNew;

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
