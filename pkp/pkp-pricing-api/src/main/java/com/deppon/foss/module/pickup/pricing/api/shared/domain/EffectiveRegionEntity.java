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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/EffectiveRegionEntity.java
 * 
 * FILE NAME        	: EffectiveRegionEntity.java
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
public class EffectiveRegionEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1956644345297617547L;
	
	/**
	 * 获取 区域编码.
	 *
	 * @return the 区域编码
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 设置 区域编码.
	 *
	 * @param regionCode the new 区域编码
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 获取 区域名称.
	 *
	 * @return the 区域名称
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * 设置 区域名称.
	 *
	 * @param regionName the new 区域名称
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 获取 区域类型.
	 *
	 * @return the 区域类型
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * 设置 区域类型.
	 *
	 * @param regionType the new 区域类型
	 */
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	/**
	 * 获取 区域状态.
	 *
	 * @return the 区域状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 区域状态.
	 *
	 * @param active the new 区域状态
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
	 * 获取 描述.
	 *
	 * @return the 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 描述.
	 *
	 * @param description the new 描述
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取 国家编号.
	 *
	 * @return the 国家编号
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * 设置 国家编号.
	 *
	 * @param nationCode the new 国家编号
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	/**
	 * 获取 国家名称.
	 *
	 * @return the 国家名称
	 */
	public String getNationName() {
		return nationName;
	}

	/**
	 * 设置 国家名称.
	 *
	 * @param nationName the new 国家名称
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	/**
	 * 获取 省份编号.
	 *
	 * @return the 省份编号
	 */
	public String getProCode() {
		return proCode;
	}

	/**
	 * 设置 省份编号.
	 *
	 * @param proCode the new 省份编号
	 */
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	/**
	 * 获取 省份名称.
	 *
	 * @return the 省份名称
	 */
	public String getProName() {
		return proName;
	}

	/**
	 * 设置 省份名称.
	 *
	 * @param proName the new 省份名称
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * 获取 市编号.
	 *
	 * @return the 市编号
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * 设置 市编号.
	 *
	 * @param cityCode the new 市编号
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * 获取 市名称.
	 *
	 * @return the 市名称
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * 设置 市名称.
	 *
	 * @param cityName the new 市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 获取 区县编号.
	 *
	 * @return the 区县编号
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * 设置 区县编号.
	 *
	 * @param countyCode the new 区县编号
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * 获取 区县编号.
	 *
	 * @return the 区县编号
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * 设置 区县编号.
	 *
	 * @param countyName the new 区县编号
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * 获取 最后修改人姓名.
	 *
	 * @return the 最后修改人姓名
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * 设置 最后修改人姓名.
	 *
	 * @param modifyUserName the new 最后修改人姓名
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * 获取 创建人姓名.
	 *
	 * @return the 创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人姓名.
	 *
	 * @param createUserName the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	 * 获取 部门CODE.
	 *
	 * @return the 部门CODE
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 设置 部门CODE.
	 *
	 * @param deptCode the new 部门CODE
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

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
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 *  区域编码
	 */
	private String regionCode;
	/**
	 *  区域名称
	 */
	private String regionName;
	/**
	 *  区域类型
	 */
	private String regionType;
	/**
	 *  区域状态
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
	 *  描述
	 */
	private String description;
	/**
	 *  时间戳
	 */
	private Long versionNo;
	/**
	 *  国家编号
	 */
	private String nationCode;
	/**
	 *  国家名称
	 */
	private String nationName;
	/**
	 *  省份编号
	 */
	private String proCode;
	/**
	 *  省份名称
	 */
	private String proName;
	/**
	 *  市编号
	 */
	private String cityCode;
	/**
	 *  市名称
	 */
	private String cityName;
	/**
	 *  区县编号
	 */
	private String countyCode;
	/**
	 *  区县编号
	 */
	private String countyName;
	/**
	 *  最后修改人姓名
	 */
	private String modifyUserName;
	/**
	 *  创建人姓名
	 */
	private String createUserName;
	/**
	 * 区域性质
	 */
	private String regionNature;
	/**
	 * 最后修改人所在部门
	 */
	private String modifyOrgCode;
	/**
	 * 创建人所在部门
	 */
	private String createOrgCode;
	/**
	 * 部门CODE
	 */
	private String deptCode;
	
	/**
	 * 开单日期
	 */
	private Date billDate;
}