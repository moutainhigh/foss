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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dto/SysConfigDto.java
 * 
 * FILE NAME        	: SysConfigDto.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;


/**
 * 系统配置dto
 * WangQianJin
 *
 */
public class SysConfigDto {

	/**
	 * 系统配置code
	 */
    private String confCode;
    
    /**
     * 组织code
     */
    private String orgCode;
    
    /**
     * 组织类型
     */
    private String confType;
    
    /**
     * 系统配置类型
     */
    private String[] confCodes;
    
    /**
     * 是否激活
     */
    private String active;

	/**
	 * @return the confCode
	 */
	public String getConfCode() {
		return confCode;
	}

	/**
	 * @param confCode the confCode to set
	 */
	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the confType
	 */
	public String getConfType() {
		return confType;
	}

	/**
	 * @param confType the confType to set
	 */
	public void setConfType(String confType) {
		this.confType = confType;
	}

	/**
	 * @return the confCodes
	 */
	public String[] getConfCodes() {
		return confCodes;
	}

	/**
	 * @param confCodes the confCodes to set
	 */
	public void setConfCodes(String[] confCodes) {
		this.confCodes=confCodes ;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

   
}