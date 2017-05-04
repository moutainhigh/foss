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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillImportException.java
 * 
 * FILE NAME        	: WaybillImportException.java
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
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-26 下午3:47:47
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-26 下午3:47:47
 * @since
 * @version
 */
public class WaybillImportException extends BusinessException {

	
	/**
	 * 生成的运单出发、到达库存部门不正确！
	 */
	
	public static  final String START_DEPT_END_DEPT_ERROR = "foss.waybillRfc.startDeptEndDeptError";

	
	/**
	 * 未找到该运单对应的流水号！
	 */
	public static  final String NOT_FIND_WAYBILL_NUMBER_FOR_SERIALS = "foss.waybillRfc.notFindWaybillNumberForSerials";

	
	/**
	 * 无此运单,请确认单号是否录入正确
	 */
	
	public static  final String NOT_FIND_WAYBILL_NUMBER_IS_ERROR = "foss.waybillRfc.notFindWaybillNumberIsError";

	
	/**
	 * 运单号不存在！
	 */
	
	public static  final String WAYBILL_NUMBER_NOT_EXIST = "foss.waybillRfc.waybillNumberNotExist";
	
	/**
	 * 不在到达部门库存！
	 */
	
	public static  final String WAYBILL_NUMBER_NOT_DEPT = "foss.waybillRfc.startDeptEndDept";

	/**
	 * 运单未补录，不能起草运单变更！
	 */
	public static  final String WAYBILL_NOT_ADDITIONAL_RECORDING_CAN_NOT_CHANGE = "foss.waybillRfc.waybillNotAdditionalRecordingCannotChange";
	
	
	/**
	 * 运单ActualFreight无数据，获取不到货物状态！
	 */
	public static  final String WAYBILL_ACTUAL_FREIGHT_NOT_DATA = "foss.waybillRfc.waybillActualFreightNotData";

	/**
	 * 运单已作废，不能起草运单变更！
	 */
	public static  final String WAYBILL_CANCEL_CAN_NOT_CAHNGE = "foss.waybillRfc.waybillCancelCannotChange";

	/**
	 * 运单已中止，不能起草运单变更！
	 */
	public static  final String WAYBILL_STOP_CAN_NOT_CAHNGE = "foss.waybillRfc.waybillStopCannotChange";
	
	/**
	 * 运单已结清货款，不能起草运单变更！
	 */
	public static  final String WAYBILL_SETTLE_CLEAR = "foss.waybillRfc.waybillSettleClear";
	
	/**
	 * 该运单正被其他人更改，稍等片刻再提交
	 */
	public static  final String WAYBILL_NUMBER_LOCKED = "foss.waybillRfc.waybillLocked";

	/**
	 * 偏线运单已中转外发，不能起草运单变更！
	 */
	public static  final String WAYBILL_ALREADY_CELL_CAN_NOT_CHANGE = "foss.waybillRfc.waybillAlreadyCellCannotChange";

	
	/**
	 * 货物已签收，不能起草运单变更！
	 */
	public static  final String WAYBILL_ALREADY_SIGN_CAN_NOT_CHANGE = "foss.waybillRfc.waybillAlreadySignCannotChange";

	/**
	 * 该运单有运单变更单还未被审核，不能起草变更运单！
	 */
	public static  final String WAYBILL_HAVE_NOT_AUDIT_CAN_NOT_CHANGE = "foss.waybillRfc.waybillHaveNotAuditCannotChange";

	/**
	 * 该运单有运单变更单还未被受理，不能起草变更运单！
	 */
	public static  final String WAYBILLRFC_HAVE_NOT_ACCEPT_CAN_NOT_CHANGE = "foss.waybillRfc.waybillRfcHaveNotAcceptCannotChange";

	/**
	 * 该运单已进行结清货款操作，如需更改请联系到达部门进行反结清货款操作！
	 */
	public static  final String WAYBILLRFC_ALREADY_SETTLE_CAN_NOT_CHANGE = "foss.waybillRfc.waybillRfcAlreadySettleCannotChange";

	/**
	 * 非本部门运单，不能起草运单变更！
	 */
	
	public static  final String HAVE_NOT_AUTHORITY_TO_CHANGE = "foss.waybillRfc.haveNotAuthorityToChange";
	
	/**
	 * 该运单已付款！
	 */
	public static  final String WAYBILL_HAS_PAID = "foss.waybillRfc.waybillHasPaid";
	
	//子母单，子单不能进行废除操作
	public static final String WAYBILL_CAN_NOT_INVALID="foss.waybillRfc.waybillCannotInvalid";
			
	//子母单，子单不能进行中止操作
	public static final String WAYBILL_CAN_NOT_ABORT="foss.waybillRfc.waybillCannotAbort";
	
	//待处理运单主键[waybillPendingId]不能为空！
	public static final String WAYBILLPENDINGID_NULL="foss.pkp.waybill.waybillAcHisPdaService.exception.nullWaybillPendingId";
			
	//待处理运单操作时间[operateTime]不能为空！
	public static final String OPERATETIME_NULL="foss.pkp.waybill.waybillAcHisPdaService.exception.nullOperateTime";
			
	//PDA补录提交时，原运单号不能为空!
	public static final String PDAWAYBILLNO_NULL="foss.pkp.waybill.waybillManagerService.exception.nullPDAWaybillNo";
 
	//删除待处理表中已补录的PDA运单失败!原因:
	public static final String DELETEPDAWAYBILL_FAIL="foss.pkp.waybill.waybillManagerService.exception.failDeletePDAWaybill";
	
    //更新待处理表中已补录的PDA运单状态失败!原因:
	public static final String UPDATEPDAWAYBILLSTATUS_FAIL="foss.pkp.waybill.waybillManagerService.exception.failUpdatePDAWaybillStatus";
    
    //生成PDA运单补录操作历史记录的失败!原因:
	public static final String ADDWAYBILLACHISPDA_FAIL="foss.pkp.waybill.waybillManagerService.exception.failAddWaybillAcHisPda";
    
	//弃货运单号为空，请输入运单号！
    public static final String ABANDONEDWAYBILLNO_NULL="foss.pkp.waybill.waybillManagerService.exception.nullAbandonedWaybillNo";
    
    //对不起,该运单已被锁定，请等待[{0}分钟]后再操作！
    public static final String WAYBILL_LOCKED="foss.pkp.waybill.WaybillPendingService.exception.lockedWaybill";
	
    //更新弃货处理运单储运事项、失效状态失败。原因：
    public static final String UPDATEABANDONEDWAYBILL_FAIL="foss.pkp.waybill.waybillManagerService.exception.failUpdateAbandonedWaybill";
    
    //起草更改单失败  该单已经发生了更改
    public static final String WAYBILLRFC_ALREADY_CHANGED ="foss.pkp.waybill.waybillManagerService.exception.waybillAlreadyChanged";
    
    //悟空运单无法再FOSS起草更改
    public static final String WAYBILLRFC_IDECS_CHANGED ="foss.pkp.waybill.waybillManagerService.exception.waybillIsECSChanged";
    
    
    
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8508645843507717365L;

	
	
	
	/**
	 * 创建一个新的实例WaybillImportException
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException() {
		super();
	}

	/**
	 * 运单导入异常类
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * 运单导入异常类
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * 运单导入异常类
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * 运单导入异常类
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * 运单导入异常类
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}

	/**
	 * 运单导入异常类
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午9:56:58
	 */
	public WaybillImportException(String msg) {
		super(msg);
		this.errCode=msg;
	}
	
	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillImportException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}

}