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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/MotorcadeEntity.java
 * 
 * FILE NAME        	: MotorcadeEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 车队
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class MotorcadeEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350014344656L;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 是否集中接送货
     */
    private String service;
    
    /**
     * 集中接送货车队编码
     */
    private String serviceCode;

    /**
     * 是否集中接送货车队组
     */
    private String serviceTeam;

    /**
     * 所属车队
     */
    private String parentOrgCode;
    
    /**
     * 所属车队
     */
    private String parentOrgCodeName;

    /**
     * 所服务外场
     */
    private String transferCenter;
    
    /**
     * 所服务外场
     */
    private String transferCenterName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String modifyTime;

    /**
     * 是否启用
     */
    private String active;

    /**
     * 是否车队调度组
     */
    private String dispatchTeam;

    /**
     * 车队类型
     */
    private String fleetType;

    /**
    * 是否顶级车队
    */	
    private String isTopFleet;

    /**
    * 是否直接管车
    */	
    private String isManageVehicle;

    /**
    * 所服务的集中开单组的部门编码
    */	
    private String serveBillTerm;
    
    /**
    * 所服务的集中开单组的部门名称
    */	
    private String serveBillTermName;
    
    /**
     * 搜索用，给公共选择器使用
     */
    private String key;
	/**
	 * 车队类型集合
	 */
	private List<String> fleetTypes;
	  /**
     * 车队负责的行政区域新增实体
     */
    private List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityAddList;
	  /**
     * 车队负责的行政区域删除实体
     */
    private List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityDeleteList;
    
    /**
     * 车队负责的营业区域新增实体
     */
    private List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityAddList;
    
    /**
     * 车队负责的营业区域删除实体
     */
    private List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityDeleteList;
	
    
    /**
     * 车队服务营业部新增实体
     */
    private List<SalesMotorcadeEntity>  motorcadeServeSalesDeptEntityAddList;
    /**
     * 车队服务营业部删除实体
     */
    private List<SalesMotorcadeEntity> motorcadeServeSalesDeptEntityDeleteList;
    
    
    public List<MotorcadeServeDistrictEntity> getMotorcadeServeDistrictEntityAddList() {
		return motorcadeServeDistrictEntityAddList;
	}

	public void setMotorcadeServeDistrictEntityAddList(
			List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityAddList) {
		this.motorcadeServeDistrictEntityAddList = motorcadeServeDistrictEntityAddList;
	}

	public List<MotorcadeServeDistrictEntity> getMotorcadeServeDistrictEntityDeleteList() {
		return motorcadeServeDistrictEntityDeleteList;
	}

	public void setMotorcadeServeDistrictEntityDeleteList(
			List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityDeleteList) {
		this.motorcadeServeDistrictEntityDeleteList = motorcadeServeDistrictEntityDeleteList;
	}

	public List<MotorcadeServeSalesAreaEntity> getMotorcadeServeSalesAreaEntityAddList() {
		return motorcadeServeSalesAreaEntityAddList;
	}

	public void setMotorcadeServeSalesAreaEntityAddList(
			List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityAddList) {
		this.motorcadeServeSalesAreaEntityAddList = motorcadeServeSalesAreaEntityAddList;
	}

	public List<MotorcadeServeSalesAreaEntity> getMotorcadeServeSalesAreaEntityDeleteList() {
		return motorcadeServeSalesAreaEntityDeleteList;
	}

	public void setMotorcadeServeSalesAreaEntityDeleteList(
			List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityDeleteList) {
		this.motorcadeServeSalesAreaEntityDeleteList = motorcadeServeSalesAreaEntityDeleteList;
	}

	public List<SalesMotorcadeEntity> getMotorcadeServeSalesDeptEntityAddList() {
		return motorcadeServeSalesDeptEntityAddList;
	}

	public void setMotorcadeServeSalesDeptEntityAddList(
			List<SalesMotorcadeEntity> motorcadeServeSalesDeptEntityAddList) {
		this.motorcadeServeSalesDeptEntityAddList = motorcadeServeSalesDeptEntityAddList;
	}

	public List<SalesMotorcadeEntity> getMotorcadeServeSalesDeptEntityDeleteList() {
		return motorcadeServeSalesDeptEntityDeleteList;
	}

	public void setMotorcadeServeSalesDeptEntityDeleteList(
			List<SalesMotorcadeEntity> motorcadeServeSalesDeptEntityDeleteList) {
		this.motorcadeServeSalesDeptEntityDeleteList = motorcadeServeSalesDeptEntityDeleteList;
	}


	public List<String> getFleetTypes() {
		return fleetTypes;
	}

	public void setFleetTypes(List<String> fleetTypes) {
		this.fleetTypes = fleetTypes;
	}

	/**
     * @return  the key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
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
	 * @return service
	 */
	public String getService() {
		return service;
	}
	
	/**
	 * @param  service  
	 */
	public void setService(String service) {
		this.service = service;
	}
	
	/**
	 * @return serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	
	/**
	 * @param  serviceCode  
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	/**
	 * @return serviceTeam
	 */
	public String getServiceTeam() {
		return serviceTeam;
	}
	
	/**
	 * @param  serviceTeam  
	 */
	public void setServiceTeam(String serviceTeam) {
		this.serviceTeam = serviceTeam;
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
	 * @return parentOrgCodeName
	 */
	public String getParentOrgCodeName() {
		return parentOrgCodeName;
	}
	
	/**
	 * @param  parentOrgCodeName  
	 */
	public void setParentOrgCodeName(String parentOrgCodeName) {
		this.parentOrgCodeName = parentOrgCodeName;
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
	 * @return transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}
	
	/**
	 * @param  transferCenterName  
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	
	/**
	 * @return createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	
	/**
	 * @param  createTime  
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * @param  modifyTime  
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
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
	 * @return fleetType
	 */
	public String getFleetType() {
		return fleetType;
	}
	
	/**
	 * @param  fleetType  
	 */
	public void setFleetType(String fleetType) {
		this.fleetType = fleetType;
	}
	
	/**
	 * @return isTopFleet
	 */
	public String getIsTopFleet() {
		return isTopFleet;
	}
	
	/**
	 * @param  isTopFleet  
	 */
	public void setIsTopFleet(String isTopFleet) {
		this.isTopFleet = isTopFleet;
	}
	
	/**
	 * @return isManageVehicle
	 */
	public String getIsManageVehicle() {
		return isManageVehicle;
	}
	
	/**
	 * @param  isManageVehicle  
	 */
	public void setIsManageVehicle(String isManageVehicle) {
		this.isManageVehicle = isManageVehicle;
	}
	
	/**
	 * @return serveBillTerm
	 */
	public String getServeBillTerm() {
		return serveBillTerm;
	}
	
	/**
	 * @param  serveBillTerm  
	 */
	public void setServeBillTerm(String serveBillTerm) {
		this.serveBillTerm = serveBillTerm;
	}
	
	/**
	 * @return serveBillTermName
	 */
	public String getServeBillTermName() {
		return serveBillTermName;
	}
	
	/**
	 * @param  serveBillTermName  
	 */
	public void setServeBillTermName(String serveBillTermName) {
		this.serveBillTermName = serveBillTermName;
	}
}