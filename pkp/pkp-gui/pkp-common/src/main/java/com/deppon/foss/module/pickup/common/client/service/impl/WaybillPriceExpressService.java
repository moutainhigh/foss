/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.service.impl
 * FILE    NAME: PublishPriceExpressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;


import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPriceExpressHessianRemoting;


/**
 * 快递包裹公布价格服务类
 * @author 026123-foss-lifengteng
 * @date 2013-8-7 上午10:54:02
 */
public class WaybillPriceExpressService implements IWaybillPriceExpressService {
	// 远程公布价查询接口
	private IWaybillPriceExpressHessianRemoting waybillPriceService;
	
	/**
	 * 初始化远程和本地服务类
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:58:49
	 */
	public WaybillPriceExpressService(){
		waybillPriceService = DefaultRemoteServiceFactory.getService(IWaybillPriceExpressHessianRemoting.class);
	}
	
	
	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:07:18
	 */
	@Override
	public List<PublishPriceExpressEntity> queryPublishPriceDetailOnline(String startDeptNo, String destinationCode){
		//非空判断若出发部门code和到达城市code有一个为空则返回空
		if(StringUtils.isEmpty(startDeptNo) || StringUtils.isEmpty(destinationCode) ){
			return null;
		}
		return waybillPriceService.queryPublishPriceDetail(startDeptNo, destinationCode);
	}


	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition) {
		
		
		
		return waybillPriceService.queryCustomerByCondition(condition);
	}
}
