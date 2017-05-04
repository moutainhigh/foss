package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 快递派送区域的exception类
 * @author 130566
 *
 */
public class ExpressDeliveryRegionsException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1490494493068928243L;

	public ExpressDeliveryRegionsException(String code, Object... args) {
		super(code, args);
	}

	public ExpressDeliveryRegionsException(String msg) {
		super(msg);
	}
	
}
