/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/WaybillPendingService.java
 * 
 * FILE NAME        	: WaybillPendingService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.service.impl
 * FILE    NAME: WaybillPendingService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IWaybillInfoService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;


/**
 * 待处理运单服务的实现类
 * @author 026123-foss-lifengteng
 * @date 2012-12-15 上午9:50:03
 */
public class WaybillInfoService implements IWaybillInfoService {
	
	// 获得运单信息的远程HessianRemoting接口
	private IWaybillHessianRemoting waybillHessianRemoting;
	/**
	 * 获取远程
	 */
	public WaybillInfoService() {
		waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
	}

	@Override
	public BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo,
			Date recevieDate) {
		// TODO Auto-generated method stub
		return waybillHessianRemoting.queryDiscountFeeByEmployeeNo(employeeNo, recevieDate);
	}

	@Override
	public List<InempDiscountPlanEntity> queryInempDiscountPlanEntity(
			Date recevieDate) {
		// TODO Auto-generated method stub
		return waybillHessianRemoting.queryInempDiscountPlanEntity(recevieDate);
	}
	
	

}