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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/define/CertificatebagConstant.java
 *  
 *  FILE NAME          :CertificatebagConstant.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.define;

public class CertificatebagConstant {
	// 证件包状态  未上交
	public static final String CERTIFICATEBAG_RETURN = "N";
	// 证件包状态   已上交
	public static final String CERTIFICATEBAG_TAKE = "Y";

	// 交接情况Y 正常
	public static final String HANDOVERSTATUS_NORMAL = "Y";
	// 交接情况 N 异常
	public static final String HANDOVERSTATUS_ERROR = "N";

	// 业务类型   长途
	public static final String BUSINESS_LONG = "LONG_TYPE";
	// 业务类型  短途
	public static final String BUSINESS_SHORT = "SHORT_TYPE";

	
	// 车牌号类型
	public static final String VEHICLE = "0";
	// 货柜号类型
	public static final String CONTAINER = "1";
	
	//行驶证
	public static final String  PERMISO_DE_CIRCULACION="CIRCULACION_CARD";
	//运输证
	public static final String  OVERLAND_TRANSPORTATION="REGISTRATION_CARD";
	//缴费单
	public static final String  DEBIT_NOTE="DEBIT_NOTE";
	//营业证
	public static final String  BUSINESS_REGISTRATION_CERTIFICATE="BUSINESS_CARD";
	//车辆购置税证明
	public static final String  VEHICLE_PURCHASE_TAX="VEHICLE_PURCHASE_TAX";
	//保险卡
	public static final String  INSURANCE_CARD="INSURANCE_CARD";
	//车钥匙
	public static final String CAR_KEY="VEHICLE_KEY";
	
	//车头证件类型
	public static final String  VEHICLEHEAD_TYPE="VEHICLEHEAD";
	//车柜证件类型
	public static final String  CONTAINER_TYPE="CONTAINER";
	//车辆证件类型
	public static final String  VEHICLE_TYPE="VEHICLE";
	
	//空字符串
	public static final String BLANK_STRING = "";
	// 查询数据
	public static final String QUERY_DATA = "查询数据";
	// 归还证件包
	public static final String RETURN_CERTIFICATEBAG = "归还证件包";
	//归还人输入有误，请输入司机工号
	public static final String RETURN_WRONG_EMPLOYEE_ID = "foss.management.certificate.exception.returnWrongEmpId";
	//车牌号未被领取，请输入已被领取的车牌号码
	public static final String VEHICLENO_NOT_TAKEN = "foss.management.certificate.exception.vehicleNumNotTaken";
	//货柜号未被领取，请输入已被领取的车货柜码
	public static final String CONTAINERNO_NOT_TAKEN = "foss.management.certificate.exception.containerNoNotTaken";
	//领取人输入有误，请输入司机工号
	public static final String RECEIVER_WRONG_EMPID =  "foss.management.certificate.exception.receiverWrongEmpId";
	//车牌号已被领取，请选择一个未被领取的车牌号
	public static final String VEHICLENO_BEEN_TAKEN = "foss.management.certificate.exception.vehiclenoTaken";
	//货柜号已被领取，请选择一个未被领取的货柜号
	public static final String CONTAINERNO_BEEN_TAKEN = "foss.management.certificate.exception.containerNoTaken";
	// 未查询到此车牌号!
	public static final String VEHICLENO_NOT_EXIST = "未查询到此车牌号,请确认此车牌号是否为已经启用的公司内部车或外请车!";
	//此车牌号公司从未使用过!
	public static final String VEHICLENO_NEVER_USE = "此车牌号公司从未使用过,请确认输入是否正确!";
	// 配置项类型为空!
	public static final String CONF_TYPE_IS_NULL = "配置项类型为空!";
}