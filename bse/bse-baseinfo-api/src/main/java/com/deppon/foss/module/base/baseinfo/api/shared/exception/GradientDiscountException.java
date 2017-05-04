package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 零担梯度折异常类
 * @author 218392 张永雪
 * @date 2015-06-05 15:12:30
 *
 */
public class GradientDiscountException extends BusinessException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public GradientDiscountException(String errCode){
		super();
		super.errCode = errCode;
	}
	public GradientDiscountException(String code,String msg){
		super(code,msg);
	}

}
