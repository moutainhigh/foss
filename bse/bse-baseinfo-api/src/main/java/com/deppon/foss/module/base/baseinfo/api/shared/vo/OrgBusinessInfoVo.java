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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OrgBusinessInfoVo.java
 * 
 * FILE NAME        	: OrgBusinessInfoVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

public class OrgBusinessInfoVo {
	OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=null; //部门基本信息（包括从UUMS同步过来的信息），公共信息
	SaleDepartmentEntity saleDepartmentEntity=null;//营业部信息
	OutfieldEntity outfieldEntity=null;//外场信息
	MotorcadeEntity motorcadeEntity=null;//车队&车队组&调度 信息

	public OrgBusinessInfoVo(){}
	
	public OrgBusinessInfoVo(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity){
		this.orgAdministrativeInfoEntity=orgAdministrativeInfoEntity;
	}
	
	
	
	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}
	public void setOrgAdministrativeInfoEntity(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}
	public SaleDepartmentEntity getSaleDepartmentEntity() {
		return saleDepartmentEntity;
	}
	public void setSaleDepartmentEntity(SaleDepartmentEntity saleDepartmentEntity) {
		this.saleDepartmentEntity = saleDepartmentEntity;
	}
	public OutfieldEntity getOutfieldEntity() {
		return outfieldEntity;
	}
	public void setOutfieldEntity(OutfieldEntity outfieldEntity) {
		this.outfieldEntity = outfieldEntity;
	}
	public MotorcadeEntity getMotorcadeEntity() {
		return motorcadeEntity;
	}
	public void setMotorcadeEntity(MotorcadeEntity motorcadeEntity) {
		this.motorcadeEntity = motorcadeEntity;
	}
	
}
