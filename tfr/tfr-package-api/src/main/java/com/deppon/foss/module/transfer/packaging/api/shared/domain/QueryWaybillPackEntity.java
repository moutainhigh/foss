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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/QueryWaybillPackEntity.java
 *  
 *  FILE NAME          :QueryWaybillPackEntity.java
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
 * FILE    NAME: QueryWaybillPackEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 *  
 * @author 046130-foss-xuduowei
 * @date 2012-10-18 下午2:25:12
 */
public class QueryWaybillPackEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1679404410114812009L;
	/**
	 * 运单号
	 */
	private String wayBillNumber;
	/**
	 * 开单部门
	 */
	private String waybillCreateDept;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 开单件数
	 */
	private String waybillNum;
	/**
	 * 包装件数
	 */
	private String packedNum;
	/**
	 * 包装材料
	 */
	private String packedMate;
	/**
	 * 包装体积
	 */
	private String packedVolume;
	/**
	 * 加托个数
	 */
	private String plusNum;
	/**
	 * 代包装要求
	 */
	private String packRequire;
	/**
	 * 标签
	 */
	private String serialNo;
	/**
	 * 是否已包装
	 */
	private String isPacked;
	/**
	 * 旧标签
	 */
	private String oldSerialNo;
	/**
	 * 新标签
	 */
	private String newSerialNo;
	/**
	 * 包装部门
	 * */
	private String packOrgCode;
	
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
	 * 获取 开单件数.
	 *
	 * @return the 开单件数
	 */
	public String getWaybillNum() {
		return waybillNum;
	}
	
	/**
	 * 设置 开单件数.
	 *
	 * @param waybillNum the new 开单件数
	 */
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	
	/**
	 * 获取 包装件数.
	 *
	 * @return the 包装件数
	 */
	public String getPackedNum() {
		return packedNum;
	}
	
	/**
	 * 设置 包装件数.
	 *
	 * @param packedNum the new 包装件数
	 */
	public void setPackedNum(String packedNum) {
		this.packedNum = packedNum;
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
	public String getPackedVolume() {
		return packedVolume;
	}
	
	/**
	 * 设置 包装体积.
	 *
	 * @param packedVolume the new 包装体积
	 */
	public void setPackedVolume(String packedVolume) {
		this.packedVolume = packedVolume;
	}
	
	/**
	 * 获取 加托个数.
	 *
	 * @return the 加托个数
	 */
	public String getPlusNum() {
		return plusNum;
	}
	
	/**
	 * 设置 加托个数.
	 *
	 * @param plusNum the new 加托个数
	 */
	public void setPlusNum(String plusNum) {
		this.plusNum = plusNum;
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
	 * 获取 标签.
	 *
	 * @return the 标签
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 标签.
	 *
	 * @param serialNo the new 标签
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 旧标签.
	 *
	 * @return the 旧标签
	 */
	public String getOldSerialNo() {
		return oldSerialNo;
	}
	
	/**
	 * 设置 旧标签.
	 *
	 * @param oldSerialNo the new 旧标签
	 */
	public void setOldSerialNo(String oldSerialNo) {
		this.oldSerialNo = oldSerialNo;
	}
	
	/**
	 * 获取 新标签.
	 *
	 * @return the 新标签
	 */
	public String getNewSerialNo() {
		return newSerialNo;
	}
	
	/**
	 * 设置 新标签.
	 *
	 * @param newSerialNo the new 新标签
	 */
	public void setNewSerialNo(String newSerialNo) {
		this.newSerialNo = newSerialNo;
	}
	
	/**
	 * 获取 是否已包装.
	 *
	 * @return the 是否已包装
	 */
	public String getIsPacked() {
		return isPacked;
	}
	
	/**
	 * 设置 是否已包装.
	 *
	 * @param isPacked the new 是否已包装
	 */
	public void setIsPacked(String isPacked) {
		this.isPacked = isPacked;
	}

	public String getPackOrgCode() {
		return packOrgCode;
	}

	public void setPackOrgCode(String packOrgCode) {
		this.packOrgCode = packOrgCode;
	}
	
}