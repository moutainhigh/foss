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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/define/TransferPDADictConstants.java
 *  
 *  FILE NAME          :TransferPDADictConstants.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.define
 * FILE    NAME: TransferPDADictConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.define;


/**
 * 中转提供给PDA的接口参数所需要的数据字典清单
 * @author 046130-foss-xuduowei
 * @date 2012-12-11 上午8:29:54
 */
public class TransferPDADictConstants {
	
	/**
	 * 包装材料常量
	 */
	//木架
	public final static String PACKAGING_MATE_WOODEN_FRAME = "WOODEN_FRAME";
	//木箱
	public final static String PACKAGING_MATE_WOOED_BOX = "WOOED_BOX";
	//纸箱
	public final static String PACKAGING_MATE_PAPER_BOX = "PAPER_BOX";
	//线袋
	public final static String PACKAGING_MATE_FBRE_BAG = "FBRE_BAG";
	
	/**
	 * 入库类型常量
	 */
	//少货找到
	public static final String LOSE_GOODS_FOUND_IN_STOCK_TYPE = "LOSS_GOODS_FIND";
	
	//异常签收
	public static final String EXCEPTION_SIGN_IN_STOCK_TYPE = "SIGN_EXCEPTION";
	
	//偏线拉回  
	public static final String PARTIALLINE_RETURN_IN_STOCK_TYPE = "PARTIALLINE_RETURN";
	
	//空运拉回
	public static final String AIR_FREIGHT_RETURN_SIGN_IN_STOCK_TYPE = "AIR_RETURN";
	
	//派送拉回
	public static final String SEND_RETURN_IN_STOCK_TYPE = "SEND_RETURN";
	
	/**
	 * 清仓任务常量
	 */
	//正在执行清仓任务
	public static final String ST_TASK_STATUS_DOING = "DOING";
	//完成清仓任务分支
	public static final String ST_TASK_STATUS_BRANCH_FINISH = "BRANCH_FINISH";
	//撤销清仓任务
	public static final String ST_TASK_STATUS_CANCEL = "CANCEL";
	
	/**
	 * 清仓扫描状态常量
	 */
	//正常
	public static final String ST_TASK_SCAN_STATUS_OK = "OK";
	//多货
	public static final String ST_TASK_SCAN_STATUS_SURPLUS = "SURPLUS";
	//撤销
	public static final String ST_TASK_SCAN_STATUS_CANCEL = "CANCEL";
	
	/**
	 * 清仓差异处理常量
	 */
	public static final String ST_REPORT_HANDLE_STATUS_DOING = "DOING";
	public static final String ST_REPORT_HANDLE_STATUS_DONE = "DONE";
	
	/**
	 * 装车任务类型常量
	 */
	//派送装车-DELIVER_LOAD
	public static final String LOAD_TASK_TYPE_DELIVER = "DELIVER_LOAD";
	//偏线装车-PARTIALLINE_LOAD
	public static final String LOAD_TASK_TYPE_PARTIALLINE = "PARTIALLINE_LOAD";
	//长途装车-LONG_DISTANCE_LOAD
	public static final String LOAD_TASK_TYPE_LONG_DISTANCE = "LONG_DISTANCE_LOAD";
	//短途装车-SHORT_DISTANCE_LOAD
	public static final String LOAD_TASK_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE_LOAD";
	//营业部装车-SHORT_DISTANCE_LOAD
	public static final String LOAD_TASK_TYPE_SALES_DISTANCE = "SALES_DEPARTMENT_LOAD";
	//落地配装车-LDP_LOAD
	public static final String LOAD_TASK_TYPE_LDP = "LDP_LOAD";
	//分部装车-DIVISION_LOAD
	public static final String LOAD_TASK_TYPE_DIVISION = "DIVISION_LOAD";
	/**
	 * 货物类型常量
	 */
	//A货-A_TYP   	           
	public static final String LOAD_GOODS_TYPE_A = "A_TYPE";
	//B货-B_TYPE
	public static final String LOAD_GOODS_TYPE_B = "B_TYPE";
	//全部-ALL
	public static final String LOAD_GOODS_TYPE_ALL = "ALL";
	
	/**装车货物状态*/
	//正常
	public static final String LOAD_GOODS_STATE_NORMAL = "NORMAL";
	//取消
	public static final String LOAD_GOODS_STATE_CANCELED = "CANCELED";
	//强装-未包装
	public static final String LOAD_GOODS_STATE_EXTRA_UNPACKAGE = "EXTRA_UNPACKAGE";
	//强装-有更改
	public static final String LOAD_GOODS_STATE_EXTRA_MODIFY = "EXTRA_MODIFY";
	//强装-未出包装货区
	public static final String LOAD_GOODS_STATE_EXTRA_PACKAGE_UNOUT_STORAGE = "EXTRA_PACKAGE_UNOUT_STORAGE";
	//强装-未出贵重物品货区
	public static final String LOAD_GOODS_STATE_EXTRA_VALUABLES_UNOUT_STORAGE = "EXTRA_VALUABLES_UNOUT_STORAGE";
	//多货-夹带
	public static final String LOAD_GOODS_STATE_MORE_ENTRAINED = "MORE_ENTRAINED";
	//多货-异地夹带
	public static final String LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED = "MORE_ALLOPATRY_ENTRAINED";
	//强装-未预配
	public static final String LOAD_GOODS_STATE_EXTRA_UNPREWIRED = "EXTRA_UNPREWIRED";
	//无效
	public static final String LOAD_GOODS_STATE_INVALID = "INVALID";
	//强装-强装
	public static final String LOAD_GOODS_STATE_EXTRA = "EXTRA";
	//强装-夹带
	public static final String LOAD_GOODS_STATE_EXTRA_ENTRAINED = "EXTRA_ENTRAINED";
	//强装-异地夹带
	public static final String LOAD_GOODS_STATE_EXTRA_ALLOPATRY_ENTRAINED = "EXTRA_ALLOPATRY_ENTRAINED";
	//未装车
	//public static final String LOAD_GOODS_STATE_NOT_LOADING = "NOT_LOADING";
	//少货
	//public static final String LOAD_GOODS_STATE_LACK = "LACK";
	
	/**
	 * 卸车货物状态
	 */
	//多货
	public static final String UNLOAD_GOODS_STATE_MORE = "MORE";
	//正常
	public static final String UNLOAD_GOODS_STATE_NORMAL = "NORMAL";
	//取消
	public static final String UNLOAD_GOODS_STATE_CANCELED = "CANCELED";
	
	/**扫描状态*/
	//已扫描-SCANED
	public static final String SCAN_STATE_SCANED = "SCANED";
	//手输-BY_HAND
	public static final String SCAN_STATE_BY_HAND = "BY_HAND";
	/**卸车多货单据编号*/
	public static final String UNLOAD_MORE_BILL_NO = "多货";
	
	/**
	 * 理货员
	 */
	//添加理货员
	public static final String ADD_LOADR = "ADD";
	//删除理货员
	public static final String DETELE_LOADR = "DETELE";
	
	/**
	 * 返回结果
	 */
	//成功
	public static final String SUCCESS = "success";
	//失败
	public static final String FAIL = "fail";
	
	/**
	 * 卸车扫描常量
	 */
	//卸车扫描-夹带-上一部门有待办事项
	public static final String UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_HAS_TODOLIST = "ENTRAINED_HAS_TODOLIST";
	//卸车扫描-夹带-上一部门无待办事项
	public static final String UNLOAD_SCAN_RETURN_SUCCESS_ENTRAINED_NO_TODOLIST = "ENTRAINED_NO_TODOLIST";
	//卸车扫描-异地夹带-上一部门有待办事项
	public static final String UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_HAS_TODOLIST = "ALLOPATRY_ENTRAINED_HAS_TODOLIST";
	//卸车扫描-异地夹带-上一部门无待办事项
	public static final String UNLOAD_SCAN_RETURN_SUCCESS_ALLOPATRY_ENTRAINED_NO_TODOLIST = "ALLOPATRY_ENTRAINED_NO_TODOLIST";
	
	/**
	 * 卸车指令类型
	 */
	//交接单
	public static final String UNLOAD_ORDER_TYPE_HANDOVER = "HANDOVER";
	//配载单
	public static final String UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE = "VEHICLEASSEMBLE";
	//派送拉回单
	public static final String UNLOAD_ORDER_TYPE_GOODS_BACK = "GOODS_BACK";
	//接货单
	public static final String UNLOAD_ORDER_TYPE_RECEIVE = "RECEIVE";
	//航空正单
	public static final String UNLOAD_ORDER_TYPE_AIRBILL = "AIRBILL";
	//电子运单
	public static final String UNLOAD_ORDER_TYPE_ELECBILL = "CREATE_PDA_BSE_PIC";
	/**zwd 200968 快递集中卸货*/
	public static final String UNLOAD_ORDER_TYPE_EWAYBILL = "EWAYBILL";
	//二程接驳单
	public static final String UNLOAD_ORDER_TYPE_SCBILL="SCBILL";
	/**
	 * 封签常量
	 */
	//后门封签 
	public static final String SEAL_TYPE_DETAIL_BACK = "BACK";
	//侧门封签
	public static final String SEAL_TYPE_DETAIL_SIDE = "SIDE";
	//封签状态-未检查
	public static final String SEAL_STATE_UNCHECK = "UNCHECK";
	//封签状态-正常
	public static final String SEAL_STATE_NORMAL = "NORMAL";
	//封签状态-异常
	public static final String SEAL_STATE_EXCEPTION = "EXCEPTION";
	//封签状态-破损
	public static final String SEAL_STATE_DAMAGED = "DAMAGED";
	//封签类型-绑定装车封签
	public static final String SEAL_TYPE_BIND = "BIND";
	//封签类型-校验卸车封签 
	public static final String SEAL_TYPE_CHECK = "CHECK";
	 
	/**
	 * 货件状态
	 */
	//已入外场库区
	public final static String PACKAGING_SERIAL_ORG_STOCK = "已入外场库区";
	//已入打木架库区
	public final static String PACKAGING_SERIAL_WOOD_STOCK = "已入打木架库区";
	/**
	 * 更改状态
	 */
	//无更改
	public final static String MODIFY_STATE_NO_CHANGE = "NO_CHANGE";
	//其他更改
	public final static String MODIFY_STATE_OTHEN_CHANGE = "OTHEN_CHANGE";
	//七种更改已受理
	public final static String MODIFY_STATE_LABLEGOODS_ACCECPT = "LABLEGOODS_ACCECPT";
	//七种更改未受理
	public final static String MODIFY_STATE_LABLEGOODS_UNACCECPT = "LABLEGOODS_UNACCECPT";
	/**
	 * 快递包
	 */
	/**任务状态*/
	//未开始
	public final static String EXPRESS_PACKAGE_STATE_UNSTART = "UNSTART";
	//进行中
	public final static String EXPRESS_PACKAGE_STATE_PROGRESS = "PROGRESS";
	//已取消
	public final static String EXPRESS_PACKAGE_STATE_CANCELED = "CANCELED";
	//已完成
	public final static String EXPRESS_PACKAGE_STATE_FINISHED = "FINISHED";
	/**货物状态*/
	//正常
	public final static String EXPRESS_PACKAGE_GOODS_STATE_NORMAL = "NORMAL";
	//多货
	public final static String EXPRESS_PACKAGE_GOODS_STATE_MORE = "MORE";
	//取消
	public final static String EXPRESS_PACKAGE_GOODS_STATE_CANCELED = "CANCELED";
	/**
	 * 分拣扫描
	 * */
	//扫描类型:上分拣
	public final static String SORTING_SCAN_TYPE_UP = "UP";
	//扫描类型:下分拣
	public final static String SORTING_SCAN_TYPE_DOWN = "DOWN";
	
	/**
	 * 快递包类型
	 * */
	//扫描类型
	public final static String EXPRESS_PACKAGE_TYPE_THROUGH_ARRIVE = "THROUGH_ARRIVE";//直达包
	//扫描类型
	public final static String EXPRESS_PACKAGE_TYPE_NORMAL_ARRIVE = "NORMAL_ARRIVE";//普通包
	//扫描类型
	public final static String EXPRESS_PACKAGE_TYPE_KD_AIR="EXPRESS_AIR";//快递空运
	//扫描类型
	public final static String EXPRESS_PACKAGE_TYPE_AIR_THROUGH_ARRIVE="AIRTHROUGH_ARRIVE";//空运直达包
	
	
	
	/**
	 * 托盘操作(扫描)类型
	 * */
	//手动拉车
	public final static String TRAYSCAN_HAND_TYPE="HANDSCAN";
	//叉车分货
	public final static String TRAYSCAN_FORK_TYPE="UNSCAN";
	
	/**
	 * pda获取预分配月台 业务类型:
	 * 长途卸车
	 * 短途卸车
	 * 接送货
	 * 
	 * */
	public final static String UNLOAD_BUSINESS_TYPE_LONG_DISTANCE="LONG_DISTANCE";//长途卸车
	
	public final static String UNLOAD_BUSINESS_TYPE_SHORT_DISTANCE="SHORT_DISTANCE";//短途卸车
	
	public final static String UNLOAD_BUSINESS_TYPE_DIVISION="DIVISION";//接送货

	//待卸车辆入场情况
	public final static String UNLOAD_VEHICLE_ENTER_TYPE_IN="IN";//入场
	
	public final static String UNLOAD_VEHICLE_ENTER_TYPE_OUT="OUT";//未入场
	
	
	
	
}
