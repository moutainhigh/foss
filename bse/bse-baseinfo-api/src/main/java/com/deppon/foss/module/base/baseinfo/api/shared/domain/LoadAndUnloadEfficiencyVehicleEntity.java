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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LoadAndUnloadEfficiencyVehicleEntity.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 装卸车标准-车-时间
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class LoadAndUnloadEfficiencyVehicleEntity  extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231351664293234L;

    /**
    *虚拟编码
    */	
    private String virtualCode;

    /**
    *部门编码
    */	
    private String orgCode;

    /**
    *部门名称
    */	
    private String orgName;

    /**
    *车型长
    */	
    private String vehicleTypeLength;
    
    /**
    *车型长为车长编码，vehicleLength的实际长度
    */	
    private BigDecimal vehicleLength;

    /**
    *带高栏敞篷车装车标准用时
    */	
    private String glCpLoadHours;

    /**
    *带高栏敞篷车装车标准用分
    */	
    private String glCpLoadMins;

    /**
    *带高栏敞篷车卸车标准用时
    */	
    private String glCpUnloadHours;

    /**
    *带高栏敞篷车卸车标准用分
    */	
    private String glCpUnloadMins;

    /**
    *不带高栏敞篷车装车标准用时
    */	
    private String nglCpLoadHours;

    /**
    *不带高栏敞篷车装车标准用分
    */	
    private String nglCpLoadMins;

    /**
    *不带高栏敞篷车卸车标准用时
    */	
    private String nglCpUnloadHours;

    /**
    *不带高栏敞篷车卸车标准用分
    */	
    private String nglCpUnloadMins;

    /**
    *非敞篷车装车标准用时
    */	
    private String ncpLoadHours;

    /**
    *非敞篷车装车标准用分
    */	
    private String ncpLoadMins;

    /**
    *非敞篷车卸车标准用时
    */	
    private String ncpUnloadHours;

    /**
    *非敞篷车卸车标准用分
    */	
    private String ncpUnloadMins;

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
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param  orgCode  
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param  orgName  
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return vehicleTypeLength
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * @param  vehicleTypeLength  
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * @return vehicleLength
	 */
	public BigDecimal getVehicleLength() {
		return vehicleLength;
	}

	/**
	 * @param  vehicleLength  
	 */
	public void setVehicleLength(BigDecimal vehicleLength) {
		this.vehicleLength = vehicleLength;
	}

	/**
	 * @return glCpLoadHours
	 */
	public String getGlCpLoadHours() {
		return glCpLoadHours;
	}

	/**
	 * @param  glCpLoadHours  
	 */
	public void setGlCpLoadHours(String glCpLoadHours) {
		this.glCpLoadHours = glCpLoadHours;
	}

	/**
	 * @return glCpLoadMins
	 */
	public String getGlCpLoadMins() {
		return glCpLoadMins;
	}

	/**
	 * @param  glCpLoadMins  
	 */
	public void setGlCpLoadMins(String glCpLoadMins) {
		this.glCpLoadMins = glCpLoadMins;
	}

	/**
	 * @return glCpUnloadHours
	 */
	public String getGlCpUnloadHours() {
		return glCpUnloadHours;
	}

	/**
	 * @param  glCpUnloadHours  
	 */
	public void setGlCpUnloadHours(String glCpUnloadHours) {
		this.glCpUnloadHours = glCpUnloadHours;
	}

	/**
	 * @return glCpUnloadMins
	 */
	public String getGlCpUnloadMins() {
		return glCpUnloadMins;
	}

	/**
	 * @param  glCpUnloadMins  
	 */
	public void setGlCpUnloadMins(String glCpUnloadMins) {
		this.glCpUnloadMins = glCpUnloadMins;
	}

	/**
	 * @return nglCpLoadHours
	 */
	public String getNglCpLoadHours() {
		return nglCpLoadHours;
	}

	/**
	 * @param  nglCpLoadHours  
	 */
	public void setNglCpLoadHours(String nglCpLoadHours) {
		this.nglCpLoadHours = nglCpLoadHours;
	}

	/**
	 * @return nglCpLoadMins
	 */
	public String getNglCpLoadMins() {
		return nglCpLoadMins;
	}

	/**
	 * @param  nglCpLoadMins  
	 */
	public void setNglCpLoadMins(String nglCpLoadMins) {
		this.nglCpLoadMins = nglCpLoadMins;
	}

	/**
	 * @return nglCpUnloadHours
	 */
	public String getNglCpUnloadHours() {
		return nglCpUnloadHours;
	}

	/**
	 * @param  nglCpUnloadHours  
	 */
	public void setNglCpUnloadHours(String nglCpUnloadHours) {
		this.nglCpUnloadHours = nglCpUnloadHours;
	}

	/**
	 * @return nglCpUnloadMins
	 */
	public String getNglCpUnloadMins() {
		return nglCpUnloadMins;
	}

	/**
	 * @param  nglCpUnloadMins  
	 */
	public void setNglCpUnloadMins(String nglCpUnloadMins) {
		this.nglCpUnloadMins = nglCpUnloadMins;
	}

	/**
	 * @return ncpLoadHours
	 */
	public String getNcpLoadHours() {
		return ncpLoadHours;
	}

	/**
	 * @param  ncpLoadHours  
	 */
	public void setNcpLoadHours(String ncpLoadHours) {
		this.ncpLoadHours = ncpLoadHours;
	}

	/**
	 * @return ncpLoadMins
	 */
	public String getNcpLoadMins() {
		return ncpLoadMins;
	}

	/**
	 * @param  ncpLoadMins  
	 */
	public void setNcpLoadMins(String ncpLoadMins) {
		this.ncpLoadMins = ncpLoadMins;
	}

	/**
	 * @return ncpUnloadHours
	 */
	public String getNcpUnloadHours() {
		return ncpUnloadHours;
	}

	/**
	 * @param  ncpUnloadHours  
	 */
	public void setNcpUnloadHours(String ncpUnloadHours) {
		this.ncpUnloadHours = ncpUnloadHours;
	}

	/**
	 * @return ncpUnloadMins
	 */
	public String getNcpUnloadMins() {
		return ncpUnloadMins;
	}

	/**
	 * @param  ncpUnloadMins  
	 */
	public void setNcpUnloadMins(String ncpUnloadMins) {
		this.ncpUnloadMins = ncpUnloadMins;
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