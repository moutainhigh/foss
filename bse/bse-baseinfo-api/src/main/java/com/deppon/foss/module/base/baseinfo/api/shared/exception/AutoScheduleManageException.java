package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class AutoScheduleManageException extends BusinessException {
	/**异常类
     * serialVersionUID
     */
    private static final long serialVersionUID = -6525548077272997158L;

    public AutoScheduleManageException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public AutoScheduleManageException(String code, String msg) {

	super(code, msg);
    }
}
