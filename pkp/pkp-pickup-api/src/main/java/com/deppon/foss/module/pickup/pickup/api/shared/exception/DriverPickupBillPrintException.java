package com.deppon.foss.module.pickup.pickup.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 *司机接货单号打印
 *
 */
public class DriverPickupBillPrintException extends BusinessException {

	//序列化版本号
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @param code  exception code
	 */
	public DriverPickupBillPrintException(String code) {
		super();
		this.errCode = code;
	}

}