/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/dto/ToDoMsgDto.java
 * 
 * FILE NAME        	: ToDoMsgDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;

/**
 * 待办事项实体Dto
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-25 上午10:13:38
 */
public class ToDoMsgDto extends ToDoMsgEntity {
 
	private static final long serialVersionUID = -8894494337349688323L;

	/**
	 * 创建开始时间
	 */
	private Date createStartTime;
	
	/**
	 * 创建结束时间
	 */
	private Date createEndTime;
	
	/**
	 * 未处理待办数量
	 */
	private int noDealNum;
	
	private String toDoItemIp;

	
	public Date getCreateStartTime() {
		return createStartTime;
	}

	
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	
	public Date getCreateEndTime() {
		return createEndTime;
	}

	
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	
	public int getNoDealNum() {
		return noDealNum;
	}

	
	public void setNoDealNum(int noDealNum) {
		this.noDealNum = noDealNum;
	}


	public String getToDoItemIp() {
		return toDoItemIp;
	}


	public void setToDoItemIp(String toDoItemIp) {
		this.toDoItemIp = toDoItemIp;
	}
	
	
	
}
