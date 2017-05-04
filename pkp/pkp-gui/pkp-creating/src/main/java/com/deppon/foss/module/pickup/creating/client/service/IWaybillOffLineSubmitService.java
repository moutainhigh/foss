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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/IWaybillOffLineSubmitService.java
 * 
 * FILE NAME        	: IWaybillOffLineSubmitService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;


/**
 * 
 * 运单 offline submit service
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public interface IWaybillOffLineSubmitService {
	

	/**
	 * 
	 * 运单提交
	 * @author 025000-FOSS-helong
	 * @date 2012-12-26 下午07:59:50
	 * @param waybillDto
	 */
	void submitWaybill(WaybillDto waybillDto);
	
	/**
	 * 
	 * 验证单号是否存在
	 * @author 025000-FOSS-helong
	 * @date 2012-12-26 下午08:00:00
	 * @param waybillNo
	 * @return
	 */
	public boolean isWayBillExsits(String waybillNo); 
}