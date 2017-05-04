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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/StorageExecdateDetailEntity.java
 * 
 * FILE NAME        	: StorageExecdateDetailEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 仓储费待执行明细表
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 10:31:15 AM
 */
public class StorageExecdateDetailEntity extends BaseEntity {
	private static final long serialVersionUID = -5020196335743688604L;

	/**
	 * 执行日期ID
	 */
	private String execdateId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 执行状态
	 */
	private String execStatus;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 操作日期
	 */
	private Date operatorTime;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;

	/**
	 * 最后入库时间
	 */
	private Date lastInStockTime;

	/**
	 * 在库天数
	 */
	private Integer storageDay;

	/**
	 * 仓储费
	 */
	private BigDecimal storageCharge;

	/**
	 * 逾期天数
	 */
	private Integer overdueDay;

	/**
	 * 获取 执行日期ID.
	 * 
	 * @return the 执行日期ID
	 */
	public String getExecdateId() {
		return execdateId;
	}

	/**
	 * 设置 执行日期ID.
	 * 
	 * @param execdateId the new 执行日期ID
	 */
	public void setExecdateId(String execdateId) {
		this.execdateId = execdateId;
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
	 * 获取 执行状态.
	 * 
	 * @return the 执行状态
	 */
	public String getExecStatus() {
		return execStatus;
	}

	/**
	 * 设置 执行状态.
	 * 
	 * @param execStatus the new 执行状态
	 */
	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	/**
	 * 获取 创建日期.
	 * 
	 * @return the 创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建日期.
	 * 
	 * @param createTime the new 创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 操作日期.
	 * 
	 * @return the 操作日期
	 */
	public Date getOperatorTime() {
		return operatorTime;
	}

	/**
	 * 设置 操作日期.
	 * 
	 * @param operatorTime the new 操作日期
	 */
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	/**
	 * 获取 货物总体积.
	 * 
	 * @return the 货物总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * 设置 货物总体积.
	 * 
	 * @param goodsVolumeTotal the new 货物总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * 获取 最后入库时间.
	 * 
	 * @return the 最后入库时间
	 */
	public Date getLastInStockTime() {
		return lastInStockTime;
	}

	/**
	 * 设置 最后入库时间.
	 * 
	 * @param lastInStockTime the new 最后入库时间
	 */
	public void setLastInStockTime(Date lastInStockTime) {
		this.lastInStockTime = lastInStockTime;
	}

	/**
	 * 获取 在库天数.
	 * 
	 * @return the 在库天数
	 */
	public Integer getStorageDay() {
		return storageDay;
	}

	/**
	 * 设置 在库天数.
	 * 
	 * @param storageDay the new 在库天数
	 */
	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * 获取 仓储费.
	 * 
	 * @return the 仓储费
	 */
	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	/**
	 * 设置 仓储费.
	 * 
	 * @param storageCharge the new 仓储费
	 */
	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}

	/**
	 * 获取 逾期天数.
	 * 
	 * @return the 逾期天数
	 */
	public Integer getOverdueDay() {
		return overdueDay;
	}

	/**
	 * 设置 逾期天数.
	 * 
	 * @param overdueDay the new 逾期天数
	 */
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
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