/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/WaybillPackEntity.java
 *  
 *  FILE NAME          :WaybillPackEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.domain
 * FILE    NAME: WaybillPackEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;


import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PDA端扫描生成包装金额查询结果
 * @author ZhangXu
 *
 * 2014-5-7
 */

public class QueryPDAPackResultEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1789126186457342138L;
	/** 
	 * 运单号
	 * 
	 */
	private String waybillNo;
	/**
	 * 开单部门code
	 * 
	 */
	private String   billOrgCode;
	/**
	 * 开单部门名称
	 * 
	 */
	private String   billOrgName;
	/**
	 * 包装部门code
	 * 
	 */
	private String   packageOrgCode;
	/**
	 * 包装部门名称
	 * 
	 */
	private String   packageOrgName;
	/**
	 * 理论打木架体积
	 * 
	 */
	private BigDecimal   theoryFrameVolume;
	/**
	 * 实际打木架体积
	 * 
	 */                  
	private BigDecimal   actualFrameVolume;
	/**
	 * 打木架体积
	 * 
	 */
	private BigDecimal   packageFrameVolume;
	/**
	 * 理论打木箱体积
	 * 
	 */
	private BigDecimal   theoryWoodenVolume;
	/**
	 * 实际打木箱体积
	 * 
	 */
	private BigDecimal   actualWoodenVolume;
	/**
	 * 打木箱体积
	 * 
	 */
	private BigDecimal   packageWoodenVolume;
	/**
	 * 理论打木托个数
	 * 
	 */
	private BigDecimal   theoryMaskNumber;
	/**
	 * 实际打木托个数
	 * 
	 */
	private BigDecimal   actualMaskNumber;
	/**
	 * 打木托个数
	 * 
	 */
	private BigDecimal   packageMaskNumber;
	/**
	 * 应付金额
	 * 
	 */
	private BigDecimal   packagePayableMoney;
	/**
	 * 包装供应商code
	 * 
	 */
	private String   packageSupplierCode;
	/**
	 * 包装供应商
	 * 
	 */
	private String   packageSupplierName;
	/**
	 * 包装材料
	 * 
	 */
	private String   packageMaterial;
	/**
	 * 创建部门code
	 * 
	 */
	private String   createOrgCode;
	/**
	 * 创建部门
	 * 
	 */
	private String   createOrgName;
	/**
	 * 创建人code
	 * 
	 */
	private String   createUserCode;
	/**
	 * 创建人
	 * 
	 */
	private String   createUserName;
	/**
	 * 创建时间
	 * 
	 */
	private Date   createTime;
	/**
	 * 修改人code
	 * 
	 */
	private String   modifyUserCode;
	/**
	 * 修改人
	 * 
	 */
	private String   modifyUserName;
	/**
	 * 修改时间
	 * 
	 */
	private Date   modifyTime;
	
	/**
	 * 审核状态
	 */
	private String auditStatus;
	//审核人
	private String auditUserName;
	//审核人code
	private String auditUserCode;
	//审核时间
	private Date auditTime;
	//反审核人
	private String backAuditName;
	//反审核人code
	private String backAuditCode;
	//反审核时间
	private Date backAuditTime;
	
	
	/** 
	 * 获取运单号
	 * 
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/** 
	 * 设置运单号
	 * 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 获取开单部门code
	 * 
	 */
	public String getBillOrgCode() {
		return billOrgCode;
	}
	/**
	 * 设置开单部门code
	 * 
	 */
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	/**
	 * 获取开单部门名称
	 * 
	 */
	public String getBillOrgName() {
		return billOrgName;
	}
	/**
	 * 设置开单部门名称
	 * 
	 */
	public void setBillOrgName(String billOrgName) {
		this.billOrgName = billOrgName;
	}
	/**
	 * 获取包装部门code
	 * 
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}
	/**
	 * 设置包装部门code
	 * 
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}
	/**
	 * 获取包装部门名称
	 * 
	 */
	public String getPackageOrgName() {
		return packageOrgName;
	}
	/**
	 * 设置包装部门名称
	 * 
	 */
	public void setPackageOrgName(String packageOrgName) {
		this.packageOrgName = packageOrgName;
	}
	/**
	 * 获取理论打木架体积
	 * 
	 */
	public BigDecimal getTheoryFrameVolume() {
		return theoryFrameVolume;
	}
	/**
	 * 设置理论打木架体积
	 * 
	 */
	public void setTheoryFrameVolume(BigDecimal theoryFrameVolume) {
		this.theoryFrameVolume = theoryFrameVolume;
	}
	/**
	 * 获取实际打木架体积
	 * 
	 */
	public BigDecimal getActualFrameVolume() {
		return actualFrameVolume;
	}
	/**
	 * 设置实际打木架体积
	 * 
	 */
	public void setActualFrameVolume(BigDecimal actualFrameVolume) {
		this.actualFrameVolume = actualFrameVolume;
	}
	/**
	 * 获取打木架体积
	 * 
	 */
	public BigDecimal getPackageFrameVolume() {
		return packageFrameVolume;
	}
	/**
	 * 设置打木架体积
	 * 
	 */
	public void setPackageFrameVolume(BigDecimal packageFrameVolume) {
		this.packageFrameVolume = packageFrameVolume;
	}
	/**
	 * 获取理论打木箱体积
	 * 
	 */
	public BigDecimal getTheoryWoodenVolume() {
		return theoryWoodenVolume;
	}
	/**
	 * 设置理论打木箱体积
	 * 
	 */
	public void setTheoryWoodenVolume(BigDecimal theoryWoodenVolume) {
		this.theoryWoodenVolume = theoryWoodenVolume;
	}
	/**
	 * 获取实际打木箱体积
	 * 
	 */
	public BigDecimal getActualWoodenVolume() {
		return actualWoodenVolume;
	}
	/**
	 * 设置实际打木箱体积
	 * 
	 */
	public void setActualWoodenVolume(BigDecimal actualWoodenVolume) {
		this.actualWoodenVolume = actualWoodenVolume;
	}
	/**
	 * 获取打木箱体积
	 * 
	 */
	public BigDecimal getPackageWoodenVolume() {
		return packageWoodenVolume;
	}
	/**
	 * 设置打木箱体积
	 * 
	 */
	public void setPackageWoodenVolume(BigDecimal packageWoodenVolume) {
		this.packageWoodenVolume = packageWoodenVolume;
	}
	/**
	 * 获取理论打木托个数
	 * 
	 */
	public BigDecimal getTheoryMaskNumber() {
		return theoryMaskNumber;
	}
	/**
	 * 设置理论打木托个数
	 * 
	 */
	public void setTheoryMaskNumber(BigDecimal theoryMaskNumber) {
		this.theoryMaskNumber = theoryMaskNumber;
	}
	/**
	 * 获取实际打木托个数
	 * 
	 */
	public BigDecimal getActualMaskNumber() {
		return actualMaskNumber;
	}
	/**
	 * 设置实际打木托个数
	 * 
	 */
	public void setActualMaskNumber(BigDecimal actualMaskNumber) {
		this.actualMaskNumber = actualMaskNumber;
	}
	/**
	 * 获取打木托个数
	 * 
	 */
	public BigDecimal getPackageMaskNumber() {
		return packageMaskNumber;
	}
	/**
	 * 设置打木托个数
	 * 
	 */
	public void setPackageMaskNumber(BigDecimal packageMaskNumber) {
		this.packageMaskNumber = packageMaskNumber;
	}
	/**
	 * 获取应付金额
	 * 
	 */
	public BigDecimal getPackagePayableMoney() {
		return packagePayableMoney;
	}
	/**
	 * 设置应付金额
	 * 
	 */
	public void setPackagePayableMoney(BigDecimal packagePayableMoney) {
		this.packagePayableMoney = packagePayableMoney;
	}
	/**
	 * 获取包装供应商code
	 * 
	 */
	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}
	/**
	 * 设置包装供应商code
	 * 
	 */
	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}
	/**
	 * 获取包装供应商名字
	 * 
	 */
	public String getPackageSupplierName() {
		return packageSupplierName;
	}
	/**
	 * 设置包装供应商名字
	 * 
	 */
	public void setPackageSupplierName(String packageSupplierName) {
		this.packageSupplierName = packageSupplierName;
	}
	/**
	 * 获取包装材料
	 * 
	 */
	public String getPackageMaterial() {
		return packageMaterial;
	}
	/**
	 * 设置包装材料
	 * 
	 */
	public void setPackageMaterial(String packageMaterial) {
		this.packageMaterial = packageMaterial;
	}
	/**
	 * 获取创建部门code
	 * 
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	/**
	 * 设置创建部门code
	 * 
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	/**
	 * 获取创建部门
	 * 
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	/**
	 * 设置创建部门
	 * 
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	/**
	 * 获取创建人code
	 * 
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * 设置创建人code
	 * 
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * 获取创建人名称
	 * 
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * 设置创建人名称
	 * 
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * 获取创建时间
	 * 
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间
	 * 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取修改人code
	 * 
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * 设置修改人code
	 * 
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
	 * 获取修改人名称
	 * 
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 * 设置修改人名称
	 * 
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	/**
	 * 获取修改时间
	 * 
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置修改时间
	 * 
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取 审核状态 auditStatus
	 * @return  auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}
	/**
	 *设置 审核状态 auditStatus
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getAuditUserCode() {
		return auditUserCode;
	}
	public void setAuditUserCode(String auditUserCode) {
		this.auditUserCode = auditUserCode;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getBackAuditName() {
		return backAuditName;
	}
	public void setBackAuditName(String backAuditName) {
		this.backAuditName = backAuditName;
	}
	public String getBackAuditCode() {
		return backAuditCode;
	}
	public void setBackAuditCode(String backAuditCode) {
		this.backAuditCode = backAuditCode;
	}
	public Date getBackAuditTime() {
		return backAuditTime;
	}
	public void setBackAuditTime(Date backAuditTime) {
		this.backAuditTime = backAuditTime;
	}

	
	
	
}