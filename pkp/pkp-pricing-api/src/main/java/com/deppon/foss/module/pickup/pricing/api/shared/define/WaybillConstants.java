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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/WaybillConstants.java
 * 
 * FILE NAME        	: WaybillConstants.java
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
package com.deppon.foss.module.pickup.pricing.api.shared.define;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.util.PropertiesUtil;

/**
 * 词条代码:WAYBILL_STATUS
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-2-25 下午4:11:40, </p>
 * @author foss-sunrui
 * @date 2013-2-25 下午4:11:40
 * @since
 * @version
 */
public class WaybillConstants {
	
	/**
	 * foss系统
	 */
	public final static String YES = "Y";
	
	//发票标记
	public final static String INVOICE_TYPE_02="02—非运输专票6%";
	public final static String INVOICE_TYPE_01="01—运输专票11%";
	public final static String INVOICE_02="INVOICE_TYPE_02";
	public final static String INVOICE_01="INVOICE_TYPE_01";
	
	public final static  String BILLINGWAY_WEIGHT = "WEIGHT";//按重量计费
	
	public final static  String BILLINGWAY_VOLUME = "VOLUME";//按体积计费
	
	public static final String DICTIONARY_TERMS_CODE="PKP_PRICE_CHANNEL";
	
	public final static  String LRF_FLIGHT="LRF" ;//精准汽运(长途)
	public final static  String SRF_FLIGHT="SRF" ;//精准汽运(短途)
	public final static  String TRUCK_FLIGHT = "FLF";//精准卡航
	public final static  String FSF_FLIGHT="FSF";//精准城运
	public final static  String TRANS_VEHICLE = "TRANS_VEHICLE";//汽运
	
	/**
	 * 内部发货类型
	 * dp-foss-dongjialing
	 * 225131
	 */
	//发货方付
	public static final String DELIVERY_PAY = "DELIVERY_PAY";
	//收货方付
	public static final String RECIVE_PAY = "RECIVE_PAY";
	
	/**
	 * 配置参数-接送货-规则引擎是否开启
	 */
	public final static String BRMS_ACTIVE_START = "BRMS_ACTIVE_START";
	
	//定义数据字典用于查询包装子服务来源
    public static final String BZ_TERMS_CODE="VALUEADDED_PACKAGE_TYPE";
    
    //零担梯度折扣
  	public static final String FRT_GRADESREBATE = "FRTGRADESREBATE";
    
    //零担统称
  	public static final String TYPE_OF_CARGO = "CARGO";
  	//快递统称
  	public static final String TYPE_OF_EXPRESS = "EXPRESS";
    //汽运偏线
  	public final static  String PX = "PX";
  	//空运
  	public final static  String KY = "KY";
}