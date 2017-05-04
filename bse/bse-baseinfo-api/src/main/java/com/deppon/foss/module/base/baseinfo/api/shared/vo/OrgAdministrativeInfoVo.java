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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OrgAdministrativeInfoVo.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonQueryVirtualSalesDeptDto;
public class OrgAdministrativeInfoVo implements Serializable{

	/**
	 * 序列化对象
	 */
	private static final long serialVersionUID = 7946775205146630391L;

    /**
     * 行政组织实体
     */
	private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;

    /**
     * 行政组织实体List
     */
	private List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList;
	
    /**
     * 所有的全路径
     */
	private String allFullPath;
	
    /**
     * 第一条的全路径
     */
	private String fullPath;
	
    /**
     * 账户信息
     */
	private PublicBankAccountEntity publicBankAccountEntity;
	
    /**
     * 外场信息
     */
	private OutfieldEntity outfieldEntity;
	
    /**
     * 车队信息
     */
	private MotorcadeEntity motorcadeEntity;
	
    /**
     * 营业部信息
     */
	private SaleDepartmentEntity saleDepartmentEntity;
	
    /**
     * 营业部产品
     */
	private List<SalesProductEntity> salesProductEntityList;

    /**
     * 营业部车队
     */
    private List<SalesMotorcadeEntity> salesMotorcadeEntityList;
    
    /**
     * 营业部集中开单组
     */
    private List<OrgAdministrativeInfoEntity> centralizedBillingGroupList;
    
    /**
     * 营业部已选的集中开单组
     */
    private List<SalesBillingGroupEntity> selectedCentralizedBillingGroupList;
    
    /**
     * 用户编码
     */
    private String userCode;
    
    /**
     * 车队负责的营业区域查询实体
     */
    private List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityList;
    
    /**
     * 车队负责的营业区域新增实体
     */
    private List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityAddList;
    
    /**
     * 车队负责的营业区域新增实体
     */
    private List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityDeleteList;
    
    /**
     * 车队负责的行政区域查询实体
     */
    private List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityList;
    
    /**
     * 车队负责的行政区域新增实体
     */
    private List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityAddList;
    
    /**
     * 车队负责的行政区域删除实体
     */
    private List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityDeleteList;
    /**
     * 车队所属营业部查询实体
     */
    private List<SalesMotorcadeEntity> motorcadeServeSalesDeptEntityList;
    /**
     * 车队服务营业部新增实体
     */
    private List<SalesMotorcadeEntity>  motorcadeServeSalesDeptEntityAddList;
    /**
     * 车队服务营业部删除实体
     */
    private List<SalesMotorcadeEntity> motorcadeServeSalesDeptEntityDeleteList;
    /**
     * 设置页面button能否被点击的属性
     */
    private boolean buttonStatus;
    /**
     * 外场和集中开单组实体类
     */
    private BillingGroupTransFerEntity billingGroupTransFerEntity;
    /**
     * 集中开单组服务营业部查询实体
     */
    private List<SalesBillingGroupEntity> salesBillingGroupEntityList;
   /**
    * 集中开单组服务营业部新增实体
    */
    private List<SalesBillingGroupEntity> salesBillingGroupEntityAddList;
    /**
     * 集中开单组服务营业部删除实体
     */
    private List<SalesBillingGroupEntity> salesBillingGroupEntityDeleteList;
    
    /**
     * 保安组
     */
    private SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity;
    /**
     * 保安组集合
     */
    private List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList;
	/**
     * 七月一号之前默认临欠金额
     */
    private int defaultOwedLimit;
    /**
     * 七月一号之后最小临欠金额
     */
    private int minOwedLimit;
    
    /**
     * 存在查询虚拟营业部的对象
     */
    private CommonQueryVirtualSalesDeptDto commonQueryVirtualSalesDeptDto;
    
    /**
     * 存放虚拟营业部的codes
     */
    private String[] codes;

    
    
	public SecurityTfrMotorcadeEntity getSecurityTfrMotorcadeEntity() {
		return securityTfrMotorcadeEntity;
	}

	public void setSecurityTfrMotorcadeEntity(
			SecurityTfrMotorcadeEntity securityTfrMotorcadeEntity) {
		this.securityTfrMotorcadeEntity = securityTfrMotorcadeEntity;
	}

	public List<SecurityTfrMotorcadeEntity> getSecurityTfrMotorcadeEntityList() {
		return securityTfrMotorcadeEntityList;
	}

	public void setSecurityTfrMotorcadeEntityList(
			List<SecurityTfrMotorcadeEntity> securityTfrMotorcadeEntityList) {
		this.securityTfrMotorcadeEntityList = securityTfrMotorcadeEntityList;
	}

	public int getDefaultOwedLimit() {
		return defaultOwedLimit;
	}

	public void setDefaultOwedLimit(int defaultOwedLimit) {
		this.defaultOwedLimit = defaultOwedLimit;
	}

	public int getMinOwedLimit() {
		return minOwedLimit;
	}

	public void setMinOwedLimit(int minOwedLimit) {
		this.minOwedLimit = minOwedLimit;
	}

	public List<MotorcadeServeDistrictEntity> getMotorcadeServeDistrictEntityList() {
		return motorcadeServeDistrictEntityList;
	}

	public void setMotorcadeServeDistrictEntityList(
			List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntityList) {
		this.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
	}

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

	public List<MotorcadeServeSalesAreaEntity> getMotorcadeServeSalesAreaEntityList() {
		return motorcadeServeSalesAreaEntityList;
	}

	public void setMotorcadeServeSalesAreaEntityList(
			List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntityList) {
		this.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
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

	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}

	public List<OrgAdministrativeInfoEntity> getOrgAdministrativeInfoEntityList() {
		return orgAdministrativeInfoEntityList;
	}

	public void setOrgAdministrativeInfoEntityList(
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {
		this.orgAdministrativeInfoEntityList = orgAdministrativeInfoEntityList;
	}

	public String getAllFullPath() {
		return allFullPath;
	}

	public void setAllFullPath(String allFullPath) {
		this.allFullPath = allFullPath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public OutfieldEntity getOutfieldEntity() {
		return outfieldEntity;
	}

	public void setOutfieldEntity(OutfieldEntity outfieldEntity) {
		this.outfieldEntity = outfieldEntity;
	}

	public MotorcadeEntity getMotorcadeEntity() {
		return motorcadeEntity;
	}

	public void setMotorcadeEntity(MotorcadeEntity motorcadeEntity) {
		this.motorcadeEntity = motorcadeEntity;
	}

	public SaleDepartmentEntity getSaleDepartmentEntity() {
		return saleDepartmentEntity;
	}

	public void setSaleDepartmentEntity(SaleDepartmentEntity saleDepartmentEntity) {
		this.saleDepartmentEntity = saleDepartmentEntity;
	}

	public List<SalesProductEntity> getSalesProductEntityList() {
		return salesProductEntityList;
	}

	public void setSalesProductEntityList(
			List<SalesProductEntity> salesProductEntityList) {
		this.salesProductEntityList = salesProductEntityList;
	}

	public List<SalesMotorcadeEntity> getSalesMotorcadeEntityList() {
		return salesMotorcadeEntityList;
	}

	public void setSalesMotorcadeEntityList(
			List<SalesMotorcadeEntity> salesMotorcadeEntityList) {
		this.salesMotorcadeEntityList = salesMotorcadeEntityList;
	}

	public BillingGroupTransFerEntity getBillingGroupTransFerEntity() {
		return billingGroupTransFerEntity;
	}

	public void setBillingGroupTransFerEntity(
			BillingGroupTransFerEntity billingGroupTransFerEntity) {
		this.billingGroupTransFerEntity = billingGroupTransFerEntity;
	}

	public List<SalesMotorcadeEntity> getMotorcadeServeSalesDeptEntityList() {
		return motorcadeServeSalesDeptEntityList;
	}

	public void setMotorcadeServeSalesDeptEntityList(
			List<SalesMotorcadeEntity> motorcadeServeSalesDeptEntityList) {
		this.motorcadeServeSalesDeptEntityList = motorcadeServeSalesDeptEntityList;
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

	
	public List<SalesBillingGroupEntity> getSalesBillingGroupEntityList() {
		return salesBillingGroupEntityList;
	}

	public void setSalesBillingGroupEntityList(
			List<SalesBillingGroupEntity> salesBillingGroupEntityList) {
		this.salesBillingGroupEntityList = salesBillingGroupEntityList;
	}
	
	public List<SalesBillingGroupEntity> getSalesBillingGroupEntityAddList() {
		return salesBillingGroupEntityAddList;
	}

	public void setSalesBillingGroupEntityAddList(
			List<SalesBillingGroupEntity> salesBillingGroupEntityAddList) {
		this.salesBillingGroupEntityAddList = salesBillingGroupEntityAddList;
	}

	public List<SalesBillingGroupEntity> getSalesBillingGroupEntityDeleteList() {
		return salesBillingGroupEntityDeleteList;
	}

	public void setSalesBillingGroupEntityDeleteList(
			List<SalesBillingGroupEntity> salesBillingGroupEntityDeleteList) {
		this.salesBillingGroupEntityDeleteList = salesBillingGroupEntityDeleteList;
	}

	/**
	 *getter
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 *setter
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public PublicBankAccountEntity getPublicBankAccountEntity() {
		return publicBankAccountEntity;
	}

	public void setPublicBankAccountEntity(
			PublicBankAccountEntity publicBankAccountEntity) {
		this.publicBankAccountEntity = publicBankAccountEntity;
	}

	/**
	 * @return buttonStatus
	 */
	public boolean isButtonStatus() {
		return buttonStatus;
	}

	/**
	 * @param  buttonStatus  
	 */
	public void setButtonStatus(boolean buttonStatus) {
		this.buttonStatus = buttonStatus;
	}

	public List<OrgAdministrativeInfoEntity> getCentralizedBillingGroupList() {
		return centralizedBillingGroupList;
	}

	public void setCentralizedBillingGroupList(
			List<OrgAdministrativeInfoEntity> centralizedBillingGroupList) {
		this.centralizedBillingGroupList = centralizedBillingGroupList;
	}

	public List<SalesBillingGroupEntity> getSelectedCentralizedBillingGroupList() {
		return selectedCentralizedBillingGroupList;
	}

	public void setSelectedCentralizedBillingGroupList(
			List<SalesBillingGroupEntity> selectedCentralizedBillingGroupList) {
		this.selectedCentralizedBillingGroupList = selectedCentralizedBillingGroupList;
	}


	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	/**
	 * @return the commonQueryVirtualSalesDeptDto
	 */
	public CommonQueryVirtualSalesDeptDto getCommonQueryVirtualSalesDeptDto() {
		return commonQueryVirtualSalesDeptDto;
	}

	/**
	 * @param commonQueryVirtualSalesDeptDto the commonQueryVirtualSalesDeptDto to set
	 */
	public void setCommonQueryVirtualSalesDeptDto(
			CommonQueryVirtualSalesDeptDto commonQueryVirtualSalesDeptDto) {
		this.commonQueryVirtualSalesDeptDto = commonQueryVirtualSalesDeptDto;
	}
}
