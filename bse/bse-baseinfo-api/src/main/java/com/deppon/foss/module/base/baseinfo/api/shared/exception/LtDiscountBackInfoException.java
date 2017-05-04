package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 零担梯度折异常类
 * @author 261997 css
 * @date 2015-08-22 15:12:30
 *
 */
public class LtDiscountBackInfoException extends BusinessException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public LtDiscountBackInfoException(String errCode){
		super();
		super.errCode = errCode;
	}
	public LtDiscountBackInfoException(String code,String msg){
		super(code,msg);
	}

}
