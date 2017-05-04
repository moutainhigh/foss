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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/PlanUnloadBillDto.java
 *  
 *  FILE NAME          :PlanUnloadBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/** 
 * @className: PlanUnloadBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 新增、修改卸车任务时，查询的待卸单据信息
 * @date: 2012-12-12 下午4:30:55
 * 
 */
public class PlanUnloadBillDto implements Serializable {

	private static final long serialVersionUID = 1199175119275776785L;
	
	//车牌号
	private String vehicleNo;
	//单据编号，可能为交接单或者配载单
	private String billNo;
	//单据类型
	private String billType;
	//交接类型
	private String handOverType;
	//体积
	private BigDecimal volume;
	//重量
	private BigDecimal weight;
	//票数
	private BigDecimal waybillTotal;
	//件数
	private BigDecimal pieces;
	//2015/9/11 272681 商务专递交接部门code
	private String orgCode;
	//2015/9/11 272681 商务专递到达部门code
	private String expressArriveCode;
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * @return the handOverType
	 */
	public String getHandOverType() {
		return handOverType;
	}
	/**
	 * @param handOverType the handOverType to set
	 */
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * @return the waybillTotal
	 */
	public BigDecimal getWaybillTotal() {
		return waybillTotal;
	}
	/**
	 * @param waybillTotal the waybillTotal to set
	 */
	public void setWaybillTotal(BigDecimal waybillTotal) {
		this.waybillTotal = waybillTotal;
	}
	/**
	 * @return the pieces
	 */
	public BigDecimal getPieces() {
		return pieces;
	}
	/**
	 * @param pieces the pieces to set
	 */
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
	
}