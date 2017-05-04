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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/define/UnloadConstants.java
 *  
 *  FILE NAME          :UnloadConstants.java
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
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.define
 * FILE    NAME: UnloadConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.define;

/**
 * 卸车模块常量
 * @author dp-duyi
 * @date 2012-11-1 上午11:51:32
 */
public class UnloadConstants {
	/**
	 * 短信发送目标-卸车发短信
	 */
	public static final String SMS_UNLOAD_TASK = "SMS_UNLOAD_TASK";
	/**批量处理条数*/
	public static final int BATCH_COUNT = 100;
	public static final String BATCH_SAVE_TYPE_INSERT = "INSERT";
	public static final String BATCH_SAVE_TYPE_UPDATE = "UPDATE";
	/**已分配卸车任务状态*/
	//未开始
	public static final String ASSIGN_UNLOAD_TASK_STATE_UNSTART = "UNSTART";
	//进行中
	public static final String ASSIGN_UNLOAD_TASK_STATE_PROCESSING = "PROCESSING";
	//已完成
	public static final String ASSIGN_UNLOAD_TASK_STATE_FINISHED = "FINISHED";
	//已取消
	public static final String ASSIGN_UNLOAD_TASK_STATE_CANCELED = "CANCELED";
	/**全部:查询使用*/
	public static final String ASSIGN_UNLOAD_TASK_STATE_ALL = "ALL";
	
	// zwd 200968  运单生效状态 - YES NO
	public static final String WAYBILL_STATUS_YES = "YES";
	public static final String WAYBILL_STATUS_NO = "NO";
		
	/**卸车任务状态*/
	//卸车中
	public static final String UNLOAD_TASK_STATE_UNLOADING = "UNLOADING";
	//已完成
	public static final String UNLOAD_TASK_STATE_FINISHED = "FINISHED";
	//已取消
	public static final String UNLOAD_TASK_STATE_CANCELED = "CANCELED";
	
	/**卸车任务类型*/
	//接送货
	public static final String UNLOAD_TASK_TYPE_DELIVER = "DELIVER";
	//快递空运
	public static final String UNLOAD_TASK_TYPE_PACKAGE = "PACKAGE_AIR";
	//偏线
	public static final String UNLOAD_TASK_TYPE_PARTIALLINE = "PARTIALLINE";
	//长途
	public static final String UNLOAD_TASK_TYPE_LONG_DISTANCE = "LONG_DISTANCE";
	//短途
	public static final String UNLOAD_TASK_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE";
	//快递集中卸车：chenmingyan
	public static final String UNLOAD_TASK_TYPE_EXPRESS = "EXPRESS_PICK";
	//二程接驳司机卸车
	public static final String UNLOAD_TASK_TYPE_DRIVER = "EXPRESS_DRIVER";
	//二程接驳卸车  hongwy-foss 218427
	public static final String UNLOAD_TASK_TYPE_SCEXPRESS="SC_EXPRESS";
	/**全部:查询使用*/
	public static final String UNLOAD_TASK_TYPE_ALL = "ALL";
	/** 310248 卸车单据类型 配载单  */
	public static final String UNLOAD_TASK_BILL_TYPE = "VEHICLEASSEMBLE" ; 
	//营业部卸车
	public static final String UNLOAD_TASK_TYPE_DEPARTMENT="SALES_DEPARTMENT";
	
	
	/**到达单据状态*/
	//已分配
	public static final String ARRIVE_BILL_STATE_ASSIGNED = "ASSIGNED";
	//未分配
	public static final String ARRIVE_BILL_STATE_UNASSIGN = "UNASSIGN";
	
	/**卸车异常类型*/
	//多货
	public static final String UNLOAD_EXCEPTION_TYPE_MOREGOODS = "MOREGOODS";
	//少货
	public static final String UNLOAD_EXCEPTION_TYPE_LACKGOODS = "LACKGOODS";
	//手输 chigo
	public static final String UNLOAD_EXCEPTION_TYPE_BYHANDGOODS = "BYHANDGOODS";
	
	/**卸车方式*/
	//PDA卸车
	public static final String UNLOAD_TASK_WAY_PDA = "PDA";
	//无PDA卸车
	public static final String UNLOAD_TASK_WAY_NO_PDA = "NO_PDA";
	
	/**扫描状态*/
	//扫描
	//public static final String UNLOAD_TASK_SCAN_STATUS_SCAN = "SCANED";
	//手输 chigo
	public static final String UNLOAD_TASK_SCAN_STATUS_INPUT = "BY_HAND";
	//不适用（手动新增卸车任务时）
	public static final String UNLOAD_TASK_SCAN_STATUS_NA = "N/A";
	
	/**单据类型：HANDOVER-交接单，VEHICLEASSEMBLE-汽运配载单,接送货交接-PICKUP,快递集中卸货-EWAYBILL*/
	public static final String BILL_TYPE_HANDOVER = "HANDOVER";
	public static final String BILL_TYPE_VEHICLEASSEMBLE = "VEHICLEASSEMBLE";
	public static final String BILL_TYPE_PICKUP = "PICKUP";
	public static final String BILL_TYPE_AIRBILL = "AIRBILL";
	/**zwd 200968 快递集中卸货 EWAYBILL*/
	public static final String BILL_TYPE_EWAYBILL = "EWAYBILL";
	/**hwy 218427 二程接驳卸货 SCBILL*/
	public static final String BILL_TYPE_SCBILL = "SCBILL";
	/**sjl 322610 零担电子面单 ELECTWAYBILL*/
	public static final String BILL_TYPE_ELECTWAYBILL = "CREATE_PDA_BSE_PIC";


	/**
	 * 查询卸车进度
	 */
	//逗号
	public static final String QUERY_UNLOAD_PROGRESS_COMMA = ",";
	//斜杠
	public static final String QUERY_UNLOAD_PROGRESS_SLASH = "/";
	//拼接查询条件链接符
	public static final String QUERY_UNLOAD_PROGRESS_UNDERLINE = "_";
	//bigdecimal初始化
	public static final String QUERY_UNLOAD_PROGRESS_ZERO = "0";
	//百分比模式
	public static final String QUERY_UNLOAD_PROGRESS_PERCENT = "##%";
	
	/**卸车货物状态*/
	//少货
	public static final String UNLOAD_GOODS_STATE_LACK = "LACK";
	//多货
	public static final String UNLOAD_GOODS_STATE_MORE = "MORE";
	//正常
	public static final String UNLOAD_GOODS_STATE_NORMAL = "NORMAL";
	//多货夹带
	public static final String UNLOAD_GOODS_STATE_MORE_ENTRAINED = "MORE_ENTRAINED";
	//多货异地夹带
	public static final String UNLOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED = "MORE_ALLOPATRY_ENTRAINED";
	//取消
	public static final String UNLOAD_GOODS_STATE_CANCELED = "CANCELED";
	/**卸车多货单据编号*/
	public static final String UNLOAD_MORE_BILL_NO = "多货";
	/**卸车差异报告处理状态*/
	//未处理
	public static final String UNLOAD_DIFF_HANDLE_STATE_RESOLVING= "RESOLVING";
	//已处理
	public static final String UNLOAD_DIFF_HANDLE_STATE_RESOLVED = "RESOLVED";
	/**托盘任务状态 */
	//建包中
	public static final String UNLOAD_TRAYSCAN_TASK_STATE_UNSCAN = "UNSCAN";
	//已扫描
	public static final String UNLOAD_TRAYSCAN_TASK_STATE_SCANNED = "SCANNED";
	//取消
	public static final String UNLOAD_TRAYSCAN_TASK_STATE_CANCEL = "CANCEL";
	/**标签处理类型*/
	public static final String LABEL_HANDLE_ADD = "ADD";
	public static final String LABEL_HANDLE_DETELE = "DETELE";
	public static final String LABEL_HANDLE_NOTHING = "NOTHING";
	
	/** 上报OA  差异类型*/
	public static final String UNLOAD_LESS_GOODS_REPORT_OA_DIFF_TYPE = "卸车少货";
	public static final String UNLOAD_MORE_GOODS_REPORT_OA_DIFF_TYPE = "卸车多货";
	/** 上报OA  事件经过*/
	public static final String UNLOAD_LESS_GOODS_REPORT_OA_EVENT = "卸车少货";
	public static final String UNLOAD_MORE_GOODS_REPORT_OA_EVENT = "卸车多货";
	/**上报oa多货时，有货无交接的两种类型*/
	public static final String GOODS_SHOULD_NOT_BE_HERE = "非经手部门发现";
	public static final String GOODS_SHOULD_BE_HERE = "经手部门发现";
	
	/***卸车插入临时表的两种类型--***/
	public static final String UNLOAD_FOR_PKP = "U";//创建
	public static final String DELETE_UNLOAD_FOR_PKP = "D";//撤销
	
	/**丢货类型**/
	public static final String DELIVER_UNLOAD_LESS_GOODS = "集中接货卸车丢货";
	public static final String SALEDEPARTMENT_TRANSFERCENTER_UNLOAD_LESS_GOODS = "营业部-外场短途卸车丢货";
	public static final String TRANSFERCENTER_TRANSFERCENTER_UNLOAD_LESS_GOODS = "外场-外场长途卸车丢货";
	public static final String TRANSFERCENTER_SALEDEPARTMENT_UNLOAD_LESS_GOODS= "外场-营业部短途卸车丢货";
	
	/**业务渠道**/
	public static final String SALEDEPARTMENT_BUSINESS_CHANNEL = "营业部";
	public static final String TRANSDEPARTMENT_BUSINESS_CHANNEL = "车队";
	public static final String TRANSFERCENTER_BUSINESS_CHANNEL = "外场";
	
	public static final String UNLOAD_INSTOCK_MSG_JOBID = "N/A";
	
	/**带出预分配月台号
	 * 数据字典中 值名称=Y 表示需校验卸车类型进行分配月台 值名称=N 表示按照现有逻辑走
	 * */
	
	public static final String TFR_UNLOAD_PRE_PLAMFORM_VALE="TFR_UNLOAD_PRE_PLAMFORM_VALE";
	
	/**分拣扫描方式*/
	//PDA扫描
	public static final String SORT_SCAN_MODE_PDA = "PDA";
	//巴枪扫描
	public static final String SORT_SCAN_MODE_BSC = "BSC";
	//是否整票
	/***是整票****/
	public static final String UNLOAD_IS_ENTIRETICKET_YES = "Y";
	/***不是整票******/
	public static final String UNLOAD_IS_ENTIRETICKET_NO = "N";
	//商务专递卸车：272681
	public static final String UNLOAD_TASK_TYPE_BUSINESS = "BUSINESS_AIR";
	//零担电子面单卸车：322610
	public static final String UNLOAD_ELECTRANSPORT = "ELECTRANSPORT";
	/* 2015/9/9  272681 商务专递运输性质*/
	public static final String UNLOAD_TASK_TYPE_TRANSPORT = "PACKAGE_AIR";
	public static final String TRANSPORT_TYPE = "商务专递";
	
	/**272681 商务专递卸货交接单类型 */
	public static final String BILL_TYPE_AIR_HANDOVERTYPE = "PACKAGE_HANDOVER";
	
	/**快递短途*/
	public static final String EXPRESS_SHORT_DISTANCE = "EXPRESS_SHORT_DISTANCE";
	
	/**快递长途*/
	public static final String EXPRESS_LONG_DISTANCE = "EXPRESS_LONG_DISTANCE";
	
	/**快递航空*/
	public static final String EXPRESS_AIR_DISTANCE = "EXPRESS_AIR_DISTANCE";
	
    //272681 卸车状态
	public static final String UNLOAD_TASK_STATE_UNSTART = "UNSTART";
	
	/**
	 * 点单差异报告 状态 ING 处理中 END 作废 VOID
	 */
	public static final String ORDER_DIFFER_REPORT_STATE_END="END";
	/**
	 * 点单差异报告 状态 ING 处理中 END 作废 VOID
	 */
	public static final String ORDER_DIFFER_REPORT_STATE_ING="ING";
	/**
	 * 点单差异报告 状态 ING 处理中 END 作废 VOID
	 */
	public static final String ORDER_DIFFER_REPORT_STATE_VOID="VOID";	
	/**快递零担标识*/
	public static final String EXPRESS="express";
	
	/**参数 交接单编号*/
	public static final String HAND_OVER_BILL_NO="handoverBillNo";
	
	/**参数  卸车任务编号*/
	public static final String UNLOAD_TASK_NO="unloadTaskNo";
	
	/**扫描状态无*/
	public static final String SCAN_NO="N";
	
	/**快递未扫描标识*/
	public static final String NOSCAN="NOSCAN";
	
	/**快递扫描标识*/
	public static final String SCAN="SCAN";
	
	/**取消同步卸车任务到悟空系统*/
	public static final String SYNC_CANCEL_ASSIGN_UNLOAD_TASK_TO_WK="SYNC_CANCEL_ASSIGN_UNLOAD_TASK_TO_WK";
	
	/**同步分配卸车任务到悟空系统*/
	public static final String SYNC_ASSIGN_UNLOAD_TASK_TO_WK="SYNC_ASSIGN_UNLOAD_TASK_TO_WK";
	
	/**同步确认卸车任务到悟空系统*/
	public static final String SYNC_CONFIRM_EXPRESS_UNLOAD_TASK_TO_WK="SYNC_CONFIRM_EXPRESS_UNLOAD_TASK_TO_WK";
	
	/**同步修改卸车任务到悟空系统*/
	public static final String SYNC_UPDATE_EXPRESS_UNLOAD_TASK="SYNC_UPDATE_EXPRESS_UNLOAD_TASK";
	
	/**同步取消卸车任务到悟空系统*/
	public static final String SYNC_CANCEL_UNLOAD_TASK_TO_WK="SYNC_CANCEL_UNLOAD_TASK_TO_WK";
	
	/**同步新建卸车任务到悟空系统*/
	public static final String SYNC_NEW_EXPRESS_UNLOAD_TASK_TO_WK="SYNC_NEW_EXPRESS_UNLOAD_TASK_TO_WK";
	
	/**快递已分配*/
	public static final String EXPRESS_BILL_STATE_ASSIGNED = "ASSINGED";
	
	/**千进位*/
	public static final int KILO_MUTIPLY = 1000;
	
	/**60 进位*/
	public static final int SIXTY_MUTIPLY = 60;
	
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_3 = 3;
	public static final int SONAR_NUMBER_4 = 4;
	public static final int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public final static int SONAR_NUMBER_7 = 7;
	public final static int SONAR_NUMBER_8 = 8;
	public final static int SONAR_NUMBER_9 = 9;
	public final static int SONAR_NUMBER_10 = 10;
	public final static int SONAR_NUMBER_11 = 11;
	public static final int SONAR_NUMBER_14 = 14;
	public static final int SONAR_NUMBER_60 = 60;
	
}