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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/impl/LocalRuleVerifyService.java
 * 
 * FILE NAME        	: LocalRuleVerifyService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service.impl;

import com.deppon.foss.module.pickup.common.client.service.impl.RuleVerifyService;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillOfflineDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.creating.client.service.ILocalRuleVerifyService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.google.inject.Inject;

/**
 * 
 * 运单本地规则验证服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-29 上午11:19:30,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-29 上午11:19:30
 * @since
 * @version
 */
public class LocalRuleVerifyService extends RuleVerifyService implements
		ILocalRuleVerifyService {

	// 运单
	private IWaybillOfflineDao waybillOfflineDao;

	@Inject
	public void setWaybillOfflineDao(IWaybillOfflineDao waybillOfflineDao) {
		this.waybillOfflineDao = waybillOfflineDao;
	}

	/**
	 * <p>
	 * 判断单号是否已存在本地数据库中
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 上午11:25:32
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.service.IRuleVerifyService#checkWaybillNumber(java.lang.String)
	 */
	@Override
	public boolean checkWaybillNumber(String waybillNo) {
		boolean retValue = true;
		WaybillOfflineEntity waybill = waybillOfflineDao.queryByWaybillNo(waybillNo);
		if (waybill != null) {
			retValue = false;
		}
		return retValue;
	}

	/**
	 * 
	 * <p>
	 * 运单提交效验
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-17 下午4:05:47
	 * @param waybill
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.service.IRuleVerifyService#verifyWaybill(com.deppon.foss.module.pickup.creating.client.domain.Waybill)
	 */
	@Override
	public ResultDto verifyWaybill(WaybillOfflineEntity waybill) {
		return null;
	}

}