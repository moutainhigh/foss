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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/ISendSmsVoiceService.java
 * 
 * FILE NAME        	: ISendSmsVoiceService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.service
 * FILE    NAME: ISendSmsVoiceService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.NotificationEntity2;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;


/**
 * 短信语音发送服务接口
 * @author 026123-foss-lifengteng
 * @date 2012-12-26 下午2:58:48
 */
public interface ISendSmsVoiceService {

	/**
	 * 给发货人发送短信
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-26 下午5:29:04
	 */
	void sendShipperSms(WaybillEntity waybill);

	/**
	 * 给收货人发送短信
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-26 下午5:29:33
	 */
	void sendConsigneeSms(WaybillEntity waybill);

	void sendDriverCodeSms(NotificationEntity2 notificationEntity);
}