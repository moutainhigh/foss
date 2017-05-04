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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/PackagingRequireEntity.java
 *  
 *  FILE NAME          :PackagingRequireEntity.java
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
 * FILE    NAME: PackagingRequire.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 包装需求类，用于接送货开单使用
 * @author 046130-foss-xuduowei
 * @date 2012-10-26 上午10:58:45
 */
public class PackagingRequireEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6218063192183336122L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 开单部门
	 */
	private String waybillCreateDept;
	/**
	 * 开单部门code
	 */
	private String waybillCreateDeptCode;
	/**
	 * 开单件数
	 */
	private int waybillNumber;
	/**
	 * 开单体积
	 */
	private BigDecimal waybillVolume;
	/**
	 * 开单时间
	 */
	private Date waybillCreateDate;
	/**
	 * 运输性质
	 */
	private String productType;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 需要包装体积
	 */
	private BigDecimal needPackVolume;
	/**
	 * 需要包装件数
	 */
	private int needPackNum;
	/**
	 * 代包装部门
	 */
	private String packagingDept;
	/**
	 * 代包装部门Code
	 */
	private String packagingDeptCode;
	/**
	 * 代包装要求
	 */
	private String packagingRequire;
	/**
	 * 已包装件数
	 */
	private int packedNum;
	/**
	 * 是否作废
	 */
	private String isDisable;
	/**
	 * 包装状态
	 */
	private String packedStatus;
	/**
	 * 打木架 新流水号
	 */
	private List<String> addWSerialNo;
	/**
	 * 打木托新流水号
	 */
	private List<String> addMSerialNo;
	/**
	 * 打木架 删除流水号
	 */
	private List<String> deleteWSerialNo;
	/**
	 * 打木托 删除流水号
	 */
	private List<String> deleteMSerialNo;
	
	
	
	
	public String getPackedStatus() {
		return packedStatus;
	}

	public void setPackedStatus(String packedStatus) {
		this.packedStatus = packedStatus;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getWaybillCreateDept() {
		return waybillCreateDept;
	}
	
	/**
	 * 设置 开单部门.
	 *
	 * @param waybillCreateDept the new 开单部门
	 */
	public void setWaybillCreateDept(String waybillCreateDept) {
		this.waybillCreateDept = waybillCreateDept;
	}
	
	/**
	 * 获取 开单件数.
	 *
	 * @return the 开单件数
	 */
	public int getWaybillNumber() {
		return waybillNumber;
	}
	
	/**
	 * 设置 开单件数.
	 *
	 * @param waybillNumber the new 开单件数
	 */
	public void setWaybillNumber(int waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	
	/**
	 * 获取 开单体积.
	 *
	 * @return the 开单体积
	 */
	public BigDecimal getWaybillVolume() {
		return waybillVolume;
	}
	
	/**
	 * 设置 开单体积.
	 *
	 * @param waybillVolume the new 开单体积
	 */
	public void setWaybillVolume(BigDecimal waybillVolume) {
		this.waybillVolume = waybillVolume;
	}
	
	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getWaybillCreateDate() {
		return waybillCreateDate;
	}
	
	/**
	 * 设置 开单时间.
	 *
	 * @param waybillCreateDate the new 开单时间
	 */
	public void setWaybillCreateDate(Date waybillCreateDate) {
		this.waybillCreateDate = waybillCreateDate;
	}
	
	/**
	 * 获取 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductType() {
		return productType;
	}
	
	/**
	 * 设置 运输性质.
	 *
	 * @param productType the new 运输性质
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	/**
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 需要包装体积.
	 *
	 * @return the 需要包装体积
	 */
	public BigDecimal getNeedPackVolume() {
		return needPackVolume;
	}
	
	/**
	 * 设置 需要包装体积.
	 *
	 * @param needPackVolume the new 需要包装体积
	 */
	public void setNeedPackVolume(BigDecimal needPackVolume) {
		this.needPackVolume = needPackVolume;
	}
	
	/**
	 * 获取 需要包装件数.
	 *
	 * @return the 需要包装件数
	 */
	public int getNeedPackNum() {
		return needPackNum;
	}
	
	/**
	 * 设置 需要包装件数.
	 *
	 * @param needPackNum the new 需要包装件数
	 */
	public void setNeedPackNum(int needPackNum) {
		this.needPackNum = needPackNum;
	}
	
	/**
	 * 获取 代包装部门.
	 *
	 * @return the 代包装部门
	 */
	public String getPackagingDept() {
		return packagingDept;
	}
	
	/**
	 * 设置 代包装部门.
	 *
	 * @param packagingDept the new 代包装部门
	 */
	public void setPackagingDept(String packagingDept) {
		this.packagingDept = packagingDept;
	}
	
	/**
	 * 获取 代包装要求.
	 *
	 * @return the 代包装要求
	 */
	public String getPackagingRequire() {
		return packagingRequire;
	}
	
	/**
	 * 设置 代包装要求.
	 *
	 * @param packagingRequire the new 代包装要求
	 */
	public void setPackagingRequire(String packagingRequire) {
		this.packagingRequire = packagingRequire;
	}
	
	/**
	 * 获取 已包装件数.
	 *
	 * @return the 已包装件数
	 */
	public int getPackedNum() {
		return packedNum;
	}
	
	/**
	 * 设置 已包装件数.
	 *
	 * @param packedNum the new 已包装件数
	 */
	public void setPackedNum(int packedNum) {
		this.packedNum = packedNum;
	}
	
	/**
	 * 获取 是否作废.
	 *
	 * @return the 是否作废
	 */
	public String getIsDisable() {
		return isDisable;
	}
	
	/**
	 * 设置 是否作废.
	 *
	 * @param isDisable the new 是否作废
	 */
	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}
	
	/**
	 * 获取 开单部门code.
	 *
	 * @return the 开单部门code
	 */
	public String getWaybillCreateDeptCode() {
		return waybillCreateDeptCode;
	}
	
	/**
	 * 设置 开单部门code.
	 *
	 * @param waybillCreateDeptCode the new 开单部门code
	 */
	public void setWaybillCreateDeptCode(String waybillCreateDeptCode) {
		this.waybillCreateDeptCode = waybillCreateDeptCode;
	}
	
	/**
	 * 获取 代包装部门Code.
	 *
	 * @return the 代包装部门Code
	 */
	public String getPackagingDeptCode() {
		return packagingDeptCode;
	}
	
	/**
	 * 设置 代包装部门Code.
	 *
	 * @param packagingDeptCode the new 代包装部门Code
	 */
	public void setPackagingDeptCode(String packagingDeptCode) {
		this.packagingDeptCode = packagingDeptCode;
	}

	/**
	 * 打木架新流水号
	 * @return
	 */
	public List<String> getAddWSerialNo() {
		return addWSerialNo;
	}
	/**
	 * 打木架新流水号
	 * @return
	 */
	public void setAddWSerialNo(List<String> addWSerialNo) {
		this.addWSerialNo = addWSerialNo;
	}
	
	/**
	 * 打木托新流水号
	 * @return
	 */
	public List<String> getAddMSerialNo() {
		return addMSerialNo;
	}
	/**
	 * 打木托 新流水号
	 * @return
	 */
	public void setAddMSerialNo(List<String> addMSerialNo) {
		this.addMSerialNo = addMSerialNo;
	}
	/**
	 * 打木架 删除流水号
	 * @return
	 */
	public List<String> getDeleteWSerialNo() {
		return deleteWSerialNo;
	}
	/**
	 * 打木架 删除流水号
	 * @return
	 */
	public void setDeleteWSerialNo(List<String> deleteWSerialNo) {
		this.deleteWSerialNo = deleteWSerialNo;
	}
	/**
	 * 打木托删除流水号
	 * @return
	 */
	public List<String> getDeleteMSerialNo() {
		return deleteMSerialNo;
	}

	/**
	 * 打木托 删除流水号
	 * @param deleteMSerialNo
	 */
	public void setDeleteMSerialNo(List<String> deleteMSerialNo) {
		this.deleteMSerialNo = deleteMSerialNo;
	}
	
	
	
	
	
	
}