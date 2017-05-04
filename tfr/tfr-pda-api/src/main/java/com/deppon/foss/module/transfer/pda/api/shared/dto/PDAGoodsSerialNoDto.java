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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/PDAGoodsSerialNoDto.java
 *  
 *  FILE NAME          :PDAGoodsSerialNoDto.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: PDAGoodsSerialNoDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;

/**
 * 流水号明细
 * @author dp-duyi
 * @date 2012-12-27 下午4:35:00
 */
public class PDAGoodsSerialNoDto  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3984948431861830197L;
	/**库区编码:装车时使用，卸车时为空*/
	private String stockAreaCode;
	/**是否未打包装*/
	private String isUnPacking;
	/**流水号*/
	private String serialNo;
	/**是否有代办事项*/
	private String isToDoList;
	/**货物位置*/
	private String goodsPosition;
	
	public String getGoodsPosition() {
		return goodsPosition;
	}

	public void setGoodsPosition(String goodsPosition) {
		this.goodsPosition = goodsPosition;
	}

	public String getIsToDoList() {
		return isToDoList;
	}

	public void setIsToDoList(String isToDoList) {
		this.isToDoList = isToDoList;
	}

	/**
	 * Gets the 库区编码:装车时使用，卸车时为空.
	 *
	 * @return the 库区编码:装车时使用，卸车时为空
	 */
	public String getStockAreaCode() {
		return stockAreaCode;
	}
	
	/**
	 * Sets the 库区编码:装车时使用，卸车时为空.
	 *
	 * @param stockAreaCode the new 库区编码:装车时使用，卸车时为空
	 */
	public void setStockAreaCode(String stockAreaCode) {
		this.stockAreaCode = stockAreaCode;
	}
	
	/**
	 * Gets the 是否未打包装.
	 *
	 * @return the 是否未打包装
	 */
	public String getIsUnPacking() {
		return isUnPacking;
	}
	
	/**
	 * Sets the 是否未打包装.
	 *
	 * @param isUnPacking the new 是否未打包装
	 */
	public void setIsUnPacking(String isUnPacking) {
		this.isUnPacking = isUnPacking;
	}
	
	/**
	 * Gets the 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * Sets the 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	} 
}