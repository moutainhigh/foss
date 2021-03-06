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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/PickupToDoMsgHessianRemoting.java
 * 
 * FILE NAME        	: PickupToDoMsgHessianRemoting.java
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
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IPickupToDoMsgHessianRemoting;

@Remote
public class PickupToDoMsgHessianRemoting implements IPickupToDoMsgHessianRemoting {

	@Resource
	IPickupToDoMsgService pickupToDoMsgService;
	
	public IPickupToDoMsgService getPickupToDoMsgService() {
		return pickupToDoMsgService;
	}

	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}

	@Override
	public List<PickupToDoMsgDto> queryToDoMsgList(String receiveOrgCode) {
		if(StringUtil.isNotBlank(receiveOrgCode)){
			return pickupToDoMsgService.queryToDoMsgList(receiveOrgCode);
		}
		return null;
		
	}
}