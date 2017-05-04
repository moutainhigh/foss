package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ExpressAutoScheduleException extends BusinessException {
	
    /**
	 *快递自动调度城市管理异常类
	 *author:yangkang
	 */
	private static final long serialVersionUID = 1L;

	public ExpressAutoScheduleException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public ExpressAutoScheduleException(String code, String msg) {

	super(code, msg);
    }
}
