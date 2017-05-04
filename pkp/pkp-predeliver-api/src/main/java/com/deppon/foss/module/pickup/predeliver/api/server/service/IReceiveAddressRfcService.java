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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IReceiveAddressRfcService.java
 * 
 * FILE NAME        	: IReceiveAddressRfcService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ReceiveAddressRfcEntity;

/**
 * 
 * 运单地址临时表Service
 * @author ibm-wangfei
 * @date Nov 30, 2012 5:22:46 PM
 */
public interface IReceiveAddressRfcService extends IService{
	
	/**
	 * 
	 * 定时JOB-更新运单附加表的集中送货小区
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 5:25:28 PM
	 */
	void updateDeliverRegionCode();

	/**
	 * 
	 * 新增
	 * 
	 * @param receiveAddressRfcEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 2:49:13 PM
	 */
	int addReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity);

	/**
	 * 
	 * 更新临时表的JOB_ID、STATUS
	 * 
	 * @param tmpEntity
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 1:39:00 PM
	 */
	int updateReceiveAddressRfcEntity(ReceiveAddressRfcEntity tmpEntity);

	/**
	 * 
	 * 执行更新
	 * 
	 * @param receiveAddressRfcEntity
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 1:40:30 PM
	 */
	void executeDetail(ReceiveAddressRfcEntity receiveAddressRfcEntity);
	
}