/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-closing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/api/shared/dto/MvrAfrDto.java
 * 
 * FILE NAME        	: MvrAfrDto.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpStEntity;

/**
 * 合伙人股份中转月报表dto
 */
public class MvrPtpStDto extends MvrPtpStEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8281462844008823663L;
	
	/** 始发部门标杆编码. */
	private String origUnifiedCode;
	
	/** 到达部门标杆编码. */
	private String destUnifiedCode;

	/** 总条数. */
	private int count;

	/** 用户编码-设置用户数据查询权限. */
	private String userCode;

	/** 查询结果列表. */
	private List<MvrPtpStEntity> mvrPtpStEntityList;

	/**
	 * Gets the count.
	 * 
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 * 
	 * @param count
	 *            the new count
	 */
	public void setCount(int count) {
		this.count = count;
	}


	public List<MvrPtpStEntity> getMvrPtpStEntityList() {
		return mvrPtpStEntityList;
	}

	public void setMvrPtpStEntityList(List<MvrPtpStEntity> mvrPtpStEntityList) {
		this.mvrPtpStEntityList = mvrPtpStEntityList;
	}

	/**
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOrigUnifiedCode() {
		return origUnifiedCode;
	}

	public void setOrigUnifiedCode(String origUnifiedCode) {
		this.origUnifiedCode = origUnifiedCode;
	}

	public String getDestUnifiedCode() {
		return destUnifiedCode;
	}

	public void setDestUnifiedCode(String destUnifiedCode) {
		this.destUnifiedCode = destUnifiedCode;
	}
	
	

}
