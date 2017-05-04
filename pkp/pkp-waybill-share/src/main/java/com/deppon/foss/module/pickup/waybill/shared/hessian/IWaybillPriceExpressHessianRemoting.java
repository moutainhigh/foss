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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/IWaybillPriceHessianRemoting.java
 * 
 * FILE NAME        	: IWaybillPriceHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.util.List;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;

/**
 * 
 * 运单价格远程计算服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-5 下午4:17:39, </p>
 * @author foss-sunrui
 * @date 2012-11-5 下午4:17:39
 * @since
 * @version
 */
public interface IWaybillPriceExpressHessianRemoting extends IHessianService {

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:20:48
	 */
	List<PublishPriceExpressEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode);

	/**
	 * @param condition
	 * @return
	 */
	List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition);
}