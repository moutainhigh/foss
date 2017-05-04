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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/ILocalRuleVerifyService.java
 * 
 * FILE NAME        	: ILocalRuleVerifyService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import com.deppon.foss.module.pickup.common.client.service.IRuleVerifyService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * 运单本地规则验证服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-29 上午11:14:37,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-29 上午11:14:37
 * @since
 * @version
 */
public interface ILocalRuleVerifyService extends IRuleVerifyService {

	/**
	 * <p>
	 * 运单号效验
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 上午11:20:44
	 * @param waybillNo
	 * @return
	 * @see
	 */
	boolean checkWaybillNumber(String waybillNo);

	/**
	 * 
	 * <p>
	 * 运单提交效验
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-29 上午11:19:11
	 * @param waybill
	 * @return
	 * @see
	 */
	ResultDto verifyWaybill(WaybillOfflineEntity waybill);

}