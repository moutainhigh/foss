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
package com.deppon.foss.module.transfer.platform.api.shared.define;

/**
 * 卸车模块常量
 * @author dp-duyi
 * @date 2012-11-1 上午11:51:32
 */
public class UnloadPlatformConstants {
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
	//偏线
	public static final String UNLOAD_TASK_TYPE_PARTIALLINE = "PARTIALLINE";
	//长途
	public static final String UNLOAD_TASK_TYPE_LONG_DISTANCE = "LONG_DISTANCE";
	//短途
	public static final String UNLOAD_TASK_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE";
	/**全部:查询使用*/
	public static final String UNLOAD_TASK_TYPE_ALL = "ALL";
	
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
	
	/**卸车方式*/
	//PDA卸车
	public static final String UNLOAD_TASK_WAY_PDA = "PDA";
	//无PDA卸车
	public static final String UNLOAD_TASK_WAY_NO_PDA = "NO_PDA";
	
	/**扫描状态*/
	//扫描
	//public static final String UNLOAD_TASK_SCAN_STATUS_SCAN = "SCANED";
	//手输
	//public static final String UNLOAD_TASK_SCAN_STATUS_INPUT = "BY_HAND";
	//不适用（手动新增卸车任务时）
	public static final String UNLOAD_TASK_SCAN_STATUS_NA = "N/A";
	
	/**单据类型：HANDOVER-交接单，VEHICLEASSEMBLE-汽运配载单,接送货交接-PICKUP*/
	public static final String BILL_TYPE_HANDOVER = "HANDOVER";
	public static final String BILL_TYPE_VEHICLEASSEMBLE = "VEHICLEASSEMBLE";
	public static final String BILL_TYPE_PICKUP = "PICKUP";
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
	/**
	 * 卸车车进度-未卸车明细=== 卡货
	* @fields NO_UNLOAD_GOODS_DETAIL_KH
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:03:48
	* @version V1.0
	*/
	public static final String NO_UNLOAD_GOODS_DETAIL_KH = "FLF";
	
	/**
	 * 卸车车进度-未卸车明细=== 城际
	* @fields NO_UNLOAD_GOODS_DETAIL_CJ
	* @author 14022-foss-songjie
	* @update 2014年4月16日 下午5:04:08
	* @version V1.0
	*/
	public static final String NO_UNLOAD_GOODS_DETAIL_CJ = "FSF";
	
	/**  导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	
	/**卸车车进度-未卸车明细=== 卡货*/
	public static final String[] NO_UNLOAD_GOODS_DETAIL_KH_ROW_HEADS = {"运单号","开单件数","交接件数","未卸件数","开单重量(公斤)","开单体积(方)","下一目的站"};
	/**卸车车进度-未卸车明细=== 城际*/
	public static final String[] NO_UNLOAD_GOODS_DETAIL_CJ_ROW_HEADS = {"运单号","开单件数","交接件数","未卸件数","开单重量(公斤)","开单体积(方)","下一目的站"};
	
	/**
	 * 卸车车进度-未卸车明细=== 卡货
	* @fields SEND_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:27:22
	* @version V1.0
	*/
	public static final String NO_UNLOAD_GOODS_DETAIL_KH_SHEET_NAME = "卡货未卸车明细";
	
	/**
	 * 卸车车进度-未卸车明细=== 城际
	* @fields NO_UNLOAD_GOODS_DETAIL_CJ_SHEET_NAME
	* @author 14022-foss-songjie
	* @update 2014年4月19日 上午10:27:22
	* @version V1.0
	*/
	public static final String NO_UNLOAD_GOODS_DETAIL_CJ_SHEET_NAME = "城际未卸车明细";
}