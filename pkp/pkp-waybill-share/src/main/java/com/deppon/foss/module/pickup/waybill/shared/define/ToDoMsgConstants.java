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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/ToDoMsgConstants.java
 * 
 * FILE NAME        	: ToDoMsgConstants.java
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
 * 任务代办常量类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-2-25 下午4:11:40, </p>
 * @author foss-sunrui
 * @date 2013-2-25 下午4:11:40
 * @since
 * @version
 */
public class ToDoMsgConstants {

	/**
	 * 代办刷新时间key
	 */
	public static final String KEY_TODO_MSG_AUTO_REFRESH_MINUTES = "key.todomsg.auto.refresh.minutes";
	
	/**
	 * 代办刷新时间
	 */
	public static final int VALUE_TODO_MSG_AUTO_REFRESH_MINUTES = 5;
	
	/**
	 * 提醒时间key
	 */
	public static final String KEY_TODO_MSG_NEED_REMIND = "key.todomsg.need.remind";
	
	/**
	 * 提醒panel标题key
	 */
	// do do message list panel title 
	public static final String KEY_TODO_MSG_LIST_PANEL_TITLE = "ui.todomsg.list.panel.title";
	
	/**
	 *提醒类型 biztype检查
	 */
	// pkp to do message business types
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_CHANGING_CHEKC = "pkp.chgchk";
	/**
	 *提醒类型 biztype处理
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_CHANGING_HANDLE = "pkp.chghdl";
	/**
	 *提醒类型 biztype修改标签
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_MODIFY_LABEL = "pkp.mdylbl";
	/**
	 *提醒类型 biztype离线运单
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_ACTIVE_OFFLINE_WAYBILL = "pkp.aofbil";
	/**
	 *提醒类型 biztype整车未签收运单
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_NOT_SIGNED_WAYBILL = "pkp.notsignbil";
	
	/**
	 *提醒类型 biztype检查
	 */
	// pkp to do message business types
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_CHANGING_CHEKC_WAYBILL = "pkp.chgchkexp";
	/**
	 *提醒类型 biztype处理
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_CHANGING_HANDLE_WAYBILL = "pkp.chghdlexp";
	/**
	 *提醒类型 biztype修改标签
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_MODIFY_LABEL_WAYBILL = "pkp.mdylblexp";
	/**
	 *提醒类型 biztype离线运单
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_ACTIVE_OFFLINE_WAYBILLRCF = "pkp.aofbilexp";
	/**
	 *提醒类型 biztype整车未签收运单
	 */
	public static final String KEY_TODO_MSG_BIZTYPE_PKP_NOT_SIGNED_WAYBILLRCF = "pkp.notsignbilexp";
	/**
	/**
	 * 提醒类型 保存成功
	 */
	// to do message save config success
	public static final String KEY_TODO_MSG_SAVE_CONFIG_SUCCESS = "ui.todomsg.save.config.success";
	/**
	 * 提醒类型 刷新时间
	 */
	// to do message config setting error 
	public static final String KEY_TODO_MSG_SET_CONFIG_ERROR_REFRESH_MINUTES = "ui.todomsg.set.config.error.refresh.minutes";
	
	
}