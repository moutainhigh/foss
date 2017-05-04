package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class OutfieldTFCompanyException extends BusinessException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	public OutfieldTFCompanyException(String errCode){
		super();
		super.errCode = errCode;
	}
	public OutfieldTFCompanyException(String code,String msg){
		super(code,msg);
	}

}
