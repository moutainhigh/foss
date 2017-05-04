package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class QmsErrorException extends BusinessException {
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * QMS差错接口无法访问
	 */
	public static final String QMS_ERRORS_INACCESSIBLE = 
			"foss.pkp.waybill.qmsErrorReportService.exception.qmsErrorsInaccessible";
	
	/**
	 * QMS差错上报信息为空
	 */
	public static final String QMS_ERRORS_INFO_NULL = 
			"foss.pkp.waybill.qmsErrorReportService.exception.qmsErrorsInfoNull";
	
	/**
	 * （创建一个新的实例 ）
	 * 
	 * @author 231434-foss-bieyexiong
	 * @date 2015-5-14 上午10:22:19
	 */
	public QmsErrorException() {
		super();
		//  Auto-generated constructor stub
	}

	/**
	 * （创建一个新的实例 ）OAErrorException
	 * 
	 * @author 231434-foss-bieyexiong
	 * @date 2015-5-14 上午10:25:12
	 */
	public QmsErrorException(String msg) {
		super(msg);
		this.errCode=msg;
	}

}
