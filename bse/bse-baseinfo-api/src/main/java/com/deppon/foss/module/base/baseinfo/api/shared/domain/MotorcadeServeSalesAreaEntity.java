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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/MotorcadeServeSalesAreaEntity.java
 * 
 * FILE NAME        	: MotorcadeServeSalesAreaEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 车队负责的营业区域
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class MotorcadeServeSalesAreaEntity  extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231353157041375L;

    /**
    *虚拟编码
    */	
    private String virtualCode;

    /**
    *车队部门编码
    */	
    private String motorcadeCode;

    /**
    *车队部门名称
    */	
    private String motorcadeName;

    /**
    *营业区域部门编码
    */	
    private String salesareaCode;

    /**
    *营业区域部门名称
    */	
    private String salesareaName;

    /**
    *是否启用
    */	
    private String active;

	/**
	 * @return virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param  virtualCode  
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * @return motorcadeCode
	 */
	public String getMotorcadeCode() {
		return motorcadeCode;
	}

	/**
	 * @param  motorcadeCode  
	 */
	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
	}

	/**
	 * @return motorcadeName
	 */
	public String getMotorcadeName() {
		return motorcadeName;
	}

	/**
	 * @param  motorcadeName  
	 */
	public void setMotorcadeName(String motorcadeName) {
		this.motorcadeName = motorcadeName;
	}

	/**
	 * @return salesareaCode
	 */
	public String getSalesareaCode() {
		return salesareaCode;
	}

	/**
	 * @param  salesareaCode  
	 */
	public void setSalesareaCode(String salesareaCode) {
		this.salesareaCode = salesareaCode;
	}

	/**
	 * @return salesareaName
	 */
	public String getSalesareaName() {
		return salesareaName;
	}

	/**
	 * @param  salesareaName  
	 */
	public void setSalesareaName(String salesareaName) {
		this.salesareaName = salesareaName;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}
}