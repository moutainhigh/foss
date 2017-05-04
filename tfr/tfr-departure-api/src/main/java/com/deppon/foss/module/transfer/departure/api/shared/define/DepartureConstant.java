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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/define/DepartureConstant.java
 *  
 *  FILE NAME          :DepartureConstant.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.define;

import java.util.HashMap;
import java.util.Map;

public class DepartureConstant{
	/******** 放行方式 *********/
	public static final String DEPART_TYPE_PDA = "保安PDA放行";

	public static final String DEPART_TYPE_MANUAL = "纸质确认放行";
	
	public static final String DEPART_TYPE_GPS = "GPS放行";

	public static final String DEPART_TYPE_MANUAL_CODE = "1";

	public static final String DEPART_TYPE_PDA_CODE = "2"; 

	/*********** 放行类型 *************/
	// 任务车辆
	public static final String DEPART_TYPE_TASK_VEHICLE = "TASK";
	// 非任务车辆
	public static final String DEPART_TYPE_NOT_TASK_VEHICLE = "NO_TASK";

	/*********** 放行状态 *************/
	// 删除
	public static final String DEPART_STATUS_DELETED = "0";
	// 已取消
	public static final String DEPART_STATUS_CANCLED = "1";
	// 已经失效
	public static final String DEPART_STATUS_Fail = "2";
	// 待放行
	public static final String DEPART_STATUS_WAIT = "5";
	// 已出发
	public static final String DEPART_STATUS_DEPARTURE = "9";

	/*********** 申请放行方式 *************/
	// 手动
	public static final String DEPART_APPLY_TYPE_MANUAL = "MANUAL";
	// 自动
	public static final String DEPART_APPLY_TYPE_AUTO = "AUTO";

	/*********** 车牌号的归属类型 *************/
	// 外清车
	public static final String TRUCK_TYPE_LEASED = "Leased";
	// 公司车
	public static final String TRUCK_TYPE_OWN = "Company";

	/************ 操作状态 ****************/
	// 状态检查失败
	public static final String DEPART_VALID_STATUS_ERROR = "1";
	// 状态检查成功
	public static final String DEPART_VALID_STATUS_SUCCESS = "2";
	// 取消申请时状态检查失败
	public static final String DEPART_CANCLE_STATUS_ERROR = "3";
	// 激活申请时状态检查失败
	public static final String DEPART_ACTIVE_STATUS_ERROR = "4";
	// 不是当天的记录不能LMS放行
	public static final String DEPART_CAN_NOT_DEPART = "5";

	/************ 申请放行事项 ****************/
	// 保养
	public static final String DEPART_ITEM_TYPE_KEEP = "NO_TASK_KEEP";
	// 年审
	public static final String DEPART_ITEM_TYPE_VERIFICATION = "NO_TASK_YEAR_VERIFICATION";
	// 临时放空出发/约车
	public static final String DEPART_ITEM_TYPE_APPOINT = "NO_TASK_APPOINT";
	// 加油
	public static final String DEPART_ITEM_TYPE_OIL = "NO_TASK_OIL";
	// 维修
	public static final String DEPART_ITEM_TYPE_REPAIR = "NO_TASK_REPAIR";
	// 其他任务出发
	public static final String DEPART_ITEM_TYPE_OTHERS = "NO_TASK_OTHERS";
	// 长途
	public static final String DEPART_ITEM_TYPE_LONG = "LONG_DISTANCE";
	// 短途
	public static final String DEPART_ITEM_TYPE_SHORT = "SHORT_DISTANCE";
	// 接送或
	public static final String DEPART_ITEM_TYPE_PKP = "DELIVER";

	/**
	 * 整车入库开始时间
	 */
	public static final String TFR_PRAM_PUSH_WHOLE_VEHICLE_BEGIN_TIME = "PUSH_WHOLE_VEHICLE_BEGIN_TIME";
	
	public final static Map<String,String> departItemMap = new HashMap();  
	static {  
		departItemMap.put("NO_TASK_KEEP","保养");
		departItemMap.put("NO_TASK_YEAR_VERIFICATION","年审");
		departItemMap.put("NO_TASK_APPOINT","临时放空出发/约车");
		departItemMap.put("NO_TASK_OIL","加油");
		departItemMap.put("NO_TASK_REPAIR","维修");
		departItemMap.put("NO_TASK_OTHERS","其他任务出发");
		departItemMap.put("LONG_DISTANCE","长途");
		departItemMap.put("SHORT_DISTANCE","短途");
		departItemMap.put("DELIVER","接送货");
	}

	// 偏线
	public static final String DEPART_ITEM_TYPE_PARTIALLINE = "PARTIALLINE";

	/*********** 是否被下拉框选中 *************/
	// 选中
	public static final String DEPART_IS_CKECKED = "1";
	// 未选中
	public static final String DEPART_IS_NOT_CKECKED = "0";

	/*********** Job表类型 *************/
	// 车辆放行
	public static final String JOB_TRUCK_DEPART = "1";
	// 车辆到达
	public static final String JOB_TRUCK_ARRIVAL = "2";
	// 取消车辆放行
	public static final String JOB_TRUCK_DEPART_CANCLE = "3";
	// 取消车辆到达
	public static final String JOB_TRUCK_ARRIVAL_CANCLE = "4";
	// 卸车(PDA)
	public static final String JOB_TRUCK_UNLOAD_PDA = "PDA";
	// 卸车(MANUAL)
	public static final String JOB_TRUCK_UNLOAD_MANUAL = "MANUAL";

	/************ JOB状态 **************/
	// 未处理
	public static final String JOB_NOT_START = "1";
	// 处理中
	public static final String JOB_START = "2";
	// 处理完毕
	public static final String JOB_END = "3";

	/************ JOB主键sequence **************/
	public static final String SEQ_TRUCK_ACTION_JOB = "tfr.SEQ_TRUCK_ACTION_DETAIL.NEXTVAL";

	/********** manual or pda not null ***********/
	// 传如这个值表示纸质放行或者PDA放行的条件有效
	public static final String MANUAL_OR_PDA_NOT_NULL = "code";

	/********** 记录PDA发车时间 （状态） ***********/
	// PDA传入的车可以放行
	public static final String PDA_CAN_DEPART = "1";
	// PDA传入的车不能放行
	public static final String PDA_CAN_NOT_DEPART = "2";
	/********** 记录PDA到达时间 （状态） ***********/
	// PDA传入的车成功到达
	public static final String PDA_CAN_ARRIVAL = "1";
	// PDA传入的车到达失败
	public static final String PDA_CAN_NOT_ARRIVAL = "2";

	/********** 定时任务处理绑定表时，存在互相抵消的情况 ***********/
	// 互相抵消的情况
	public static final String ACTION_TASK_OFFSET = "1";
	// 不互相抵消的情况
	public static final String ACTION_TASK_NOT_OFFSET = "2";

	// 已出库
	public static final String OUT_STOCK = "OUT_STOCK";
	// 库存中
	public static final String IN_STOCK = "IN_STOCK";

	/********** 自动放行类型 ***********/
	// 封签
	public static final String AUTO_DEPART_TYPE_SEAL = "1";
	// 接送货
	public static final String AUTO_DEPART_TYPE_DELIVERBILL = "2";
	// 约车
	public static final String AUTO_DEPART_TYPE_ABOUT_CARS = "3";

	/********** PDA绑定状态 ***********/
	// 已绑定
	public static final String PDA_BUND_YES = "BUNDLE";
	// 已解绑
	public static final String PDA_BUND_NO = "UNBUNDLE";

	/*********** 实际放行（到达）类型 *************/
	/**
	 * 人工放行-MANUAL，PDA放行-PDA，GPS放行-GPS，人工出发确认-MANUAL_CONFIRM, FOSS自动到达-FOSS
	 * 当当前到达为最终到达时, 自动更新当前主任务下其他子任务为已到达
	 */
	public static final String ACTUAL_DEPART_TYPE_FOSS = "FOSS";
	public static final String ACTUAL_DEPART_TYPE_MANUAL = "MANUAL";
	public static final String ACTUAL_DEPART_TYPE_PDA = "PDA";
	public static final String ACTUAL_DEPART_TYPE_GPS = "GPS";
	public static final String ACTUAL_DEPART_TYPE_MANUAL_CONFIRM = "MANUAL_CONFIRM";

	/*********** 车辆状态 *************/
	// 待出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED,中途到达-HALFWAY_ARRIVE
	public static final String ARRIVAL_VEHICLE_UNDEPART = "UNDEPART";
	public static final String ARRIVAL_VEHICLE_ONTHEWAY = "ONTHEWAY";
	public static final String ARRIVAL_VEHICLE_HALFWAY_ARRIVE = "HALFWAY_ARRIVE";
	public static final String ARRIVAL_VEHICLE_ARRIVED = "ARRIVED";
	public static final String ARRIVAL_VEHICLE_CANCLED = "CANCLED";

	/******* 到达或者撤销到达时往PKP的job表插一条记录 ****/
	// 到达A，取消到达C,空运代理F
	public static final String ARRIVAL_FOR_PKP = "A";
	public static final String ARRIVAL_FOR_PKP_ARI = "F";
	public static final String CANCLE_ARRIVAL_FOR_PKP = "C";

	/*********** 重量的单位 *************/
	public static final String VEHICLE_UNIT_OF_WEIGHT = "吨/";
	
	/*********** 重量的单位 *************/
	public static final String VEHICLE_UNIT_OF_KG = "千克/";

	/*********** 体积的单位 *************/
	public static final String VEHICLE_UNIT_OF_VOLUME = "立方";

	/*********** 停车原因 *************/
	// 事故-10，违章-20，保养-30，审计-40,车体广告-50，,维修-69,更新-70,过户-80
	public static final String PARKING_REASON_ACCIDENT = "10";
	public static final String PARKING_REASON_VIOLATE = "20";
	public static final String PARKING_REASON_AMINTEN = "30";
	public static final String PARKING_REASON_AUDIT = "40";
	public static final String PARKING_REASON_CARADS = "50";
	public static final String PARKING_REASON_SERVICE = "69";
	public static final String PARKING_REASON_UPDATES = "70";
	public static final String PARKING_REASON_TRANSFER = "80";

	/*********** 执行JOB的标识 *************/
	// 更新走货路径的状态-CALCULATE_TRANSPORT_PATH，更新交接单、配载单的状态-BILL_STATUS、入库-INSTOCK、计算月台-PLATFORM
	public static final String TASK_FLAG_CALCULATE_TRANSPORT_PATH = "CALCULATE_TRANSPORT_PATH";

	public static final String TASK_FLAG_BILL_STATUS = "BILL_STATUS";

	public static final String TASK_FLAG_INSTOCK = "INSTOCK";

	public static final String TASK_FLAG_PLATFORM = "PLATFORM";
	
	/***************一个小时有几分钟****************/
	public static final int SIXTEEN = 60;
	
	
	/**
	 * 车辆出发到达操作类型-取消出发
	 */
	public static final String ACTION_TYPE_CANCEL_DEPART = "CANCEL_DEPART";

	/**
	 * 车辆出发到达操作类型-取消到达
	 */
	public static final String ACTION_TYPE_CANCEL_ARRIVAL = "CANCEL_ARRIVAL";
	
	/**
	 * 派送放行后发送短信-短信模板编码
	 */
	public static final String SMS_TEMPLATE_CODE_DELIVER_DEPART = "TFR_DELIVERDEPART";
}