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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/SaleDepartmentVo.java
 * 
 * FILE NAME        	: SaleDepartmentVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.vo
 * FILE    NAME: SaleDepartmentVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * 营业部vo
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:23:54
 */
public class SaleDepartmentVo implements Serializable{
	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = 8956173309045310167L;
	//查询条件
	private SaleDepartmentEntity departmentEntity;
	//返回结果
	private List<SaleDepartmentEntity> departmentEntities;
	/**
	 *getter
	 */
	public SaleDepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}
	/**
	 *setter
	 */
	public void setDepartmentEntity(SaleDepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}
	/**
	 *getter
	 */
	public List<SaleDepartmentEntity> getDepartmentEntities() {
		return departmentEntities;
	}
	/**
	 *setter
	 */
	public void setDepartmentEntities(List<SaleDepartmentEntity> departmentEntities) {
		this.departmentEntities = departmentEntities;
	}
	
}
