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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/define/DeliverHandlerConstants.java
 * 
 * FILE NAME        	: DeliverHandlerConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.define;

/**
 * 派送处理
 * @author foss-meiying
 * @date 2012-11-12 下午1:58:30
 * @since
 * @version
 */
public class DeliverHandlerConstants {
	/**
	 * 交接类型--接货
	 */
	public static final String HANDOVER_TYPE_RECEIVE = "RECEIVE";
	/**
	 * 交接类型--转货
	 */
	public static final String HANDOVER_TYPE_TRANSFER = "TRANSFER";
	/**
	 * 交接类型--拉回货物
	 */
	public static final String HANDOVER_TYPE_BACK = "GOODS_BACK";
	/**
	 * 默认驻地外场
	 */
	public static final String DEFAULT_TRANSFER_CENTER = "N/A";
}