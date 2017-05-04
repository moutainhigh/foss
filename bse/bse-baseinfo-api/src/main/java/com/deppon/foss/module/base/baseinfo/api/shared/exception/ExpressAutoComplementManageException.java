package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ExpressAutoComplementManageException extends BusinessException{

	/**
	 * serialVersionUID
	 * @author 218392 zhangyongxue
	 * @date 2015-05-18 13:45:20
	 */
	private static final long serialVersionUID = 1L;
	
	public ExpressAutoComplementManageException(String errCode){
		super();
		super.errCode = errCode;
	}
	public ExpressAutoComplementManageException(String code,String msg){
		super(code,msg);
	}

}
