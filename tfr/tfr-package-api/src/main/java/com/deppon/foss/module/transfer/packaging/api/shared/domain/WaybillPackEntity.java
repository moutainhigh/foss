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
 * 包装录入主信息
 * @author 046130-foss-xuduowei
 * @date 2012-10-18 下午5:21:34
 */
public class WaybillPackEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6349067022457432754L;
	
	/**
	 * 运单号
	 */
	private String wayBillNumber;

	/**
	 * 开单部门code
	 */
	private String waybillCreateDept;

	/**
	 * 开单部门名称
	 */
	private String waybillCreateDeptName;

	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 包装材料
	 */
	private String packedMate;

	/**
	 * 包装体积(手动录入的体积)
	 */
	private BigDecimal packedVolume;
	/**
	 * 开单乘以系数之后的体积(未包装体积)
	 * unPackedVolume =  unPackedVolumeCreate * 1.4
	 */
	private BigDecimal unPackedVolume;
	
	/**
	 * 开单之后的体积(未包装体积)
	 * unPackedVolume =  unPackedVolumeCreate * 1.4
	 */
	private BigDecimal unPackedVolumeCreate;

	/**
	 * 加托个数
	 */
	private Integer plusNum;

	/**
	 * 代包装要求
	 */
	private String packRequire;
	
	/**
	 * 是否PDA
	 */
	private String isPDA;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 包装部门名称
	 */
	private String orgName;

	/**
	 * 包装部门CODE
	 */
	private String orgCode;

	/**
	 * 修改人姓名
	 */
	private String createUserName;

	/**
	 * 修改人姓名
	 */
	private String modifyUserName;
	/**
	 * 乐观锁字段
	 */
	private String modifyDateOptimistic;
	
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
	
	/**
	 * 开单时间
	 */
	private Date billTime;

    /**
     * 返回需要在页面提示警告的信息
     */
    private String msg;

	/**
	 * set,get方法
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
	 * 获取 开单部门code.
	 *
	 * @return the 开单部门code
	 */
	public String getWaybillCreateDept() {
		return waybillCreateDept;
	}
	
	/**
	 * 设置 开单部门code.
	 *
	 * @param waybillCreateDept the new 开单部门code
	 */
	public void setWaybillCreateDept(String waybillCreateDept) {
		this.waybillCreateDept = waybillCreateDept;
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
	 * 获取 代包装要求.
	 *
	 * @return the 代包装要求
	 */
	public String getPackRequire() {
		return packRequire;
	}
	
	/**
	 * 设置 代包装要求.
	 *
	 * @param packRequire the new 代包装要求
	 */
	public void setPackRequire(String packRequire) {
		this.packRequire = packRequire;
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
	 * 获取 包装部门名称.
	 *
	 * @return the 包装部门名称
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * 设置 包装部门名称.
	 *
	 * @param orgName the new 包装部门名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	 * 获取 修改人姓名.
	 *
	 * @return the 修改人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 修改人姓名.
	 *
	 * @param createUserName the new 修改人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 修改人姓名.
	 *
	 * @return the 修改人姓名
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	
	/**
	 * 设置 修改人姓名.
	 *
	 * @param modifyUserName the new 修改人姓名
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
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
	 * 获取 乐观锁字段.
	 *
	 * @return the 乐观锁字段
	 */
	public String getModifyDateOptimistic() {
		return modifyDateOptimistic;
	}
	
	/**
	 * 设置 乐观锁字段.
	 *
	 * @param modifyDateOptimistic the new 乐观锁字段
	 */
	public void setModifyDateOptimistic(String modifyDateOptimistic) {
		this.modifyDateOptimistic = modifyDateOptimistic;
	}
	
	/**
	 * 获取 开单部门名称.
	 *
	 * @return the 开单部门名称
	 */
	public String getWaybillCreateDeptName() {
		return waybillCreateDeptName;
	}
	
	/**
	 * 设置 开单部门名称.
	 *
	 * @param waybillCreateDeptName the new 开单部门名称
	 */
	public void setWaybillCreateDeptName(String waybillCreateDeptName) {
		this.waybillCreateDeptName = waybillCreateDeptName;
	}

	public String getIsPDA() {
		return isPDA;
	}

	public void setIsPDA(String isPDA) {
		this.isPDA = isPDA;
	}

	/**
	 * @return the unPackedVolume
	 */
	public BigDecimal getUnPackedVolume() {
		return unPackedVolume;
	}

	/**
	 * @param unPackedVolume the unPackedVolume to set
	 */
	public void setUnPackedVolume(BigDecimal unPackedVolume) {
		this.unPackedVolume = unPackedVolume;
	}

	/**
	 * @return the unPackedVolumeCreate
	 */
	public BigDecimal getUnPackedVolumeCreate() {
		return unPackedVolumeCreate;
	}

	/**
	 * @param unPackedVolumeCreate the unPackedVolumeCreate to set
	 */
	public void setUnPackedVolumeCreate(BigDecimal unPackedVolumeCreate) {
		this.unPackedVolumeCreate = unPackedVolumeCreate;
	}

	/**
	 * 包装类型 
	 * enum 枚举类型
	 * com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing
	 * @return
	 */
	public String getPackageType() {
		return packageType;
	}

	/**
	 * 包装类型 
	 * enum 枚举类型
	 * com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing
	 * @param packageType
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	/**
	 *  包装类型(非当前包装类型)
	 * @return
	 */
	public String getUnPackageType() {
		return unPackageType;
	}
	/**
	 * 包装类型(非当前包装类型)
	 * @param unPackageType
	 */
	public void setUnPackageType(String unPackageType) {
		this.unPackageType = unPackageType;
	}
    /**
	 *  返回需要在页面提示警告的信息
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 返回需要在页面提示警告的信息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}