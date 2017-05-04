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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/SendSmsVoiceException.java
 * 
 * FILE NAME        	: SendSmsVoiceException.java
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
 * FILE    NAME: SendSmsVoiceException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 发送短信或语音异常类
 * @author 026123-foss-lifengteng
 * @date 2012-12-27 上午11:02:04
 */
public class SendSmsVoiceException extends BusinessException {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -2802392152545063450L;

	//网点参数[省份名]不允许为空！
	public static final String STATIONPROVINCE_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationProvince";
		
	//网点参数[城市名]不允许为空！
	public static final String STATIONCITY_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationCity";
	
	//网点参数[区县名]不允许为空！
	public static final String STATIONDIST_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationDist";
		
	//网点参数[地址]不允许为空！
	public static final String STATIONADDRESS_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationAddress";
			
	//网点参数[电话]和[手机]不允许全为空！
	public static final String STATIONTELORPHONE_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationTelOrPhone";
		
	//网点参数[运输类型]不允许为空！
	public static final String STATIONTRANSPORTWAY_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationTransportWay";
			
	//网点参数[提货方式]不允许为空！
	public static final String STATIONMATCHTYPES_NULL="foss.pkp.waybill.gisQueryService.exception.nullStationMatchTypes";
			
	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException() {
		super();
	}

	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
	}

	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
	}

	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException(String code, String msg) {
		super(code, msg);
	}

	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 * 创建一个新的实例 SendSmsVoiceException
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午11:02:37
	 */
	public SendSmsVoiceException(String msg) {
		super(msg);
		this.errCode=msg;
	}
	
}