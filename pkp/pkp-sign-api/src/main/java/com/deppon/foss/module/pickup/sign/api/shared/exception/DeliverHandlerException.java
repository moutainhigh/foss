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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/exception/DeliverHandlerException.java
 * 
 * FILE NAME        	: DeliverHandlerException.java
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
 * 派送处理异常类
 * @author foss-meiying
 * @date 2012-10-30 上午11:41:41
 * @since
 * @version
 */
public class DeliverHandlerException extends BusinessException {
	//DeliverHandlerAction
	/**
	 * 运单号为{0}的运单还没有进行签收确认，不能生成送货交接列表！
	 */
	public static final String WAYBILLNO_NOT_SIGN ="pkp.sign.deliverHandlerException.waybillnoNotSign"; //运单号为{0}的运单还没有进行签收确认，不能生成送货交接列表！
	/**
	 * 运单号为空
	 */
	public static final String WAYBILLNO_IS_NULL="pkp.sign.deliverHandlerException.waybillnoisNull";//运单号为空
	/**
	 * 该派送单没有拉回货物.送货确认成功!
	 */
	public static final String NO_PULLBACK_GOODS_DELIVER_CONFIRM_SUCCESS ="pkp.sign.deliverHandlerException.noPullbackGoodsDeliverConfirmSuccess"; //该派送单没有拉回货物.送货确认成功!
	/**
	 * 送货确认成功！
	 */
	public static final String DELIVER_CONFIRM_SUCCESS ="pkp.sign.deliverHandlerException.deliverConfirmSuccess"; //送货确认成功！
	/**
	 * 拒收操作失败，请重新查询一下
	 */
	public static final String REFUSE_FAILED="pkp.sign.deliverHandlerException.RefuseFailed";//拒收操作失败，请重新查询或处理异常
	/**
	 * 签收失败，请重新查询一下
	 */
	public static final String SIGN_FAILED="pkp.sign.deliverHandlerException.signFailed";//签收操作失败，请在当前页面重新查询!
	/**
	 * 操作成功!
	 */
	public static final String OPERATER_SUCCESS="pkp.sign.deliverHandlerException.operaterSuccess";//操作成功!	
	/**
	 * 当前运单操作中，请稍后再试
	 */
	public static final String WAYBILL_LOCKED="pkp.sign.deliverHandlerException.waybill.locked";//当前运单存在其他用户操作中，请稍后在签收
	/**
	 * 该派送单已做过送货确认，不能重复操作
	 */
	public static final String DELIVER_IS_SIGNINFO_CONFIRMED="pkp.sign.deliverHandlerException.deliverbillIssignInfoConfirmed";//该派送单已做过送货确认，不能重复操作
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 当前部门有误，操作失败！
	 */
	public static final String CURRENTORG_CODE_FAILED="pkp.sign.deliverHandlerException.currentOrgCodeFailed";//登陆人部门有误，请重新登录操作
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2012-12-25 下午2:31:06
	 * @param code
	 */
	public DeliverHandlerException(String code) {
		super();
		this.errCode = code;
	}
	
	/**
	 * 有参构造函数
	 * @author foss-meiying
	 * @date 2012-12-25 下午2:31:14
	 * @param code
	 * @param cause
	 */
	public DeliverHandlerException(String code, Throwable cause) {
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
	public DeliverHandlerException(String code,Object... args) {
		super(code,args);
	}
	
}
