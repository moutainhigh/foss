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
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/enteringAryWaybill/EnteringAryWaybillPrtDataSource.java
 *  
 *  FILE NAME          :EnteringAryWaybillPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 * 1、弹出选择打印模板界面，默认选中航空正单模板；
 * 
 * 2、如果选中航空正单模板，参见SUC-269打印航空正单用例；
 * 
 * 3、如果选中空运外发托运书模板，参见SUC-280打印外发托运书；
 * 
 * 4、如果选中外发清单模板，参见SUC-369打印空运外发清单用例；
 * 
 * */
package com.deppon.foss.prt.enteringAryWaybill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


public class EnteringAryWaybillPrtDataSource implements JasperDataSource {

	/*
	 * 
	 * 航空正单打印模板 (图1) 字段与航空正单界面字段对应关系（前为打印模板字段，后为航空正单界面字段）：
	 * 
     *“始发站”对应“始发站”，“目的站”对应“目的站”，“托运人姓名”对应“托运人”，“收货人姓名”对应“收货人”和“电话”
     *，
     *“结算事项”对应“结算事项”，“填开代理”对应“填开代理”，“航班号”对应“航班号”，“航班日期”对应“航班日期”，
     *
     *“声明价值”对应“声明价值”，“件数”对应“件数”，“毛重”对应“毛重”，“计费重量”对应“计费重量”，
     *
     *“商品代号”对应“商品代号”，“运价”对应“运价”，“航空运费”对应“航空运费”，“名称和包装”对应“货物名称”
     *
     *和“包装”，“费用说明”对应“费用说明”， “附加费”对应“附加费”，“地面运费”对应“地面运费”，
     *
     *“其他费用”对应“燃油附加税”，“保险费”对应“保险费”，“总金额”对应“总金额”，“付款方式”对应“付款方式”，
     *
     *“托运人或其代理人”对应“填开代理”，“填开日期”对应“制单日期”，“填开地点”对应“始发站”，
     *
     *“填开人或其代理人”对应“制单人”  
     *
	 * */
	
	
	//航空正单打印数据
	private AirWaybillEntity entity;
	//根据id获取打印数据
	public void queryAirWaybillListByPrint(JasperContext jasperContext){
		IAirWaybillService airWaybillService = jasperContext.getApplicationContext().getBean("airWaybillService", IAirWaybillService.class);
		//根据ID查询运单明细
		entity = airWaybillService.queryAirWaybillEntityPrint(jasperContext.get("aiyWayBillId").toString());
	}
	
	@SuppressWarnings("rawtypes")
	public Map createParameterDataSource(JasperContext jasperContext) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		queryAirWaybillListByPrint(jasperContext);
		//日期格式转换
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//要求起始站和目的站不能有"市"
		//设置始发站
		parameter.put("deptRegionName",simpleRegionName(entity.getDeptRegionName()));
		//设置目的站
		parameter.put("arrvRegionName",simpleRegionName(entity.getArrvRegionName()));
		
		//设置托运人姓名
		parameter.put("shipperName",entity.getShipperName());
		//设置托运人地址
		parameter.put("shipperAddress",entity.getShipperAddress());
		//设置托运人电话
		parameter.put("shipperContactPhone",entity.getShipperContactPhone());
		//设置收货人姓名
		parameter.put("receiverName",entity.getReceiverName());
		//设置收货人地址
		parameter.put("receiverAddress",entity.getReceiverAddress());
		//设置收货人电话
		parameter.put("receiverContactPhone",entity.getReceiverContactPhone());
		//设置结算事项
		parameter.put("accountItem",entity.getAccountItem());
		//设置填开代理
		parameter.put("billingAgency",entity.getBillingAgency());
		//设置航班号
		parameter.put("flightNo",entity.getFlightNo()+"/"+format.format(entity.getFlightDate()));
		//设置航班日期
		parameter.put("flightDate",format.format(entity.getFlightDate()));
		//设置航空公司二字代码
		parameter.put("airLineTwoletter",entity.getAirLineTwoletter());
		//设置声明价值
		parameter.put("declareValue",entity.getDeclareValue());
		//设置储运事项
		parameter.put("storageItem",entity.getStorageItem());
		//设置件数
		parameter.put("goodsQty",entity.getGoodsQty());
		//设置毛重
		parameter.put("grossWeight",entity.getGrossWeight());
		//设置商品代号
		if(entity.getItemCode().equals(AirfreightConstants.AIR_WAYBILL_ITEM_CODE))
		{
			entity.setItemCode("");
		}
		parameter.put("itemCode",entity.getItemCode());
		//设置计费重量
		parameter.put("billingWeight",entity.getBillingWeight());
		//设置名称和包装
		parameter.put("goodsName",entity.getGoodsName());
		//设置费用说明
		parameter.put("feePlain",entity.getFeePlain());
		if(AirfreightConstants.AIRFREIGHT_ISNOTMONEY.equals(jasperContext.get("isnotMoney"))){
			 /* 1、	正单号来源航空正单的正单号；
				2、	始发站、目的站来源于航空正单的始发站、目的站；
				3、	托运人单位/姓名、托运人电话/手机分别来源航空正单的托运人和当前部门的电话；
				4、	收货人单位/姓名、收货人电话/手机和收货人地址分别来源航空正单的收货人信息的收货人、电话和地址；
				5、	货物名称、件数和重量分别来源于航空正单的货物名称、件数和重量；
				6、	储运事项来源于航空正单的航班号和运价拼接而成，形如：航班号+“；”+“运价 ”+运价；
				7、	提货方式来源于航空正单的提货方式；
				8、	外发代理来源于航空正单的外发代理，时间为航空正单制单时间，制单人为航空正单的制单人；
				9、	以上信息填入外发托运书后，均能在外发托运书中进行修改，且修改只是针对本次打印，不牵涉其他信息修改；
				*/
			//设置运价
			parameter.put("fee",entity.getFee());
			//设置航空运费
			parameter.put("airFee",entity.getAirFee());
			//设置附加费
			parameter.put("extraFee",entity.getExtraFee());
			//设置地面运费
			parameter.put("groundFee",entity.getGroundFee());
			//设置其他运费   “其他费用”对应“燃油附加税”
			parameter.put("parameter1",entity.getFuelSurcharge());
			//设置保险费
			parameter.put("inseranceFee",entity.getInseranceFee());
			//设置总金额
			parameter.put("feeTotal",entity.getFeeTotal());
		}
		//设置付款方式
		parameter.put("paymentType",DictUtil.rendererSubmitToDisplay(entity.getPaymentType(), "PAYMENT_TYPE"));
		//设置 制单人
		parameter.put("createUserName",entity.getCreateUserName());
		//设置制单日期
		parameter.put("createTime",format.format(entity.getCreateTime()));
		return parameter;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List createDetailDataSource(JasperContext jasperContext) {
		List lst = new ArrayList();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(AirfreightConstants.AIRFREIGHT_ISNOTMONEY.equals(jasperContext.get("isnotMoney"))){
			//设置航空运费
			dataMap.put("airFee","航空运费");
			//设置附加费
			dataMap.put("extraFee","附加费");
			//设置地面运费
			dataMap.put("groundFee","地面运费");
			//设置其他运费
			dataMap.put("parameter1","其他运费");
			//设置保险费
			dataMap.put("inseranceFee","保险费");
			//设置总金额
			dataMap.put("feeTotal","总金额");
		}
		lst.add(dataMap);
		return lst;
	}
	
	//此方法不能完全保证取到的地区名称是符合实际情况的，暂时使用，因为系统中取不到地区简称
	public String simpleRegionName(String regionName){
		if(regionName == null){
			return "";
		}
		if(regionName.endsWith("市") || regionName.endsWith("盟")){
			return regionName.substring(0, regionName.length()-1);
		}else if(regionName.endsWith("地区")){
			return regionName.substring(0, regionName.length()-2);
		}else if(regionName.startsWith("黔东南") || regionName.startsWith("黔西南")){
			return regionName.substring(0,ConstantsNumberSonar.SONAR_NUMBER_3);
		}else if(regionName.startsWith("博尔塔拉") || regionName.startsWith("巴音郭楞")
				|| regionName.startsWith("西双版纳")){
			return regionName.substring(0,ConstantsNumberSonar.SONAR_NUMBER_4);
		}else{
			return regionName.substring(0,2);
		}
	}
}