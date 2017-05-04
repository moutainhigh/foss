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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaSignService.java
 * 
 * FILE NAME        	: IPdaSignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDto;

/**
 * 签收出库(PDA和中转接口)
 * @author foss-meiying
 * @date 2012-11-27 下午2:03:23
 * @since
 * @version
 */
public interface IPdaSignService extends IService {
	/**
	 * pda签收出库操作
	 * @author foss-meiying
	 * @date 2012-11-27 下午2:57:29
	 * @see
	 */
	String pdaSign(PdaSignDto dto);
	
	
	/**
	 * 
	 * 快递PDA开单
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-26
	 */
	String pdaExpressSign(PdaSignDto dto);

}