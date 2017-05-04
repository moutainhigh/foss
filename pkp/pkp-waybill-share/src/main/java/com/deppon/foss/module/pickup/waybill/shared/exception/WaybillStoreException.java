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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillStoreException.java
 * 
 * FILE NAME        	: WaybillStoreException.java
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
 * FILE    NAME: WaybillStoreException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 运单库存异常
 * @author 026123-foss-lifengteng
 * @date 2012-11-17 下午7:18:40
 */
public class WaybillStoreException extends BusinessException {

	
	
	/**
	 * 运单实体为空！
	 */
	public  static final String WAYBILL_ENTITY_IS_NULL = "foss.waybillRfc.waybillEntityIsNull";

	
	
	/**
	 * 最终配载为空！
	 */
	public  static final String FINAL_LOAD_DEPT_IS_NULL = "foss.waybillRfc.finalLoadDeptIsNull";
	
	//生成运单库存信息失败！原因：
	public static final String WAYBILLSTOCKINFO_FAIL="foss.pkp.waybill.waybillManagerService.exception.failWaybillStockInfo";
	
	//生成库存时，传入空运单实体为空
	public static final String WAYBILL_ENTRY_NULL="foss.pkp.waybill.waybillStockService.exception.nullWaybillEntry";
		
	//生成库存时，传入的运单扩展实体为空
	public static final String ACTUAL_FREIGHT_ENTITY_NULL="foss.pkp.waybill.waybillStockService.exception.nullActualFreightEntity";
		
	//生成库存时，传入的始发库存部门为空
	public static final String START_STOCK_ORG_CODE_NULL="foss.pkp.waybill.waybillStockService.exception.nullStartStockOrgCode";
		
	//生成库存时，传入的运单号为空
	public static final String WAYBILLNO_NULL="foss.pkp.waybill.waybillStockService.exception.nullWaybillNo";
	
	//生成库存时，传入的用户实体为空
	public static final String USER_ENTITY_NULL="foss.pkp.waybill.waybillStockService.exception.nullUserEntity";
		
	//生成库存时，传入的操作人实体为空
	public static final String EMPLOYEE_ENTITY_NULL="foss.pkp.waybill.waybillStockService.exception.nullEmployeeEntity";
		
	//生成库存时，传入的操作人姓名为空
	public static final String EMPLOYEE_NAME_NULL="foss.pkp.waybill.waybillStockService.exception.nullEmployeeName";
		
	//生成库存时，传入的操作人编码为空
	public static final String EMPLOYEE_CODE_NULL="foss.pkp.waybill.waybillStockService.exception.nullEmployeeCode";
			
	//生成库存时，运单实体是否集中接送货字段为空
	public static final String PICKUP_CENTRALIZED_NULL="foss.pkp.waybill.waybillStockService.exception.nullPickupCentralized";

	//生成库存时，传入的始发库存为空
	public static final String STOCK_DEPT_NULL="foss.pkp.waybill.waybillStockService.exception.nullStockDept";
		
	//生成库存，调用中转入库服务时异常：
	public static final String STOCK_CREATE_BILL_FAIL="foss.pkp.waybill.waybillStockService.exception.failStockCreateBill";
		
	//生成库存时，传入的始发库存为空
		public static final String STOCK_DEPT_NULL_PCD="foss.pkp.waybill.waybillStockService.exception.nullStockDeptPCD";
    //部门取消
	public static final String DEPT_NULL ="foss.pkp.waybill.waybillStockService.exception.deptNull";
	
	/**
	 * 生成序列号信息
	 */
	private static final long serialVersionUID = -852125550461262599L;

	
	
	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */

	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String code) {
		super(code);
		this.errCode=code;
	}

	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}

	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String code, String msg) {
		super(code, msg);
	}
	
	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}

	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillStoreException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-17 下午7:18:40
	 */
	public WaybillStoreException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	
	
}