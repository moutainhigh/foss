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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/exception/AirAgencySignException.java
 * 
 * FILE NAME        	: AirAgencySignException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 异常处理
 * @author ibm-meiying
 * @date 2012-10-15 下午2:48:59
 * @since
 * @version
 */
public class AirAgencySignException extends BusinessException {
	//签收偏线空运(AirAgencySignAction)下的
	public static final String SIGN_SUCCESS ="pkp.sign.airAgencySignException.signSuccess";//签收成功! 
	public static final String SIGN_FAILED ="pkp.sign.airAgencySignException.signFailed";//签收失败! 请重新查询一下
	public static final String PRODUCT_CODE_IS_NOT_NULL ="pkp.sign.airAgencySignException.productCodeIsNotNull";//运输性质为空，不能进行查询; 
	public static final String QUERY_EMPTY ="pkp.sign.airAgencySignException.queryEmpty";//查询条件为空，不能进行查询; 
	public static final String WAYBILLNO_IS_NOT_EXIST ="pkp.sign.airAgencySignException.waybillnoIsNotExist";//该运单号不存在!; 
	public static final String WAYBILL_IS_SIGNED ="pkp.sign.airAgencySignException.waybillIsSigned";//该运单已经签收!; 
	public static final String PRODUCT_CODE_IS_INCONSISTENCIES ="pkp.sign.airAgencySignException.productCodeIsInconsistencies";//该运单的运输性质与所选的运输性质不一致!; 
	public static final String LAST_LOAD_ORGCODE_INCONSISTENCIES ="pkp.sign.airAgencySignException.lastLoadOrgcodeInconsistencies";//当前登录部门与该运单的最终配载部门不一致!; 
	public static final String EXTERNAL_IS_NOT_INPUT ="pkp.sign.airAgencySignException.externalIsNotInput";//未录入外发单，请先录入外发单!;
	public static final String GOODS_NOT_OUT ="pkp.sign.airAgencySignException.goodsNotOut";//货物未出库，请确认货物状态!;
	public static final String ID_ISNULL ="pkp.sign.airAgencySignException.idIsnull";//传入的id为空，不能进行反签收操作; 
	public static final String WAYBILL_SIGN_RESULT_IS_NOT_EXIST ="pkp.sign.airAgencySignException.waybillSignResultIsNotExist";//该运单在运单签收结果里不存在; 
	public static final String ENTITY_IS_NOT_EXIST ="pkp.sign.airAgencySignException.entityIsNotExist";//该运单对应实体不存在 ; 
	public static final String WAYBILL_SIGN_RESULT_ADD_FAILED ="pkp.sign.airAgencySignException.waybillSignResultAddFailed";//运单签收结果添加失败,签收失败
	public static final String WAYBILL_IS_OBSOLETE ="pkp.sign.airAgencySignException.waybillIsObsolete";//该运单状态已作废，不能进行签收
	public static final String WAYBILL_IS_ABORTED ="pkp.sign.airAgencySignException.waybillIsAborted";//该运单状态已中止，不能进行签收
	public static final String WAYBILL_IS_NOT_FDP ="pkp.sign.airAgencySignException.waybillIsNotFDP";//该运单未做合大票，不能进行签收
	public static final String AIR_ORG_CODE_NOT_SAME ="pkp.sign.airAgencySignException.airorgCodeNotSame";//当前登录部门与航空正单的部门不一致
	public static final String END_STOCK_ORG_CODE_WRONG ="pkp.sign.airAgencySignException.endstockorgCodeWrong";//当前运单最终库存部门不对
	public static final String NOT_AUDIT_STATUS = "pkp.sign.expAirAgencySignException.notAuditStatus";//该运单对应的外发单不是已审核状态
	
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2012-12-25 下午2:30:31
	 * @param code
	 */
	public AirAgencySignException(String code) {
		super();
		this.errCode = code;
	}
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2012-12-25 下午2:30:43
	 * @param code
	 * @param cause
	 */
	public AirAgencySignException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2013-3-11 下午2:10:12
	 * @param code
	 * @param args
	 */
	public AirAgencySignException(String code,Object... args) {
		super(code,args);
	}
	
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2013-3-11 下午2:10:12
	 * @param code
	 * @param args
	 */
	public AirAgencySignException(String code,String args) {
		super(code,args);
		this.errCode = code;
	}
}