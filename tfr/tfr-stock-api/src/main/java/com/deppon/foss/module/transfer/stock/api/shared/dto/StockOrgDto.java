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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/dto/StockOrgDto.java
 *  
 *  FILE NAME          :StockOrgDto.java
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
 * 库存部门信息
 */
public class StockOrgDto implements Serializable{

	private static final long serialVersionUID = 7577373841025487515L;
	/** 部门编号*/
	private String orgCode;
	/** 部门名称*/
	private String orgName;
	/** 货区编号*/
	private String goodsAreaCode;
	/** 货区名称*/
	private String goodsAreaName;
	/** 是否是外场*/
	private String isTransferCenter;
	/** 是否是驻地派送部*/
	private String isStationDelivery;
	/** 是否是空运总调*/
	private String isAirDispatch;
	
	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * 设置 部门名称.
	 *
	 * @param orgName the new 部门名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 货区名称.
	 *
	 * @return the 货区名称
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	
	/**
	 * 设置 货区名称.
	 *
	 * @param goodsAreaName the new 货区名称
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
	/**
	 * 获取 是否是外场.
	 *
	 * @return the 是否是外场
	 */
	public String getIsTransferCenter() {
		return isTransferCenter;
	}
	
	/**
	 * 设置 是否是外场.
	 *
	 * @param isTransferCenter the new 是否是外场
	 */
	public void setIsTransferCenter(String isTransferCenter) {
		this.isTransferCenter = isTransferCenter;
	}
	
	/**
	 * 获取 是否是驻地派送部.
	 *
	 * @return the 是否是驻地派送部
	 */
	public String getIsStationDelivery() {
		return isStationDelivery;
	}
	
	/**
	 * 设置 是否是驻地派送部.
	 *
	 * @param isStationDelivery the new 是否是驻地派送部
	 */
	public void setIsStationDelivery(String isStationDelivery) {
		this.isStationDelivery = isStationDelivery;
	}

	/**
	 * 获取 是否是空运总调.
	 *
	 * @return the 是否是空运总调
	 */
	public String getIsAirDispatch() {
		return isAirDispatch;
	}

	/**
	 * 设置 是否是空运总调.
	 *
	 * @param isAirDispatch the new 是否是空运总调
	 */
	public void setIsAirDispatch(String isAirDispatch) {
		this.isAirDispatch = isAirDispatch;
	}
	
	

}