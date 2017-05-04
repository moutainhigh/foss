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


import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PDA端扫描生成包装金额查询条件
 * @author ZhangXu
 *
 * 2014-5-7
 */

public class QueryPdaPcPackConditionEntity extends BaseEntity {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * 运单号
	 * 
	 */
	private String waybillNo;

	/**
	 *  包装部门
	 * 
	 */
	private String packedDept;
	/**
	 *  包装部门code
	 * 
	 */
	private String packedDeptCode;
	/**
	 * 开单部门Code
	 * 
	 */	
	private String billOrgCode;
	/**
	 * 开单部门
	 */
	private String billOrgName;
	/**
	 *  包装供应商
	 * 
	 */
	private String packageSupplierName;
	/**
	 *  包装供应商Code
	 * 
	 */
	private String packageSupplierCode;
	/**
	 *  包装结束时间   
	 * 
	 */
	private Date packedEndDate;
	/**
	 * 
	 * 包装开始时间
	 */
	private Date packedBeginDate;
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
	 * 获取包装部门packedDept
	 * 
	 */
	public String getPackedDept() {
		return packedDept;
	}
	/**
	 *设置 包装部门 packedDept
	 */
	public void setPackedDept(String packedDept) {
		this.packedDept = packedDept;
	}
	/**
	 * 获取 包装部门 Code packedDeptCode
	 * @return  packedDeptCode
	 */
	public String getPackedDeptCode() {
		return packedDeptCode;
	}
	/**
	 *设置 包装部门 Code  packedDeptCode
	 */
	public void setPackedDeptCode(String packedDeptCode) {
		this.packedDeptCode = packedDeptCode;
	}
	/**
	 * 获取开单部门Code   billOrgCode
	 * @return  billOrgCode
	 */
	public String getBillOrgCode() {
		return billOrgCode;
	}
	/**
	 *设置开单部门Code  billOrgCode
	 */
	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}
	
	
	/**
	 * 获取开单部门billOrgName
	 * @return  billOrgName
	 */
	public String getBillOrgName() {
		return billOrgName;
	}
	/**
	 *设置开单部门billOrgName
	 */
	public void setBillOrgName(String billOrgName) {
		this.billOrgName = billOrgName;
	}
	/**
	 * 获取包装供应商Code 
	 */
	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}
	/**
	 *设置 包装供应商Code
	 */
	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}
	/**
	 * 获取 包装供应商
	 * 
	 */
	public String getPackageSupplierName() {
		return packageSupplierName;
	}
	/**
	 * 设置 包装供应商
	 * 
	 */
	public void setPackageSupplierName(String packageSupplierName) {
		this.packageSupplierName = packageSupplierName;
	}
	/**
	 * 获取 包装结束时间   
	 * 
	 */
	public Date getPackedEndDate() {
		return packedEndDate;
	}
	/**
	 * 设置 包装结束时间   
	 * 
	 */
	public void setPackedEndDate(Date packedEndDate) {
		this.packedEndDate = packedEndDate;
	}
	/**
	 * 获取 包开始时间   
	 * 
	 */
	public Date getPackedBeginDate() {
		return packedBeginDate;
	}
	/**
	 * 设置 包装开始时间   
	 * 
	 */
	public void setPackedBeginDate(Date packedBeginDate) {
		this.packedBeginDate = packedBeginDate;
	}
	
	
}