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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/exception/RepaymentException.java
 * 
 * FILE NAME        	: RepaymentException.java
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
 * 结清货款异常处理
 * @author 043258-
 * 		foss-zhaobin
 * @date 2012-11-27 
 * 		下午5:26:34
 */
public class RepaymentException extends BusinessException 
{
	/**
	 * 存在未受理的更改单，请受理后再次操作！
	 */
	public static final String EXIST_WAYBILLRFC = "pkp.sign.settlePayment.exist.waybillrfc";
	/**
	 * 操作人密码错误，请重新输入！
	 */
	public static final String PASSWORD_ERROR = "pkp.sign.settlePayment.password.error"; 
	/**
	 * 传入参数有误！请重新操作
	 */
	public static final String ARGS_ERROR = "pkp.sign.settlePayment.args.error";
	/**
	 * 付款信息有误！
	 */
	public static final String PAYMENT_ERROR = "pkp.sign.settlePayment.payment.error";
	/**张新 2015-2-3
	 * 此时原单不能结清货款！
	 */
	public static final String EXPRESS_ERROR = "pkp.sign.settlePayment.express.error";
	/**张新 2015-2-3
	 * 新单必须一次性结清！
	 */
	public static final String ONCE_ERROR = "pkp.sign.settlePayment.once.error";
	/**张新 2015-2-4
	 * 结清原单时，原单应收代收款不为零
	 */
	public static final String EXIST_WAYBILLERROR = "pkp.sign.settlePayment.exist.waybillerror";
	/**张新 2015-2-5
	 * 该原单应收到付款应该为零
	 */
	public static final String OLDNUM_ERROR = "pkp.sign.settlePayment.oldnum.error";
	/**
	 * 运单状态异常！
	 */
	public static final String WAYBILLRFC_ERROR = "pkp.sign.settlePayment.waybillrfc.error";//当前运单已经中止/作废/未补录，不可以进行操作！
	
	public static final String PAYMENT_OPERATING = "pkp.sign.settlePayment.payment.operating";
	
	public static final String TRANSFER_ERROR = "pkp.sign.settlePayment.Transfer.error";
	
	public static final String REFUND_ERROR = "pkp.sign.settlePayment.Refund.error";
	
	public static final String PAYMENT_FINISH="pkp.sign.settlePayment.payment.finish";
	
	public static final String PAYMENT_NOTGTPAYAMOUNT="pkp.sign.settlePayment.actualFreightNotGTReceiveablePayAmoout";
	
	public static final String PAYMENT_NOTGTRECEIVEABLE="pkp.sign.settlePayment.codAmountNotGTReceiveableAmount";
	
	public static final String VEHICLEASSEMBLEBILL_INVALID = "pkp.sign.settlePayment.vehicleassemblebill.invalid";

	public static final String PAYMENTTYPE_ISBLANK = "pkp.sign.settlePayment.paymentType.blank";
	
	public static final String WAYBILLNO_IS_NULL="pkp.sign.settlePayment.waybillnoIsNull";
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:26
	 * @param code
	 */
	public RepaymentException(String code) 
	{
		super();
		this.errCode = code;
	}
	
	/**
	 * 
	 * 有参构造
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-11 上午11:46:45
	 * @param code
	 * @param cause
	 */
	public RepaymentException(String code,  Throwable cause){
		  super(code, cause);
		  this.errCode = code;
	  }
}