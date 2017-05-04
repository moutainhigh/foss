package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class CenterAndDeliveryAreaException extends BusinessException {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4783480400417303019L;

	public CenterAndDeliveryAreaException(String errCode){
		super();
		super.errCode = errCode;
	    }
	    
	    public CenterAndDeliveryAreaException(String code,String msg){
		
		super(code,msg);
	    }
}
