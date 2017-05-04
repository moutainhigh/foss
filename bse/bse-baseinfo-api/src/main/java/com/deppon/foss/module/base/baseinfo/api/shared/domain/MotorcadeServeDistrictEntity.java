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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/MotorcadeServeDistrictEntity.java
 * 
 * FILE NAME        	: MotorcadeServeDistrictEntity.java
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
 * 车队负责的行政区域
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class MotorcadeServeDistrictEntity  extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231353143619578L;

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
    *行政区域部门编码
    */	
    private String districtCode;

    /**
    *行政区域部门名称
    */	
    private String districtName;
    /**
     *行政区域上级部门名称
     */	
     private String parentDistrictName;

    /**
    *是否启用
    */	
    private String active;

    
    /**
	 * @return parentDistrictName
	 */
	public String getParentDistrictName() {
		return parentDistrictName;
	}
	/**
	 * @param  parentDistrictName  
	 */
	public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
	}

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
	 * @return districtCode
	 */
	public String getDistrictCode() {
		return districtCode;
	}

	/**
	 * @param  districtCode  
	 */
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	/**
	 * @return districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * @param  districtName  
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
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