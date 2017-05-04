/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.service
 * FILE    NAME: IPublishPriceExpressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;


/**
 * 快递包裹公布价格服务接口
 * @author 026123-foss-lifengteng
 * @date 2013-8-7 上午10:50:12
 */
public interface IWaybillPriceExpressService {

	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:07:18
	 */
	List<PublishPriceExpressEntity> queryPublishPriceDetailOnline(String startDeptNo, String destinationCode);

	/**
	 * @param gainQueryCondition
	 * @return
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto gainQueryCondition);

}
