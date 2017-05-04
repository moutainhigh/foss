package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 供应商接口的响应
 * @author 232607 
 * @date 2015-12-21 下午5:25:34
 * @since
 * @version
 */
@XmlRootElement(name="SupplierResponse")
public class SupplierResponse {
	/**
	 * 是否成功
	 */
	private boolean isSuccess;
	/**
	 * 信息
	 */
	private String message;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
