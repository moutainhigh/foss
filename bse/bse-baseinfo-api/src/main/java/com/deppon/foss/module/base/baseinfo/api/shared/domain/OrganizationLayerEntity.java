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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OrganizationLayerEntity.java
 * 
 * FILE NAME        	: OrganizationLayerEntity.java
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
 * FILE    NAME: OrganizationLayerEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.sql.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 组织机构层级实体
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-13 上午10:26:21
 */
public class OrganizationLayerEntity extends BaseEntity {

	/**
	* 序列化ID
	*/
	private static final long serialVersionUID = 4991980473759227789L;

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
	* 标杆编码
	*/
	private String unifiedCode;

	/**
	* 负责人
	*/
	private String leader;

	/**
	* 法人编码
	*/
	private String principalNo;

	/**
	* 父组织名称
	*/
	private String parentOrgName;

	/**
	* 父组织编码
	*/
	private String parentOrgCode;

	/**
	* 所属子公司编码
	*/
	private String subsidiaryCode;

	/**
	* 状态
	*/
	private String status;

	/**
	* 作废日期
	*/
	private Date endTime;

	/**
	* 启用日期
	*/
	private Date beginTime;

	/**
	* 是否事业部
	*/
	private String devision;

	/**
	* 事业部编码
	*/
	private String devisionCode;

	/**
	* 是否大区
	*/
	private String bigRegion;

	/**
	* 是否小区
	*/
	private String smallRegion;

	/**
	* 地址
	*/
	private String address;

	/**
	* 部门面积
	*/
	private String deptArea;

	/**
	* 省份编码
	*/
	private String provinceCode;

	/**
	* 城市编码
	*/
	private String cityCode;

	/**
	* 区县编码
	*/
	private String countyCode;

	/**
	* 是否营业部
	*/
	private String salesDepartment;

	/**
	* 是否转运中心
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
	 * 是否派送派单服务外场
	 */
	private String deliverOutField;

	/**
	 * 是否理货部门服务外场
	 */
	private String arrangeOutFiled;
	
	/**
	 * 理货业务类型
	 */
	private String arrangeBizType;

	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 是否空运总调
	 */
	private String airDispatch;
	
	/**
	 * 是否实体财务部
	 */
	private String entityFinance;
	
	/**
	 * 部门服务区坐标编码
	 */
	private String depCoordinate;

	/**
	 * 部门电话
	 */
	private String depTelphone;

	/**
	 * 部门传真
	 */
	private String depFax;
	
	/**
	 * 是否实体财务部
	 */
	private String isEntityFinance;
	
	/**
	 * 部门简称
	 */
	private String orgSimpleName;
	
	/**
	 * 全路径
	 */
	private String fullPath;

	/**
	 * 层级1
	 */
	private String level1;
	
	/**
	 * 层级2
	 */
	private String level2;

	/**
	 * 层级3
	 */
	private String level3;

	/**
	 * 层级4
	 */
	private String level4;
	
	/**
	 * 层级5
	 */
	private String level5;
	
	/**
	 * 层级6
	 */
	private String level6;
	
	/**
	 * 层级7
	 */
	private String level7;
	
	/**
	 * 层级8
	 */
	private String level8;
	
	/**
	 * 层级9
	 */
	private String level9;

	/**
	 * 层级10
	 */
	private String level10;
	
	/**
	 *getter
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 *setter
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 *getter
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *setter
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *getter
	 */
	public String getPinYin() {
		return pinYin;
	}
	
	/**
	 *setter
	 */
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
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
	public String getLeader() {
		return leader;
	}
	
	/**
	 *setter
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	/**
	 *getter
	 */
	public String getPrincipalNo() {
		return principalNo;
	}
	
	/**
	 *setter
	 */
	public void setPrincipalNo(String principalNo) {
		this.principalNo = principalNo;
	}
	
	/**
	 *getter
	 */
	public String getParentOrgName() {
		return parentOrgName;
	}
	
	/**
	 *setter
	 */
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	
	/**
	 *getter
	 */
	public String getParentOrgCode() {
		return parentOrgCode;
	}
	
	/**
	 *setter
	 */
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}
	
	/**
	 *getter
	 */
	public String getSubsidiaryCode() {
		return subsidiaryCode;
	}
	
	/**
	 *setter
	 */
	public void setSubsidiaryCode(String subsidiaryCode) {
		this.subsidiaryCode = subsidiaryCode;
	}
	
	/**
	 *getter
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 *setter
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 *getter
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 *setter
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 *getter
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	 *setter
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	/**
	 *getter
	 */
	public String getDevision() {
		return devision;
	}
	
	/**
	 *setter
	 */
	public void setDevision(String devision) {
		this.devision = devision;
	}
	
	/**
	 *getter
	 */
	public String getDevisionCode() {
		return devisionCode;
	}
	
	/**
	 *setter
	 */
	public void setDevisionCode(String devisionCode) {
		this.devisionCode = devisionCode;
	}
	
	/**
	 *getter
	 */
	public String getBigRegion() {
		return bigRegion;
	}
	
	/**
	 *setter
	 */
	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}
	
	/**
	 *getter
	 */
	public String getSmallRegion() {
		return smallRegion;
	}
	
	/**
	 *setter
	 */
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}
	
	/**
	 *getter
	 */
	public String getAddress() {
		return address;
	}
	/**
	 *setter
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 *getter
	 */
	public String getDeptArea() {
		return deptArea;
	}
	
	/**
	 *setter
	 */
	public void setDeptArea(String deptArea) {
		this.deptArea = deptArea;
	}
	
	/**
	 *getter
	 */
	public String getProvinceCode() {
		return provinceCode;
	}
	
	/**
	 *setter
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	/**
	 *getter
	 */
	public String getCityCode() {
		return cityCode;
	}
	
	/**
	 *setter
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 *getter
	 */
	public String getCountyCode() {
		return countyCode;
	}
	
	/**
	 *setter
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	/**
	 *getter
	 */
	public String getSalesDepartment() {
		return salesDepartment;
	}
	
	/**
	 *setter
	 */
	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}
	
	/**
	 *getter
	 */
	public String getTransferCenter() {
		return transferCenter;
	}
	
	/**
	 *setter
	 */
	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}
	
	/**
	 *getter
	 */
	public String getDoAirDispatch() {
		return doAirDispatch;
	}
	
	/**
	 *setter
	 */
	public void setDoAirDispatch(String doAirDispatch) {
		this.doAirDispatch = doAirDispatch;
	}
	
	/**
	 *getter
	 */
	public String getTransDepartment() {
		return transDepartment;
	}
	
	/**
	 *setter
	 */
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}
	
	/**
	 *getter
	 */
	public String getDispatchTeam() {
		return dispatchTeam;
	}
	
	/**
	 *setter
	 */
	public void setDispatchTeam(String dispatchTeam) {
		this.dispatchTeam = dispatchTeam;
	}
	
	/**
	 *getter
	 */
	public String getBillingGroup() {
		return billingGroup;
	}
	
	/**
	 *setter
	 */
	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}
	
	/**
	 *getter
	 */
	public String getTransTeam() {
		return transTeam;
	}
	
	/**
	 *setter
	 */
	public void setTransTeam(String transTeam) {
		this.transTeam = transTeam;
	}
	
	/**
	 *getter
	 */
	public String getIsDeliverSchedule() {
		return isDeliverSchedule;
	}
	
	/**
	 *setter
	 */
	public void setIsDeliverSchedule(String isDeliverSchedule) {
		this.isDeliverSchedule = isDeliverSchedule;
	}
	
	/**
	 *getter
	 */
	public String getIsArrangeGoods() {
		return isArrangeGoods;
	}
	
	/**
	 *setter
	 */
	public void setIsArrangeGoods(String isArrangeGoods) {
		this.isArrangeGoods = isArrangeGoods;
	}
	
	/**
	 *getter
	 */
	public String getDeliverOutField() {
		return deliverOutField;
	}
	
	/**
	 *setter
	 */
	public void setDeliverOutField(String deliverOutField) {
		this.deliverOutField = deliverOutField;
	}
	
	/**
	 *getter
	 */
	public String getArrangeOutFiled() {
		return arrangeOutFiled;
	}
	
	/**
	 *setter
	 */
	public void setArrangeOutFiled(String arrangeOutFiled) {
		this.arrangeOutFiled = arrangeOutFiled;
	}
	
	/**
	 *getter
	 */
	public String getArrangeBizType() {
		return arrangeBizType;
	}
	
	/**
	 *setter
	 */
	public void setArrangeBizType(String arrangeBizType) {
		this.arrangeBizType = arrangeBizType;
	}
	
	/**
	 *getter
	 */
	public String getActive() {
		return active;
	}
	
	/**
	 *setter
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
	/**
	 *getter
	 */
	public String getAirDispatch() {
		return airDispatch;
	}
	
	/**
	 *setter
	 */
	public void setAirDispatch(String airDispatch) {
		this.airDispatch = airDispatch;
	}
	
	/**
	 *getter
	 */
	public String getEntityFinance() {
		return entityFinance;
	}
	
	/**
	 *setter
	 */
	public void setEntityFinance(String entityFinance) {
		this.entityFinance = entityFinance;
	}
	
	/**
	 *getter
	 */
	public String getDepCoordinate() {
		return depCoordinate;
	}
	
	/**
	 *setter
	 */
	public void setDepCoordinate(String depCoordinate) {
		this.depCoordinate = depCoordinate;
	}
	
	/**
	 *getter
	 */
	public String getDepTelphone() {
		return depTelphone;
	}
	
	/**
	 *setter
	 */
	public void setDepTelphone(String depTelphone) {
		this.depTelphone = depTelphone;
	}
	
	/**
	 *getter
	 */
	public String getDepFax() {
		return depFax;
	}
	
	/**
	 *setter
	 */
	public void setDepFax(String depFax) {
		this.depFax = depFax;
	}
	
	/**
	 *getter
	 */
	public String getIsEntityFinance() {
		return isEntityFinance;
	}
	
	/**
	 *setter
	 */
	public void setIsEntityFinance(String isEntityFinance) {
		this.isEntityFinance = isEntityFinance;
	}
	
	/**
	 *getter
	 */
	public String getOrgSimpleName() {
		return orgSimpleName;
	}
	
	/**
	 *setter
	 */
	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}
	
	/**
	 *getter
	 */
	public String getFullPath() {
		return fullPath;
	}
	
	/**
	 *setter
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	/**
	 *getter
	 */
	public String getLevel1() {
		return level1;
	}
	
	/**
	 *setter
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
	
	/**
	 *getter
	 */
	public String getLevel2() {
		return level2;
	}
	
	/**
	 *setter
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	
	/**
	 *getter
	 */
	public String getLevel3() {
		return level3;
	}
	
	/**
	 *setter
	 */
	public void setLevel3(String level3) {
		this.level3 = level3;
	}
	
	/**
	 *getter
	 */
	public String getLevel4() {
		return level4;
	}
	
	/**
	 *setter
	 */
	public void setLevel4(String level4) {
		this.level4 = level4;
	}
	
	/**
	 *getter
	 */
	public String getLevel5() {
		return level5;
	}
	
	/**
	 *setter
	 */
	public void setLevel5(String level5) {
		this.level5 = level5;
	}
	
	/**
	 *getter
	 */
	public String getLevel6() {
		return level6;
	}
	
	/**
	 *setter
	 */
	public void setLevel6(String level6) {
		this.level6 = level6;
	}
	
	/**
	 *getter
	 */
	public String getLevel7() {
		return level7;
	}
	
	/**
	 *setter
	 */
	public void setLevel7(String level7) {
		this.level7 = level7;
	}
	
	/**
	 *getter
	 */
	public String getLevel8() {
		return level8;
	}
	
	/**
	 *setter
	 */
	public void setLevel8(String level8) {
		this.level8 = level8;
	}
	
	/**
	 *getter
	 */
	public String getLevel9() {
		return level9;
	}
	
	/**
	 *setter
	 */
	public void setLevel9(String level9) {
		this.level9 = level9;
	}
	
	/**
	 *getter
	 */
	public String getLevel10() {
		return level10;
	}
	
	/**
	 *setter
	 */
	public void setLevel10(String level10) {
		this.level10 = level10;
	}
}