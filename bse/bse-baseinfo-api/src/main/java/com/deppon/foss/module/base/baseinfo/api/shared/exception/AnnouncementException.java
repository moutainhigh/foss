package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * @author zengjunfan
 * @date	2013-4-18下午3:20:04
 * 
 */
public class AnnouncementException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AnnouncementException(String errCode) {
		super();
		super.errCode =errCode;
	}
	public AnnouncementException(String code, String msg) {
		super(code, msg);
		
	}
	
}
