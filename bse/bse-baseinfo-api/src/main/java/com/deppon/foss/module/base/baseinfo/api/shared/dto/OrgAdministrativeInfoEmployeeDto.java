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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/OrgAdministrativeInfoEmployeeDto.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoEmployeeDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.Date;

/**
 * 组织信息
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class OrgAdministrativeInfoEmployeeDto  {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -3967231352101877656L;

    /**
    * 组织信息_ID
    */	
    private String orgAdministrativeInfoId;

    /**
    * 组织信息_组织编码
    */	
    private String orgAdministrativeInfoCode;

    /**
    * 组织信息_组织名称
    */	
    private String orgAdministrativeInfoName;

    /**
    * 组织信息_拼音
    */	
    private String orgAdministrativeInfoPinyin;

    /**
    * 组织信息_组织标杆编码
    */	
    private String orgAdministrativeInfoUnifiedCode;

    /**
    * 组织信息_组织负责人
    */	
    private String orgAdministrativeInfoLeader;

    /**
    * 组织信息_组织负责人工号
    */	
    private String orgAdministrativeInfoPrincipalNo;

    /**
    * 组织信息_上级组织名称
    */	
    private String orgAdministrativeInfoParentOrgName;

    /**
    * 组织信息_上级组织标杆编码
    */	
    private String orgAdministrativeInfoParentOrgUnifiedCode;

    /**
    * 组织信息_所属子公司编码
    */	
    private String orgAdministrativeInfoSubsidiaryCode;

    /**
    * 组织信息_所属成本中心编码
    */	
    private String orgAdministrativeInfoCostCenterCode;

    /**
    * 组织信息_组织状态
    */	
    private String orgAdministrativeInfoStatus;

    /**
    * 组织信息_作废日期
    */	
    private Date orgAdministrativeInfoEndTime;

    /**
    * 组织信息_启用日期
    */	
    private Date orgAdministrativeInfoBeginTime;

    /**
    * 组织信息_是否事业部
    */	
    private String orgAdministrativeInfoDivision;

    /**
    * 组织信息_事业部编码
    */	
    private String orgAdministrativeInfoDivisionCode;

    /**
    * 组织信息_是否大区
    */	
    private String orgAdministrativeInfoBigRegion;

    /**
    * 组织信息_是否小区
    */	
    private String orgAdministrativeInfoSmallRegion;
    
    /**
     * 组织信息_是否快递大区
     */
    private String expressBigRegion;
    /**
     * 组织信息_是否虚拟营业部
     */
    private String expressSalesDepartment;
    /**
     * 组织信息_是否快递点部
     */
    private String expressPart;
    /**
    * 组织信息_部门地址
    */	
    private String orgAdministrativeInfoAddress;

    /**
    * 组织信息_部门面积
    */	
    private String orgAdministrativeInfoDeptArea;

    /**
    * 组织信息_省份
    */	
    private String orgAdministrativeInfoProvCode;

    /**
    * 组织信息_城市
    */	
    private String orgAdministrativeInfoCityCode;

    /**
    * 组织信息_区县
    */	
    private String orgAdministrativeInfoCountyCode;

    /**
    * 组织信息_是否营业部派送部
    */	
    private String orgAdministrativeInfoSalesDepartment;

    /**
    * 组织信息_是否外场
    */	
    private String orgAdministrativeInfoTransferCenter;

    /**
    * 组织信息_是否可空运配载
    */	
    private String orgAdministrativeInfoDoAirDispatch;

    /**
    * 组织信息_是否车队
    */	
    private String orgAdministrativeInfoTransDepartment;

    /**
    * 组织信息_是否车队调度组
    */	
    private String orgAdministrativeInfoDispatchTeam;

    /**
    * 组织信息_是否集中开单组
    */	
    private String orgAdministrativeInfoBillingGroup;

    /**
    * 组织信息_是否车队组
    */	
    private String orgAdministrativeInfoTransTeam;

    /**
    * 组织信息_是否派送排单
    */	
    private String orgAdministrativeInfoIsDeliverSchedule;

    /**
    * 组织信息_是否理货
    */	
    private String orgAdministrativeInfoIsArrangeGoods;

    /**
    * 组织信息_派送排单服务外场
    */	
    private String orgAdministrativeInfoDeliverOutfield;

    /**
    * 组织信息_理货部门服务外场
    */	
    private String orgAdministrativeInfoArrangeOutfield;

    /**
    * 组织信息_理货业务类型
    */	
    private String orgAdministrativeInfoArrangeBizType;

    /**
    * 组织信息_创建时间
    */	
    private Date orgAdministrativeInfoCreateDate;

    /**
    * 组织信息_更新时间
    */	
    private Date orgAdministrativeInfoModifyDate;

    /**
    * 组织信息_是否启用
    */	
    private String orgAdministrativeInfoActive;

    /**
    * 组织信息_创建人
    */	
    private String orgAdministrativeInfoCreateUser;

    /**
    * 组织信息_更新人
    */	
    private String orgAdministrativeInfoModifyUser;

    /**
    * 组织信息_是否空运总调
    */	
    private String orgAdministrativeInfoAirDispatch;

    /**
    * 组织信息_所属实体财务部
    */	
    private String orgAdministrativeInfoEntityFinance;

    /**
    * 组织信息_部门服务区坐标编号
    */	
    private String orgAdministrativeInfoDepCoordinate;

    /**
    * 组织信息_部门电话
    */	
    private String orgAdministrativeInfoDepTelephone;

    /**
    * 组织信息_部门传真
    */	
    private String orgAdministrativeInfoDepFex;




    /**
    * 人员_ID
    */	
    private String employeeId;

    /**
    * 人员_职员姓名
    */	
    private String employeeEmpName;

    /**
    * 人员_拼音
    */	
    private String employeePinyin;

    /**
    * 人员_工号
    */	
    private String employeeEmpCode;

    /**
    * 人员_性别
    */	
    private String employeeGender;

    /**
    * 人员_部门标杆编码
    */	
    private String employeeUnifieldCode;

    /**
    * 人员_职位
    */	
    private String employeeTitle;

    /**
    * 人员_职位名称
    */	
    private String employeeTitleName;

    /**
    * 人员_职等
    */	
    private String employeeDegree;

    /**
    * 人员_出生日期
    */	
    private Date employeeBirthdate;

    /**
    * 人员_状态
    */	
    private String employeeStatus;

    /**
    * 人员_电话
    */	
    private String employeePhone;

    /**
    * 人员_身份证号
    */	
    private String employeeIdentityCard;

    /**
    * 人员_入职日期
    */	
    private Date employeeEntryDate;

    /**
    * 人员_离职日期
    */	
    private Date employeeLeaveDate;

    /**
    * 人员_手机号码
    */	
    private String employeeMobilePhone;

    /**
    * 人员_电子邮箱
    */	
    private String employeeEmail;

    /**
    * 人员_创建时间
    */	
    private Date employeeCreateDate;

    /**
    * 人员_更新时间
    */	
    private Date employeeModifyDate;

    /**
    * 人员_是否启用
    */	
    private String employeeActive;

    /**
    * 人员_创建人
    */	
    private String employeeCreateUser;

    /**
    * 人员_更新人
    */	
    private String employeeModifyUser;


    public String getOrgAdministrativeInfoId() {
		return orgAdministrativeInfoId;
    }
    public void setOrgAdministrativeInfoId(String orgAdministrativeInfoId) {
		this.orgAdministrativeInfoId = orgAdministrativeInfoId;
    }

    public String getOrgAdministrativeInfoCode() {
		return orgAdministrativeInfoCode;
    }
    public void setOrgAdministrativeInfoCode(String orgAdministrativeInfoCode) {
		this.orgAdministrativeInfoCode = orgAdministrativeInfoCode;
    }

    public String getOrgAdministrativeInfoName() {
		return orgAdministrativeInfoName;
    }
    public void setOrgAdministrativeInfoName(String orgAdministrativeInfoName) {
		this.orgAdministrativeInfoName = orgAdministrativeInfoName;
    }

    public String getOrgAdministrativeInfoPinyin() {
		return orgAdministrativeInfoPinyin;
    }
    public void setOrgAdministrativeInfoPinyin(String orgAdministrativeInfoPinyin) {
		this.orgAdministrativeInfoPinyin = orgAdministrativeInfoPinyin;
    }

    public String getOrgAdministrativeInfoUnifiedCode() {
		return orgAdministrativeInfoUnifiedCode;
    }
    public void setOrgAdministrativeInfoUnifiedCode(String orgAdministrativeInfoUnifiedCode) {
		this.orgAdministrativeInfoUnifiedCode = orgAdministrativeInfoUnifiedCode;
    }

    public String getOrgAdministrativeInfoLeader() {
		return orgAdministrativeInfoLeader;
    }
    public void setOrgAdministrativeInfoLeader(String orgAdministrativeInfoLeader) {
		this.orgAdministrativeInfoLeader = orgAdministrativeInfoLeader;
    }

    public String getOrgAdministrativeInfoPrincipalNo() {
		return orgAdministrativeInfoPrincipalNo;
    }
    public void setOrgAdministrativeInfoPrincipalNo(String orgAdministrativeInfoPrincipalNo) {
		this.orgAdministrativeInfoPrincipalNo = orgAdministrativeInfoPrincipalNo;
    }

    public String getOrgAdministrativeInfoParentOrgName() {
		return orgAdministrativeInfoParentOrgName;
    }
    public void setOrgAdministrativeInfoParentOrgName(String orgAdministrativeInfoParentOrgName) {
		this.orgAdministrativeInfoParentOrgName = orgAdministrativeInfoParentOrgName;
    }

    public String getOrgAdministrativeInfoParentOrgUnifiedCode() {
		return orgAdministrativeInfoParentOrgUnifiedCode;
    }
    public void setOrgAdministrativeInfoParentOrgUnifiedCode(String orgAdministrativeInfoParentOrgUnifiedCode) {
		this.orgAdministrativeInfoParentOrgUnifiedCode = orgAdministrativeInfoParentOrgUnifiedCode;
    }

    public String getOrgAdministrativeInfoSubsidiaryCode() {
		return orgAdministrativeInfoSubsidiaryCode;
    }
    public void setOrgAdministrativeInfoSubsidiaryCode(String orgAdministrativeInfoSubsidiaryCode) {
		this.orgAdministrativeInfoSubsidiaryCode = orgAdministrativeInfoSubsidiaryCode;
    }

    public String getOrgAdministrativeInfoCostCenterCode() {
		return orgAdministrativeInfoCostCenterCode;
    }
    public void setOrgAdministrativeInfoCostCenterCode(String orgAdministrativeInfoCostCenterCode) {
		this.orgAdministrativeInfoCostCenterCode = orgAdministrativeInfoCostCenterCode;
    }

    public String getOrgAdministrativeInfoStatus() {
		return orgAdministrativeInfoStatus;
    }
    public void setOrgAdministrativeInfoStatus(String orgAdministrativeInfoStatus) {
		this.orgAdministrativeInfoStatus = orgAdministrativeInfoStatus;
    }

    public Date getOrgAdministrativeInfoEndTime() {
		return orgAdministrativeInfoEndTime;
    }
    public void setOrgAdministrativeInfoEndTime(Date orgAdministrativeInfoEndTime) {
		this.orgAdministrativeInfoEndTime = orgAdministrativeInfoEndTime;
    }

    public Date getOrgAdministrativeInfoBeginTime() {
		return orgAdministrativeInfoBeginTime;
    }
    public void setOrgAdministrativeInfoBeginTime(Date orgAdministrativeInfoBeginTime) {
		this.orgAdministrativeInfoBeginTime = orgAdministrativeInfoBeginTime;
    }

    public String getOrgAdministrativeInfoDivision() {
		return orgAdministrativeInfoDivision;
    }
    public void setOrgAdministrativeInfoDivision(String orgAdministrativeInfoDivision) {
		this.orgAdministrativeInfoDivision = orgAdministrativeInfoDivision;
    }

    public String getOrgAdministrativeInfoDivisionCode() {
		return orgAdministrativeInfoDivisionCode;
    }
    public void setOrgAdministrativeInfoDivisionCode(String orgAdministrativeInfoDivisionCode) {
		this.orgAdministrativeInfoDivisionCode = orgAdministrativeInfoDivisionCode;
    }

    public String getOrgAdministrativeInfoBigRegion() {
		return orgAdministrativeInfoBigRegion;
    }
    public void setOrgAdministrativeInfoBigRegion(String orgAdministrativeInfoBigRegion) {
		this.orgAdministrativeInfoBigRegion = orgAdministrativeInfoBigRegion;
    }

    public String getOrgAdministrativeInfoSmallRegion() {
		return orgAdministrativeInfoSmallRegion;
    }
    public void setOrgAdministrativeInfoSmallRegion(String orgAdministrativeInfoSmallRegion) {
		this.orgAdministrativeInfoSmallRegion = orgAdministrativeInfoSmallRegion;
    }

    public String getExpressBigRegion() {
		return expressBigRegion;
	}
	public void setExpressBigRegion(String expressBigRegion) {
		this.expressBigRegion = expressBigRegion;
	}
	public String getExpressSalesDepartment() {
		return expressSalesDepartment;
	}
	public void setExpressSalesDepartment(String expressSalesDepartment) {
		this.expressSalesDepartment = expressSalesDepartment;
	}
	public String getExpressPart() {
		return expressPart;
	}
	public void setExpressPart(String expressPart) {
		this.expressPart = expressPart;
	}
	public String getOrgAdministrativeInfoAddress() {
		return orgAdministrativeInfoAddress;
    }
    public void setOrgAdministrativeInfoAddress(String orgAdministrativeInfoAddress) {
		this.orgAdministrativeInfoAddress = orgAdministrativeInfoAddress;
    }

    public String getOrgAdministrativeInfoDeptArea() {
		return orgAdministrativeInfoDeptArea;
    }
    public void setOrgAdministrativeInfoDeptArea(String orgAdministrativeInfoDeptArea) {
		this.orgAdministrativeInfoDeptArea = orgAdministrativeInfoDeptArea;
    }

    public String getOrgAdministrativeInfoProvCode() {
		return orgAdministrativeInfoProvCode;
    }
    public void setOrgAdministrativeInfoProvCode(String orgAdministrativeInfoProvCode) {
		this.orgAdministrativeInfoProvCode = orgAdministrativeInfoProvCode;
    }

    public String getOrgAdministrativeInfoCityCode() {
		return orgAdministrativeInfoCityCode;
    }
    public void setOrgAdministrativeInfoCityCode(String orgAdministrativeInfoCityCode) {
		this.orgAdministrativeInfoCityCode = orgAdministrativeInfoCityCode;
    }

    public String getOrgAdministrativeInfoCountyCode() {
		return orgAdministrativeInfoCountyCode;
    }
    public void setOrgAdministrativeInfoCountyCode(String orgAdministrativeInfoCountyCode) {
		this.orgAdministrativeInfoCountyCode = orgAdministrativeInfoCountyCode;
    }

    public String getOrgAdministrativeInfoSalesDepartment() {
		return orgAdministrativeInfoSalesDepartment;
    }
    public void setOrgAdministrativeInfoSalesDepartment(String orgAdministrativeInfoSalesDepartment) {
		this.orgAdministrativeInfoSalesDepartment = orgAdministrativeInfoSalesDepartment;
    }

    public String getOrgAdministrativeInfoTransferCenter() {
		return orgAdministrativeInfoTransferCenter;
    }
    public void setOrgAdministrativeInfoTransferCenter(String orgAdministrativeInfoTransferCenter) {
		this.orgAdministrativeInfoTransferCenter = orgAdministrativeInfoTransferCenter;
    }

    public String getOrgAdministrativeInfoDoAirDispatch() {
		return orgAdministrativeInfoDoAirDispatch;
    }
    public void setOrgAdministrativeInfoDoAirDispatch(String orgAdministrativeInfoDoAirDispatch) {
		this.orgAdministrativeInfoDoAirDispatch = orgAdministrativeInfoDoAirDispatch;
    }

    public String getOrgAdministrativeInfoTransDepartment() {
		return orgAdministrativeInfoTransDepartment;
    }
    public void setOrgAdministrativeInfoTransDepartment(String orgAdministrativeInfoTransDepartment) {
		this.orgAdministrativeInfoTransDepartment = orgAdministrativeInfoTransDepartment;
    }

    public String getOrgAdministrativeInfoDispatchTeam() {
		return orgAdministrativeInfoDispatchTeam;
    }
    public void setOrgAdministrativeInfoDispatchTeam(String orgAdministrativeInfoDispatchTeam) {
		this.orgAdministrativeInfoDispatchTeam = orgAdministrativeInfoDispatchTeam;
    }

    public String getOrgAdministrativeInfoBillingGroup() {
		return orgAdministrativeInfoBillingGroup;
    }
    public void setOrgAdministrativeInfoBillingGroup(String orgAdministrativeInfoBillingGroup) {
		this.orgAdministrativeInfoBillingGroup = orgAdministrativeInfoBillingGroup;
    }

    public String getOrgAdministrativeInfoTransTeam() {
		return orgAdministrativeInfoTransTeam;
    }
    public void setOrgAdministrativeInfoTransTeam(String orgAdministrativeInfoTransTeam) {
		this.orgAdministrativeInfoTransTeam = orgAdministrativeInfoTransTeam;
    }

    public String getOrgAdministrativeInfoIsDeliverSchedule() {
		return orgAdministrativeInfoIsDeliverSchedule;
    }
    public void setOrgAdministrativeInfoIsDeliverSchedule(String orgAdministrativeInfoIsDeliverSchedule) {
		this.orgAdministrativeInfoIsDeliverSchedule = orgAdministrativeInfoIsDeliverSchedule;
    }

    public String getOrgAdministrativeInfoIsArrangeGoods() {
		return orgAdministrativeInfoIsArrangeGoods;
    }
    public void setOrgAdministrativeInfoIsArrangeGoods(String orgAdministrativeInfoIsArrangeGoods) {
		this.orgAdministrativeInfoIsArrangeGoods = orgAdministrativeInfoIsArrangeGoods;
    }

    public String getOrgAdministrativeInfoDeliverOutfield() {
		return orgAdministrativeInfoDeliverOutfield;
    }
    public void setOrgAdministrativeInfoDeliverOutfield(String orgAdministrativeInfoDeliverOutfield) {
		this.orgAdministrativeInfoDeliverOutfield = orgAdministrativeInfoDeliverOutfield;
    }

    public String getOrgAdministrativeInfoArrangeOutfield() {
		return orgAdministrativeInfoArrangeOutfield;
    }
    public void setOrgAdministrativeInfoArrangeOutfield(String orgAdministrativeInfoArrangeOutfield) {
		this.orgAdministrativeInfoArrangeOutfield = orgAdministrativeInfoArrangeOutfield;
    }

    public String getOrgAdministrativeInfoArrangeBizType() {
		return orgAdministrativeInfoArrangeBizType;
    }
    public void setOrgAdministrativeInfoArrangeBizType(String orgAdministrativeInfoArrangeBizType) {
		this.orgAdministrativeInfoArrangeBizType = orgAdministrativeInfoArrangeBizType;
    }

    public Date getOrgAdministrativeInfoCreateDate() {
		return orgAdministrativeInfoCreateDate;
    }
    public void setOrgAdministrativeInfoCreateDate(Date orgAdministrativeInfoCreateDate) {
		this.orgAdministrativeInfoCreateDate = orgAdministrativeInfoCreateDate;
    }

    public Date getOrgAdministrativeInfoModifyDate() {
		return orgAdministrativeInfoModifyDate;
    }
    public void setOrgAdministrativeInfoModifyDate(Date orgAdministrativeInfoModifyDate) {
		this.orgAdministrativeInfoModifyDate = orgAdministrativeInfoModifyDate;
    }

    public String getOrgAdministrativeInfoActive() {
		return orgAdministrativeInfoActive;
    }
    public void setOrgAdministrativeInfoActive(String orgAdministrativeInfoActive) {
		this.orgAdministrativeInfoActive = orgAdministrativeInfoActive;
    }

    public String getOrgAdministrativeInfoCreateUser() {
		return orgAdministrativeInfoCreateUser;
    }
    public void setOrgAdministrativeInfoCreateUser(String orgAdministrativeInfoCreateUser) {
		this.orgAdministrativeInfoCreateUser = orgAdministrativeInfoCreateUser;
    }

    public String getOrgAdministrativeInfoModifyUser() {
		return orgAdministrativeInfoModifyUser;
    }
    public void setOrgAdministrativeInfoModifyUser(String orgAdministrativeInfoModifyUser) {
		this.orgAdministrativeInfoModifyUser = orgAdministrativeInfoModifyUser;
    }

    public String getOrgAdministrativeInfoAirDispatch() {
		return orgAdministrativeInfoAirDispatch;
    }
    public void setOrgAdministrativeInfoAirDispatch(String orgAdministrativeInfoAirDispatch) {
		this.orgAdministrativeInfoAirDispatch = orgAdministrativeInfoAirDispatch;
    }

    public String getOrgAdministrativeInfoEntityFinance() {
		return orgAdministrativeInfoEntityFinance;
    }
    public void setOrgAdministrativeInfoEntityFinance(String orgAdministrativeInfoEntityFinance) {
		this.orgAdministrativeInfoEntityFinance = orgAdministrativeInfoEntityFinance;
    }

    public String getOrgAdministrativeInfoDepCoordinate() {
		return orgAdministrativeInfoDepCoordinate;
    }
    public void setOrgAdministrativeInfoDepCoordinate(String orgAdministrativeInfoDepCoordinate) {
		this.orgAdministrativeInfoDepCoordinate = orgAdministrativeInfoDepCoordinate;
    }

    public String getOrgAdministrativeInfoDepTelephone() {
		return orgAdministrativeInfoDepTelephone;
    }
    public void setOrgAdministrativeInfoDepTelephone(String orgAdministrativeInfoDepTelephone) {
		this.orgAdministrativeInfoDepTelephone = orgAdministrativeInfoDepTelephone;
    }

    public String getOrgAdministrativeInfoDepFex() {
		return orgAdministrativeInfoDepFex;
    }
    public void setOrgAdministrativeInfoDepFex(String orgAdministrativeInfoDepFex) {
		this.orgAdministrativeInfoDepFex = orgAdministrativeInfoDepFex;
    }

    public String getEmployeeId() {
		return employeeId;
    }
    public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
    }

    public String getEmployeeEmpName() {
		return employeeEmpName;
    }
    public void setEmployeeEmpName(String employeeEmpName) {
		this.employeeEmpName = employeeEmpName;
    }

    public String getEmployeePinyin() {
		return employeePinyin;
    }
    public void setEmployeePinyin(String employeePinyin) {
		this.employeePinyin = employeePinyin;
    }

    public String getEmployeeEmpCode() {
		return employeeEmpCode;
    }
    public void setEmployeeEmpCode(String employeeEmpCode) {
		this.employeeEmpCode = employeeEmpCode;
    }

    public String getEmployeeGender() {
		return employeeGender;
    }
    public void setEmployeeGender(String employeeGender) {
		this.employeeGender = employeeGender;
    }

    public String getEmployeeUnifieldCode() {
		return employeeUnifieldCode;
    }
    public void setEmployeeUnifieldCode(String employeeUnifieldCode) {
		this.employeeUnifieldCode = employeeUnifieldCode;
    }

    public String getEmployeeTitle() {
		return employeeTitle;
    }
    public void setEmployeeTitle(String employeeTitle) {
		this.employeeTitle = employeeTitle;
    }

    public String getEmployeeDegree() {
		return employeeDegree;
    }
    public void setEmployeeDegree(String employeeDegree) {
		this.employeeDegree = employeeDegree;
    }

    public Date getEmployeeBirthdate() {
		return employeeBirthdate;
    }
    public void setEmployeeBirthdate(Date employeeBirthdate) {
		this.employeeBirthdate = employeeBirthdate;
    }

    public String getEmployeeStatus() {
		return employeeStatus;
    }
    public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
    }

    public String getEmployeePhone() {
		return employeePhone;
    }
    public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
    }

    public String getEmployeeIdentityCard() {
		return employeeIdentityCard;
    }
    public void setEmployeeIdentityCard(String employeeIdentityCard) {
		this.employeeIdentityCard = employeeIdentityCard;
    }

    public Date getEmployeeEntryDate() {
		return employeeEntryDate;
    }
    public void setEmployeeEntryDate(Date employeeEntryDate) {
		this.employeeEntryDate = employeeEntryDate;
    }

    public Date getEmployeeLeaveDate() {
		return employeeLeaveDate;
    }
    public void setEmployeeLeaveDate(Date employeeLeaveDate) {
		this.employeeLeaveDate = employeeLeaveDate;
    }

    public String getEmployeeMobilePhone() {
		return employeeMobilePhone;
    }
    public void setEmployeeMobilePhone(String employeeMobilePhone) {
		this.employeeMobilePhone = employeeMobilePhone;
    }

    public String getEmployeeEmail() {
		return employeeEmail;
    }
    public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
    }

    public Date getEmployeeCreateDate() {
		return employeeCreateDate;
    }
    public void setEmployeeCreateDate(Date employeeCreateDate) {
		this.employeeCreateDate = employeeCreateDate;
    }

    public Date getEmployeeModifyDate() {
		return employeeModifyDate;
    }
    public void setEmployeeModifyDate(Date employeeModifyDate) {
		this.employeeModifyDate = employeeModifyDate;
    }

    public String getEmployeeActive() {
		return employeeActive;
    }
    public void setEmployeeActive(String employeeActive) {
		this.employeeActive = employeeActive;
    }

    public String getEmployeeCreateUser() {
		return employeeCreateUser;
    }
    public void setEmployeeCreateUser(String employeeCreateUser) {
		this.employeeCreateUser = employeeCreateUser;
    }

    public String getEmployeeModifyUser() {
		return employeeModifyUser;
    }
    public void setEmployeeModifyUser(String employeeModifyUser) {
		this.employeeModifyUser = employeeModifyUser;
    }
    
    /**
     * @return  the employeeTitleName
     */
    public String getEmployeeTitleName() {
        return employeeTitleName;
    }
    
    /**
     * @param employeeTitleName the employeeTitleName to set
     */
    public void setEmployeeTitleName(String employeeTitleName) {
        this.employeeTitleName = employeeTitleName;
    }
}
