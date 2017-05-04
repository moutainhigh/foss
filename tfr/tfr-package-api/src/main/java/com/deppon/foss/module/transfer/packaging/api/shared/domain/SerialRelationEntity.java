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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/SerialRelationEntity.java
 *  
 *  FILE NAME          :SerialRelationEntity.java
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
 * FILE    NAME: NewSerialEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 流水号关系类
 * @author 046130-foss-xuduowei
 * @date 2012-10-18 下午5:26:46
 */
public class SerialRelationEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5871034096681558930L;
	
	/**
	 * 
	 */
	private String waybillNo;
	/**
	 * 原流水号
	 */
	private String oldSerialNo;
	/**
	 * 是否已包装
	 */
	private String isPacked;
	/**
	 * 是否选中
	 */
	private String tempSerialNo;
	/**
	 * 新流水号
	 */
	private String newSerialNo;
	/**
	 * 包装id
	 */
	private String packedId;
	/**
	 * 实现走货路径接口时变量集合的标志位
	 */
	private String flag;
	
	/**
	 * 包装类型
	 */
	private String packageType;
	
	/**
	 * 实际包装ID
	 */
	private String actualPackageId;
	
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 包装需求明细ID
	 */
	private String packageRequestDetailId;

    /**
     * 包装需求明细ID
     * @return
     */
    public String getPackageRequestDetailId() {
        return packageRequestDetailId;
    }

    /**
     * 包装需求明细ID
     * @param packageRequestDetailId
     */
    public void setPackageRequestDetailId(String packageRequestDetailId) {
        this.packageRequestDetailId = packageRequestDetailId;
    }

    /**
	 * 获取 新流水号.
	 *
	 * @return the 新流水号
	 */
	public String getNewSerialNo() {
		return newSerialNo;
	}
	
	/**
	 * 设置 新流水号.
	 *
	 * @param newSerialNo the new 新流水号
	 */
	public void setNewSerialNo(String newSerialNo) {
		this.newSerialNo = newSerialNo;
	}
	
	/**
	 * 获取 原流水号.
	 *
	 * @return the 原流水号
	 */
	public String getOldSerialNo() {
		return oldSerialNo;
	}
	
	/**
	 * 设置 原流水号.
	 *
	 * @param oldSerialNo the new 原流水号
	 */
	public void setOldSerialNo(String oldSerialNo) {
		this.oldSerialNo = oldSerialNo;
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
	 * 获取 是否选中.
	 *
	 * @return the 是否选中
	 */
	public String getTempSerialNo() {
		return tempSerialNo;
	}
	
	/**
	 * 设置 是否选中.
	 *
	 * @param tempSerialNo the new 是否选中
	 */
	public void setTempSerialNo(String tempSerialNo) {
		this.tempSerialNo = tempSerialNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 
	 *
	 * @param waybillNo 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 包装id.
	 *
	 * @return the 包装id
	 */
	public String getPackedId() {
		return packedId;
	}
	
	/**
	 * 设置 包装id.
	 *
	 * @param packedId the new 包装id
	 */
	public void setPackedId(String packedId) {
		this.packedId = packedId;
	}
	
	/**
	 * 获取 实现走货路径接口时变量集合的标志位.
	 *
	 * @return the 实现走货路径接口时变量集合的标志位
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * 设置 实现走货路径接口时变量集合的标志位.
	 *
	 * @param flag the new 实现走货路径接口时变量集合的标志位
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getActualPackageId() {
		return actualPackageId;
	}

	public void setActualPackageId(String actualPackageId) {
		this.actualPackageId = actualPackageId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
}