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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPickupDoneService.java
 * 
 * FILE NAME        	: IPickupDoneService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PickupDoneDto;
/**
 * 完成接货接口Service
 * @author foss-meiying
 * @date 2012-12-17 下午3:23:26
 * @since
 * @version
 */
public interface IPickupDoneService extends IService {
	/**
	 * 进行接货处理
	 * @author foss-meiying
	 * @date 2012-12-10 下午3:37:17
	 * @param record.vehicleNo 车牌号
	 * @param record.driverCode 司机工号
	 * @param record.createUserName 创建人
	 * @param record.createTime 创建时间
	 * @return 
	 * @see
	 */
	boolean donePickUp(PickupDoneDto record);
}