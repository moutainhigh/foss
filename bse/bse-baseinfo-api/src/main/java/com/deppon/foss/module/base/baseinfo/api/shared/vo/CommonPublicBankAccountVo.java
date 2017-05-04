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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonPublicBankAccountVo.java
 * 
 * FILE NAME        	: CommonPublicBankAccountVo.java
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
 * FILE    NAME: CommonOrgVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PublicBankAccountDto;

/**
 * 组织对外账户action和前台传递值实体
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 上午10:04:16
 */
public class CommonPublicBankAccountVo implements Serializable {

	private static final long serialVersionUID = -9053036159938201215L;

	/**
	 * 传递到前台的组织对公账户集合
	 */
	private List<PublicBankAccountEntity> publicBankAccountEntityList;

	/**
	 * 组织对公账户Dto
	 */
	private PublicBankAccountDto publicBankAccountDto;

	public List<PublicBankAccountEntity> getPublicBankAccountEntityList() {
		return publicBankAccountEntityList;
	}

	public void setPublicBankAccountEntityList(
			List<PublicBankAccountEntity> publicBankAccountEntityList) {
		this.publicBankAccountEntityList = publicBankAccountEntityList;
	}

	public PublicBankAccountDto getPublicBankAccountDto() {
		return publicBankAccountDto;
	}

	public void setPublicBankAccountDto(
			PublicBankAccountDto publicBankAccountDto) {
		this.publicBankAccountDto = publicBankAccountDto;
	}

}
