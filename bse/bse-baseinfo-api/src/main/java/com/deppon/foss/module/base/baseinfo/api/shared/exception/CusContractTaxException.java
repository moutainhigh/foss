package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 客户发票标记信息异常类
 * @author 132599-foss-shenweihua
 * @date 2013-11-19 下午3:30:01
 * @since
 * @version
 */
public class CusContractTaxException extends BusinessException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7161830835132976087L;
	
	public CusContractTaxException(String errCode) {
		super();
		super.errCode = errCode;
	    }

	    public CusContractTaxException(String code, String msg) {

		super(code, msg);
	    }

}
