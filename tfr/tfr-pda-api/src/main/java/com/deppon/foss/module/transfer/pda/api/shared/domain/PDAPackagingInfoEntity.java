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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/PDAPackagingInfoEntity.java
 *  
 *  FILE NAME          :PDAPackagingInfoEntity.java
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
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * pda包装录入信息
 * @author 046130-foss-xuduowei
 * @date 2012-10-18 下午5:21:34
 */
public class PDAPackagingInfoEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 224590050725796796L;
	/**
	 * 运单号
	 */
	private String wayBillNumber;
	/**
	 * 包装材料
	 */
	private String packedMate;
	/**
	 * 包装体积
	 */
	private BigDecimal packedVolume;
	/**
	 * 加托个数
	 */
	private Integer plusNum;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * PDA编号
	 */
	private String PDACode;
	/**
	 * 包装部门CODE
	 */
	private String orgCode;
	/**
	 * 流水号集合(旧标签号)
	 */
	private List<String> serialNo;
	/**
	 * 包装人集合
	 */
	private List<String> empCode;
	/**
	 * 操作人编码
	 */
	private String operatorCode;
	
	
	/**
	 * 包装类型 
	 * enum 枚举类型
	 * com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing
	 */
	private String packageType;
	
	/**
	 * 包装类型 (不是但前包装类型)
	 * enum 枚举类型
	 * com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing
	 */
	private String unPackageType;
	
	
	/*
	 * 包装供应商编码
	 * 
	 * **/
	private String packageSupplierCode;
	
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNumber the new 运单号
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}
	
	/**
	 * 获取 包装材料.
	 *
	 * @return the 包装材料
	 */
	public String getPackedMate() {
		return packedMate;
	}
	
	/**
	 * 设置 包装材料.
	 *
	 * @param packedMate the new 包装材料
	 */
	public void setPackedMate(String packedMate) {
		this.packedMate = packedMate;
	}
	
	/**
	 * 获取 包装体积.
	 *
	 * @return the 包装体积
	 */
	public BigDecimal getPackedVolume() {
		return packedVolume;
	}
	
	/**
	 * 设置 包装体积.
	 *
	 * @param packedVolume the new 包装体积
	 */
	public void setPackedVolume(BigDecimal packedVolume) {
		this.packedVolume = packedVolume;
	}
	
	/**
	 * 获取 加托个数.
	 *
	 * @return the 加托个数
	 */
	public Integer getPlusNum() {
		return plusNum;
	}
	
	/**
	 * 设置 加托个数.
	 *
	 * @param plusNum the new 加托个数
	 */
	public void setPlusNum(Integer plusNum) {
		this.plusNum = plusNum;
	}
	
	/**
	 * 获取 备注.
	 *
	 * @return the 备注
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * 设置 备注.
	 *
	 * @param remark the new 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getPDACode() {
		return PDACode;
	}
	
	/**
	 * 
	 *
	 * @param pDACode 
	 */
	public void setPDACode(String pDACode) {
		PDACode = pDACode;
	}
	
	/**
	 * 获取 包装部门CODE.
	 *
	 * @return the 包装部门CODE
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 包装部门CODE.
	 *
	 * @param orgCode the new 包装部门CODE
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 流水号集合(旧标签号).
	 *
	 * @return the 流水号集合(旧标签号)
	 */
	public List<String> getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号集合(旧标签号).
	 *
	 * @param serialNo the new 流水号集合(旧标签号)
	 */
	public void setSerialNo(List<String> serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 包装人集合.
	 *
	 * @return the 包装人集合
	 */
	public List<String> getEmpCode() {
		return empCode;
	}
	
	/**
	 * 设置 包装人集合.
	 *
	 * @param empCode the new 包装人集合
	 */
	public void setEmpCode(List<String> empCode) {
		this.empCode = empCode;
	}
	
	/**
	 * 获取 操作人编码.
	 *
	 * @return the 操作人编码
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	
	/**
	 * 设置 操作人编码.
	 *
	 * @param operatorCode the new 操作人编码
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getUnPackageType() {
		return unPackageType;
	}

	public void setUnPackageType(String unPackageType) {
		this.unPackageType = unPackageType;
	}

	public String getPackageSupplierCode() {
		return packageSupplierCode;
	}

	public void setPackageSupplierCode(String packageSupplierCode) {
		this.packageSupplierCode = packageSupplierCode;
	}
	
	
}