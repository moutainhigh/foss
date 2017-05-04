package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 日承载货量异常类
 * @author 130346
 *
 */
public class TransferVolumeDayException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8738973293686015159L;
	
	
	public TransferVolumeDayException(String errCode) {
		super();
		super.errCode = errCode;
	  }

    public TransferVolumeDayException(String code, String msg) {

		super(code, msg);
	  }
}
