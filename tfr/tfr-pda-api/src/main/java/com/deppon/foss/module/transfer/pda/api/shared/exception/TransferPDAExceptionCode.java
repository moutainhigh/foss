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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/exception/TransferPDAExceptionCode.java
 *  
 *  FILE NAME          :TransferPDAExceptionCode.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.exception
 * FILE    NAME: TransferPDAExceptionCode.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.exception;

/**
 * TransferPDAExceptionCode
 * @author dp-duyi
 * @date 2012-12-29 下午2:12:15
 */
public class TransferPDAExceptionCode {
	/**没有查询到任务记录或该任务不为执行中*/
	public static final String EXCEPTION_INVALID_TASK_MESSAGECODE = "无效任务";
	/**没有查询到任务记录或该任务不为执行中*/
	public static final String EXCEPTION_INVALID_PARAMETER_MESSAGECODE = "无效参数";
	/**无效月台*/
	public static final String EXCEPTION_INVALID_PLATEFORM = "无效月台";
	/**封签无效**/
	public static final String EXCEPTION_INVALID_SEALNO_MESSAGECODE = "封签号只能为7到9位的数字";
	/**PDA设备号为空**/
	public static final String EXCEPTION_PDANO_EMPTY = "PDA设备号为空";
	/**当前部门不能为空.**/
	public static final String EXCEPTION_CURRENTDEPT_NOTBENULL = "当前部门不能为空.";
	/**下一部门不能为空.**/
	public static final String EXCEPTION_NEXTDEPT_NOTBENULL = "下一部门不能为空.";
	/**封签无效**/
	public static final String EXCEPTION_BILLNO_NOTBENULL = "单号明细不能为空.";
	/**找不到该车辆的任务信息**/
	public static final String EXCEPTION_UNFIND_TRUCKTASK_MESSAGECODE = "找不到该车辆的任务信息";
	/**暂不能录入封签。系统后台正在处理装车数据，预计将于3分钟左右处理完毕，请耐心等待**/
	public static final String EXCEPTION_UNCREATE_TRUCKTASK_MESSAGECODE = "暂不能录入封签。系统后台正在处理装车数据，请耐心等待";
	/**当前任务车辆已绑定封签, 不能重复绑定.**/
	public static final String EXCEPTION_SEAL_BINDED = "当前任务车辆已绑定封签, 不能重复绑定.";
	/**只有车辆状态为未到达时才能绑定**/
	public static final String EXCEPTION_BIND_INVALID_TRUCKTASKSTATE_MESSAGECODE = "只有车辆状态为未到达时才能绑定";
	/**车辆任务已被作废**/
	public static final String EXCEPTION_TRUCKTASK_CANCELED = "车辆任务已被作废";
	/**只有中途到达、已到达、已卸车时才能校验**/
	public static final String EXCEPTION_CHECKED_INVALID_TRUCKTASKSTATE_MESSAGECODE = "只有中途到达、已到达、已卸车时才能校验";
	/**封签号重复**/
	public static final String EXCEPTION_REPEAT_SEALNO = "封签号已被使用";
	/**找不到相应用户**/
	public static final String EXCEPTION_UNFIND_USER_MESSAGECODE = "用户编号错误, 找不到相应用户";
	/**封签已被检查*/
	public static final String EXCEPTION_SEAL_CHECKED_MESSAGECODE = "封签状态错误, 已被校验!";
	/**封签未检查*/
	public static final String EXCEPTION_SEAL_UNCHEK_MESSAGECODE = "封签未检查";
	/**无效卸车单据*/
	public static final String EXCEPTION_INVALID_UNLAOD_BILL_MESSAGECODE = "无效卸车单据";
	/**无效卸车单据*/
	public static final String EXCEPTION_EXTIST_SCAN_RECORD_MESSAGECODE = "存在扫描记录";
	/**存在未完成任务PDA*/
	public static final String EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE = "存在未完成任务PDA";
	/**无效标签*/
	public static final String EXCEPTION_INVALID_LABELED_GOODS_MESSAGECODE = "无效标签";
	/**程序异常*/
	public static final String EXCEPTION_PROCEDURE_ERROR_MESSAGECODE = "程序异常";
	/**无效重量或体积*/
	public static final String EXCEPTION_INVALID_WEIGHT_OR_VOLUME_MESSAGECODE = "无效重量或体积";
	/**重复扫描/删除*/
	public static final String EXCEPTION_REPEAT_OPERATION_MESSAGECODE = "重复扫描/删除";
	/**没有发车计划*/
	public static final String EXCEPTION_NO_DEPARTURE_PLANE = "没有发车计划";
	/**无效派送单单据*/
	public static final String EXCEPTION_INVALID_DELIVER_BILL_MESSAGECODE = "无效装车单据";
	/**无效理货员*/
	public static final String EXCEPTION_INVALID_LOADER_MESSAGECODE = "无效理货员";
	/**请使用挂牌号建立装车任务 200968 2016-01-06*/
	public static final String EXCEPTION_INVALID_LOAD_VEHICLETYPE  = "请使用挂牌号建立装车任务";
	/**无效部门*/
	public static final String EXCEPTION_INVALID_ORG_MESSAGECODE = "无效部门";
	/**未找到库区编号*/
	public static final String EXCEPTION_STTASK_GOODS_IS_NULL = "未找到此库件";
	/**与PDA传入的部门编号不匹配*/
	public static final String EXCEPTION_STTASK_DEPTCODE_UNMATCH = "与PDA传入的部门编号不匹配";
	/**清仓任务，PDA分支未完成任务*/
	public static final String EXCEPTION_STTASK_BRANCH_UNFINISHED = "PDA分支未完成任务";
	/**清仓任务，PDA分支不存在或状态不正确，不能执行完成任务*/
	public static final String EXCEPTION_STTASK_BRANCH_NOT_EXISTS = "PDA分支不存在或状态不正确，不能执行完成任务";
	/**清仓任务， 无法正确获取部门编号*/
	public static final String EXCEPTION_ERROR_DEPT = "此外场无法正确获取到货区编号";
	/**清仓任务， 存在未结束的清仓任务*/
	public static final String EXCEPTION_STTASK_EXIST_IN_PROCESS = "当前建立任务部门库区存在未结束的清仓任务";
	/**没有查询到任务记录或该任务不为执行中*/
	public static final String EXCEPTION_INVALID_LINE_MESSAGECODE = "无效线路";
	/**没有查询到任务记录或该任务不为执行中*/
	public static final String EXCEPTION_NO_TASK_DETAIL_MESSAGECODE = "无任务明细";
	/**查询走货路径失败*/
	public static final String EXCEPTION_QUERY_TRANSPORT_PATH_FAILURE_MESSAGECODE = "查询走货路径失败";
	public static final String EXCEPTION_QUERY_STOCK_GOODS_STATE_FAILURE_MESSAGECODE = "查询库存货物状态失败";
	public static final String EXCEPTION_VEHICLE_SEALED = "车辆已封签,不能提交装车任务";
	public static final String EXCEPTION_VEHICLE_SEALED1 = "车辆已封签,不能建立装车任务";
	public static final String EXCEPTION_VEHICLE_INVALID = "无效车牌,不能建立装车任务";
	public static final String EXCEPTION_UNLOAD_EXTRA = "非到达本部门货物，不能卸车";
	/**查询运单失败*/
	public static final String EXCEPTION_QUERY_WAYBILLNO_FAILURE_MESSAGECODE= "查询运单失败";
	/**查询托盘任务失败*/
	public static final String EXCEPTION_QUERY_TRAYSCANTASK_FAILURE_MESSAGECODE = "查询托盘任务失败";
	/**该票货已经签收*/
	public static final String EXCEPTION_ALREADY_SIGN__MESSAGECODE = "该票货已经签收";
	/**整车不允许进行装车扫描*/
	public static final String EXCEPTION_LOAD_WHOLE_VEHICLE__MESSAGECODE = "整车不允许进行装车扫描";
	/**无效包号*/
	public static final String EXCEPTION_INVALID_PACKAGE_MESSAGECODE = "无效包号";
	/**该包没有运单明细*/
	public static final String EXCEPTION_INVALID_NOPACKAGEDETAIL_MESSAGECODE = "该包没有运单明细";
	/**非外场到营业部装车，不能使用快递车辆*/
	public static final String EXCEPTION_NOTUSE_EXPRESS_VEHICLE = "非外场到营业部装车，不能使用快递车辆";
	/**无效的卸车单据（创建卸车任务）**/
	public static final String EXCEPTION_INVALID_UNLOADBILL = "无效的卸车单据，商务专递类型不能与其他类型进行混合操作";
	/**点单任务管理**/
	public static final String EXCEPTION_INVALID_ORDERTASKNO = "点单任务编号不存在";
	public static final String EXCEPTION_ORDERTASK_NOT_EXISTS = "点单任务不存在或不在点单中";
}
