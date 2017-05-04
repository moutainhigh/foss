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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/WaybillPriceHessianRemoting.java
 * 
 * FILE NAME        	: WaybillPriceHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPriceExpressHessianRemoting;


/**
 * 价格远程服务接口实现类
 * @author 026123-foss-lifengteng
 * @date 2013-8-7 上午10:17:16
 */
@Remote()
public class WaybillPriceExpressHessianRemoting implements IWaybillPriceExpressHessianRemoting {

	/**
	 * 公布查询服务接口
	 */
	@Resource
	private IWaybillExpressService waybillExpressService;

	
	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:07:18
	 */
	@Override
	public List<PublishPriceExpressEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode){
		return waybillExpressService.queryPublishPriceDetail(startDeptNo, destinationCode);
	}
	
	
	/**************************** get和set方法 ****************/
	/**
	 * 公布查询服务注入方法
	 */
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}


	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPriceExpressHessianRemoting#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition) {
		return waybillExpressService.queryCustomerByCondition(condition);
	}
}