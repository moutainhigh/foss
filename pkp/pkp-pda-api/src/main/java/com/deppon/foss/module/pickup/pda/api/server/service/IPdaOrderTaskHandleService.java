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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaOrderTaskHandleService.java
 * 
 * FILE NAME        	: IPdaOrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;

/**
 * 
 * 转货完成 AND 更新调度订单接口
 * @author ibm-wangfei
 * @date Dec 11, 2012 2:11:37 PM
 */
public interface IPdaOrderTaskHandleService extends IService {
	
	/**
	 * 转货完成 AND 更新调度订单
	 * 
	 * @author ibm-wangfei
	 * @date Dec 11, 2012 2:21:02 PM
	 * @param dto
	 */
	String udpateOrder(PdaOrderDto dto) throws PdaProcessException;

}