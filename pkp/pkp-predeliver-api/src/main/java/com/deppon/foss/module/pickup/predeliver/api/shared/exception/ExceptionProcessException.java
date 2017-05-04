/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.shared.exception
 * FILE    NAME: ExceptionProcessException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.predeliver.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 
 *
 * 处理异常
 * @author 043258-foss-zhaobin
 * @date 2012-10-31 上午11:35:23
 * @since
 * @version
 */
public class ExceptionProcessException extends BusinessException
{
	/**
	 * 当前运单异常已处理完毕，请核实！
	 */
	public static final String EXCEPTION_DISPOSED = "pkp.predeliver.exceptionProcess.exception.disposed";
	/**
	 * 当前运单号与流水号不匹配，请检查！
	 */
	public static final String SERIALNO_NOT_MATCH = "pkp.predeliver.exceptionProcess.serialNo.not.match";
	/**
	 * 当前运单号流水号存在处理中异常，请处理完毕后再新增！
	 */
	public static final String SERIALNO_IS_HANDLING = "pkp.predeliver.exceptionProcess.serialNo.is.handling";
	/**
	 * 运单号不存在！
	 */
	public static final String WAYBILLNO_NOTEXIST = "pkp.predeliver.exceptionProcess.waybillNo.notExist";
	/**
	 * 运单号与部门不匹配！
	 */
	public static final String WAYBILLNO_NOT_MATCH = "pkp.predeliver.exceptionProcess.waybillNo.not.match";
	/**
	 * 运单已签收！
	 */
	public static final String WAYBILLNO_SIGNED = "pkp.predeliver.exceptionProcess.waybillNo.isSigned";
	/**
	 * 通知失败
	 */
	public static final String NOTICE_FAILED = "pkp.predeliver.exceptionProcess.waybillNo.noticeFailed";
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:26
	 * @param code
	 */
	public ExceptionProcessException(String code){
		  super();
		  this.errCode = code;
	  }
	
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:45
	 * @param code
	 * @param cause
	 */
	public ExceptionProcessException(String code,  Throwable cause){
		  super(code, cause);
		  this.errCode = code;
	  }
}