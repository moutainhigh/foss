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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/LoadAndUnloadEfficiencyManEntity.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyManEntity.java
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
 * 装卸车标准-吨-人天
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class LoadAndUnloadEfficiencyManEntity  extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231351673463718L;

    /**
    *部门编码
    */	
    private String orgCode;

    /**
    *部门名称
    */	
    private String orgName;

    /**
    *平均人天装车吨数
    */	
    private BigDecimal loadWeightStd;

    /**
    *平均人天卸车吨数
    */	
    private BigDecimal loadVolumeStd;

    /**
    *是否启用
    */	
    private String active;

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
	 * @return loadWeightStd
	 */
	public BigDecimal getLoadWeightStd() {
		return loadWeightStd;
	}

	/**
	 * @param  loadWeightStd  
	 */
	public void setLoadWeightStd(BigDecimal loadWeightStd) {
		this.loadWeightStd = loadWeightStd;
	}

	/**
	 * @return loadVolumeStd
	 */
	public BigDecimal getLoadVolumeStd() {
		return loadVolumeStd;
	}

	/**
	 * @param  loadVolumeStd  
	 */
	public void setLoadVolumeStd(BigDecimal loadVolumeStd) {
		this.loadVolumeStd = loadVolumeStd;
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