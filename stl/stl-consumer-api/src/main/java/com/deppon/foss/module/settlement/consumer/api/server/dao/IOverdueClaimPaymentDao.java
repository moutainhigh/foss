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
package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueClaimPaymentDto;


import java.util.List;
import java.util.Map;


/**
 * FOSS现金缴款同步接口规格文档
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 下午3:41:09,content:TODO </p>
 * @author 105762
 * @date 2014-7-28 下午3:41:09
 * @since
 * @version
 */
public interface IOverdueClaimPaymentDao {

	/**
	 * <p>查询现金缴款每日统计数据</p> 
	 * @author 105762
	 * @date 2014-7-28 下午3:41:15
	 * @param param 其中 beginTime 开始时间（不包含） endTime 结束时间（包含）
	 * @return 超时理赔信息
	 */
	List<OverdueClaimPaymentDto> queryOverdueClaimPaymentData(Map param);

	/**
	 * 查询收银员
	 * @param employeeEntity
	 * @return
	 */
	List<EmployeeEntity> queryEmployeeAndUserByEntity(EmployeeEntity employeeEntity);
}
