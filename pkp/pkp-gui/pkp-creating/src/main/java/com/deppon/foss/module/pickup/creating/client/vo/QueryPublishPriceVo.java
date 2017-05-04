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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/vo/QueryPublishPriceVo.java
 * 
 * FILE NAME        	: QueryPublishPriceVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.creating.client.vo;

/**
 * 查询公布价
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-17 下午4:11:08,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-17 下午4:11:08
 * @since
 * @version
 */
public class QueryPublishPriceVo {
	
	/**
	 *出发地
	 */
	private String createOrgCode;
	
	/**
	 * 出发地name
	 */
	private String createOrgName;
	
	/**
	 * b)	目的地
	 */
	private String targetOrgCode;

	/**
	 * b)	目的地name
	 */
	private String targetOrgName;
	
	

	/**
	 * @return the createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}


	/**
	 * @param createOrgName the createOrgName to set
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}


	/**
	 * @return the targetOrgName
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}


	/**
	 * @param targetOrgName the targetOrgName to set
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}


	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}


	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}


	/**
	 * @return the targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}


	/**
	 * @param targetOrgCode the targetOrgCode to set
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	
	

}