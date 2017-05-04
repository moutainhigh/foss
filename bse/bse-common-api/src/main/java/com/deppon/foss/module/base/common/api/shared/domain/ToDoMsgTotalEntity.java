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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/ToDoMsgTotalEntity.java
 * 
 * FILE NAME        	: ToDoMsgTotalEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 待办事项统计
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-7 下午4:45:57
 */
public class ToDoMsgTotalEntity extends BaseEntity {

	private static final long serialVersionUID = 7032238657526149459L;

	private String id;

	/**
	 * 待办标题
	 */
	private String title;

	/**
	 * 接收方组织编码
	 */
	private String receiveOrgCode;

	/**
	 * 接收方角色编码
	 */
	private String receiveRoleCode;
	
	/**
	 * 接收方类型
	 */
	private String receiveType;
	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 未处理条目
	 */
	private int noDealNum;

	 
	public ToDoMsgTotalEntity() {

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	
	public String getReceiveRoleCode() {
		return receiveRoleCode;
	}

	
	public void setReceiveRoleCode(String receiveRoleCode) {
		this.receiveRoleCode = receiveRoleCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	
	public int getNoDealNum() {
		return noDealNum;
	}

	
	public void setNoDealNum(int noDealNum) {
		this.noDealNum = noDealNum;
	}

	
	public String getReceiveType() {
		return receiveType;
	}

	
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	 
}
