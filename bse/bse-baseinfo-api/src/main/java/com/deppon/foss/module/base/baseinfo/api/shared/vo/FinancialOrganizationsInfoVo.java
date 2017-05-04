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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/FinancialOrganizationsInfoVo.java
 * 
 * FILE NAME        	: FinancialOrganizationsInfoVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
public class FinancialOrganizationsInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7946775205146630391L;
    //财务组织实体
	private FinancialOrganizationsEntity financialOrganizationsEntity;

	public FinancialOrganizationsEntity getFinancialOrganizationsEntity() {
		return financialOrganizationsEntity;
	}

	public void setFinancialOrganizationsEntity(
			FinancialOrganizationsEntity financialOrganizationsEntity) {
		this.financialOrganizationsEntity = financialOrganizationsEntity;
	}
	//全路径
	private String fullPath;
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	

}
