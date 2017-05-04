/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 *<p>Title: CourierScheduleException</p>
 * <p>Description: </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-17
 */
public class CourierScheduleException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param code
	 * @param msg
	 */
	public CourierScheduleException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * @param msg
	 */
	public CourierScheduleException(String msg) {
		super(msg);
	}
	
	

}
