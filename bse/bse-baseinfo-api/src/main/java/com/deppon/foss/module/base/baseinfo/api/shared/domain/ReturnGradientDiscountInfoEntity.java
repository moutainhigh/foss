package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-06-03 16:39:40
 * CRM同步数据过来，不管成功失败返回的信息
 * 
 */
@XmlRootElement(name = "responseMsg")
public class ReturnGradientDiscountInfoEntity implements Serializable{

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
