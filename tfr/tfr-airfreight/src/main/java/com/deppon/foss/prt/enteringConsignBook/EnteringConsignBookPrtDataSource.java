/**
 *  initial comments.
 */
/*******************************************************************************
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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/enteringConsignBook/EnteringConsignBookPrtDataSource.java
 *  
 *  FILE NAME          :EnteringConsignBookPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: foss-print
 * PACKAGE NAME: com.deppon.foss.prt.zhoudejun
 * FILE    NAME: ZhouDeJunPrtDataSource.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.prt.enteringConsignBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;

/**
 * TODO（描述类的职责）
 * @author 104306-foss-zhoudejun
 * @date 2012-11-3 上午10:26:20
 */
public class EnteringConsignBookPrtDataSource implements JasperDataSource {
	
	private AirWaybillEntity entity;
	//根据id获取打印数据
	public void queryAirWaybillListByPrint(JasperContext jasperContext){
		IAirWaybillService airWaybillService = jasperContext.getApplicationContext().getBean("airWaybillService", IAirWaybillService.class);
		entity = airWaybillService.queryAirWaybillEntityPrint(jasperContext.get("agencyAgencyId").toString());
	}
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 104306-foss-zhoudejun
	 * @date 2012-11-3 上午10:26:28
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map createParameterDataSource(JasperContext jasperContext) {
		Map parameter = new HashMap();
		queryAirWaybillListByPrint(jasperContext);
		//设置始发站
		parameter.put("deptRegionName", entity.getDeptRegionName());
		//设置目的站
		parameter.put("arrvRegionName", entity.getArrvRegionName());
		//设置托运人单位/姓名
		parameter.put("shipperName", entity.getShipperName());
		//设置托运人电话/手机
		parameter.put("shipperContactPhone", entity.getShipperContactPhone());
		//设置 收货人单位/姓名
		parameter.put("receiverName", entity.getReceiverName());
		//设置收货人电话/手机
		parameter.put("receiverContactPhone", entity.getReceiverContactPhone());
		//设置收货人地址
		parameter.put("shipperAddress", entity.getShipperAddress());
		//设置储运事项
		parameter.put("pickupType", entity.getPickupType());
		//设置提货方式
		parameter.put("storageItem", entity.getStorageItem());
		//设置货物名称
		parameter.put("goodsName", entity.getGoodsName());
		//设置件数
		parameter.put("goodsQty", entity.getGoodsQty());
		//设置重量
		parameter.put("billingWeight", entity.getGrossWeight());
		//设置 外发代理
		parameter.put("agencyName", entity.getAgencyName());
		//设置 时间
		parameter.put("flightDate", entity.getFlightDate());
		//设置制单人
		parameter.put("createUserName", entity.getCreateUserName());
		return parameter;
	}

	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 104306-foss-zhoudejun
	 * @date 2012-11-3 上午10:26:28
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public List createDetailDataSource(JasperContext jasperContext) {
		List lst = new ArrayList();
		Map dataMap = new HashMap();
		dataMap.put("deptRegionName", "SEW");
		lst.add(dataMap);
		return lst;
	}
}