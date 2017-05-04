/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/StorageJobDto.java
 * 
 * FILE NAME        	: StorageJobDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 仓储费计算Dto
 * @author ibm-wangfei
 * @date Feb 28, 2013 8:00:15 PM
 */
public class StorageJobDto implements Serializable{

	private static final long serialVersionUID = 4827641224140109304L;


	/**
	 * 默认库存超过计算天数
	 */
	private Integer warehouseFreeSafeDataNum;
	
	/**
	 * 仓储费计算最大日期
	 */
	private Integer maxWarehouseFreeSafeData;
	
	/**
	 *  超过日期的计算金额
	 */
	private BigDecimal storageChargeUnit;
	
	/**
	 *  最大日仓储费金额
	 */
	private BigDecimal storageChangeMax;
	
	/**
	 *  最小日仓储费金额
	 */
	private BigDecimal storageChangeMin;
	
	/**
	 * 是否初始化仓储费
	 */
	private String isStorageChargeInit;
	
	/**
	 * 超过X天，算仓储异常
	 */
	private Integer warehouseTimeoutData;

	/**
	 * 获取 默认库存超过计算天数.
	 *
	 * @return the 默认库存超过计算天数
	 */
	public Integer getWarehouseFreeSafeDataNum() {
		return warehouseFreeSafeDataNum;
	}

	/**
	 * 设置 默认库存超过计算天数.
	 *
	 * @param warehouseFreeSafeDataNum the new 默认库存超过计算天数
	 */
	public void setWarehouseFreeSafeDataNum(Integer warehouseFreeSafeDataNum) {
		this.warehouseFreeSafeDataNum = warehouseFreeSafeDataNum;
	}

	/**
	 * 获取 仓储费计算最大日期.
	 *
	 * @return the 仓储费计算最大日期
	 */
	public Integer getMaxWarehouseFreeSafeData() {
		return maxWarehouseFreeSafeData;
	}

	/**
	 * 设置 仓储费计算最大日期.
	 *
	 * @param maxWarehouseFreeSafeData the new 仓储费计算最大日期
	 */
	public void setMaxWarehouseFreeSafeData(Integer maxWarehouseFreeSafeData) {
		this.maxWarehouseFreeSafeData = maxWarehouseFreeSafeData;
	}

	/**
	 * 获取 超过日期的计算金额.
	 *
	 * @return the 超过日期的计算金额
	 */
	public BigDecimal getStorageChargeUnit() {
		return storageChargeUnit;
	}

	/**
	 * 设置 超过日期的计算金额.
	 *
	 * @param storageChargeUnit the new 超过日期的计算金额
	 */
	public void setStorageChargeUnit(BigDecimal storageChargeUnit) {
		this.storageChargeUnit = storageChargeUnit;
	}

	/**
	 * 获取 最大日仓储费金额.
	 *
	 * @return the 最大日仓储费金额
	 */
	public BigDecimal getStorageChangeMax() {
		return storageChangeMax;
	}

	/**
	 * 设置 最大日仓储费金额.
	 *
	 * @param storageChangeMax the new 最大日仓储费金额
	 */
	public void setStorageChangeMax(BigDecimal storageChangeMax) {
		this.storageChangeMax = storageChangeMax;
	}

	/**
	 * 获取 最小日仓储费金额.
	 *
	 * @return the 最小日仓储费金额
	 */
	public BigDecimal getStorageChangeMin() {
		return storageChangeMin;
	}

	/**
	 * 设置 最小日仓储费金额.
	 *
	 * @param storageChangeMin the new 最小日仓储费金额
	 */
	public void setStorageChangeMin(BigDecimal storageChangeMin) {
		this.storageChangeMin = storageChangeMin;
	}

	/**
	 * 获取 是否初始化仓储费.
	 *
	 * @return the 是否初始化仓储费
	 */
	public String getIsStorageChargeInit() {
		return isStorageChargeInit;
	}

	/**
	 * 设置 是否初始化仓储费.
	 *
	 * @param isStorageChargeInit the new 是否初始化仓储费
	 */
	public void setIsStorageChargeInit(String isStorageChargeInit) {
		this.isStorageChargeInit = isStorageChargeInit;
	}

	/**
	 * 获取 超过X天，算仓储异常.
	 *
	 * @return the 超过X天，算仓储异常
	 */
	public Integer getWarehouseTimeoutData() {
		return warehouseTimeoutData;
	}

	/**
	 * 设置 超过X天，算仓储异常.
	 *
	 * @param warehouseTimeoutData the new 超过X天，算仓储异常
	 */
	public void setWarehouseTimeoutData(Integer warehouseTimeoutData) {
		this.warehouseTimeoutData = warehouseTimeoutData;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}