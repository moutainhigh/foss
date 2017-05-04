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
package com.deppon.foss.module.settlement.agency.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayQueryDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayResultDto;

/**
 * 包装其他应收应付查询界面 service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-7 下午4:43:02,content:TODO </p>
 * @author 105762
 * @date 2014-6-7 下午4:43:02
 * @since 1.6
 * @version 1.0
 */
public interface IPackingRecAndPayService {

	/**
	 * <p>查询</p> 
	 * @author 105762
	 * @date 2014-6-7 下午4:43:44
	 * @param packingRecAndPayResultDto
	 * @param currentInfo
	 * @return 
	 * @see
	 */
	PackingRecAndPayResultDto query(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo);

	/**
	 * <p>作废/红冲</p> 
	 * @author 105762
	 * @date 2014-6-10 下午6:13:46
	 * @param packingRecAndPayQueryDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void invalid(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo);

	/**
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-6-10 下午6:13:52
	 * @param packingRecAndPayQueryDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void approve(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo);

	/**
	 * <p>反审核</p> 
	 * @author 105762
	 * @date 2014-6-10 下午6:13:58
	 * @param packingRecAndPayQueryDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void reverseApprove(PackingRecAndPayQueryDto packingRecAndPayQueryDto, CurrentInfo currentInfo);

}
