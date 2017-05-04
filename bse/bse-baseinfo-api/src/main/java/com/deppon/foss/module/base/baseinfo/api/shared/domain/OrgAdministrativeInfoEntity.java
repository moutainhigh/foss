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

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 组织信息
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class OrgAdministrativeInfoEntity  extends BaseEntity {
	
	/**
	 * 序列化
	 */
    private static final long serialVersionUID = -3967231352083248609L;
    
    //350909     郭倩云        零担轻货上分拣取的是城市简称
    private String  simpleCityName;
  
    /**
     *所属子公司名称
     */	
     private String subsidiaryName;
    
    /**
     *所属成本中心名称
     */	
     private String costCenterName;

    /**
     *国家地区名称
     */	
     private String countryRegionName;
    
    /**
    *省份
    */	
    private String provName;

    /**
    *城市
    */	
    private String cityName;

    /**
    *区县
    */	
    private String countyName;
    
    /**
    * 组织编码
    */	
    private String code;

    /**
    * 组织名称
    */	
    private String name;

    /**
    * 拼音
    */	
    private String pinyin;

    /**
    * 组织标杆编码
    */	
    private String unifiedCode;

    /**
    * 组织负责人
    */	
    private String leader;

    /**
    * 组织负责人工号
    */	
    private String principalNo;

    /**
    * 上级组织名称
    */	
    private String parentOrgName;

    /**
    * 上级组织标杆编码
    */	
    private String parentOrgUnifiedCode;

    /**
    * 所属子公司编码
    */	
    private String subsidiaryCode;

    /**
    * 所属成本中心编码
    */	
    private String costCenterCode;

    /**
    * 组织状态
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
    private String division;

    /**
    * 事业部编码
    */	
    private String divisionCode;

    /**
    * 是否大区
    */	
    private String bigRegion;

    /**
    * 是否小区
    */	
    private String smallRegion;

    /**
    * 部门地址
    */	
    private String address;

    /**
    * 部门面积
    */	
    private BigDecimal deptArea;

    /**
    * 省份
    */	
    private String provCode;

    /**
    * 城市
    */	
    private String cityCode;

    /**
    * 区县
    */	
    private String countyCode;

    /**
    * 是否营业部派送部
    */	
    private String salesDepartment;

    /**
    * 是否外场
    */	
    private String transferCenter;
    
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
    * 派送排单服务外场
    */	
    private String deliverOutfield;
    
    /**
    * 派送排单服务外场
    */	
    private String deliverOutfieldName;

    /**
    * 理货部门服务外场
    */	
    private String arrangeOutfield;
    
    /**
    * 理货部门服务外场
    */	
    private String arrangeOutfieldName;

    
    /**
    * 理货业务类型
    */	
    private String arrangeBizType;

    /**
    * 是否启用
    */	
    private String active;

    /**
    * 是否空运总调
    */	
    private String airDispatch;

    /**
    * 是否实体财务部
    */	
    private String isEntityFinance;

    /**
    * 所属实体财务部
    */	
    private String entityFinance;
    
    /**
    * 所属实体财务部名字
    */	
    private String entityFinanceName;

    /**
    * 部门服务区坐标编号
    */	
    private String depCoordinate;

    /**
    * 部门电话
    */	
    private String depTelephone;

    /**
    * 部门传真
    */	
    private String depFax;

    /**
    * 部门简称
    */	
    private String orgSimpleName;

    /**
    * 国家地区
    */	
    private String countryRegion;

    /**
    * 部门英文简称
    */	
    private String orgEnSimple;

    /**
    * 部门备注描述信息
    */	
    private String deptDesc;

    /**
    * 数据版本号
    */	
    private Long versionNo;

    /**
    * 上级组织编码
    */	
    private String parentOrgCode;


    /**
    * UUMS主键ID
    */	
    private String uumsId;

    /**
    * UUMS上级主键ID
    */	
    private String uumsParentId;

    /**
    * UUMS主键ID序列
    */	
    private String uumsIds;


    /**
    * 是否为叶子节点
    */	
    private String isLeaf;

    /**
    * 显示顺序
    */	
    private String displayOrder;

    /**
    * 部门层级
    */	
    private String deptLevel;

    /**
    * 地区编码默认拼音
    */	
    private String areaCode;

    /**
    * 组织邮箱
    */	
    private String deptEmail;
    
    /**
     * 是否保安组
     */	
     private String isSecurity;

    /**
    * 邮编号码
    */	
    private String zipCode;

    /**
    * 组织性质
    */	
    private String deptAttribute;

    /**
    * 已封存系统
    */	
    private String canceledSystems;

    /**
    * EHR部门编码
    */	
    private String ehrDeptCode;
    
    /**
    * 组织级别，取自数据字典，包括但不限于事业部，大区，小区，部门等，
    */	
    private String orgLevel;
    /**
     * 部门补码简称
     */
    private String complementSimpleName;
    /**
    *部门标杆编码集合
    */	
    private List<String> unifiedCodes;
    
    /**
     *部门编码集合
     */	
    private List<String> codes;
    /**
     * 是否快递分拣
     */
    private String expressSorting;
    /**
     * 是否快递分部
     */
    private String expressBranch; 
    /**
     * 是否经营本部
     */
    private String isManageDepartment;   
    
    /**
     * 配合主数据项目接口,用于接收创建时间-187862-dujunhui
     */
    private String createDateOfUU;
    /**
     * 配合主数据项目接口,用于接收修改时间-187862-dujunhui
     */
    private String modifyDateOfUU;
    /**
     * 配合主数据项目接口,用于接收开始时间-187862-dujunhui
     */
    private String beginTimeOfUU;
    /**
     * 配合主数据项目接口,用于接收结束时间-187862-dujunhui
     */
    private String endTimeOfUU;
    /**
     * 配合主数据项目接口,用于标识UUMS推送至FOSS的数据状态（0--待处理，1--处理中，2--处理成功，3--处理失败）-187862-dujunhui
     */
    private String interfaceStatus;
    /**
     * 配合主数据项目接口,用于标识UUMS推送至FOSS的操作时间-187862-dujunhui
     */
    private String operateTime;
    /**
     * 配合主数据项目接口,用于标识UUMS推送至FOSS的失败原因-187862-dujunhui
     */
    private String exceptionMsg;
    /**
     * 配合主数据项目，用于标识上游中间表同一部门编码同一创建时间信息的先后
     */
    private int num;
    /**
     * 配合主数据项目，用于标识上游中间表唯一性标识
     */
    private String fossId;
    
    public String getExpressSorting() {
		return expressSorting;
	}

	public void setExpressSorting(String expressSorting) {
		this.expressSorting = expressSorting;
	}

	/**
     * 
     * <p>是否营业部</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:08:51 PM
     * @return
     * @see
     */
    public boolean checkSaleDepartment() {
    	return StringUtils.equals(salesDepartment, FossConstants.YES);
    }

    /**
     * 
     * <p>是否车队组</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 4:25:30 PM
     * @return
     * @see
     */
    public boolean checkTransTeam() {
    	return StringUtils.equals(transTeam, FossConstants.YES);
    }

    /**
     * 
     * <p>是否车队调度组</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 4:24:21 PM
     * @return
     * @see
     */
    public boolean checkDispatchTeam() {
    	return StringUtils.equals(dispatchTeam, FossConstants.YES);
    }
    
    /**
     * 
     * <p>是否外场</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:08:51 PM
     * @return
     * @see
     */
    public boolean checkTransferCenter() {
    	return StringUtils.equals(transferCenter, FossConstants.YES);
    }

    /**
     * 
     * <p>是否快递大区</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:08:51 PM
     * @return
     * @see
     */
    public boolean checkExpressBigRegion() {
    	return StringUtils.equals(expressBigRegion, FossConstants.YES);
    }
    
    /**
     * 
     * <p>是否快递虚拟营业部</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:08:51 PM
     * @return
     * @see
     */
    public boolean checkExpressSalesDepartment() {
    	return StringUtils.equals(expressSalesDepartment, FossConstants.YES);
    }
    
    /**
     * 
     * <p>是否快递点部</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:08:51 PM
     * @return
     * @see
     */
    public boolean checkExpressPart() {
    	return StringUtils.equals(expressPart, FossConstants.YES);
    }
    
    /**
     * 
     * <p>是否快递分部</p> 
     * @author foss-WeiXing
     * @date sep 24, 2014 2:08:51 PM
     * @return
     * @see
     */
    public boolean checkExpressBranch() {
		  return StringUtils.equals(expressBranch, FossConstants.YES);
    }
    
    /**
     * 
     * <p>是否车队</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 3:01:26 PM
     * @return
     * @see
     */
    public boolean checkTransDepartment() {
    	return StringUtils.equals(transDepartment, FossConstants.YES);
    }

    /**
     * 
     * <p>是否空运总调</p> 
     * @author foss-zhujunyong
     * @date Nov 21, 2012 3:56:45 PM
     * @return
     * @see
     */
    public boolean checkDoAirDispatch () {
    	return StringUtils.equals(doAirDispatch, FossConstants.YES);
    }

    /**
     * @return complementSimpleName
     */
	public String getComplementSimpleName() {
		return complementSimpleName;
	}
	/**
	 * @param complementSimpleName
	 */
	public void setComplementSimpleName(String complementSimpleName) {
		this.complementSimpleName = complementSimpleName;
	}

	/**
	 * @return subsidiaryName
	 */
	public String getSubsidiaryName() {
		return subsidiaryName;
	}

	/**
	 * @param  subsidiaryName  
	 */
	public void setSubsidiaryName(String subsidiaryName) {
		this.subsidiaryName = subsidiaryName;
	}

	/**
	 * @return costCenterName
	 */
	public String getCostCenterName() {
		return costCenterName;
	}

	/**
	 * @param  costCenterName  
	 */
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	/**
	 * @return countryRegionName
	 */
	public String getCountryRegionName() {
		return countryRegionName;
	}

	/**
	 * @param  countryRegionName  
	 */
	public void setCountryRegionName(String countryRegionName) {
		this.countryRegionName = countryRegionName;
	}

	/**
	 * @return provName
	 */
	public String getProvName() {
		return provName;
	}

	/**
	 * @param  provName  
	 */
	public void setProvName(String provName) {
		this.provName = provName;
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
	 * @return code
	 */
	public String getCode() {
		return code;
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
	 * @return pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param  pinyin  
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
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
	 * @return leader
	 */
	public String getLeader() {
		return leader;
	}

	/**
	 * @param  leader  
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * @return principalNo
	 */
	public String getPrincipalNo() {
		return principalNo;
	}

	/**
	 * @param  principalNo  
	 */
	public void setPrincipalNo(String principalNo) {
		this.principalNo = principalNo;
	}

	/**
	 * @return parentOrgName
	 */
	public String getParentOrgName() {
		return parentOrgName;
	}

	/**
	 * @param  parentOrgName  
	 */
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	/**
	 * @return parentOrgUnifiedCode
	 */
	public String getParentOrgUnifiedCode() {
		return parentOrgUnifiedCode;
	}

	/**
	 * @param  parentOrgUnifiedCode  
	 */
	public void setParentOrgUnifiedCode(String parentOrgUnifiedCode) {
		this.parentOrgUnifiedCode = parentOrgUnifiedCode;
	}

	/**
	 * @return subsidiaryCode
	 */
	public String getSubsidiaryCode() {
		return subsidiaryCode;
	}

	/**
	 * @param  subsidiaryCode  
	 */
	public void setSubsidiaryCode(String subsidiaryCode) {
		this.subsidiaryCode = subsidiaryCode;
	}

	/**
	 * @return costCenterCode
	 */
	public String getCostCenterCode() {
		return costCenterCode;
	}

	/**
	 * @param  costCenterCode  
	 */
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param  status  
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return divisionCode
	 */
	public String getDivisionCode() {
		return divisionCode;
	}

	/**
	 * @param  divisionCode  
	 */
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
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
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param  address  
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return deptArea
	 */
	public BigDecimal getDeptArea() {
		return deptArea;
	}

	/**
	 * @param  deptArea  
	 */
	public void setDeptArea(BigDecimal deptArea) {
		this.deptArea = deptArea;
	}

	/**
	 * @return provCode
	 */
	public String getProvCode() {
		return provCode;
	}

	/**
	 * @param  provCode  
	 */
	public void setProvCode(String provCode) {
		this.provCode = provCode;
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
	 * @return deliverOutfield
	 */
	public String getDeliverOutfield() {
		return deliverOutfield;
	}

	/**
	 * @param  deliverOutfield  
	 */
	public void setDeliverOutfield(String deliverOutfield) {
		this.deliverOutfield = deliverOutfield;
	}

	/**
	 * @return deliverOutfieldName
	 */
	public String getDeliverOutfieldName() {
		return deliverOutfieldName;
	}

	/**
	 * @param  deliverOutfieldName  
	 */
	public void setDeliverOutfieldName(String deliverOutfieldName) {
		this.deliverOutfieldName = deliverOutfieldName;
	}

	/**
	 * @return arrangeOutfield
	 */
	public String getArrangeOutfield() {
		return arrangeOutfield;
	}

	/**
	 * @param  arrangeOutfield  
	 */
	public void setArrangeOutfield(String arrangeOutfield) {
		this.arrangeOutfield = arrangeOutfield;
	}

	/**
	 * @return arrangeOutfieldName
	 */
	public String getArrangeOutfieldName() {
		return arrangeOutfieldName;
	}

	/**
	 * @param  arrangeOutfieldName  
	 */
	public void setArrangeOutfieldName(String arrangeOutfieldName) {
		this.arrangeOutfieldName = arrangeOutfieldName;
	}

	/**
	 * @return arrangeBizType
	 */
	public String getArrangeBizType() {
		return arrangeBizType;
	}

	/**
	 * @param  arrangeBizType  
	 */
	public void setArrangeBizType(String arrangeBizType) {
		this.arrangeBizType = arrangeBizType;
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
	 * @return entityFinance
	 */
	public String getEntityFinance() {
		return entityFinance;
	}

	/**
	 * @param  entityFinance  
	 */
	public void setEntityFinance(String entityFinance) {
		this.entityFinance = entityFinance;
	}

	/**
	 * @return entityFinanceName
	 */
	public String getEntityFinanceName() {
		return entityFinanceName;
	}

	/**
	 * @param  entityFinanceName  
	 */
	public void setEntityFinanceName(String entityFinanceName) {
		this.entityFinanceName = entityFinanceName;
	}

	/**
	 * @return depCoordinate
	 */
	public String getDepCoordinate() {
		return depCoordinate;
	}

	/**
	 * @param  depCoordinate  
	 */
	public void setDepCoordinate(String depCoordinate) {
		this.depCoordinate = depCoordinate;
	}

	/**
	 * @return depTelephone
	 */
	public String getDepTelephone() {
		return depTelephone;
	}

	/**
	 * @param  depTelephone  
	 */
	public void setDepTelephone(String depTelephone) {
		this.depTelephone = depTelephone;
	}

	/**
	 * @return depFax
	 */
	public String getDepFax() {
		return depFax;
	}

	/**
	 * @param  depFax  
	 */
	public void setDepFax(String depFax) {
		this.depFax = depFax;
	}

	/**
	 * @return orgSimpleName
	 */
	public String getOrgSimpleName() {
		return orgSimpleName;
	}

	/**
	 * @param  orgSimpleName  
	 */
	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}

	/**
	 * @return countryRegion
	 */
	public String getCountryRegion() {
		return countryRegion;
	}

	/**
	 * @param  countryRegion  
	 */
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	/**
	 * @return orgEnSimple
	 */
	public String getOrgEnSimple() {
		return orgEnSimple;
	}

	/**
	 * @param  orgEnSimple  
	 */
	public void setOrgEnSimple(String orgEnSimple) {
		this.orgEnSimple = orgEnSimple;
	}

	/**
	 * @return deptDesc
	 */
	public String getDeptDesc() {
		return deptDesc;
	}

	/**
	 * @param  deptDesc  
	 */
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
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
	 * @return parentOrgCode
	 */
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	/**
	 * @param  parentOrgCode  
	 */
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	/**
	 * @return uumsId
	 */
	public String getUumsId() {
		return uumsId;
	}

	/**
	 * @param  uumsId  
	 */
	public void setUumsId(String uumsId) {
		this.uumsId = uumsId;
	}

	/**
	 * @return uumsParentId
	 */
	public String getUumsParentId() {
		return uumsParentId;
	}

	/**
	 * @param  uumsParentId  
	 */
	public void setUumsParentId(String uumsParentId) {
		this.uumsParentId = uumsParentId;
	}

	/**
	 * @return uumsIds
	 */
	public String getUumsIds() {
		return uumsIds;
	}

	/**
	 * @param  uumsIds  
	 */
	public void setUumsIds(String uumsIds) {
		this.uumsIds = uumsIds;
	}

	/**
	 * @return isLeaf
	 */
	public String getIsLeaf() {
		return isLeaf;
	}

	/**
	 * @param  isLeaf  
	 */
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * @return displayOrder
	 */
	public String getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param  displayOrder  
	 */
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return deptLevel
	 */
	public String getDeptLevel() {
		return deptLevel;
	}

	/**
	 * @param  deptLevel  
	 */
	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	/**
	 * @return areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param  areaCode  
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return deptEmail
	 */
	public String getDeptEmail() {
		return deptEmail;
	}

	/**
	 * @param  deptEmail  
	 */
	public void setDeptEmail(String deptEmail) {
		this.deptEmail = deptEmail;
	}

	/**
	 * @return zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param  zipCode  
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return deptAttribute
	 */
	public String getDeptAttribute() {
		return deptAttribute;
	}

	/**
	 * @param  deptAttribute  
	 */
	public void setDeptAttribute(String deptAttribute) {
		this.deptAttribute = deptAttribute;
	}

	/**
	 * @return canceledSystems
	 */
	public String getCanceledSystems() {
		return canceledSystems;
	}

	/**
	 * @param  canceledSystems  
	 */
	public void setCanceledSystems(String canceledSystems) {
		this.canceledSystems = canceledSystems;
	}

	/**
	 * @return ehrDeptCode
	 */
	public String getEhrDeptCode() {
		return ehrDeptCode;
	}

	/**
	 * @param  ehrDeptCode  
	 */
	public void setEhrDeptCode(String ehrDeptCode) {
		this.ehrDeptCode = ehrDeptCode;
	}

	/**
	 * @return orgLevel
	 */
	public String getOrgLevel() {
		return orgLevel;
	}

	/**
	 * @param  orgLevel  
	 */
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * @return unifiedCodes
	 */
	public List<String> getUnifiedCodes() {
		return unifiedCodes;
	}

	/**
	 * @param  unifiedCodes  
	 */
	public void setUnifiedCodes(List<String> unifiedCodes) {
		this.unifiedCodes = unifiedCodes;
	}

	/**
	 * @return codes
	 */
	public List<String> getCodes() {
		return codes;
	}

	/**
	 * @param  codes  
	 */
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getIsSecurity() {
		return isSecurity;
	}

	public void setIsSecurity(String isSecurity) {
		this.isSecurity = isSecurity;
	}

	public String getExpressBranch() {
		return expressBranch;
	}

	public void setExpressBranch(String expressBranch) {
		this.expressBranch = expressBranch;
	}

	/**
	 * @return  the isManageDepartment
	 */
	public String getIsManageDepartment() {
		return isManageDepartment;
	}

	/**
	 * @param isManageDepartment the isManageDepartment to set
	 */
	public void setIsManageDepartment(String isManageDepartment) {
		this.isManageDepartment = isManageDepartment;
	}

	public String getCreateDateOfUU() {
		return createDateOfUU;
	}

	public void setCreateDateOfUU(String createDateOfUU) {
		this.createDateOfUU = createDateOfUU;
	}

	public String getModifyDateOfUU() {
		return modifyDateOfUU;
	}

	public void setModifyDateOfUU(String modifyDateOfUU) {
		this.modifyDateOfUU = modifyDateOfUU;
	}

	public String getBeginTimeOfUU() {
		return beginTimeOfUU;
	}

	public void setBeginTimeOfUU(String beginTimeOfUU) {
		this.beginTimeOfUU = beginTimeOfUU;
	}

	public String getEndTimeOfUU() {
		return endTimeOfUU;
	}

	public void setEndTimeOfUU(String endTimeOfUU) {
		this.endTimeOfUU = endTimeOfUU;
	}

	public String getInterfaceStatus() {
		return interfaceStatus;
	}

	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getFossId() {
		return fossId;
	}

	public void setFossId(String fossId) {
		this.fossId = fossId;
	}

	public String getSimpleCityName() {
		return simpleCityName;
	}

	public void setSimpleCityName(String simpleCityName) {
		this.simpleCityName = simpleCityName;
	}
}