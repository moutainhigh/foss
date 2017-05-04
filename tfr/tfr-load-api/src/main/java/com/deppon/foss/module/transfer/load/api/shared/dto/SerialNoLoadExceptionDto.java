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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/SerialNoLoadExceptionDto.java
 *  
 *  FILE NAME          :SerialNoLoadExceptionDto.java
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

/** 
 * @className: SerialNoLoadExceptionDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 查看交接单详情时，双击某运单，展开其下流水号列表，包含流水号、装车异常类型两字段
 * @date: 2012-10-24 下午6:09:03
 * 
 */
public class SerialNoLoadExceptionDto implements Serializable {

	private static final long serialVersionUID = -7009665360115964564L;

	//交接单号
	private String handOverBillNo;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//装车异常类型
	private String loadExceptionType;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volumn;
	//运输性质
	private String productCode;
	//是否上计泡机
	private String beScan;
	
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getLoadExceptionType() {
		return loadExceptionType;
	}
	public void setLoadExceptionType(String loadExceptionType) {
		this.loadExceptionType = loadExceptionType;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBeScan() {
		return beScan;
	}
	public void setBeScan(String beScan) {
		this.beScan = beScan;
	}
	
}