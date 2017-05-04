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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CommonOrgEntity.java
 * 
 * FILE NAME        	: CommonOrgEntity.java
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
 * FILE    NAME: CommonOrgEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 公共组件选择器 组织实体
 * 
 * @author panGuangJun
 * @date 2012-11-28 上午10:04:18
 */
public class CommonOrgEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2088683501434626701L;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 标杆编码
	 */
	private String unifiedCode;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 简称
	 */
	private String simpleName;
	/**
	 * 拼音
	 */
	private String pinYin;
	/**
	 * 省份编码
	 */
	private String provinceCode;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 县编码
	 */
	private String countyCode;
	/**
	 * 区县名称
	 */
	private String countyName;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 是否事业部
	 */
	private String division;

	/**
	 * 是否大区
	 */
	private String bigRegion;

	/**
	 * 是否小区
	 */
	private String smallRegion;

	/**
	 * 是否营业部派送部
	 */
	private String salesDepartment;

	/**
	 * 是否外场
	 */
	private String transferCenter;

	/**
	 * 是否可空运配载
	 */
	private String doAirDispatch;

	/**
	 * 是否车队
	 */
	private String transDepartment;

	/**
	 * 是否车队调度组
	 */
	private String dispatchTeam;

	/**
	 * 是否集中开单组
	 */
	private String billingGroup;

	/**
	 * 是否车队组
	 */
	private String transTeam;

	/**
	 * 是否派送排单
	 */
	private String isDeliverSchedule;

	/**
	 * 是否理货
	 */
	private String isArrangeGoods;

	/**
	 * 是否空运总调
	 */
	private String airDispatch;

	/**
	 * 是否实体财务部
	 */
	private String isEntityFinance;
	
	 /**
     * 组织信息_是否虚拟营业部
     */
    private String expressSalesDepartment;
    
    /**
     * 是否快递点部
     *
     */
    private String expressPart;
    
    /**
     * 组织信息_是否快递大区
     */
    private String expressBigRegion;
    /**
     * 是否经营本部
     */
    private String isManageDepartment;
    
    

	public String getIsManageDepartment() {
		return isManageDepartment;
	}

	public void setIsManageDepartment(String isManageDepartment) {
		this.isManageDepartment = isManageDepartment;
	}

	public String getExpressSalesDepartment() {
		return expressSalesDepartment;
	}

	public void setExpressSalesDepartment(String expressSalesDepartment) {
		this.expressSalesDepartment = expressSalesDepartment;
	}

	/**
	 * 以下是get、set方法
	 * 
	 * @author panGuangJun
	 * @date 2012-11-28 上午10:11:43
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code)   {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String getSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	public String getDoAirDispatch() {
		return doAirDispatch;
	}

	public void setDoAirDispatch(String doAirDispatch) {
		this.doAirDispatch = doAirDispatch;
	}

	public String getTransDepartment() {
		return transDepartment;
	}

	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}

	public String getDispatchTeam() {
		return dispatchTeam;
	}

	public void setDispatchTeam(String dispatchTeam) {
		this.dispatchTeam = dispatchTeam;
	}

	public String getBillingGroup() {
		return billingGroup;
	}

	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}

	public String getTransTeam() {
		return transTeam;
	}

	public void setTransTeam(String transTeam) {
		this.transTeam = transTeam;
	}

	public String getIsDeliverSchedule() {
		return isDeliverSchedule;
	}

	public void setIsDeliverSchedule(String isDeliverSchedule) {
		this.isDeliverSchedule = isDeliverSchedule;
	}

	public String getIsArrangeGoods() {
		return isArrangeGoods;
	}

	public void setIsArrangeGoods(String isArrangeGoods) {
		this.isArrangeGoods = isArrangeGoods;
	}

	public String getAirDispatch() {
		return airDispatch;
	}

	public void setAirDispatch(String airDispatch) {
		this.airDispatch = airDispatch;
	}

	public String getIsEntityFinance() {
		return isEntityFinance;
	}

	public void setIsEntityFinance(String isEntityFinance) {
		this.isEntityFinance = isEntityFinance;
	}

	/**
	 *getter
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}

	/**
	 *setter
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	/**
	 *getter
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 *setter
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 *getter
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 *setter
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 *getter
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 *setter
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the expressPart
	 */
	public String getExpressPart() {
		return expressPart;
	}

	/**
	 * @param expressPart the expressPart to set
	 */
	public void setExpressPart(String expressPart) {
		this.expressPart = expressPart;
	}

	/**
	 * @return the expressBigRegion
	 */
	public String getExpressBigRegion() {
		return expressBigRegion;
	}

	/**
	 * @param expressBigRegion the expressBigRegion to set
	 */
	public void setExpressBigRegion(String expressBigRegion) {
		this.expressBigRegion = expressBigRegion;
	}
}
