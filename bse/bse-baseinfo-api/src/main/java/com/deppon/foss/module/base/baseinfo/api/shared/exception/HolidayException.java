/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 法定节假日基础资料异常处理类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午2:54:11,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午2:54:11
 * @since
 * @version
 */
public class HolidayException extends BusinessException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	public HolidayException(String errCode){
		super();
		super.errCode=errCode;
	}
	
	public HolidayException(String errCode,String msg){
		super(errCode,msg);
	}

}
