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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OrgAdministrativeInfoEntity.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 配合主数据项目推送FOSS组织信息给UUMS系统中间表组织信息实体
 * @author 187862-dujunhui
 * @date 2015-4-10 下午4:40:53
 */
public class OrgAdministrativeInfoToUUEntity  extends BaseEntity {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 7166809349148000442L;
	/**
	 * 组织编码
	 */	
	private String code;
	/**
	 * 组织名称
	 */	
	private String name;
	/**
	 * 是否空运总调
	 */	
	private String airDispatch;
	/**
	 * 理货业务类型
	 */	
	private String arrangeBizType;
	/**
	 * 理货部门服务外场编码
	 */	
	private String arrangeOutfield;
	/**
	 * 是否集中开单组
	 */	
	private String billingGroup;
	/**
     * 部门补码简称
     */
    private String complementSimpleName;
    /**
     * 国家地区
     */	
    private String countryRegion;
    /**
     * 派送排单服务外场编码
     */	
    private String deliverOutfield;
    /**
     * 部门服务区坐标编号
     */	
    private String depCoordinate;
    /**
     * 部门面积
     */	
     private BigDecimal deptArea;
     /**
     * 城市
     */	
     private String cityCode;
     /**
     * 区县
     */	
     private String countyCode;
     /**
      * 省份
      */	
     private String provCode;
     /**
      * 是否车队调度组
      */	
     private String dispatchTeam;
     /**
      * 是否可空运配载
      */	
     private String doAirDispatch;
     /**
      * 是否快递大区
      */
     private String expressBigRegion;
     /**
      * 是否快递分部
      */
     private String expressBranch; 
     /**
      * 是否快递点部
      */
     private String expressPart;
     /**
      * 组织信息_是否虚拟营业部
      */
     private String expressSalesDepartment;
     /**
      * 是否快递分拣
      */
     private String expressSorting;
     /**
      * 是否理货
      */	
     private String isArrangeGoods;
     /**
      * 是否派送排单
      */	
     private String isDeliverSchedule;
     /**
      * 是否实体财务部
      */	
     private String isEntityFinance;
      /**
      * 是否营业大区
      */	
     private String bigRegion;
     /**
      * 是否事业部
      */	
     private String division;
     /**
      * 是否保安组
      */	
     private String isSecurity;
     /**
      * 是否营业小区
      */	
     private String smallRegion;
     /**
      * 组织拼音
      */	
     private String pinyin;
     /**
      * 是否营业部派送部
      */	
     private String salesDepartment;
     /**
      * 是否车队
      */	
     private String transDepartment;
     /**
      * 是否车队组
      */	
    private String transTeam;
     /**
      * 是否外场
      */	
    private String transferCenter;
     /**
      * 事业部编码
      */	
    private String divisionCode;
     /**
      * 部门简称
      */	
    private String orgSimpleName;
     /**
      * 组织标杆编码
      */	
    private String unifiedCode;
    /**
     * 联系电话
     */
    private String orgPhone;
    /**
     * 传真
     */
    private String orgFax;
    /**
     * 邮编号码
     */
    private String orgZipCode;
    /**
     * 部门地址
     */
    private String orgAddress;
    /**
     * 是否经营本部
     */
    private String isManageDepartment;
    /**
     * 数据版本号
     */	
     private Long versionNo;
     /**
      * 部门英文简称
      */	
      private String orgEnSimple;
    /**
     * 操作类型
     */
    private String operateType;
     /**
      * 异常次数
      */
    private String failCount;
     /**
      * 发送状态
      */
    private String sendStatus;
     /**
      * 信息修改时间
      */
    private Date updateTime;
    /**
     * 信息插入时间
     */
    private Date insertTime; 
    /**
     * 发送状态数组作为查询条件
     */
    private List<String> conditionSendStatusList;
    /**
     * 以修改时间作为查询条件
     */
    private Date conditionUpdateTime;
    /**
     * 字符串型修改时间
     */
    private String modifyDateOfUU;
    
	public String getModifyDateOfUU() {
		return modifyDateOfUU;
	}
	public void setModifyDateOfUU(String modifyDateOfUU) {
		this.modifyDateOfUU = modifyDateOfUU;
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
	public String getAirDispatch() {
		return airDispatch;
	}
	public void setAirDispatch(String airDispatch) {
		this.airDispatch = airDispatch;
	}
	public String getArrangeBizType() {
		return arrangeBizType;
	}
	public void setArrangeBizType(String arrangeBizType) {
		this.arrangeBizType = arrangeBizType;
	}
	public String getArrangeOutfield() {
		return arrangeOutfield;
	}
	public void setArrangeOutfield(String arrangeOutfield) {
		this.arrangeOutfield = arrangeOutfield;
	}
	public String getBillingGroup() {
		return billingGroup;
	}
	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}
	public String getComplementSimpleName() {
		return complementSimpleName;
	}
	public void setComplementSimpleName(String complementSimpleName) {
		this.complementSimpleName = complementSimpleName;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getDeliverOutfield() {
		return deliverOutfield;
	}
	public void setDeliverOutfield(String deliverOutfield) {
		this.deliverOutfield = deliverOutfield;
	}
	public String getDepCoordinate() {
		return depCoordinate;
	}
	public void setDepCoordinate(String depCoordinate) {
		this.depCoordinate = depCoordinate;
	}
	public BigDecimal getDeptArea() {
		return deptArea;
	}
	public void setDeptArea(BigDecimal deptArea) {
		this.deptArea = deptArea;
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
	public String getProvCode() {
		return provCode;
	}
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	public String getDispatchTeam() {
		return dispatchTeam;
	}
	public void setDispatchTeam(String dispatchTeam) {
		this.dispatchTeam = dispatchTeam;
	}
	public String getDoAirDispatch() {
		return doAirDispatch;
	}
	public void setDoAirDispatch(String doAirDispatch) {
		this.doAirDispatch = doAirDispatch;
	}
	public String getExpressBigRegion() {
		return expressBigRegion;
	}
	public void setExpressBigRegion(String expressBigRegion) {
		this.expressBigRegion = expressBigRegion;
	}
	public String getExpressBranch() {
		return expressBranch;
	}
	public void setExpressBranch(String expressBranch) {
		this.expressBranch = expressBranch;
	}
	public String getExpressPart() {
		return expressPart;
	}
	public void setExpressPart(String expressPart) {
		this.expressPart = expressPart;
	}
	public String getExpressSalesDepartment() {
		return expressSalesDepartment;
	}
	public void setExpressSalesDepartment(String expressSalesDepartment) {
		this.expressSalesDepartment = expressSalesDepartment;
	}
	public String getExpressSorting() {
		return expressSorting;
	}
	public void setExpressSorting(String expressSorting) {
		this.expressSorting = expressSorting;
	}
	public String getIsArrangeGoods() {
		return isArrangeGoods;
	}
	public void setIsArrangeGoods(String isArrangeGoods) {
		this.isArrangeGoods = isArrangeGoods;
	}
	public String getIsDeliverSchedule() {
		return isDeliverSchedule;
	}
	public void setIsDeliverSchedule(String isDeliverSchedule) {
		this.isDeliverSchedule = isDeliverSchedule;
	}
	public String getIsEntityFinance() {
		return isEntityFinance;
	}
	public void setIsEntityFinance(String isEntityFinance) {
		this.isEntityFinance = isEntityFinance;
	}
	public String getBigRegion() {
		return bigRegion;
	}
	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getIsSecurity() {
		return isSecurity;
	}
	public void setIsSecurity(String isSecurity) {
		this.isSecurity = isSecurity;
	}
	public String getSmallRegion() {
		return smallRegion;
	}
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getSalesDepartment() {
		return salesDepartment;
	}
	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}
	public String getTransDepartment() {
		return transDepartment;
	}
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}
	public String getTransTeam() {
		return transTeam;
	}
	public void setTransTeam(String transTeam) {
		this.transTeam = transTeam;
	}
	public String getTransferCenter() {
		return transferCenter;
	}
	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getOrgSimpleName() {
		return orgSimpleName;
	}
	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}
	public String getUnifiedCode() {
		return unifiedCode;
	}
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	public String getFailCount() {
		return failCount;
	}
	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOrgPhone() {
		return orgPhone;
	}
	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}
	public String getOrgFax() {
		return orgFax;
	}
	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}
	public String getOrgZipCode() {
		return orgZipCode;
	}
	public void setOrgZipCode(String orgZipCode) {
		this.orgZipCode = orgZipCode;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public List<String> getConditionSendStatusList() {
		return conditionSendStatusList;
	}
	public void setConditionSendStatusList(List<String> conditionSendStatusList) {
		this.conditionSendStatusList = conditionSendStatusList;
	}
	public Date getConditionUpdateTime() {
		return conditionUpdateTime;
	}
	public void setConditionUpdateTime(Date conditionUpdateTime) {
		this.conditionUpdateTime = conditionUpdateTime;
	}
	public String getIsManageDepartment() {
		return isManageDepartment;
	}
	public void setIsManageDepartment(String isManageDepartment) {
		this.isManageDepartment = isManageDepartment;
	}
	public Long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	public String getOrgEnSimple() {
		return orgEnSimple;
	}
	public void setOrgEnSimple(String orgEnSimple) {
		this.orgEnSimple = orgEnSimple;
	}
	
}