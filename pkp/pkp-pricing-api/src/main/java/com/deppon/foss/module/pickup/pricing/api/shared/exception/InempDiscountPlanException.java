package com.deppon.foss.module.pickup.pricing.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class InempDiscountPlanException extends BusinessException {

	/**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;
	//折扣方案信息不能为空
	public static final String INEMP_DISCOUNT_PLAN_EXCEPTION_PLANNULL_ERRORCODE ="foss.pkp.pkp-pricing.InempDiscountPlanException.checkPlanIsNull";
	
	public InempDiscountPlanException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	    }

    public InempDiscountPlanException(String code, String msg) {
		super(code, msg);
	    }
    
    //国际化处理code
    public InempDiscountPlanException(String code) {
    	super(code);
    	}
}
