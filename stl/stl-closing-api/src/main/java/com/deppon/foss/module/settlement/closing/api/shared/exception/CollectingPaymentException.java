package com.deppon.foss.module.settlement.closing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 代收货款查询接口异常类
 * @author 218392 zhangyongxue
 * @date 2015-09-08 09:51:20
 */
public class CollectingPaymentException extends BusinessException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public CollectingPaymentException(String errorCod){
		super();
		this.errCode = errCode;
	}
	public CollectingPaymentException(String code,String msg){
		super(code,msg);
	}

}
