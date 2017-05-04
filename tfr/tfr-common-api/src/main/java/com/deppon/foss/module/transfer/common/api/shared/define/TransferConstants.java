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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/define/TransferConstants.java
 *  
 *  FILE NAME          :TransferConstants.java
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
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.define;

public class TransferConstants {
	/**
	 * 创建卸车任务发短信 限制的业务时间段08:00-17:30
	 */
	public static final String BUSINESSTIME="0800-1730";
	
	/**导出文件时指定的各SHEET最大数据量*/
	public static final int EXPORT_FILE_MAX_ROW = 10000;
	/**字段为空时的代替输入值*/
	public static final String NULL_VALUE = "N/A";
	/**字符默认分隔符*/
	public static final String DEFAULT_SPLIT_STRING = ":";
	
	/** JOB处理结果*/
	/** 执行成功 */
	public static final String JOB_SUCCESS = "SUCCESS";
	/** 执行失败 */
	public static final String JOB_FAILURE = "FAILURE";
	
	/** 清仓状态  **/
	/** 清仓中 */
	public static final String STOCK_CHECKING_DOING  = "DOING";
	/** 清仓完毕 */
	public static final String STOCK_CHECKING_DONE   = "DONE";
	/** 已取消 */
	public static final String STOCK_CHECKING_CANCEL = "CANCEL";
	/** 未清仓 */
	public static final String STOCK_CHECKING_HAVENOT = "HAVENOT";
	
	/** 清仓差异报告状态 **/
	/** 处理中 */
	public static final String STOCK_CHECKING_REPORT_DOING = "DOING";
	/** 处理完毕 */
	public static final String STOCK_CHECKING_REPORT_DONE  = "DONE";
	
	/**扫描处理结果**/
	/**已扫描*/
	public static final String SCAN_DONE = "DONE";
	/**未扫描*/
	public static final String SCAN_HAVENOT = "HAVENOT";
	/**手工输入*/
	public static final String SCAN_MANUAL = "MANUAL";
	
	/**清仓比对结果、以及PDA返回结果*/
	/**正常*/
	public static final String GOODS_STATUS_OK = "OK";
	/**少货  此状态PDA无法返回*/
	public static final String GOODS_STATUS_LACK = "LACK";
	/**多货*/
	public static final String GOODS_STATUS_SURPLUS = "SURPLUS";
	
	
	/**
	 * 出发丢货
	* @fields LOSE_STARTING
	* @author 14022-foss-songjie
	* @update 2015年1月6日 下午1:58:29
	* @version V1.0
	*/
	public static final String LOSE_STARTING="LOSE_STARTING";
	/**以下三种状态为多货的三种不同情况，用于清仓差异报告明细中*/
	/**放错货区*/
	public static final String GOODS_STATUS_SURPLUS_ERROR_GOODSAREA = "ERROR_GOODSAREA";
	/**多货-夹带*/
	public static final String GOODS_STATUS_SURPLUS_CARRY = "CARRY";
	/**多货-异地夹带*/
	public static final String GOODS_STATUS_SURPLUS_CARRY_OTHERS = "CARRY_OTHERS";
	
	/**清仓差异原因**/
	/**放错货区*/
	public static final String DIFFERENCE_REASON_FCHQ = "FCHQ";
	/**装车漏扫*/
	public static final String DIFFERENCE_REASON_ZCLS = "ZCLS";
	/**清仓漏扫**/
	public static final String DIFFERENCE_REASON_QCLS ="QCLS";
	/**签收未出库*/
	public static final String DIFFERENCE_REASON_QSWCK = "QSWCK";
	/**其他*/
	public static final String DIFFERENCE_REASON_QT = "QT";
	
	/**清仓差异类型新增“已签收”、“已作废“、“中止作废”、“更改目的站”的差异类型*/
	//已签收
	public static final String GOODS_STATUS_SIGN = "SIGN";
	//已作废
	public static final String GOODS_STATUS_OBSOLETE = "OBSOLETE";
	//中止作废
	public static final String GOODS_STATUS_ABORTED = "ABORTED";
	//更改目的站
	public static final String GOODS_STATUS_RFC_DEST = "RFC_DEST";
	
	
	/**存在异常的情况,包括多货/少货，只使用在清仓差异报告查询中*/
	public static final String EXIST_EXCEPTION = "EXIST";
	
	/**查询清仓差异报告明细中异常类型相互匹配的数据，只查询30天内的数据 */
	public static final int QUERY_RELATIVE_SCAN_RESULT_LIMITED_DAYS = 30;
	
	/**车辆状态 出发*/
	public static final String VEHICLE_STATUS_DEPARTURE = "DEPARTURE";
	/**车辆状态 到达*/
	public static final String VEHICLE_STATUS_ARRIVE = "ARRIVE";
	
	/**
	 * 外围接口编码
	 */
	//GPS上传任务车辆信息
	public static final String GPS_TASK_VEHICLE_SERVICE_CODE = "ESB_FOSS2ESB_TRANSMIT_VEHICLE";
	//GPS上传任务车辆信息-短途
	public static final String GPS_SYNC_VEHTASK_CODE = "ESB_FOSS2ESB_SYNC_VEHTASK";
	//LMS更新车辆状态
	public static final String LMS_VEHICLE_STATE_SERVICE_CODE = "ESB_FOSS2ESB_UPDATE_VEHICLESTATE";
	//LMS修改车辆行驶数据
	public static final String LMS_VEHICLE_TRAVEL_DATA_SERVICE_CODE = "ESB_FOSS2ESB_DRIVE_KM";
	//OA上报无标签多货
	public static final String OA_NO_LABEL_SERVICE_CODE = "ESB_FOSS2ESB_REPORT_NOLABEL";
	//OA上报少货
	public static final String OA_LESS_GOODS_SERVICE_CODE = "ESB_FOSS2ESB_REPORT_CLEARLESS";
	//OA上报多货
	public static final String OA_MORE_GOODS_SERVICE_CODE = "ESB_FOSS2ESB_REPORT_CLEARMORE";
	//OA上报封签差错
	public static final String OA_SLIP_ERROR_SERVICE_CODE = "ESB_FOSS2ESB_REPORT_SLIPERROR";
	//OA上报少货找到
	public static final String OA_LESS_GOODS_FOUND_SERVICE_CODE = "ESB_FOSS2ESB_REPORT_LESSFOUND";
	//OA卸货少货处理状态
	public static final String OA_UNLOAD_ERROR_STATUS_SERVICE_CODE = "ESB_FOSS2ESB_QUERY_UNLOADDIFF";
	//OA违禁品
	public static final String OA_CONTRABAND_GOODS_SERVICE_CODE = "ESB_FOSS2ESB_QUERY_CONTRABAND";
	//无标签转弃货 OA处理状态
	public static final String OA_NO_LABEL_UPDATE_STATEOFGOODS ="ESB_FOSS2ESB_UPDATE_STATEOFGOODS";
	//QMS上报卸车多货
	public static final String QMS_ESB_CODE_UNLOADMOREGOODS = "ESB_FOSS2ESB_REPORT_MORE_CARGO2QMS";
	//QMS上报分批配载
	public static final String QMS_ESB_CODE_BATCHLOADING = "ESB_FOSS2ESB_LOADING_TO_REPORT2QMS";
	
	/**EDI接口上传合票清单常量*/
	//邮件夹名称
	public static final String EDI_SUMBILLREQUEST_MAILFOLDERNAME = "清单夹";
	//提醒标志
	public static final String EDI_SUMBILLREQUEST_NOTICEFLAG = "1";
	//已读标志
	public static final String EDI_SUMBILLREQUEST_READFLAG = "";
	//邮件标志
	public static final String EDI_SUMBILLREQUEST_MAILFLAG = "1";
	//优先级别
	public static final String EDI_SUMBILLREQUEST_PRIORITYLEVEL = "1";
	
	/**ESB头版本信息*/
	//ACCESSHEADER
	public static final String ESB_ACCESSHEADER_VERSION = "1.0";
	//ESBHEADER
	public static final String ESB_ESBHEADER_VERSION = "1.0";
	//ExchangePattern
	public static final int ESB_EXCHANGEPATTERN = 1;
	
	/**
	 * 少货找到，差异报告类型
	 */
	//卸车少货
	public static final String REPORT_TYPE_UNLOAD = "UNLOAD";
	//清仓少货
	public static final String REPORT_TYPE_ST = "ST";
	//出发丢货 Lose_Starting
	public static final String REPORT_TYPE_LS = "LS";
	
	/**
	 * FOSS匹配责任部门
	 * */
	//目的站 targetOrgCode
	public static final String  CRM_RESPONBILITY_WAYBILL_TARGETORGCODE="targetOrgCode"; 
	//运输性质 productCode
	public static final String CRM_RESPONBILITY_WAYBILL_PRODUCTCODE="productCode";
	
	/**
	 * FOSS与100交互，异常信息
	 */
	//FOSS推送代理单号给100时，100返回的异常信息
	public static final String EXP_FOSS2100_AGENTWAYBILLNO = "FOSS2100AGENTWAYBILLNO";
	//100推送轨迹给FOSS时，100轨迹的异常信息
	public static final String EXP_1002FOSS_AGENTWAYBILLTRACK = "1002FOSSAGENTWAYBILLTRACK";
		
	/**
	 * tps约车审核 信息部 名称
	 */
	public static final String TPS_INFODEPT_NAME = "运力采购系统";
	
	//自动签收 272681 2015-7-15
	public static final String AUTO_SIGN = "自动签收";	
	//快递空运产品类型(商务专递)
	public static final String PRODUCT_EXPRESS_AIR="DEAP";
	
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_0 = 0;
	public final static int SONAR_NUMBER_1 = 1;
	public final static int SONAR_NUMBER_2 = 2;
	public final static int SONAR_NUMBER_3 = 3;
	public final static int SONAR_NUMBER_4 = 4;
	public final static int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public static final int SONAR_NUMBER_10 = 10;
	public static final int SONAR_NUMBER_12 = 12;
	public static final int SONAR_NUMBER_20 = 20;
	public static final int SONAR_NUMBER_24 = 24;
	public static final int SONAR_NUMBER_30 = 30;
	public static final int SONAR_NUMBER_50 = 50;
	public static final int SONAR_NUMBER_60 = 60;
	public static final int SONAR_NUMBER_200 = 200;
	public static final int SONAR_NUMBER_500 = 500;
	public static final int SONAR_NUMBER_900 = 900;
	public static final int SONAR_NUMBER_1000 = 1000;
	public static final int SONAR_NUMBER_3000 = 3000;
	public static final int SONAR_NUMBER_3600 = 3600;
	public static final int SONAR_NUMBER_4000 = 4000;
	public static final int SONAR_NUMBER_10000 = 10000;
	public static final int SONAR_NUMBER_20000 = 20000;
	public static final int SONAR_NUMBER_40000 = 40000;
	public static final int SONAR_NUMBER_60000 = 60000;


}