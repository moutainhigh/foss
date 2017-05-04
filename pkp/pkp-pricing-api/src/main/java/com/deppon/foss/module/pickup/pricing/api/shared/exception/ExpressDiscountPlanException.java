package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ExpressDiscountPlanException extends BusinessException {

	/**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;
	//折扣主方案信息不能为空
	public static final String EXPRESS_DISCOUNT_PLAN_EXCEPTION_PLANNULL_ERRORCODE ="foss.pkp.pkp-pricing.ExpressDiscountPlanException.checkPlanIsNull";
	
	public ExpressDiscountPlanException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	    }

    public ExpressDiscountPlanException(String code, String msg) {
		super(code, msg);
	    }
    
    //国际化处理code
    public ExpressDiscountPlanException(String code) {
    	super(code);
    	}
}
