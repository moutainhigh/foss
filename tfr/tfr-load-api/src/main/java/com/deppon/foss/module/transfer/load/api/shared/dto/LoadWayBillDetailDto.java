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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoadWayBillDetailDto.java
 *  
 *  FILE NAME          :LoadWayBillDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;

/**
 * 装车运单明细
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:36:47
 */
public class LoadWayBillDetailDto extends LoadWaybillDetailEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 96817022679631049L;
	
	/**库存重量**/
	private BigDecimal stockWeight;		

	/**库存体积**/
	private BigDecimal stockVolume;
	
	/**出发部门名称**/
	private String origOrgName;
	
	/**流水号**/
	private String serialNo;
	
	/**操作时间**/
	private Date optTime;
	
	private String cargoType;
	
	private String cargoNo;
	
	/**
	 * 获取 库存重量*.
	 *
	 * @return the 库存重量*
	 */
	public BigDecimal getStockWeight() {
		return stockWeight;
	}
	
	/**
	 * 设置 库存重量*.
	 *
	 * @param stockWeight the new 库存重量*
	 */
	public void setStockWeight(BigDecimal stockWeight) {
		this.stockWeight = stockWeight;
	}
	
	/**
	 * 获取 库存体积*.
	 *
	 * @return the 库存体积*
	 */
	public BigDecimal getStockVolume() {
		return stockVolume;
	}
	
	/**
	 * 设置 库存体积*.
	 *
	 * @param stockVolume the new 库存体积*
	 */
	public void setStockVolume(BigDecimal stockVolume) {
		this.stockVolume = stockVolume;
	}

	/**
	 * 获取 出发部门名称*.
	 *
	 * @return the 出发部门名称*
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 出发部门名称*.
	 *
	 * @param origOrgName the new 出发部门名称*
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**   
	 * serialNo   
	 *   
	 * @return  the serialNo   
	 */
	
	public String getSerialNo() {
		return serialNo;
	}

	/**   
	 * @param serialNo the serialNo to set
	 * Date:2013-6-24下午7:49:15
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**   
	 * optTime   
	 *   
	 * @return  the optTime   
	 */
	
	public Date getOptTime() {
		return optTime;
	}

	/**   
	 * @param optTime the optTime to set
	 * Date:2013-6-24下午7:49:15
	 */
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	
	
	
}