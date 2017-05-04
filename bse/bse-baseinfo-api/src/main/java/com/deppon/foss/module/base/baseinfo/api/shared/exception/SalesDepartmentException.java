package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class SalesDepartmentException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7308301396431021444L;

	public SalesDepartmentException(String msg, Throwable cause) {
		super(msg, cause);
		
	}

	public SalesDepartmentException(String msg) {
		super(msg);
		
	}
	

}
