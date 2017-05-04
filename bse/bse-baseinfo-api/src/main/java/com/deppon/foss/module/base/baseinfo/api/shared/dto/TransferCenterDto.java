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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/TransferCenterDto.java
 * 
 * FILE NAME        	: TransferCenterDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferCenterEntity;

/**
 * 外场扩展Dto
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-30 下午2:19:30
 */
public class TransferCenterDto extends TransferCenterEntity {

	private static final long serialVersionUID = -3008888730643693707L;

	/**
	 * 模糊查询条件
	 */
	private String queryParam;
	
	/**
	 * 是否查询标识
	 */
	private String isFlag;
	
	/**
	 * 用户编码
	 */
	private String userCode;
	
	/**
	 * 部门编码集合
	 */
	private List<String> orgCodes;
	/**
	 * 用户当前所属部门
	 */
	private String currentOrgCode;
	
	private String flag;
	
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}

	
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}

	
	public String getUserCode() {
		return userCode;
	}

	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}


	
	public List<String> getOrgCodes() {
		return orgCodes;
	}


	
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}
