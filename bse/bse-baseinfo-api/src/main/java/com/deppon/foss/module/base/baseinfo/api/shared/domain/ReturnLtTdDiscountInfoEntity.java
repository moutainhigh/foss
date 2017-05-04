package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author 261997 CSS
 * @date 2015-08-21 10:39:40
 * CRM同步数据过来，不管成功失败返回的信息
 * 
 */
@XmlRootElement(name = "responseMsg")
public class ReturnLtTdDiscountInfoEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8799787897897L;

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
