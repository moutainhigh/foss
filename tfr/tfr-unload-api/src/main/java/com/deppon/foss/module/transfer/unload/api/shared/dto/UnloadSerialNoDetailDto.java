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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadSerialNoDetailDto.java
 *  
 *  FILE NAME          :UnloadSerialNoDetailDto.java
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
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: UnloadSerialNoDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;

/**
 * 卸车流水号明细dto
 * @author dp-duyi
 * @date 2012-12-21 上午9:29:41
 */
public class UnloadSerialNoDetailDto extends UnloadSerialNoDetailEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6885888073346095712L;
	/**wayBillNo*/
	private String wayBillNo;
	/**billNo*/
	private String billNo;
	/**handOverQty*/
	private int handOverQty;
	/**optGoodsQty*/
	private int optGoodsQty;
	/**scanGoodsQty*/
	private int scanGoodsQty;
	/**weight*/
	private double weight;
	/**volume*/
	private double volume;
	/** zwd 200968  运单生效状态 - YES NO*/
	private String wayBillStatus; 
	/** zwd 200968  运单生效状态 - YES NO*/
	public String getWayBillStatus() {
		return wayBillStatus;
	}
	/** zwd 200968  运单生效状态 - YES NO*/
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}

	/**
	 * @return the handOverQty
	 */
	public int getHandOverQty() {
		return handOverQty;
	}

	/**
	 * @param handOverQty the handOverQty to set
	 */
	public void setHandOverQty(int handOverQty) {
		this.handOverQty = handOverQty;
	}

	/**
	 * @return the optGoodsQty
	 */
	public int getOptGoodsQty() {
		return optGoodsQty;
	}

	/**
	 * @param optGoodsQty the optGoodsQty to set
	 */
	public void setOptGoodsQty(int optGoodsQty) {
		this.optGoodsQty = optGoodsQty;
	}

	/**
	 * @return the scanGoodsQty
	 */
	public int getScanGoodsQty() {
		return scanGoodsQty;
	}

	/**
	 * @param scanGoodsQty the scanGoodsQty to set
	 */
	public void setScanGoodsQty(int scanGoodsQty) {
		this.scanGoodsQty = scanGoodsQty;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	/**
	 * Gets the wayBillNo.
	 *
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * Sets the wayBillNo.
	 *
	 * @param wayBillNo the new wayBillNo
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * Gets the billNo.
	 *
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the billNo.
	 *
	 * @param billNo the new billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
}