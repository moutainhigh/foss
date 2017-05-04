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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/aryWaybill/AryWaybillPrtDataSource.java
 *  
 *  FILE NAME          :AryWaybillPrtDataSource.java
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
 *  
 *  1、	如果在SUC-247录入航空正单明细弹出选择是否打印收货部门选择框时选择否，则模板中航空运费、附加费、地面运费、其他费用、保险费、
 *  总金额不用打印，如果选择是，则按模板描述中的数据打印；
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.prt.airPickupbill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


public class AirPickupbillPrtDataSource implements JasperDataSource {
	//合大票entity
	private AirPickupbillEntity airPickupbillEntity;
	//合大票明细
	private List<AirPickupbillDetailEntity> airPickupbillDetailList;
		
	@Override
	public Map<String, Object> createParameterDataSource(JasperContext jasperContext) throws Exception {
		// 接受打印map
		Map<String, Object> parameter = new HashMap<String, Object>();
		IAirTransPickupBillService airTransPickupBillService = jasperContext.getApplicationContext().getBean("airTransPickupBillService",
				IAirTransPickupBillService.class);
		String airPickupbillId = (String)jasperContext.get("airPickupbillId");
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillService.queryAirTransPickupBillOrDetail(airPickupbillId);
		
		airPickupbillEntity = airTransPickupBillDto.getAirPickupbillEntity();
		airPickupbillDetailList = airTransPickupBillDto.getAirPickupbillDetailList();
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// 设置代理名称
			parameter.put("airWaybillNo", airPickupbillEntity.getAirWaybillNo());
			// 设置总件数
			parameter.put("flightNo", airPickupbillEntity.getFlightNo());
			// 设置总重量
			parameter.put("flightDate", format.format(airPickupbillEntity.getFlightDate()));
			// 设置制单日期
			parameter.put("createDate", format.format(airPickupbillEntity.getCreateTime()));
			// 设置制单人
			parameter.put("createUserName", airPickupbillEntity.getCreateUserName());
			// 设置制单人
			parameter.put("agentName", airPickupbillEntity.getDestOrgName());
			// 设置制单人
			String cityName = "";
			if(airPickupbillEntity.getOrigOrgName() != null){
				cityName = airPickupbillEntity.getOrigOrgName().substring(0, 2);
			}
			parameter.put("cityName", "("+cityName+")");
			//总件数
			parameter.put("goodsQtyTotal", airPickupbillEntity.getGoodsQtyTotal());
			//总毛重
			parameter.put("grossWeightTotal", airPickupbillEntity.getGrossWeightTotal());
			//总代收款
			parameter.put("collectionFeeTotal", airPickupbillEntity.getCollectionFeeTotal());
			//总到付款
			parameter.put("arrivalFeeTotal", airPickupbillEntity.getArrivalFeeTotal());
			//总送货费
			parameter.put("deliverFeeTotal", airPickupbillEntity.getDeliverFeeTotal());

		return parameter;
	}
	
		public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) {
			List<Map<String, Object>> map = new ArrayList<Map<String, Object>>(airPickupbillDetailList.size());
			for(int i=0;i<airPickupbillDetailList.size();i++){
				Map<String, Object> dataMap = new HashMap<String, Object>();
				AirPickupbillDetailEntity entity = airPickupbillDetailList.get(i);
				//设置 运单号
				dataMap.put("waybillNo",entity.getWaybillNo());
				//设置 件数
				dataMap.put("goodsQty",entity.getGoodsQty());
				//设置 毛重
				dataMap.put("grossWeight",entity.getWeight());
				//设置 计费重量
				dataMap.put("billingWeight",entity.getBillingWeight());
				//设置 目的站
				dataMap.put("arrvRegionName",entity.getArrvRegionName());
				//设置 品名
				dataMap.put("goodsName",entity.getGoodsName());
				//设置 收货人
				StringBuffer receiverInfo = new StringBuffer();
				if(entity.getReceiverName() != null){
					receiverInfo.append(entity.getReceiverName());
				}
				if(entity.getReceiverAddress() != null){
					receiverInfo.append("/").append(entity.getReceiverAddress());
				}
				if(entity.getReceiverContactPhone() != null){
					receiverInfo.append("/").append(entity.getReceiverContactPhone());
				}
				dataMap.put("receiverName",receiverInfo.toString());
				//设置 送货费
				dataMap.put("deliverFee",entity.getDeliverFee());
				//设置 到付款
				dataMap.put("arrivalFee",entity.getArrivalFee());
				//设置 代收款
				dataMap.put("collectionFee",entity.getCollectionFee());
				//设置 备注
				dataMap.put("notes",entity.getNotes());
				map.add(dataMap);
			}
			return map;
		}
		
}