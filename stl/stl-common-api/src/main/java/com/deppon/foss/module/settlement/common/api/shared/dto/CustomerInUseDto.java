package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;

/**
 * @author 曾斌文
 * @desc 查看客户是否在使用
 * @date 2013-05-09
 */
public class CustomerInUseDto implements Serializable {

	/**
	 * 序列化
	 * 
	 */
	private static final long serialVersionUID = -2438990473034551975L;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 是否在使用
	 */
	private boolean inUse;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

}
