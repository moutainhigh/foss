/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IOrderTaskHandleService.java
 * 
 * FILE NAME        	: IOrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;

/**
 * 
* @ClassName: IAutoExpressAcceptService 
* @Description: 自动受理订单服务
* @author YANGBIN
* @date 2014-5-6 上午10:20:34 
*
 */
public interface IAutoExpressAcceptService extends IService {
	
	/**
	 * 
	 * @Title: acceptOrder 
	 * @Description: 自动受理
	 * @param @param orderHandleDto
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean acceptOrder(OrderHandleDto orderHandleDto);
	/**
	 * 
	 * @Title: pdaReturnProcess 
	 * @Description: 快递PDA退回处理 (来不及接货)
	 * @param @param pdaReturnDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void pdaReturnProcess(PdaReturnDto pdaReturnDto);
	
	/**
	 * 
	 * @Title: pdaForwardProcess 
	 * @Description: 快递PDA转发处理
	 * @param @param orderForwardRecordEntity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void pdaForwardProcess(PdaReturnDto pdaReturnDto);
}