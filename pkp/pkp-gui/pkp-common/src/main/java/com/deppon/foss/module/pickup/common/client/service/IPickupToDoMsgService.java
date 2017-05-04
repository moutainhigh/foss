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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IPickupToDoMsgService.java
 * 
 * FILE NAME        	: IPickupToDoMsgService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.PickupToDoMsgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;

/**
 * 本地定时提醒接口
 * @author 026113-foss-linwensheng
 *
 */
public interface IPickupToDoMsgService {

	
	
	/*******待办事项消息*******/
	/** 
	 * <p>
	 * Description:根据传入条件查询待办事项，目前参数为业务类型（可为Null）<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-11-5S
	 * @param toDoMsgDto
	 * @return
	 * List<ToDoMsgDto>
	 */
	List<PickupToDoMsgDto> queryToDoMsgList(PickupToDoMsgDto toDoMsgDto);

	/**
	 * 查询更改单代办数字
	 * @return
	 */
	Long queryTodoActionList(TodoConditionDto todoConditionDto);

	/**
	 * DMANA-9463 整车运单未签收提示功能
	 * @author 218438-foss-zhulei
	 */
	List<PickupToDoMsgDto> queryWaybillNotSignDataList(PickupToDoMsgDto toDoMsgDto);
	
	
	
	
	
	
	
	
}