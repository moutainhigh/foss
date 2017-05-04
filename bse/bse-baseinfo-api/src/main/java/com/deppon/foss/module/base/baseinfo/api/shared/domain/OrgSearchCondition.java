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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OrgSearchCondition.java
 * 
 * FILE NAME        	: OrgSearchCondition.java
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
 * FILE    NAME: OrgSearchCondtion.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 组织机构实体
 * 
 * @author panGuangJun
 * @date 2012-11-28 上午10:53:10
 */
public class OrgSearchCondition extends BaseEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 6539382307974729234L;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 标杆编码
	 */
	private String unifiedCode;

	/**
	 * 简称
	 */
	private String simpleName;

	/**
	 * 拼音
	 */
	private String pinYin;

	/**
	 * 查询参数
	 */
	private String queryParam;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 类型 "xxx,xxx,xxx"形式
	 */
	private String type;

	/**
	 * 类型集合
	 */
	private List<String> types;
	/**
	 * 类型集合
	 */
	private List<String> queryTypes;

	/**
	 * 分页最大记录数
	 */
	private int limit;

	/**
	 * 存在的sql
	 */
	private String existsSql;

	/**
	 * 不存在的sql
	 */
	private String notExistsSql;

	/**
	 * 分页开始记录数
	 */
	private int start;

	/**
	 * 省份编码
	 */
	private String provinceCode;

	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 县编码
	 */
	private String countyCode;

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
	 * 部门属性
	 */
	private String nature;
	/**
	 * 组织集合
	 */
	private List<String> orgList;
	
	 /**
     * 组织信息_是否虚拟营业部
     */
    private String expressSalesDepartment;
    
    /**
     * 
     * 是否快递点部
     *
     */
    private String expressPart;
    
    /**
     * 组织信息_是否快递大区
     */
    private String expressBigRegion;

	public String getExpressSalesDepartment() {
		return expressSalesDepartment;
	}

	public void setExpressSalesDepartment(String expressSalesDepartment) {
		this.expressSalesDepartment = expressSalesDepartment;
	}
	

	/**
	 * @return code
	 */
	
	public String getCode() {
		return code;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	/**
	 * @param  code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param  name  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return unifiedCode
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}

	/**
	 * @param  unifiedCode  
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	/**
	 * @return simpleName
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * @param  simpleName  
	 */
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	/**
	 * @return pinYin
	 */
	public String getPinYin() {
		return pinYin;
	}

	/**
	 * @param  pinYin  
	 */
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
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
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param  type  
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * @param  types  
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * @return queryTypes
	 */
	public List<String> getQueryTypes() {
		return queryTypes;
	}

	/**
	 * @param  queryTypes  
	 */
	public void setQueryTypes(List<String> queryTypes) {
		this.queryTypes = queryTypes;
	}

	/**
	 * @return limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param  limit  
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return existsSql
	 */
	public String getExistsSql() {
		return existsSql;
	}

	/**
	 * @param  existsSql  
	 */
	public void setExistsSql(String existsSql) {
		this.existsSql = existsSql;
	}

	/**
	 * @return notExistsSql
	 */
	public String getNotExistsSql() {
		return notExistsSql;
	}

	/**
	 * @param  notExistsSql  
	 */
	public void setNotExistsSql(String notExistsSql) {
		this.notExistsSql = notExistsSql;
	}

	/**
	 * @return start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param  start  
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param  provinceCode  
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
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
	 * @return division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param  division  
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @return bigRegion
	 */
	public String getBigRegion() {
		return bigRegion;
	}

	/**
	 * @param  bigRegion  
	 */
	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	/**
	 * @return smallRegion
	 */
	public String getSmallRegion() {
		return smallRegion;
	}

	/**
	 * @param  smallRegion  
	 */
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	/**
	 * @return salesDepartment
	 */
	public String getSalesDepartment() {
		return salesDepartment;
	}

	/**
	 * @param  salesDepartment  
	 */
	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	/**
	 * @return transferCenter
	 */
	public String getTransferCenter() {
		return transferCenter;
	}

	/**
	 * @param  transferCenter  
	 */
	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	/**
	 * @return doAirDispatch
	 */
	public String getDoAirDispatch() {
		return doAirDispatch;
	}

	/**
	 * @param  doAirDispatch  
	 */
	public void setDoAirDispatch(String doAirDispatch) {
		this.doAirDispatch = doAirDispatch;
	}

	/**
	 * @return transDepartment
	 */
	public String getTransDepartment() {
		return transDepartment;
	}

	/**
	 * @param  transDepartment  
	 */
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}

	/**
	 * @return dispatchTeam
	 */
	public String getDispatchTeam() {
		return dispatchTeam;
	}

	/**
	 * @param  dispatchTeam  
	 */
	public void setDispatchTeam(String dispatchTeam) {
		this.dispatchTeam = dispatchTeam;
	}

	/**
	 * @return billingGroup
	 */
	public String getBillingGroup() {
		return billingGroup;
	}

	/**
	 * @param  billingGroup  
	 */
	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}

	/**
	 * @return transTeam
	 */
	public String getTransTeam() {
		return transTeam;
	}

	/**
	 * @param  transTeam  
	 */
	public void setTransTeam(String transTeam) {
		this.transTeam = transTeam;
	}

	/**
	 * @return isDeliverSchedule
	 */
	public String getIsDeliverSchedule() {
		return isDeliverSchedule;
	}

	/**
	 * @param  isDeliverSchedule  
	 */
	public void setIsDeliverSchedule(String isDeliverSchedule) {
		this.isDeliverSchedule = isDeliverSchedule;
	}

	/**
	 * @return isArrangeGoods
	 */
	public String getIsArrangeGoods() {
		return isArrangeGoods;
	}

	/**
	 * @param  isArrangeGoods  
	 */
	public void setIsArrangeGoods(String isArrangeGoods) {
		this.isArrangeGoods = isArrangeGoods;
	}

	/**
	 * @return airDispatch
	 */
	public String getAirDispatch() {
		return airDispatch;
	}

	/**
	 * @param  airDispatch  
	 */
	public void setAirDispatch(String airDispatch) {
		this.airDispatch = airDispatch;
	}

	/**
	 * @return isEntityFinance
	 */
	public String getIsEntityFinance() {
		return isEntityFinance;
	}

	/**
	 * @param  isEntityFinance  
	 */
	public void setIsEntityFinance(String isEntityFinance) {
		this.isEntityFinance = isEntityFinance;
	}

	/**
	 * @return nature
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * @param  nature  
	 */
	public void setNature(String nature) {
		this.nature = nature;
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