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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmTransportTypeEnum.java
 * 
 * FILE NAME        	: CrmTransportTypeEnum.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

/**
 * crm运输方式- foss运输方式
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-15 下午4:41:42, </p>
 * @author foss-sunrui
 * @date 2012-11-15 下午4:41:42
 * @since
 * @version
 */
public enum CrmTransportTypeEnum {
	//精准汽运(长)
	JZQYLONG("JZQY_LONG", "精准汽运(长)", "LRF" ), //精准汽运(长)
	JZQYSHORT("JZQY_SHORT", "精准汽运(短)", "SRF" ), //精准汽运(短)
	JZKY("JZKY", "精准空运",  "AF"), //精准空运
	JZKH("JZKH", "精准卡航",  "FLF"), //精准卡航
	//zxy 20131012 BUG-56985 start 修改：精准城运code写错了，改成FSF
	JZCY("JZCY", "精准城运",  "FSF"), //精准城运
	//zxy 20131012 BUG-56985 end 修改：精准城运code写错了，改成FSF
	AGENTVEHICLE("AGENT_VEHICLE", "汽运偏线", "PLF"),//汽运偏线
	PACKAGE("PACKAGE", "标准快递", "PACKAGE"),//标准快递
	RCP("RCP", "3.60特惠件", "RCP"),//标准快递
	DEAP("DEAP", "商务专递", "DEAP"),//商务专递
	EPAP("EPEP", "电商尊享", "EPEP"),//电商尊享
	//wutao 添加新产品
	//DEFECT-5165:059387登陆GUI，导入场到场订单Y141101123855开单，运输性质带入门到门，且没有勾选精准大票
	DTD("DTD", "精准大票.经济件", "DTD"),//精准大票.经济件
	YTY("YTY", "精准大票.标准件", "YTY"),//精准大票.标准件
	PCP("PCP","精准包裹","PCP");//精准包裹


	/**
	 * crm传过来的运输方式code
	 */
	private String code;
	
	/**
	 * 中文名称
	 */
	private String name;
	
	/**
	 * foss运输方式code
	 */
	private String fossCode;
	
	/**
	 * 构造方法
	 * @param crmCode
	 * @param name
	 * @param fossCode
	 */
	private CrmTransportTypeEnum(String code, String name, String fossCode) {
	     this.code = code;
	     this.name = name;
	     this.fossCode = fossCode;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the fossCode
	 */
	public String getFossCode() {
		return fossCode;
	}
	
	
	
}