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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/ISignByOtherService.java
 * 
 * FILE NAME        	: ISignByOtherService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchWaybillSignByOtherDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;

/***
 * 处理他人收件 Service
 * 
 * @date 2012-11-26 上午10:36:08
 */
public interface ISignByOtherService {

	/***
	 * 营业员通过输入运单号，经部门经理授权后查询发货客户联系方式 根据录入条件查询弃货运单
	 * 
	 * @date 2012-11-26 上午10:41:13
	 */
	RtSearchWaybillSignByOtherDto queryWaybillReceiverInfo(SearchWaybillSignByOtherDto dto);

	/***
	 * 发货客户更改收货人的电子凭证
	 * 
	 * @date 2012-11-26 上午10:47:23
	 */
	RtSearchWaybillSignByOtherDto updateWaybillReceiverInfo(SearchWaybillSignByOtherDto searchWaybillSignByOtherDto);

	/**
	 * 
	 * 根据运单号，返回处理他人收件信息
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 16, 2013 11:41:46 AM
	 */
	SearchWaybillSignByOtherDto querySignByOtherDto(String waybillNo);
}