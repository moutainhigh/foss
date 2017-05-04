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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaPullbackGoodsService.java
 * 
 * FILE NAME        	: IPdaPullbackGoodsService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaPullbackgoodsDto;

/**
 * PDA客户货物签收   拉回货物接口
 * @author foss-meiying
 * @date 2012-12-10 上午10:23:34
 * @since
 * @version
 */
public interface IPdaPullbackGoodsService extends IService {
	/**
	 * 
	 * 保存拉回货物信息
	 * @author foss-meiying
	 * @date 2012-12-10 上午11:31:20
	 * @param pullbackgoodsDto.waybillNo 运单号
	 * @param pullbackgoodsDto.arrivesheetNo  到达联编号
	 * @param pullbackgoodsDto.pullbackReason 拉回原因 
	 * @param pullbackgoodsDto.driverCode  司机工号
	 * @param pullbackgoodsDto.pullbackTime 拉回时间 
	 * @param pullbackgoodsDto.signNote  备注
	 * @param pullbackgoodsDto.pullbackQty 拉回件数
	 * @param pullbackgoodsDto.vehicleNo 车牌号 
	 * @return
	 * @see
	 */
	 String addPullbackGoodsSign(PdaPullbackgoodsDto pullbackgoodsDto);
}