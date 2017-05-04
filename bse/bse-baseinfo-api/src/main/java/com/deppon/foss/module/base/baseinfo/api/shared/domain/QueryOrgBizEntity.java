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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/QueryOrgBizEntity.java
 * 
 * FILE NAME        	: QueryOrgBizEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class QueryOrgBizEntity extends BaseEntity {
	
	/**
	 * serialVersionUID:类唯一标识
	 */
	private static final long serialVersionUID = 5285477858542150020L;

	/**
	 * 主键
	 */
	private String tid;
	
	/**
	 * 编码
	 */
	private String tregionCode;

	/**
	 * 区域全称
	 */
	private String tfullName;

	/**
	 * 简称
	 */
	private String tforShort;

	/**
	 * 可用名称
	 */
	private String tusableName;

	/**
	 * 区域全称-后缀
	 */
	private String tregionSuffix;

	/**
	 * 上级行政区域
	 */
	private String tparentRegionCode;

	/**
	 * 上级行政区域-显示名称
	 */
	private String tparentRegionDis;

	/**
	 * 是否-虚拟行政区域，0为否，1为是,3为全部
	 */
	private String tisVirtualCity="3";

	/**
	 * 是否-虚拟行政区域，0为否，1为是,3为全部
	 */
	private String tqueryOrgBizLevel;
	
	/**
	 * 行政区域是否可用
	 */
	private String tusable="1";

	/**
	 * @return tid
	 */
	public String getTid() {
		return tid;
	}

	/**
	 * @param  tid  
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return tregionCode
	 */
	public String getTregionCode() {
		return tregionCode;
	}

	/**
	 * @param  tregionCode  
	 */
	public void setTregionCode(String tregionCode) {
		this.tregionCode = tregionCode;
	}

	/**
	 * @return tfullName
	 */
	public String getTfullName() {
		return tfullName;
	}

	/**
	 * @param  tfullName  
	 */
	public void setTfullName(String tfullName) {
		this.tfullName = tfullName;
	}

	/**
	 * @return tforShort
	 */
	public String getTforShort() {
		return tforShort;
	}

	/**
	 * @param  tforShort  
	 */
	public void setTforShort(String tforShort) {
		this.tforShort = tforShort;
	}

	/**
	 * @return tusableName
	 */
	public String getTusableName() {
		return tusableName;
	}

	/**
	 * @param  tusableName  
	 */
	public void setTusableName(String tusableName) {
		this.tusableName = tusableName;
	}

	/**
	 * @return tregionSuffix
	 */
	public String getTregionSuffix() {
		return tregionSuffix;
	}

	/**
	 * @param  tregionSuffix  
	 */
	public void setTregionSuffix(String tregionSuffix) {
		this.tregionSuffix = tregionSuffix;
	}

	/**
	 * @return tparentRegionCode
	 */
	public String getTparentRegionCode() {
		return tparentRegionCode;
	}

	/**
	 * @param  tparentRegionCode  
	 */
	public void setTparentRegionCode(String tparentRegionCode) {
		this.tparentRegionCode = tparentRegionCode;
	}

	/**
	 * @return tparentRegionDis
	 */
	public String getTparentRegionDis() {
		return tparentRegionDis;
	}

	/**
	 * @param  tparentRegionDis  
	 */
	public void setTparentRegionDis(String tparentRegionDis) {
		this.tparentRegionDis = tparentRegionDis;
	}

	/**
	 * @return tisVirtualCity
	 */
	public String getTisVirtualCity() {
		return tisVirtualCity;
	}

	/**
	 * @param  tisVirtualCity  
	 */
	public void setTisVirtualCity(String tisVirtualCity) {
		this.tisVirtualCity = tisVirtualCity;
	}

	/**
	 * @return tqueryOrgBizLevel
	 */
	public String getTqueryOrgBizLevel() {
		return tqueryOrgBizLevel;
	}

	/**
	 * @param  tqueryOrgBizLevel  
	 */
	public void setTqueryOrgBizLevel(String tqueryOrgBizLevel) {
		this.tqueryOrgBizLevel = tqueryOrgBizLevel;
	}

	/**
	 * @return tusable
	 */
	public String getTusable() {
		return tusable;
	}

	/**
	 * @param  tusable  
	 */
	public void setTusable(String tusable) {
		this.tusable = tusable;
	}
}