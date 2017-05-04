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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/PublicBankAccountDto.java
 * 
 * FILE NAME        	: PublicBankAccountDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;

/**
 * 司机dto
 * @author 078823-foss-panGuangJun
 * @date 2012-12-28 上午10:27:47
 */
public class PublicBankAccountDto extends PublicBankAccountEntity{

	private static final long serialVersionUID = -4454336017791286068L;
	
	//部门编码
	private String deptCode;

	
	public String getDeptCode() {
		return deptCode;
	}

	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
 
}
