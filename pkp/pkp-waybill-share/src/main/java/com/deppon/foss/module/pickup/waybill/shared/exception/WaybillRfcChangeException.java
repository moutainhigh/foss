/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillRfcChangeException.java
 * 
 * FILE NAME        	: WaybillRfcChangeException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * @author suyujun
 * @function 更改单验证异常类
 * @date 2012-11-27
 * 
 */
public class WaybillRfcChangeException extends BusinessException {
	/**
	 * 悟空单无法更改！
	 */
	public static final String WAYBILL_IS_ISECS = "foss.pkp.waybill.waybillManagerService.exception.waybillIsECSChanged";
	
	/**
	 * 请检查运单,该运单已上报工单同意无法起草更改
	 */
	public static final String WAYBILL_NOT_CHANGE = "pkp.waybill.waybillrfc.waybillNotChange";
	/**
	 * 请检查运单,该运单不存在
	 */
	public static final String WAYBILL_NOT_EXSIT = "pkp.waybill.waybillrfc.waybillNotExsit";
	
	/**
	 * 请检查运单号,运单号只能是8位到10位！
	 */
	public static final String NUMBER_CHECK_ERROR = "foss.waybillRfc.numberCheckError";
	
	/**
	 * 请检查运单号,运单号只能由数字组成
	 */
	public static final String NUMBER_CHECK_MUST_INT = "foss.waybillRfc.numberCheckMustInt";
	
	
	/**
	 * 请检查申请开始日期及申请结束日期都已选择！
	 */
	public static final String APPLY_DATE_CHECK_MUST_SELECTED = "foss.waybillRfc.applyDateCHeckMustSelected";

	
	/**
	 * 起始时间不能大于结束日期！
	 */
	
	public static final String STARTE_DATE_MUST_NOT_GREATER_THAN_END_DATE = "foss.waybillRfc.starteDateMustNotGreaterThanEndDate";
	
	
	/**
	 * 凭证文件不存在！
	 */
	public static final String FILE_NOT_EXIST = "foss.waybillRfc.fileNotExist";

	
	/**
	 * 更改凭证读取失败！
	 */
	public static final String WAYBILLRFC_BILL_READ_FAILURE = "foss.waybillRfc.waybillRfcBillReadFailure";

	
	
	/**
	 * 文件流关闭失败：
	 */
	public static final String FILE_CLOSS_FAILURE = "foss.waybillRfc.fileClossFailure";
	
	
	/**
	 * 当前用户没有审核权限！
	 */

	public  static final String USER_NOT_AUDIT_PERMISSION = "foss.waybillRfc.userNotAuditPermission";
	
	
	/**
	 * 该运单已出开单部门库存，请重新起草转运单或返货单修改提货网点!
	 */
	
	public  static final String DRAFT_AGAIN_WAYBILLRFC = "foss.waybillRfc.draftAgainWaybillRfc";

	
	/**
	 * 该运单已出开单部门库存，无法更改运单号!
	 */
	
	public  static final String CAN_NOT_CHANGE_WAYBILL_NUMBER = "foss.waybillRfc.cannotChangeWaybillNumber";

	
	/**
	 * 起始时间与截止时间不能超过4天！
	 */
	
	public  static final String CAN_NOT_PASS_TEN_DAYS = "foss.waybillRfc.cannotPassTenDays";

	
	/**
	 * 请填写审核拒绝备注
	 */
	public  static final String MUST_FILL_IN_AUDIT_NOTES = "foss.waybillRfc.mustFillInAuditNotes";
	
	
	/**
	 * 请填写拒绝受理备注
	 */
	public  static final String MUST_FILL_IN_ACCEPT_NOTES = "foss.waybillRfc.mustFillInAcceptNotes";

	
	
	

	/**
	 * 新旧运单数据异常，检查运单的Id及运单号！
	 */
	public  static final String NEW_WAYBILL_AND_OLD_WAYBILL_IS_EXCEPTION = "foss.waybillRfc.NewWaybillAndOldWaybillIsException";

	/**
	 * 请检查运单号,运单号只能是8位或9位！
	 */
	public  static final String CHECK_NUMBER_ONLY_EIGHT_OR_NINE = "foss.waybillRfc.CheckNumberOnlyEightOrNine";
	
	
	/**
	 * 请检查运单号,运单号只能由数字组成
	 */
	
	public static  final String WAYBILL_NUMBER_MUST_INT = "foss.waybillRfc.waybillNumberMustInt";
	
	
	/**
	 * 请检查申请开始日期及申请结束日期都已选择！
	 */
	public static  final String CHECK_APPLY_START_AND_END_DATE = "foss.waybillRfc.checkApplyStartAndEndDay";

	/**
	 * 起始时间不能大于结束日期！
	 */
	public static  final String START_DATE_CAN_NOT_GREATER_THAN_END_DATE = "foss.waybillRfc.startDateCanNotGreaterThanEndDate";

	
	/**
	 * 起始时间与截止时间不能超过十天！
	 */
	public static  final String START_DATE_CAN_NOT_GREATER_THAN_END_DAT_TEN_DAY = "foss.waybillRfc.startDateCanNotGreaterThanEndDateTenDay";
	
	/**
	 * 没有选择代理人！
	 */
	public static  final String HAVE_NOT_SELECT_AGENT = "foss.waybillRfc.haveNotSelectAgent";

	
	/**
	 * 请确保生效时间和终止时间都已选择！
	 */
	public static  final String INSURE_START_DATE_AND_END_DATE_HAVE_SELECTED = "foss.waybillRfc.insureStartDateAndEndDateHaveSelected";

	
	/**
	 * 生效时间不能早于当前时间！
	 */
	
	public static  final String START_DATE_CAN_NOT_GREATER_THAN_CURRENT_DATE = "foss.waybillRfc.startDateCannotGreaterThanCurrentDate";

	/**
	 * 终止时间不能早于生效时间！
	 */
	public static  final String END_DATE_CAN_NOT_GREATER_THAN_CURRENT_DATE = "foss.waybillRfc.endDateCannotGreaterThanCurrentDate";
	
	
	/**
	 * 查询日期范围不能超过1个月！
	 */
	public static  final String DATE_SCOPE_CAN_NOT_PASS_ONE_MONTH = "foss.waybillRfc.DateScopeCanNotMonth";
	

	/**
	 * 查询条件不能为空
	 */
	
	public static  final String QUERY_CONDITION_CAN_NOT_NULL = "foss.waybillRfc.QueryConditionCanNotNull";
	
	
	/**
	 * 请选择相应的记录执行操作
	 */
	public static  final String SELECT_RECOED = "foss.waybillRfc.QueryConditionCanNotNull";
	
	/**
	 * 数据有误，不允许删除操作！
	 */
	
	public static  final String DATA_ERROR_CAN_NOT_OPERATE = "foss.waybillRfc.dataErrorCannotOperate";

	/**
	 * 当前状态下不允许删除操作！
	 */
	public static  final String CURRENT_STATUS_CAN_NOT_OPERATE = "foss.waybillRfc.currentStatusCannotOperate";

	/**
	 * 审核代理已生效，无法删除
	 */
	public static  final String AUDIT_AGENT_EFFECT_CAN_NOT_DELETE = "foss.waybillRfc.auditAgentEffectCannotDelete";

	
	/**
	 * 数据有误，不能进行中止操作！
	 */
	public static  final String DATA_ERROR_CAN_NOT_STOP = "foss.waybillRfc.dataErrorCannotStop";

	/**
	 * 当前状态下不能进行中止操作！
	 */
	
	public static  final String CURRENT_STATUS_CAN_NOT_STOP = "foss.waybillRfc.currentStatusCannotStop";
	
	//查询到达部门实体为空！
	public static final String SALEDEPARTMENT_NULL="foss.pkp.waybill.todoActionService.exception.nullSaleDepartment";
			

    //请检查申请开始日期及申请结束日期都已选择！
	public static final String APPLYDATE_NULL="foss.pkp.waybill.waybillManagerService.exception.nullApplyDate";
    
    //起始时间不能大于结束日期！
	public static final String ENDTIME_OVER="foss.pkp.waybill.waybillManagerService.exception.overEndTime";
    
    //起始时间与截止时间不能超过30天！
	public static final String ThirtyDays_OVER="foss.pkp.waybill.waybillManagerService.exception.overThirtyDays";
    
	//查询不到部门编号为{0}部门实体
    public static final String ORG_ADMINISTRATIVE_INFO_NULL="foss.pkp.waybill.waybillRfcVarificationService.exception.nullOrgAdministrativeInfo";
    
    //调用CRM接口失败
    public static final String USE_CRM_SERVICE_FAIL="foss.pkp.waybill.waybillRfcVarificationService.exception.failUseCrmService";
    
    //该用户被重复授权
    public static final String AGENT_REPEAT="foss.pkp.waybill.waybillRfcVarificationService.exception.agentRepeat";
    
    /**
	 * 此更改单已被审核
	 */
	public static final String CHANGE_WAYBILL_APPROVED = "foss.waybillRfc.waybillRfcChangeException.changeWaybillApproved";
	/**
	 * 此更改单正在被审核
	 */
	public static final String CHANGE_WAYBILL_APPROVING = "foss.waybillRfc.waybillRfcChangeException.changeWaybillApproving";
	
	/**
	 * 此更改单已被受理
	 */
	public static final String CHANGE_WAYBILL_ACCECPTED = "foss.waybillRfc.waybillRfcChangeException.changeWaybillAccecpted";
	
	/**
	 * 此更改单对应的运单已经做合票，不能受理
	 */
	public static final String CHANGE_WAYBILL_ACCECPTED_ALREADY_ADD = "foss.waybillRfc.waybillRfcChangeException.changeWaybillAccecpted.already.add";
	/**
	 * 此更改单正在被受理
	 */
	public static final String CHANGE_WAYBILL_ACCECPTING = "foss.waybillRfc.waybillRfcChangeException.changeWaybillAccecpting";
	
	/**
	 * 此单号是PDA待补录运单号
	 */
	public static final String WAYBILL_IS_PDA_PENDING = "foss.gui.creating.waybillDescriptor.waybillNo.isPDApending";
	
	/**
	 * 电子运单同城货物次日6点以后无法更改
	 */
	public static final String WAYBILL_CANNOT_RFC_OVER_6HOUR_PDA_PENDING = "foss.waybillRfc.waybillRfc.except.overNextSixHour.cannotChange";
	
	/**
	 * 电子运单非同城货物次日12点以后无法更改
	 */
	public static final String WAYBILL_CANNOT_RFC_OVER_12HOUR_PDA_PENDING = "foss.waybillRfc.waybillRfc.except.overNext12Hour.cannotChange";
	
	/**
	 * 电子运单非同城货物次日12点以后无法更改
	 */
	public static final String WAYBILL_RFC_DETAIL_IS_NULL = "foss.waybillRfc.waybillRfc.except.changeDetailIsNull";
	
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -7644945129698621656L;
	
	//未能获取快递运单信息 2016年3月29日 19:37:14 葛亮
	public static final String WAYBILL_RFC_EXPRESS_IS_NULL = "foss.waybillrfc.express.isnull";

	/**
	 * 创建一个新的实例 WaybillRfcChangeException
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 上午9:58:07
	 */
	public WaybillRfcChangeException() {
		super();
	}

	/**
	 * 更改单验证异常方法
	 * 
	 * @author foss-suyujun
	 * @date 2012-12-25 上午9:58:15
	 */
	public WaybillRfcChangeException(String code, String msg) {
		super(code, msg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillRfcChangeException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}
	/**
	 * 更改单验证异常方法
	 * 
	 * @author foss-suyujun
	 * @date 2012-12-25 上午9:58:15
	 */
	public WaybillRfcChangeException(String message) {
		super(message);
		this.errCode=message;
	}
}