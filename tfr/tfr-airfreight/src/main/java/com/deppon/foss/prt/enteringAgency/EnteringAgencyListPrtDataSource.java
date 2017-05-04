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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/enteringAgency/EnteringAgencyListPrtDataSource.java
 *  
 *  FILE NAME          :EnteringAgencyListPrtDataSource.java
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
 * 1、打印模板分为航空正单模板、外发清单模板、外发托运书；
 * 
 * 2、确定按钮：点击确定，进行打印功能，参见打印用例；
 * 
 * 3、取消按钮：点击取消，关闭选择打印格式界面，取消本次打印；
 * */
package com.deppon.foss.prt.enteringAgency;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


public class EnteringAgencyListPrtDataSource implements JasperDataSource {
	/*
	 * 1、储运事项来源于航空正单的航班号和运价拼接而成，形如：航班号+“；”+“运价 ”+运价；
     * 2、提货方式来源于航空正单的提货方式；
     * 3、外发代理来源于航空正单的外发代理，时间为航空正单制单时间，制单人为航空正单的制单人；
     * 4、以上信息填入外发托运书后，均能在外发托运书中进行修改，且修改只是针对本次打印，不牵涉其他信息修改；正单号来源航空正单的正单号；
     * 5、始发站、目的站来源于航空正单的始发站、目的站；
     * 6  托运人单位/姓名、托运人电话/手机分别来源航空正单的托运人和当前部门的电话；
     * 7、收货人单位/姓名、收货人电话/手机和收货人地址分别来源航空正单的收货人信息的收货人、电话和地址；
     * 8、货物名称、件数和重量分别来源于航空正单的货物名称、件数和重量；
	 * */
	private List<AirWaybillDetailEntity> list;
	
	private AirWaybillEntity entity;
	
	@SuppressWarnings("rawtypes")
	public Map createParameterDataSource(JasperContext jasperContext) {
		//接受打印map
		Map<String,Object> parameter = new HashMap<String,Object>();
		//获取前台json
		queryAirWaybillListByPrint(jasperContext);
		//设置总件数
		int goodsQtyTotal = 0;
		//设置总重量
		BigDecimal grossWeightTotal = new BigDecimal(0) ;
		for(int i=0;i<list.size();i++){
			//计算总件数
			goodsQtyTotal = (int) (goodsQtyTotal + list.get(i).getGoodsQty());
			//计算总重量
			grossWeightTotal = grossWeightTotal.add(list.get(i).getGrossWeight());
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = entity.getFlightNo()+ '/' + format.format(entity.getTakeOffTime()) + '/' + format.format(entity.getArriveTime());
		//设置代理名称
		parameter.put("agencyName",entity.getAgencyName());
		//设置航班号/起飞时间/到达时间
		parameter.put("flightNo", str);
		//设置合票号
		parameter.put("jointTicketNo",entity.getJointTicketNo());
		//设置托运人
		parameter.put("shipperName", entity.getShipperName());
		//设置打印时间
		parameter.put("printTime", format.format(new Date()));
		//设置总件数
		parameter.put("goodsQtyTotal", goodsQtyTotal);
		//设置总重量
		parameter.put("grossWeightTotal", grossWeightTotal.longValue());
		//设置制单人
		parameter.put("createUserName", entity.getCreateUserName());

		return parameter;
	}
	//根据id获取打印数据
	public void queryAirWaybillListByPrint(JasperContext jasperContext){
		IAirWaybillService airWaybillService = jasperContext.getApplicationContext().getBean("airWaybillService", IAirWaybillService.class);
		entity = airWaybillService.queryAirWaybillEntityPrint(jasperContext.get("aiyWayBillId").toString());
		list = airWaybillService.queryAirWaybillListForPrint(entity.getId());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List createDetailDataSource(JasperContext jasperContext) {
		List lst = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//设置运单号
			dataMap.put("airWaybillNo", list.get(i).getWaybillNo());
			//设置件数
			dataMap.put("goodsQty", list.get(i).getGoodsQty());
			//设置目的站
			dataMap.put("arrvRegionName", list.get(i).getArrvRegionName());
			//设置重量
			dataMap.put("grossWeight", list.get(i).getGrossWeight());
			//设置品名/包装名
			dataMap.put("goodsName", list.get(i).getGoodsName()+""+list.get(i).getGoodsPackage());
			//dataMap.put("goodsName", list.get(i).getGoodsName());
			if(jasperContext.get("isNotreceiverName").toString().equals("Y")){
				//设置收货部门
				dataMap.put("receiverOrgName",list.get(i).getReceiveOrgName());
			}
			lst.add(dataMap);
		}
		return lst;
		
	}
}