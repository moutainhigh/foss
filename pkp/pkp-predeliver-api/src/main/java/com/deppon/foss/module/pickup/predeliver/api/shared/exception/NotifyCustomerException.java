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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/exception/NotifyCustomerException.java
 * 
 * FILE NAME        	: NotifyCustomerException.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 
 * 客户通知异常类
 * @author ibm-wangfei
 * @date Nov 14, 2012 5:34:33 PM
 */
public class NotifyCustomerException extends BusinessException {

	private static final long serialVersionUID = -2057299393964010630L;
	// error常量
	public static final String ERROR = "error";
	// 短信or语音不能为空
	public static final String MESSAGE_EMPTY = "pkp.predeliver.notifyCustomer.smsOrVoice.is.empty";
	// 短语or语音模版不存在
	public static final String TEMPLATE_EMPTY = "pkp.predeliver.notifyCustomer.not.match.smsOrVoice.template";
	
	//发送短信失败！原因：
	public static final String SENDMESSAGE_FAIL="foss.pkp.waybill.sendSmsVoiceService.exception.failSendMessage";
	//发送短信失败！
	public static final String SMS_FAIL ="发送短信失败！";
	//短信/语音模板为空
	public static final String SMSTEMPLETE_NULL="foss.pkp.waybill.sendSmsVoiceService.exception.nullSMSTemplete";
	public static final String NOTIFY_UNDERWAY ="pkp.predeliver.notifyCustomer.notify.underway"; //本运单正在通知中
	public static final String WAYBILL_NOT_MAKEUP ="pkp.predeliver.notifyCustomer.waybillNotMakeUp"; //运单号{0}未补录，需要开单部门补录信息
	public static final String WAYBILL_IS_SEND_MESS ="pkp.predeliver.notifyCustomer.waybillIsSendMess"; //该运单当天已经通知过，不能重复发送
	//推送到货信息的运单号为空
	public static final String WAYBILLNO_IS_NULL = "推送到货信息运单号为空";
	//推送到货信息的接口无法访问
	public static final String ARRIVAL_GOODS_INACCESSIBLE = "推送到货信息接口无法访问";
	/**
	 * NotifyCustomerException 构造函数
	 * 
	 * @param errCode
	 * 
	 * @author ibm-wangfie
	 * @date Mar 15, 2013 4:37:57 PM
	 */
	public NotifyCustomerException(String errCode) {
		super();
		super.errCode = errCode;
	}
	/**
	 * NotifyCustomerException 构造函数
	 * 
	 * @param code
	 * @param msg
	 * @author ibm-wangfie
	 * @date Mar 15, 2013 4:37:57 PM
	 */
	public NotifyCustomerException(String code, String msg) {
		super(code, msg);
		this.errCode=code;
	}
	/**
	 * NotifyCustomerException 构造函数
	 * 
	 * @param msg
	 * @param cause
	 * @author ibm-wangfie
	 * @date Mar 15, 2013 4:37:57 PM
	 */
	public NotifyCustomerException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode=msg;
	}
	
	/**
	 * NotifyCustomerException 构造函数
	 * 
	 * @param msg
	 * @param cause
	 * @author ibm-wangfie
	 * @date Mar 15, 2013 4:37:57 PM
	 */
	public NotifyCustomerException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}
}