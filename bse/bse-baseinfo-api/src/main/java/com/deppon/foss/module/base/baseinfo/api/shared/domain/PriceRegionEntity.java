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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/PriceRegionEntity.java
 * 
 * FILE NAME        	: PriceRegionEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.domain
 * FILE    NAME: PriceRegionEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 价格区域实体
 * 
 * @author panGuangJun
 * @date 2012-12-4 上午9:53:07
 */
public class PriceRegionEntity extends BaseEntity {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 区域编码
	 */
	private String regionCode;
	
	/**
	 * 区域名称
	 */
	private String regionName;

	/**
	 * 区域类型
	 */
	private String regionType;

	/**
	 * 区域状态
	 */
	private String active;

	/**
	 * 开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 时间戳
	 */
	private Long versionNo;

	/**
	 * 国家编号
	 */
	private String nationCode;

	/**
	 * 国家名称
	 */
	private String nationName;

	/**
	 * 省份编号
	 */
	private String proCode;

	/**
	 * 省份名称
	 */
	private String proName;

	/**
	 * 市编号
	 */
	private String cityCode;

	/**
	 * 市名称
	 */
	private String cityName;

	/**
	 * 区县编号
	 */
	private String countyCode;

	/**
	 * 区县名称
	 */
	private String countyName;

	/**
	 * 最后修改人姓名
	 */
	private String modifyUserName;

	/**
	 * 创建人姓名
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
	 * 价格类型
	 */
	private String airPriceFlag;
	
	/**
	 * 查询参数
	 */
	private String queryParam;
	/**
	 * 开单日期
	 */
	private Date billDate;
	/**
	 * 价格区域标识
	 * @return
	 */
	private String priceRegionFlag;
	
	public String getPriceRegionFlag() {
		return priceRegionFlag;
	}

	public void setPriceRegionFlag(String priceRegionFlag) {
		this.priceRegionFlag = priceRegionFlag;
	}

	private final int PRIORITY_INT_4 = 4;
	private final int PRIORITY_INT_3 = 3;
	
	public int getPriority() {
		if (StringUtil.isNotBlank(this.getCountyCode())) {
			return PRIORITY_INT_4;
		} else if (StringUtil.isNotBlank(this.getCityCode())) {
			return PRIORITY_INT_3;
		} else if (StringUtil.isNotBlank(this.getProCode())) {
			return 2;
		} else {
			return 1;
		}

	}

	/**
	 * @return regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param  regionCode  
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param  regionName  
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * @return regionType
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * @param  regionType  
	 */
	public void setRegionType(String regionType) {
		this.regionType = regionType;
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

	/**
	 * @return beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param  beginTime  
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param  endTime  
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param  description  
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * @param  nationCode  
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	/**
	 * @return nationName
	 */
	public String getNationName() {
		return nationName;
	}

	/**
	 * @param  nationName  
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	/**
	 * @return proCode
	 */
	public String getProCode() {
		return proCode;
	}

	/**
	 * @param  proCode  
	 */
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	/**
	 * @return proName
	 */
	public String getProName() {
		return proName;
	}

	/**
	 * @param  proName  
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param  cityCode  
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param  cityName  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return countyCode
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * @param  countyCode  
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * @return countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * @param  countyName  
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param  modifyUserName  
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param  createUserName  
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return regionNature
	 */
	public String getRegionNature() {
		return regionNature;
	}

	/**
	 * @param  regionNature  
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}

	/**
	 * @return modifyOrgCode
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * @param  modifyOrgCode  
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param  createOrgCode  
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param  deptCode  
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return airPriceFlag
	 */
	public String getAirPriceFlag() {
		return airPriceFlag;
	}

	/**
	 * @param  airPriceFlag  
	 */
	public void setAirPriceFlag(String airPriceFlag) {
		this.airPriceFlag = airPriceFlag;
	}

	/**
	 * @return queryParam
	 */
	public String getQueryParam() {
		return queryParam;
	}

	/**
	 * @param  queryParam  
	 */
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	/**
	 * @return billDate
	 */
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * @param  billDate  
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
}