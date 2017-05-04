
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/*
 * 
 */

public class BillInspectionRemindException extends BusinessException {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6987873224042825488L;

	/**
     * serialVersionUID
     */
   

    public BillInspectionRemindException(String errCode) {
	super();
	super.errCode = errCode;
    }

    public BillInspectionRemindException(String code, String msg) {

	super(code, msg);
    }
}
