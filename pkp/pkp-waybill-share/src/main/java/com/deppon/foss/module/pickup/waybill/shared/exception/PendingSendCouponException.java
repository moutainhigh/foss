package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class PendingSendCouponException extends BusinessException {
	
	
	/**
	 * 待处理返券实体为空！
	 */
	public static final String PENDING_SENDCOUPON_ENTITY_IS_NULL = "foss.pendingSendCouponException.pendingSendcouponEntityIsNull";
	

	/**
	 * 生成序列号信息
	 */
	private static final long serialVersionUID = -6038705065369120746L;
	
	
	/**
	 *  （创建一个新的实例 ）PendingSendCouponException
	 * @author 
	 * @date 
	 */
	public PendingSendCouponException(String code) {
		super(code);
		this.errCode=code;
	}


}
