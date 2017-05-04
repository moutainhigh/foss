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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/LabeledGoodChangeHistoryConstants.java
 * 
 * FILE NAME        	: LabeledGoodChangeHistoryConstants.java
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
package com.deppon.foss.module.pickup.waybill.shared.define;

/**
 * 更改单修改件数和打木架数量修改详细信息常量
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-2-25 下午4:11:40, </p>
 * @author foss-sunrui
 * @date 2013-2-25 下午4:11:40
 * @since
 * @version
 */
public class LabeledGoodChangeHistoryConstants {

	/**
	 * 更改的货签的类型   新增一个货签 
	 */
	public static final String CHANGE_TYPE_NEW = "CHANGE_TYPE_NEW";
	
	/**
	 *  更改的货签的类型   删除一个货签
	 */
	public static final String CHANGE_TYPE_DELETE = "CHANGE_TYPE_DELETE";
	
	/**
	 * 更改的货签的类型 对一个已经存在的货签进行新增打木架或者打木箱处理
	 */
	public static final String CHANGE_TYPE_WOODEN_ADD = "CHANGE_TYPE_WOODEN_ADD";
	
	/**
	 * 对一个已经存在的要打木架或者打木箱的货签进行不再进行打木架或者打木箱操作
	 */
	public static final String CHANGE_TYPE_WOODEN_DELETE = "CHANGE_TYPE_WOODEN_DELETE";
	
	/**
	 * 更改的货签的类型 对一个新增的货签进行打木架处理
	 */
	public static final String CHANGE_TYPE_WOODEN_ADD_NEW = "CHANGE_TYPE_WOODEN_ADD_NEW";
	
	/**
	 * 包装类型:新增打木托  zxy 20131118 ISSUE-4391
	 */
	public static final String PACKAGE_TYPE_SALVER_ADD_NEW ="SALVER_ADD_NEW";
	
	/**
	 * 包装类型:删除打木托  zxy 20131118 ISSUE-4391
	 */
	public static final String PACKAGE_TYPE_SALVER_DELETE ="SALVER_DELETE";
	
	/**
	 * 该更改单的审批状态 流程处理中
	 */
	public static final String FLOW_STATUS_IN_PROCESS = "IN_PROCESS";
	
	/**
	 * 该更改单的审批状态  审批通过
	 */
	public static final String FLOW_STATUS_APPROVED = "APPROVED";
	
	
}