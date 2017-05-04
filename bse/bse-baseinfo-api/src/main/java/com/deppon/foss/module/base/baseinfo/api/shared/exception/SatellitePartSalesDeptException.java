package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 卫星点部营业部关系异常类
 * @author 130566
 *
 */
public class SatellitePartSalesDeptException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SatellitePartSalesDeptException(String code, String msg) {
		super(code, msg);
	}

	public SatellitePartSalesDeptException(String msg) {
		super(msg);
	
	}

	
	
}
