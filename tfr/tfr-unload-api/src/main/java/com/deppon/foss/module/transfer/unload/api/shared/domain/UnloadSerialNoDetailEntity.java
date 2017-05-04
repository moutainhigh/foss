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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/UnloadSerialNoDetailEntity.java
 *  
 *  FILE NAME          :UnloadSerialNoDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: UnloadSerialNoDetailEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车任务流水号明细实体
 * @date: 2012-12-13 上午10:01:34
 * 
 */
public class UnloadSerialNoDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 7877156389933658185L;
	
	//卸车运单明细id
	private String unloadWaybillDetailId;
	//扫描状态
	private String scanStatus;
	//货物状态
	private String goodsStatus;
	//操作时间
	private Date optTime;
	//设备编号
	private String deviceNo;
	//设备ID
	private String deviceId;
	//流水号
	private String serialNo;
	//任务建立时间
	private Date taskCreateTime;
	//建立任务部门编号
	private String createOrgCode;
	
	public String getUnloadWaybillDetailId() {
		return unloadWaybillDetailId;
	}
	public void setUnloadWaybillDetailId(String unloadWaybillDetailId) {
		this.unloadWaybillDetailId = unloadWaybillDetailId;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
}