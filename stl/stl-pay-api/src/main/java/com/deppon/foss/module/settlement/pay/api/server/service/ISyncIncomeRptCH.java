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

import com.deppon.esb.core.exception.ESBException;

/**
 * 同步现金缴款报表到财务自助
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 下午2:21:56,content:TODO </p>
 * @author 105762
 * @date 2014-7-28 下午2:21:56
 * @since 1.6
 * @version 1.0
 */
public interface ISyncIncomeRptCH {
	/**
	 * <p>同步现金缴款报表到财务自助</p> 
	 * @author 105762 Yang Shushuo
	 * @throws ESBException 
	 * @date 2014-7-28 下午2:22:22
	 */
	void process() throws ESBException;
}
