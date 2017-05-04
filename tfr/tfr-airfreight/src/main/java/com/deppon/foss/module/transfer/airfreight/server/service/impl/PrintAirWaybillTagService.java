/**
 *  initial comments.
 */
/*******************************************************************************
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/PrintAirWaybillTagService.java
 *  
 *  FILE NAME          :PrintAirWaybillTagService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  SR-1	1、	如果在SUC-247录入航空正单明细弹出选择是否打印收货部门选择框时选择否，则模板中航空运费、附加费、地面运费、其他费用、保险费
 * 、总金额不用打印，如果选择是，则按模板描述中的数据打印；
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IPrintAirWaybillTagDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IPrintAirWaybillTagService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 打印正单标签相关
 * @author foss-liuxue(for IBM)
 * @date 2012-11-28 下午3:08:59
 */
public class PrintAirWaybillTagService implements IPrintAirWaybillTagService {
	
	/**
	 * 打印标签dao
	 */
	private IPrintAirWaybillTagDao printAirWaybillTagDao;
	
	/**
	 * 航空公司service
	 */
	private IAirlinesService airlinesService;
	
	/**
	 * 获取上级空运总调
	 */
	private IAirDispatchUtilService airDispatchUtilService;
	
	
	/**
	 * 设置 获取上级空运总调.
	 *
	 * @param airDispatchUtilService the new 获取上级空运总调
	 */
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}

	/**
	 * 设置 打印标签dao.
	 *
	 * @param printAirWaybillTagDao the new 打印标签dao
	 */
	public void setPrintAirWaybillTagDao(
			IPrintAirWaybillTagDao printAirWaybillTagDao) {
		this.printAirWaybillTagDao = printAirWaybillTagDao;
	}

	/**
	 * 设置 航空公司service.
	 *
	 * @param airlinesService the new 航空公司service
	 */
	public void setAirlinesService(IAirlinesService airlinesService) {
		this.airlinesService = airlinesService;
	}
	
	/**
	 * 获取航空公司信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-28 下午7:23:21
	 */
	public List<AirlinesEntity> queryAllAirlines(){
		return airlinesService.queryAllAirlines();
	}

	/**
	 * 根据正单号获取相应信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-26 下午8:15:26
	 */
	@Override
	public AirWaybillEntity queryAirWaybillInfo(
			AirWayBillDto airWayBillDto) {
		//根据正单号获取相应信息
		airWayBillDto.setCreateOrgCode(airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode()).getCode());
		List<AirWaybillEntity> airWaybillList = printAirWaybillTagDao.queryAirWaybillInfo(airWayBillDto);
		//如果没有找到
		if(airWaybillList.size() <= 0 ){
			//抛出异常
			throw new TfrBusinessException("未找到这条正单信息！","");
		}
		//返回实体
		return airWaybillList.get(0);
	}

}