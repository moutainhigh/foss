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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/dto/UpdateStockPreHandOverStateDto.java
 *  
 *  FILE NAME          :UpdateStockPreHandOverStateDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.util.Date;

/**
 * 运单库存信息
 */
public class WaybillStockDetailDto implements java.io.Serializable{

	private static final long serialVersionUID = -1234544517773287773L;
	
	/**运单号*/
	private String waybillNo;
	/**流水号*/
	private String serialNo;
	/**库存状态*/
	private String stockStatus;
	/**部门标杆CODE*/
	private String unifiedOrgCode;
	/**部门名称*/
	private String unifiedOrgName;
	/**出入库操作时间*/
	private Date inOutStockTime;
	/**操作人姓名*/
	private String operatorName;
	/**库存ID*/
	private String stockId;
	/**开单时间*/
	private Date createBillTime;
	
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
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * 设置 库存状态.
	 *
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	
	/**
	 * 获取 部门标杆CODE.
	 *
	 * @return the 部门标杆CODE
	 */
	public String getUnifiedOrgCode() {
		return unifiedOrgCode;
	}
	
	/**
	 * 设置 部门标杆CODE.
	 *
	 * @param unifiedOrgCode the new 部门标杆CODE
	 */
	public void setUnifiedOrgCode(String unifiedOrgCode) {
		this.unifiedOrgCode = unifiedOrgCode;
	}
	
	/**
	 * 获取 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getUnifiedOrgName() {
		return unifiedOrgName;
	}
	
	/**
	 * 设置 部门名称.
	 *
	 * @param unifiedOrgName the new 部门名称
	 */
	public void setUnifiedOrgName(String unifiedOrgName) {
		this.unifiedOrgName = unifiedOrgName;
	}
	
	/**
	 * 获取 出入库操作时间.
	 *
	 * @return the 出入库操作时间
	 */
	public Date getInOutStockTime() {
		return inOutStockTime;
	}
	
	/**
	 * 设置 出入库操作时间.
	 *
	 * @param inOutStockTime the new 出入库操作时间
	 */
	public void setInOutStockTime(Date inOutStockTime) {
		this.inOutStockTime = inOutStockTime;
	}
	
	/**
	 * 获取 操作人姓名.
	 *
	 * @return the 操作人姓名
	 */
	public String getOperatorName() {
		return operatorName;
	}
	
	/**
	 * 设置 操作人姓名.
	 *
	 * @param operatorName the new 操作人姓名
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	/**
	 * 获取 库存ID.
	 *
	 * @return the 库存ID
	 */
	public String getStockId() {
		return stockId;
	}
	
	/**
	 * 设置 库存ID.
	 *
	 * @param stockId the new 库存ID
	 */
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	
	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getCreateBillTime() {
		return createBillTime;
	}
	
	/**
	 * 设置 开单时间.
	 *
	 * @param createBillTime the new 开单时间
	 */
	public void setCreateBillTime(Date createBillTime) {
		this.createBillTime = createBillTime;
	}
	
	
}
