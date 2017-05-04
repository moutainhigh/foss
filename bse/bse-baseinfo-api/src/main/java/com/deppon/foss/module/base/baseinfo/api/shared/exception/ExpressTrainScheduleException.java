package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class ExpressTrainScheduleException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -604814344758770850L;

	public ExpressTrainScheduleException(String code, String msg,
			String natvieMsg) {
		super(code, msg, natvieMsg);
		// TODO Auto-generated constructor stub
	}

	public ExpressTrainScheduleException(String code, String msg) {
		super(code, msg);
		// TODO Auto-generated constructor stub
	}

	public ExpressTrainScheduleException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
}
