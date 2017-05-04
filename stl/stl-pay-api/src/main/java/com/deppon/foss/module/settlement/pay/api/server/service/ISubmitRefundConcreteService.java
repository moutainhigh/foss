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
package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.settlement.pay.api.shared.dto.SubmitRefundDto;

/**
 * 转报销
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-15 上午9:00:45,content:TODO </p>
 * @author 105762
 * @date 2014-7-15 上午9:00:45
 * @since
 * @version
 */
public interface ISubmitRefundConcreteService {

	/** 
	 * <p>转报销接口实现方法</p> 
	 * @author 105762
	 * @date 2014-7-10 下午4:04:51
	 * @param esbHeader
	 * @param submitRefundRequest
	 * @return
	 * @throws CommonException 
	 * @see com.deppon.foss.submitrefund.ISubmitRefundService#submitRefund(javax.xml.ws.Holder, com.deppon.foss.inteface.domain.stl.SubmitRefundRequest)
	 */
	boolean submitRefund(SubmitRefundDto dto);

}
