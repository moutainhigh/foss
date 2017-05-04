package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递分部营业部关系异常类
 * @author WeiXing
 *
 */
public class ExpressBranchSalesDeptException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpressBranchSalesDeptException(String code, String msg) {
		super(code, msg);
	}

	public ExpressBranchSalesDeptException(String msg) {
		super(msg);
	
	}

	
	
}
