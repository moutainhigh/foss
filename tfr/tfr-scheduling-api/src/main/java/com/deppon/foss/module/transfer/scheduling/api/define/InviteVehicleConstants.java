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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/InviteVehicleConstants.java
 * 
 *  FILE NAME     :InviteVehicleConstants.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.define
 * FILE    NAME: OrderVehicleConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.define;

/**
 * 外请约车常量容器
 * @author 104306-foss-wangLong
 * @date 2012-10-16 下午4:08:23
 */
public final class InviteVehicleConstants {
	
	/** 外请约车状态  暂存 */
	public static final String INVITEVEHICLE_STATUS_STAGING = "STAGING";
	
	
	/** 外请约车状态  未审核  */
	public static final String INVITEVEHICLE_STATUS_UNCOMMITTED = "UNCOMMITTED";
	
	/** 外请约车状态  已受理  */
	public static final String INVITEVEHICLE_STATUS_COMMITTED = "COMMITTED";
	
	/** 外请约车状态 已拒绝  */
	public static final String INVITEVEHICLE_STATUS_REJECT = "REJECT";
	
	/** 外请约车状态 已退回  */
	public static final String INVITEVEHICLE_STATUS_RETURN = "RETURN";
	
	/** 外请约车状态 确认到达 .[报到] */
	public static final String INVITEVEHICLE_STATUS_VERIFY_ARRIVE = "VERIFY_ARRIVE";
	
	/** 外请约车状态 释放 */
	public static final String INVITEVEHICLE_STATUS_RELEASE = "RELEASE";
	
	/** 外请约车状态  已撤销 */
	public static final String INVITEVEHICLE_STATUS_UNDO = "UNDO";
	
	/**  用车类型  到营业部 */
	public static final String INVITEVEHICLE_USEVEHICLE_TYPE_TO_SALES_DEPARTMENT = "TO_SALES_DEPARTMENT";
	
	/**  用车类型  到客户 */
	public static final String INVITEVEHICLE_USEVEHICLE_TYPE_TO_CLIENT = "TO_CLIENT";
	
	/**  用车类型  中转场  */
	public static final String INVITEVEHICLE_USEVEHICLE_TYPE_TO_TRANSIT = "TO_TRANSIT";
	
	/** 使用状态  已使用 */
	public static final String INVITEVEHICLE_USESTATUS_USING = "USING";
	public static final String INVITEVEHICLE_USESTATUS_USING_NAME = "已使用";
	
	/** 使用状态   未使用   */
	public static final String INVITEVEHICLE_USESTATUS_UNUSED = "UNUSED";
	public static final String INVITEVEHICLE_USESTATUS_UNUSED_NAME = "未使用";
	/** 外请车   */
	public static final String TRUCK_TYPE_LEASED = "Leased";
	/** 同城约车   */
	public static final String INVITEVEHICLE_USEPURPOSE = "SAMECITY_ORDER";
	
	/** 异常  */
	public static final String INVITEVEHICLE_PARAMETERERROR_MESSAGE = "foss.scheduling.inviteVehicle.parameterException";
	
	
	/** 临时租车标记按单号查询  */
	public static final String QBB = "QBB";
	/** 临时租车标记按日期查询  */
	public static final String QBD = "QBD";
	/** 单据类型：零担运单**/
	public static final String RENTALMARK_WAYBILL = "waybill";
	/** 单据类型：交接单**/
	public static final String RENTALMARK_HANDOVERBILL = "handoverbill";
	/** 单据类型：派送单**/
	public static final String RENTALMARK_DELIVERBILL = "deliverbill";
	//269701--lln--2015-09-09 begin
	/** 单据类型：快递运单**/
	public static final String RENTALMARK_EXPRESSRBILL = "expressbill";
	//269701--lln--2015-09-09 end
	/**单据类型：配载单**/
	public static final String RENTALMARK_STOWAGEBILL = "stowagebill";
	//313352--gouyangang--2016-05-09 begin
	/** 单据类型：快递交接单**/
	public static final String RENTALMARK_EXPRESSDELIVERYBILL = "express";
	/** 单据类型：快递运单**/	
	public static final String MARK_EXPRESSDELIVERYBILL = "makeExpress";
	// 313352 -- gouyangyang 2016-06-06
	/** 数据有效状态：Y为有效**/
	public static final String RENTALMARK_Y = "Y";
	/** 数据有效状态：N为无效**/
	public static final String RENTALMARK_N = "N";
	/**租车用途：接货**/
	public static final String RENTALMARK_JH = "JH";
	/**租车用途：送货**/
	public static final String RENTALMARK_SH = "SH";
	/**租车用途：转货**/
	public static final String RENTALMARK_ZH = "ZH";
	/**租车用途：接送货**/
	public static final String RENTALMARK_JSH = "JSH";
	/**用车原因:缺人**/
	public static final String RENTALMARK_SHORTHANDED = "shortHanded";
	/**用车原因:异形货**/
	public static final String RENTALMARK_SPECIALGOODS = "specialGoods";
	/**用车原因:会展**/
	public static final String RENTALMARK_EXHIBITION = "exhibition";
	/**用车原因:进仓**/
	public static final String RENTALMARK_WAREHOUSEENTRY= "warehouseEntry";
	/**用车原因:缺车**/
	public static final String RENTALMARK_LACKVEHICLES = "lackVehiclesS";
	/**用车原因:公司限行**/
	public static final String RENTALMARK_LIMITLINE = "limitLine";
	/**用车原因:客户原因**/
	public static final String RENTALMARK_CUSTOMERREASON = "customerReason";
	/**用车原因:超远派送**/
	public static final String RENTALMARK_LONGLIVERY = "longDelivery";
	/**用车原因:外场原因**/
	public static final String RENTALMARK_EXTERNALCAUSES = "externalCauses";
	/**用车原因:其他**/
	public static final String RENTALMARK_OTHERS = "others";
	
	/**用车原因:缺人**/
	public static final String RENTALMARK_shortHanded = "缺人";
	/**用车原因:异形货**/
	public static final String RENTALMARK_specialGoods = "异形货";
	/**用车原因:会展**/
	public static final String RENTALMARK_exhibition = "会展";
	/**用车原因:进仓**/
	public static final String RENTALMARK_warehouseEntry= "进仓";
	/**用车原因:缺车**/
	public static final String RENTALMARK_lackVehiclesS = "缺车";
	/**用车原因:公司限行**/
	public static final String RENTALMARK_limitLine = "公司限行";
	/**用车原因:客户原因**/
	public static final String RENTALMARK_customerReason = "客户原因";
	/**用车原因:超远派送**/
	public static final String RENTALMARK_longDelivery = "超远派送";
	/**用车原因:外场原因**/
	public static final String RENTALMARK_externalCauses = "外场原因";
	/**用车原因:其他**/
	public static final String RENTALMARK_others = "其他";
	
	/**业务类型:接送货**/
	public static final String RENTALMARK_PICKDELIVERY = "Pick_Delivery";
	/**业务类型:转货**/
	public static final String RENTALMARK_TRANSFER = "Transfer";
	/**业务类型:大客户接货**/
	public static final String RENTALMARK_BIGCUSTOMER = "bigCustomer";
	/**业务类型:快递**/
	public static final String RENTALMARK_EXPRESSAGE = "expressage";
	/**业务类型:接送货**/
	public static final String RENTALMARK_pickdelivery = "接送货";
	/**业务类型:转货**/
	public static final String RENTALMARK_transfer = "转货";
	/**业务类型:大客户接货**/
	public static final String RENTALMARK_bigcustomer = "大客户接货";
	/**业务类型:快递**/
	public static final String RENTALMARK_expressage = "快递";
	/**业务类型:同城外租车**/
	public static final String RENTALMARK_SAMECITYORDER = "SAMECITY_ORDER";
	
	
	
	private InviteVehicleConstants() {
		
	}
}