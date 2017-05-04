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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/PackagingRequireDetailsEntity.java
 *  
 *  FILE NAME          :PackagingRequireDetailsEntity.java
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
 * FILE    NAME: PackagingRequireDetailsEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 接送货开单的需要包装的流水号明细
 * @author 046130-foss-xuduowei
 * @date 2012-11-5 下午3:07:10
 */
public class PackagingRequireDetailsEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1494954593927083341L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 关联ID
	 */
	private String requireId;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 开单时间
	 */
	private Date waybillCreateDate;
	/**
	 * 是否已包装
	 */
	private String isPacked;
	
	/**
	 * 包装类型
	 */
	private String packageType;
	/**
	 * 实际包装ID
	 */
	private String actualPackageId;

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
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	/**
	 * 获取 关联ID.
	 *
	 * @return the 关联ID
	 */
	public String getRequireId() {
		return requireId;
	}

	/**
	 * 设置 关联ID.
	 *
	 * @param requireId the new 关联ID
	 */
	public void setRequireId(String requireId) {
		this.requireId = requireId;
	}

	/**
	 * 包装类型
	 * @return
	 */
	public String getPackageType() {
		return packageType;
	}

	/**
	 * 包装类型
	 * @param packageType
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getActualPackageId() {
		return actualPackageId;
	}

	public void setActualPackageId(String actualPackageId) {
		this.actualPackageId = actualPackageId;
	}
	
}