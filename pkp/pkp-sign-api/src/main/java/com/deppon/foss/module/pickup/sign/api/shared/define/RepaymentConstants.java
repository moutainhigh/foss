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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/define/RepaymentConstants.java
 * 
 * FILE NAME        	: RepaymentConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.define;

/**
 * 
 * 付款
 * @author 043258-foss-zhaobin
 * @date 2012-11-28 上午11:45:14
 */
public class RepaymentConstants
{
		/***************************财务单据生成状态*******************************/
		// 财务单据未生成
		public static final String STLBILL_NOGENERATE = "STLBILL_NOGENERATE";
		// 财务单据生成中
		public static final String STLBILL_GENERATEING = "STLBILL_GENERATEING";
		// 财务单据已生成
		public static final String STLBILL_GENERATED = "STLBILL_GENERATED";
		// 财务单据无需生成
		public static final String STLBILL_NOTREQUIRE = "STLBILL_NOTREQUIRE";
		
		/***************************证件类型*******************************/	
		// 身份证
		public static final String ID_CARD = "ID_CARD";
		// 护照
		public static final String PASSPORT = "PASSPORT";
		// 驾驶证
		public static final String DRIVER_LICENSE = "DRIVER_LICENSE";
		// 暂住证
		public static final String TEMPORARY_RESIDENCE = "TEMPORARY_RESIDENCE";
		// 军官证
		public static final String MILITARY_OFFICER = "MILITARY_OFFICER";

	   /***************************合伙人单据扣款状态*******************************/
	   //扣款成功
		public static final String DONE_STATE = "DONE";
}