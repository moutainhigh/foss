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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/UnloadBillDetailEntity.java
 *  
 *  FILE NAME          :UnloadBillDetailEntity.java
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

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: UnloadBillDetailEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车任务单据明细实体
 * @date: 2012-12-13 上午10:06:02
 * 
 */
public class UnloadBillDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -5798129780662092967L;
	
	//卸车任务ID
	private String unloadTaskId;
	//单据编号
	private String billNo;
	//车牌号
	private String vehicleNo;
	//单据类型
	private String billType;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volume;
	//票数
	private BigDecimal waybillTotal;
	//件数
	private BigDecimal pieces;
	//272681 商务专递交接部门
	private String orgCode;
	//272681 商务专递到达部门
	private String expressArriveCode;
	//件号
	private String cargoNo;
	//件类型
	private String cargoType;
	//区分零担快递标识 express为快递 lingdan为零担
	private String expressOrLingDan;
	
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getWaybillTotal() {
		return waybillTotal;
	}
	public void setWaybillTotal(BigDecimal waybillTotal) {
		this.waybillTotal = waybillTotal;
	}
	public BigDecimal getPieces() {
		return pieces;
	}
	public void setPieces(BigDecimal pieces) {
		this.pieces = pieces;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getExpressArriveCode() {
		return expressArriveCode;
	}
	public void setExpressArriveCode(String expressArriveCode) {
		this.expressArriveCode = expressArriveCode;
	}
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	public String getExpressOrLingDan() {
		return expressOrLingDan;
	}
	public void setExpressOrLingDan(String expressOrLingDan) {
		this.expressOrLingDan = expressOrLingDan;
	}
	
}