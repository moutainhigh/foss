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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonOrgVo.java
 * 
 * FILE NAME        	: CommonOrgVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.vo
 * FILE    NAME: CommonOrgVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;

/**
 * action和前台传递值实体
 * 
 * @author panGuangJun
 * @date 2012-11-28 上午8:49:58
 */
public class CommonOrgVo implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 组织信息Dto
	 */
	private CommonOrgDto dto;
	/**
	 * 传递到前台的组织集合
	 */
	private List<CommonOrgDto> commonDtoList;
	/**
	 * 传递到前台的组织集合
	 */
	List<CommonOrgEntity> commonOrgEntityList;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 拼音
	 */
	private String pinYin;
	/**
	 * 查询参数
	 */
	private String queryParam;
	 
	/**
	 * 简称
	 */
	private String simpleName;
	/**
	 * 传入的sql
	 */
	private String sql;
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
	 * 分页最大记录数
	 */
	private int limit;
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
	 * 分页开始记录数
	 */
	private int start;
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
	 * 组织编码
	 */
	private String deptCode;
	
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
     * 是否快递大区
     */
    private String expressBigRegion;
    /**
     * 上级部门编码
     * @return
     */
    private String parentCode;
    /**
     * 是都到达部门
     * @return
     */
    private String arrive;
    
    

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getExpressSalesDepartment() {
		return expressSalesDepartment;
	}

	public void setExpressSalesDepartment(String expressSalesDepartment) {
		this.expressSalesDepartment = expressSalesDepartment;
	}
	
	/**
	 * get、set方法
	 * 
	 * @author panGuangJun
	 * @date 2012-11-28 上午10:54:04
	 */
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
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

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<CommonOrgEntity> getCommonOrgEntityList() {
		return commonOrgEntityList;
	}

	public void setCommonOrgEntityList(List<CommonOrgEntity> commonOrgEntityList) {
		this.commonOrgEntityList = commonOrgEntityList;
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

	
	public String getQueryParam() {
		return queryParam;
	}

	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	
	public CommonOrgDto getDto() {
		return dto;
	}

	
	public void setDto(CommonOrgDto dto) {
		this.dto = dto;
	}

	
	public List<CommonOrgDto> getCommonDtoList() {
		return commonDtoList;
	}

	
	public void setCommonDtoList(List<CommonOrgDto> commonDtoList) {
		this.commonDtoList = commonDtoList;
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
