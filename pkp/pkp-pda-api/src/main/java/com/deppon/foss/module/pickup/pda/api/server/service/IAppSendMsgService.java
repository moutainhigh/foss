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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IAppSendMsgService.java
 * 
 * FILE NAME        	: IAppSendMsgService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.AppSMSDto;

/**
 * DN201606250013 接送货异常联系短信推送
 * @author 
 * @date 2016-09-12 下午5:15:23
 * @since
 * @version
 */
public interface IAppSendMsgService extends IService {
	/**
	 * 接送货异常联系短信推送
	 * @author 
	 * @date 2016-09-12 下午5:15:23
	 * @see
	 */
	public void appSendMsg(AppSMSDto appSMSDto);
	
	

}