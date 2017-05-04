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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/vo/TodoActionVo.java
 * 
 * FILE NAME        	: TodoActionVo.java
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
package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LatestHandOverDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;

/**
 * 
 * 待办事宜VO
 * 
 * @author dp-zhaobin
 * @date 2012-10-20 下午3:27:03
 */
public class TodoActionVo {

	/**
	 * 待办事宜查询条件dto
	 */
	private TodoConditionDto todoConditionDto;

	/**
	 * TodoActionDto对象列表
	 */
	private List<TodoActionDto> todoActionDtoList;
	
	/**
	 * 标签打印用到
	 */
	private List<BarcodePrintLabelDto> barcodePrintLabelDto;

	/**
	 * 标签重打
	 */
	private LabeledGoodTodoDto labeledGoodTodoDto;
	
	private List<LatestHandOverDto> latestHandoverDtoList;

	public List<LatestHandOverDto> getLatestHandoverDtoList() {
		return latestHandoverDtoList;
	}

	public void setLatestHandoverDtoList(
			List<LatestHandOverDto> latestHandoverDtoList) {
		this.latestHandoverDtoList = latestHandoverDtoList;
	}

	/**
	 * Gets the todo condition dto.
	 *
	 * @return the todoConditionDto .
	 */
	public TodoConditionDto getTodoConditionDto() {
		return todoConditionDto;
	}

	/**
	 * Sets the todo condition dto.
	 *
	 * @param todoConditionDto the todoConditionDto to set.
	 */
	public void setTodoConditionDto(TodoConditionDto todoConditionDto) {
		this.todoConditionDto = todoConditionDto;
	}

	/**
	 * Gets the todo action dto list.
	 *
	 * @return the todoActionDtoList .
	 */
	public List<TodoActionDto> getTodoActionDtoList() {
		return todoActionDtoList;
	}

	/**
	 * Sets the todo action dto list.
	 *
	 * @param todoActionDtoList the todoActionDtoList to set.
	 */
	public void setTodoActionDtoList(List<TodoActionDto> todoActionDtoList) {
		this.todoActionDtoList = todoActionDtoList;
	}

	/**
	 * Gets the labeled good todo dto.
	 *
	 * @return the labeledGoodTodoDto .
	 */
	public LabeledGoodTodoDto getLabeledGoodTodoDto() {
		return labeledGoodTodoDto;
	}

	/**
	 * Sets the labeled good todo dto.
	 *
	 * @param labeledGoodTodoDto the labeledGoodTodoDto to set.
	 */
	public void setLabeledGoodTodoDto(LabeledGoodTodoDto labeledGoodTodoDto) {
		this.labeledGoodTodoDto = labeledGoodTodoDto;
	}

	/**
	 * @return the barcodePrintLabelDto
	 */
	public List<BarcodePrintLabelDto> getBarcodePrintLabelDto() {
		return barcodePrintLabelDto;
	}

	/**
	 * @param barcodePrintLabelDto the barcodePrintLabelDto to set
	 */
	public void setBarcodePrintLabelDto(
			List<BarcodePrintLabelDto> barcodePrintLabelDto) {
		this.barcodePrintLabelDto = barcodePrintLabelDto;
	}
}