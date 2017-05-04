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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillSubmitException.java
 * 
 * FILE NAME        	: WaybillSubmitException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.exception
 * FILE    NAME: WaybillSubmitException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 运单提交异常
 * @author 026123-foss-lifengteng
 * @date 2012-11-8 下午3:06:09
 */
public class WaybillSubmitException extends BusinessException {

	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = -5456474945981511512L;
	
	//运单生成[历史发货客户]/[历史收货客户]信息失败，原因：
    public static final String DELIVERCUSTCODE_FAIL="foss.pkp.waybill.waybillManagerService.exception.failDeliverCustCode";
	
    //运单生成签收单返单失败，原因：
    public static final String SIGNRETURNBILL_FAIL="foss.pkp.waybill.waybillManagerService.exception.failSignReturnBill";

    //运单信息生成失败:
    public static final String ADDWAYBILLINFO_FAIL="foss.pkp.waybill.waybillManagerService.exception.failAddWaybillInfo";	 

    //调用结算接口出错，原因：
    public static final String ADDSETTLEBILL_FAIL="foss.pkp.waybill.waybillManagerService.exception.failAddSettleBill";
    
    //拷贝运单信息到【WaybillPickupInfoDto】对象失败！原因：
    public static final String COPYWAYBILLINFO_FAIL="foss.pkp.waybill.waybillManagerService.exception.failCopyWaybillInfo";
    
    //传入的运单号不存在有效记录
    public static final String WAYBILLBEAN_NULL="foss.pkp.waybill.waybillManagerService.exception.nullWaybillBean";
    
    //查询约车编号：
    public static final String QUERYINVITENO="foss.pkp.waybill.waybillManagerService.exception.queryInviteNo";
    
    //查询约车报价：
    public static final String QUERYINVITECOST="foss.pkp.waybill.waybillManagerService.exception.queryInviteCost";
    
    //运单提交失败！原因：
    public static final String SUBMIT_WAYBILL_FAIL="foss.pkp.waybill.waybillRfcVarificationService.exception.failSubmitWaybill";
    
    //当前登陆用户信息为空!
    public static final String CURRENT_INFO_NULL="foss.pkp.waybill.woodenRequirementsService.exception.nullCurrentInfo";
    
    //调用中转接口生成包装信息失败！
    public static final String ADD_PACKAGING_REQUIRE_FAIL="foss.pkp.waybill.woodenRequirementsService.exception.failAddPackagingRequire";
    
    /**
	 * 该运单正被其他人提交
	 */
	public static  final String WAYBILL_SUBMIT_LOCKED = "foss.waybillSubmitException.waybillSubmitLocked";
	
	/**
	 * 该订单已存在
	 */
	public static  final String WAYBILL_ORDER_SUBMIT_LOCKED = "foss.waybillSubmitException.waybillOrderSubmitLocked";
	/**
	 * 营业部的外场不存在
	 */
	public static  final String OUTFIEL_SALEDEPT_NOTFOUND="foss.pkp.waybill.waybillManagerService.exception.nullSaleDepartmentEntityFound";
	
	/**
	 * 运单号重复
	 */
	public static  final String WAYBILLNO_REPEAT="foss.pkp.waybill.waybillManagerService.exception.waybillNoRepeat";
    
	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	

	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public WaybillSubmitException(String msg) {
		super(msg);
		this.errCode = msg;
	}

	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public WaybillSubmitException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}

	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public WaybillSubmitException(String code, String msg) {
		super(code, msg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public WaybillSubmitException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public WaybillSubmitException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillSubmitException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-8 下午3:06:10
	 */
	public WaybillSubmitException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}
	
	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillSubmitException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}

}