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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/GoodsAreaEntityDto.java
 * 
 * FILE NAME        	: GoodsAreaEntityDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;


/**
 *  
 * @author 105089-foss-yangtong
 * @date 2012-11-5 下午9:46:43
 */
public class GoodsAreaEntityDto extends GoodsAreaEntity{

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	//部门编码
	private String orgCode;
	
	//创建时间
	private Date createTime;
	
	//更新时间
	private Date modifyTime;
	
	//创建用户
	private String createUserCode;
	
	//更新用户
	private String modifyUserCode;

	
	/**
	 * @return the orgCode .
	 */
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	 *@param orgCode the orgCode to set.
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 *@param createTime the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * @return the modifyTime .
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	
	/**
	 *@param modifyTime the modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	
	/**
	 * @return the createUserCode .
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	
	/**
	 *@param createUserCode the createUserCode to set.
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	
	/**
	 * @return the modifyUserCode .
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	
	/**
	 *@param modifyUserCode the modifyUserCode to set.
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	
	
	
	
}