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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/define/OutsideVehicleChargeConstants.java
 *  
 *  FILE NAME          :OutsideVehicleChargeConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.define;

/**
 * 外请车费用常量类
 * 
 * @author 038300-foss-pengzhen
 * @date 2012-1-28 下午21:08:03
 */
public class OutsideVehicleChargeConstants {
	// 奖励
	public static final String REWARD = "REWARD";
	// 罚款
	public static final String FINE = "FINE";
	// 未审核
	public static final String NOAUDIT = "NOAUDIT";
	// 未通过
	public static final String AUDITNOTPASS = "AUDITNOTPASS";
	// 已通过
	public static final String AUDITPASS = "AUDITPASS";
	// 审批中  ---lln（269701）-20150708
	public static final String AUDITIN = "AUDITIN";
	// 时效提前
	public static final String AGINGINADVANCE = "AGINGINADVANCE";
	// 时效延误
	public static final String AGINGDELAY = "AGINGDELAY";
	// 其他
	public static final String OTHER = "OTHER";
	// 处罚范围 最大值
	public static final String DEFAULT_FINE_MAXFEE = "DEFAULT_FINE_MAXFEE";
	// 处罚范围 最小值
	public static final String DEFAULT_FINE_MINFEE = "DEFAULT_FINE_MINFEE";
	// 奖励范围 最大值
	public static final String DEFAULT_AWARD_MAXFEE = "DEFAULT_AWARD_MAXFEE";
	// 奖励范围 最小值
	public static final String DEFAULT_AWARD_MINFEE = "DEFAULT_AWARD_MINFEE";
	// waybillNo为空：输入的单号为空
	public static final String OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY = "foss.load.outsidevehiclecharge.exception.waybillNoEmpty";
	// waybillNo为空：输入的单号为空
	public static final String OUTSIDEVEHICLECHARGE_WILLWAYNO_EMPTY_LOG = "输入的单号为空";
	// 可结算的记录数量
	public static final String OUTSIDEVEHICLECHARGE_PAYABLE_COUNT = "可结算的记录数量";
	// 提示信息，费用调整未审核
	public static final String OUTSIDEVEHICLECHARGE_FEE_NOAUDIT = "foss.load.outsidevehiclecharge.exception.adjustOutVehicleFeeNoAudit";
	// 提示信息，费用调整未审核
	public static final String OUTSIDEVEHICLECHARGE_FEE_NOAUDIT_LOG = "费用调整未审核";
	// 调用结算接口：提示信息,无法申请
	public static final String OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE = "foss.load.outsidevehiclecharge.exception.billPayableFalse";
	// 调用结算接口：提示信息,无法申请
	public static final String OUTSIDEVEHICLECHARGE_BILLPAYABLE_FALSE_LOG = "已经结算,无法申请";
	// 当前用户不可以审核，请重新登录
	public static final String CURRENT_USER_NO_AUDIT_RIGHT = "foss.load.outsidevehiclecharge.exception.noAuditRight";
	// 外请车是在途装卸，不可以调整外请车费用
	public static final String ON_WAY_UNLOAD = "foss.load.outsidevehiclecharge.exception.onWayUnload";
	// 出发部门与当前部门是同一部门
	public static final Object CURRENT_DEPT_SAME_AS_ORGINAL_DEPT = "出发部门与当前部门是同一部门";
	// 当前用户不可以调整费用，请重新登录
	public static final String CURRENT_USER_NO_ADJUST_RIGHT = "foss.load.outsidevehiclecharge.exception.noAdjustRight";
	// 到达部门与当前部门是同一部门
	public static final Object CURRENT_DEPT_SAME_AS_DEST_DEPT = "到达部门与当前部门是同一部门";
	// 当前用户不可以删除，请重新登录
	public static final String CURRENT_USER_NO_DELETE_RIGHT = "foss.load.outsidevehiclecharge.exception.noDeleteRight";
	// 当前用户不可以申请费用，请重新登录
	public static final String CURRENT_USER_NO_APPLY_RIGHT = "foss.load.outsidevehiclecharge.exception.noApplyRight";
	// 公司车不可以调整费用
	public static final String COMPANY_VEHICLE_CANNOT_ADJUST = "foss.load.outsidevehiclecharge.exception.compVehicleNotAdj";
	// 配载车次号输入有误，请重新输入配载车次号
	public static final String WRONG_WAYBILLNO = "foss.load.outsidevehiclecharge.exception.wrongWaybillNo";
	// 外请车可以使用
	public static final Object OUTSIDEVEHICLE_CAN_USE = "外请车可以使用";
	// 是外请车
	public static final Object BE_OUTSIDEVEHICLE = "是外请车";
	// 查询数据
	public static final String QUERY_DATA = "查询数据";
	
	// 整车--lln（269701）-20150708
	public static final String CAR_LOAD = "CAR_LOAD";
	//专线--lln（269701）-20150708
	public static final String OWNER_LINE = "OWNER_LINE";
	// 当前用户不可以审批，请重新登录
	public static final String CURRENT_USER_NO_APPROVAL_RIGHT = "foss.load.outsidevehiclecharge.exception.noApprovalRight";

	// 未输入配载车次号
	public static final String VEHICLEASSEMBLENOLIST_NULL = "未输入配载车次号!";
	
	//必须在车次发车之后才能调整外请车费用
	public static final String MUST_AFTER_LEAVE = "foss.load.outsidevehiclecharge.exception.mustAfterLeave";
	
	//2013年12月14日 11:24:11 by zyx
	/**
	 * 增减类型-费用增加
	 */
	public static final String ADJUST_TYPE_FEE_AUGMENT = "FEE_AUGMENT";

	/**
	 * 增减类型-费用减少
	 */
	public static final String ADJUST_TYPE_FEE_REDUCE = "FEE_REDUCE";
	
	/**
	 * 费用增加原因-押车费
	 */
	public static final String FEE_AUGMENT_REASON_ESCORT_FEE = "ESCORT_FEE";

	/**
	 * 费用增加原因-办单错误
	 */
	public static final String FEE_AUGMENT_REASON_SINGLE_ERROR = "SINGLE_ERROR";

	/**
	 * 费用增加原因-交警罚款
	 */
	public static final String FEE_AUGMENT_REASON_POLICE_FINE = "POLICE_FINE";

	/**
	 * 费用增加原因-超高或超重
	 */
	public static final String FEE_AUGMENT_REASON_OVERLOAD = "OVERLOAD";

	/**
	 * 费用增加原因-目的站临时变更
	 */
	public static final String FEE_AUGMENT_REASON_DESTINATION_CHANGE = "DESTINATION_CHANGE";

	/**
	 * 费用减少原因-大车小用
	 */
	public static final String FEE_REDUCE_REASON_DESTINATION_UNFULLLOAD = "UNFULLLOAD";

	/**
	 * 费用减少原因-货物受损
	 */
	public static final String FEE_REDUCE_REASON_DESTINATION_GOODS_DAMAGED = "GOODS_DAMAGED";
	
	/**
	 * 费用减少原因-配载错误
	 */
	public static final String FEE_REDUCE_REASON_DESTINATION_STOWAGE_ERROR = "STOWAGE_ERROR";

	/**
	 * 费用减少原因-目的站临时变更
	 */
	public static final String FEE_REDUCE_REASON_DESTINATION_DESTINATION_CHANGE = "DESTINATION_CHANGE";

	/**
	 * 产生原因-时效提前
	 */
	public static final String CAUSE_AGINGINADVANCE = "AGINGINADVANCE";

	/**
	 * 产生原因-时效延误
	 */
	public static final String CAUSE_AGINGDELAY = "AGINGDELAY";

	/**
	 * 时效提前原因-时效提前
	 */
	public static final String CAUSE_AGINGINADVANCE_REASON_AGINGINADVANCE = "AGINGINADVANCE";

	/**
	 * 时效延误原因-交通事故
	 */
	public static final String CAUSE_AGINGDELAY_REASON_ROAD_ACCIDENT = "ROAD_ACCIDENT";

	/**
	 * 时效延误原因-自然灾害
	 */
	public static final String CAUSE_AGINGDELAY_REASON_NATURAL_DISASTERS = "NATURAL_DISASTERS";

	/**
	 * 时效延误原因-晚录到达
	 */
	public static final String CAUSE_AGINGDELAY_REASON_LATE_ENTERING_ARRIVE = "LATE_ENTERING_ARRIVE";

	/**
	 * 时效延误原因-提前出港
	 */
	public static final String CAUSE_AGINGDELAY_REASON_EARLY_DEPART = "EARLY_DEPART";
	
	/**
	 * 是否奖励
	 */
	public static final String OUTSIDEVEHICLECHARGE_BE_REWARD = "OUTSIDEVEHICLECHARGE_BE_REWARD";
	
	/**
	 * 空
	 */
	public static final String NONE = "NONE";
	
	/**
	 * 新增 log
	 * lln（269701）-20150715
	 */
	public static final String ADDLOG = "ADDLOG";
	
	/**
	 * 修改 log
	 * lln（269701）-20150715
	 */
	public static final String UPDATELOG = "UPDATELOG";
	
	/**
	 * 审核 log
	 * lln（269701）-20150715
	 */
	public static final String AUDITLOG = "AUDITLOG";
	
	/**
	 * 审批 log
	 * lln（269701）-20150715
	 */
	public static final String APPROVALLOG = "APPROVALLOG";
	
	/**
	 * 退回 log
	 * lln（269701）-20150715
	 */
	public static final String BACKLOG = "BACKLOG";
	/**
	 * 删除 log
	 * lln（269701）-20150725
	 */
	public static final String DELETELOG = "DELETELOG";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}