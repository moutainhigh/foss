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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadGoodsDetailDto.java
 *  
 *  FILE NAME          :LoadGoodsDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
 package com.deppon.foss.module.transfer.pda.api.shared.dto;


/**
 * 返回给PDA装车任务列表Dto
 * @author dp-duyi
 * @date 2012-11-19 上午9:14:56
 */
public class LoadGoodsDetailDto extends PDAGoodsDetailDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1831453138530519134L;
	
	/**是否合车*/
	private String beJoinCar;
	/**库存件数*/
	private int stockQty;
	/**贵重物品货区编码*/
	private String valueGoodsAreaCode;
	/**包装货区编码*/
	private String packGoodsAreaCode;
	/**运输性质编码*/
	private String transportTypeCode;
	public String getValueGoodsAreaCode() {
		return valueGoodsAreaCode;
	}

	public void setValueGoodsAreaCode(String valueGoodsAreaCode) {
		this.valueGoodsAreaCode = valueGoodsAreaCode;
	}

	public String getPackGoodsAreaCode() {
		return packGoodsAreaCode;
	}

	public void setPackGoodsAreaCode(String packGoodsAreaCode) {
		this.packGoodsAreaCode = packGoodsAreaCode;
	}
	public String getTransportTypeCode() {
		return transportTypeCode;
	}

	public void setTransportTypeCode(String transportTypeCode) {
		this.transportTypeCode = transportTypeCode;
	}

	/**
	 * Gets the 库存件数.
	 *
	 * @return the 库存件数
	 */
	public int getStockQty() {
		return stockQty;
	}

	/**
	 * Sets the 库存件数.
	 *
	 * @param stockQty the new 库存件数
	 */
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	/**
	 * Gets the 是否合车.
	 *
	 * @return the 是否合车
	 */
	public String getBeJoinCar() {
		return beJoinCar;
	}
	
	/**
	 * Sets the 是否合车.
	 *
	 * @param beJoinCar the new 是否合车
	 */
	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}

	@Override
	public String toString() {
		return "LoadGoodsDetailDto [beJoinCar=" + beJoinCar + ", stockQty=" + stockQty + ", valueGoodsAreaCode="
				+ valueGoodsAreaCode + ", packGoodsAreaCode=" + packGoodsAreaCode + ", transportTypeCode="
				+ transportTypeCode + ", getValueGoodsAreaCode()=" + getValueGoodsAreaCode()
				+ ", getPackGoodsAreaCode()=" + getPackGoodsAreaCode() + ", getTransportTypeCode()="
				+ getTransportTypeCode() + ", getStockQty()=" + getStockQty() + ", getBeJoinCar()=" + getBeJoinCar()
				+ ", getStationNumber()=" + getStationNumber() + ", getAdminiRegions()=" + getAdminiRegions()
				+ ", getOperateQty()=" + getOperateQty() + ", getStockAreaName()=" + getStockAreaName()
				+ ", getStockAreaCode()=" + getStockAreaCode() + ", getStockAreaType()=" + getStockAreaType()
				+ ", getTaskNo()=" + getTaskNo() + ", getWayBillNo()=" + getWayBillNo() + ", getSerialNos()="
				+ getSerialNos() + ", getWayBillQty()=" + getWayBillQty() + ", getWeight()=" + getWeight()
				+ ", getVolume()=" + getVolume() + ", getGoodsName()=" + getGoodsName() + ", getStockTime()="
				+ getStockTime() + ", getTransportType()=" + getTransportType() + ", getReceiveOrgCode()="
				+ getReceiveOrgCode() + ", getReceiveOrgName()=" + getReceiveOrgName() + ", getReachOrgCode()="
				+ getReachOrgCode() + ", getReachOrgName()=" + getReachOrgName() + ", getPacking()=" + getPacking()
				+ ", getBePriorityGoods()=" + getBePriorityGoods() + ", getIsValue()=" + getIsValue() + ", getNotes()="
				+ getNotes() + ", getReceiveCustDistName()=" + getReceiveCustDistName() + ", getPackageRemark()="
				+ getPackageRemark() + ", getBeEWaybill()=" + getBeEWaybill() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
