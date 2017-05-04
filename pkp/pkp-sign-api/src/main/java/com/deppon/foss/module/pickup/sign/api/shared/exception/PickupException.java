package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 派送处理异常类
 * @author foss-yuting
 * @date 2014-11-25 上午11:41:41
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class PickupException extends BusinessException {
	/**
	 * 当前运单操作中，请稍后再试
	 */
	public static final String WAYBILL_LOCKED="pkp.sign.pickupException.waybill.locked";//当前运单操作中，请稍后再试
	/**
	 * 有参构造函数
	 * @author foss-yuting
	 * @date 2014-11-25 上午11:41:41
	 * @param code
	 */
	public PickupException(String code) {
		super();
		this.errCode = code;
	}
	
	/**
	 * 有参构造函数
	 * @author foss-yuting
	 * @date 2014-11-25 上午11:41:41
	 * @param code
	 * @param cause
	 */
	public PickupException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}
	/**
	 * 有参构造函数
	 * @author foss-yuting
	 * @date 2014-11-25 上午11:41:41
	 * @param code
	 * @param args
	 */
	public PickupException(String code,Object... args) {
		super(code,args);
	}
	
}
