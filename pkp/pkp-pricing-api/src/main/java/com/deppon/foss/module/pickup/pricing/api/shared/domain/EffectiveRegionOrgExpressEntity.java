/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/EffectiveRegionOrgEntity.java
 * 
 * FILE NAME        	: EffectiveRegionOrgEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class EffectiveRegionOrgExpressEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4442507046817821455L;
	/**
	 *  区域编码
	 */
	private String priceRegionCode;
	/**
	 * 区域名称
	 */
	private String priceRegionName;
	/**
	 *  区域ID
	 */
	private String priceRegionId;
	/**
	 *  区域性质
	 */
	private String regionNature;
	/**
	 *  部门Id
	 */
	private String includeOrgId;
	/**
	 *  部门标杆编码
	 */
	private String includeOrgCode;
	/** 部门名称
	 * 
	 */
	private String includeOrgName;
	/**
	 *  部门所属城市CODE
	 */
	private String cityCode;
	/**
	 *  部门所属城市名称
	 */
	private String cityName;
	/**
	 *  部门所属区县CODE
	 */
	private String countyCode;
	/**
	 *  部门所属城市名称
	 */
	private String countyName;
	/**
	 *  部门所属省份CODE
	 */
	private String proCode;
	/** 
	 * 
	 * 部门所属省份名称
	 * 
	 */
	private String proName;
	/**
	 *  部门所属国家CODE
	 */
	private String nationCode;
	/**
	 *  部门所属国家名称
	 */
	private String nationName;
	/**
	 *  状态
	 */
	private String active; 
	/**
	 *  开始时间
	 */
	private Date beginTime;
	/**
	 *  结束时间
	 */
	private Date endTime;
	/**
	 *  时间戳
	 */
	private Long versionNo;
	/**
	 * 最后修改人所在部门
	 */
	private String modifyOrgCode;
	/**
	 * 创建人所在部门
	 * 
	 */
	private String createOrgCode;
	
	/**
	 * 开单日期
	 */
	private Date billDate;
	
	/**
	 * 获取 开单日期.
	 *
	 * @return the 开单日期
	 */
	public Date getBillDate() {
	    return billDate;
	}
	
	/**
	 * 设置 开单日期.
	 *
	 * @param billDate the new 开单日期
	 */
	public void setBillDate(Date billDate) {
	    this.billDate = billDate;
	}
	
	
	/**
	 * 
	 * <p>获得价格区域Code</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-25 上午9:31:26
	 * @return
	 * @see
	 */
	public String getPriceRegionCode() {
	    return priceRegionCode;
	}

	/**
	 * 
	 * <p>设置价格区域编码CODE</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-25 上午9:31:49
	 * @param priceRegionCode
	 * @see
	 */
	public void setPriceRegionCode(String priceRegionCode) {
	    this.priceRegionCode = priceRegionCode;
	}

	/**
	 * 
	 * <p>获得区域名称</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-25 上午9:32:06
	 * @return
	 * @see
	 */
	public String getPriceRegionName() {
	    return priceRegionName;
	}

	/**
	 * 
	 * <p>设置价格区域名称</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-25 上午9:32:18
	 * @param priceRegionName
	 * @see
	 */
	public void setPriceRegionName(String priceRegionName) {
	    this.priceRegionName = priceRegionName;
	}

	/**
	 * 
	 * <p>获得价格区域ID</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-25 上午9:32:35
	 * @return
	 * @see
	 */
	public String getPriceRegionId() {
	    return priceRegionId;
	}

	/**
	 * 
	 * <p>设置价格区域ID</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-2-25 上午9:32:49
	 * @param priceRegionId
	 * @see
	 */
	public void setPriceRegionId(String priceRegionId) {
	    this.priceRegionId = priceRegionId;
	}

	/**
	 * 获取 部门Id.
	 *
	 * @return the 部门Id
	 */
	public String getIncludeOrgId() {
		return includeOrgId;
	}
	
	/**
	 * 设置 部门Id.
	 *
	 * @param includeOrgId the new 部门Id
	 */
	public void setIncludeOrgId(String includeOrgId) {
		this.includeOrgId = includeOrgId;
	}
	
	/**
	 * 获取 部门标杆编码.
	 *
	 * @return the 部门标杆编码
	 */
	public String getIncludeOrgCode() {
		return includeOrgCode;
	}
	
	/**
	 * 设置 部门标杆编码.
	 *
	 * @param includeOrgCode the new 部门标杆编码
	 */
	public void setIncludeOrgCode(String includeOrgCode) {
		this.includeOrgCode = includeOrgCode;
	}
	
	/**
	 * 获取 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getIncludeOrgName() {
		return includeOrgName;
	}
	
	/**
	 * 设置 部门名称.
	 *
	 * @param includeOrgName the new 部门名称
	 */
	public void setIncludeOrgName(String includeOrgName) {
		this.includeOrgName = includeOrgName;
	}
	
	/**
	 * 获取 部门所属城市CODE.
	 *
	 * @return the 部门所属城市CODE
	 */
	public String getCityCode() {
		return cityCode;
	}
	
	/**
	 * 设置 部门所属城市CODE.
	 *
	 * @param cityCode the new 部门所属城市CODE
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * 获取 部门所属城市名称.
	 *
	 * @return the 部门所属城市名称
	 */
	public String getCityName() {
		return cityName;
	}
	
	/**
	 * 设置 部门所属城市名称.
	 *
	 * @param cityName the new 部门所属城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	/**
	 * 获取 状态.
	 *
	 * @return the 状态
	 */
	public String getActive() {
		return active;
	}
	
	/**
	 * 设置 状态.
	 *
	 * @param active the new 状态
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
	/**
	 * 获取 开始时间.
	 *
	 * @return the 开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	 * 设置 开始时间.
	 *
	 * @param beginTime the new 开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	/**
	 * 获取 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * 设置 结束时间.
	 *
	 * @param endTime the new 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 获取 时间戳.
	 *
	 * @return the 时间戳
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	
	/**
	 * 设置 时间戳.
	 *
	 * @param versionNo the new 时间戳
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	
	/**
	 * 获取 部门所属区县CODE.
	 *
	 * @return the 部门所属区县CODE
	 */
	public String getCountyCode() {
		return countyCode;
	}
	
	/**
	 * 设置 部门所属区县CODE.
	 *
	 * @param countyCode the new 部门所属区县CODE
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	/**
	 * 获取 部门所属城市名称.
	 *
	 * @return the 部门所属城市名称
	 */
	public String getCountyName() {
		return countyName;
	}
	
	/**
	 * 设置 部门所属城市名称.
	 *
	 * @param countyName the new 部门所属城市名称
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	/**
	 * 获取 部门所属省份CODE.
	 *
	 * @return the 部门所属省份CODE
	 */
	public String getProCode() {
		return proCode;
	}
	
	/**
	 * 设置 部门所属省份CODE.
	 *
	 * @param proCode the new 部门所属省份CODE
	 */
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	
	/**
	 * 获取 部门所属省份名称.
	 *
	 * @return the 部门所属省份名称
	 */
	public String getProName() {
		return proName;
	}
	
	/**
	 * 设置 部门所属省份名称.
	 *
	 * @param proName the new 部门所属省份名称
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	/**
	 * 获取 部门所属国家CODE.
	 *
	 * @return the 部门所属国家CODE
	 */
	public String getNationCode() {
		return nationCode;
	}
	
	/**
	 * 设置 部门所属国家CODE.
	 *
	 * @param nationCode the new 部门所属国家CODE
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
	
	/**
	 * 获取 部门所属国家名称.
	 *
	 * @return the 部门所属国家名称
	 */
	public String getNationName() {
		return nationName;
	}
	
	/**
	 * 设置 部门所属国家名称.
	 *
	 * @param nationName the new 部门所属国家名称
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
	/**
	 * 获取 最后修改人所在部门.
	 *
	 * @return the 最后修改人所在部门
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	
	/**
	 * 设置 最后修改人所在部门.
	 *
	 * @param modifyOrgCode the new 最后修改人所在部门
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
	
	/**
	 * 获取 创建人所在部门.
	 *
	 * @return the 创建人所在部门
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * 设置 创建人所在部门.
	 *
	 * @param createOrgCode the new 创建人所在部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * 获取 区域性质.
	 *
	 * @return the 区域性质
	 */
	public String getRegionNature() {
		return regionNature;
	}
	
	/**
	 * 设置 区域性质.
	 *
	 * @param regionNature the new 区域性质
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}


	

}