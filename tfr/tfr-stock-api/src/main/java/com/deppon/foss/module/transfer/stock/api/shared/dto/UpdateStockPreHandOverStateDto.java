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

import java.io.Serializable;

/** 
 * @className: UpdateStockPreHandOverStateDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 更新库存预配状态时批量传入的对象
 * @date: 2012-11-28 下午6:43:12
 * 
 */
public class UpdateStockPreHandOverStateDto implements Serializable {

	private static final long serialVersionUID = -1340296877718554004L;
	/** 运单号*/
	private String waybillNo;
	/** 流水号*/
	private String serialNo;
	/** 库存部门*/
	private String deptCode;
	
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
	 * 获取 库存部门.
	 *
	 * @return the 库存部门
	 */
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * 设置 库存部门.
	 *
	 * @param deptCode the new 库存部门
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
}