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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/domain/TogetherTruckStockEntity.java
 *  
 *  FILE NAME          :TogetherTruckStockEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 记录合车信息
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:46:43
 */
public class TogetherTruckStockEntity extends BaseEntity{

	private static final long serialVersionUID = -3980482701105069034L;
	
	/** 运单号*/
	private String waybillNO;
	/** 流水号*/
	private String serialNO;
	/** 新货区*/
	private String newGoodsAreaCode;
	/** 原货区*/
	private String origGoodsAreaCode;
	/** 部门*/
	private String orgCode;
	
	
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNO() {
		return waybillNO;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNO the new 运单号
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNO() {
		return serialNO;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNO the new 流水号
	 */
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	
	/**
	 * 获取 新货区.
	 *
	 * @return the 新货区
	 */
	public String getNewGoodsAreaCode() {
		return newGoodsAreaCode;
	}
	
	/**
	 * 设置 新货区.
	 *
	 * @param newGoodsAreaCode the new 新货区
	 */
	public void setNewGoodsAreaCode(String newGoodsAreaCode) {
		this.newGoodsAreaCode = newGoodsAreaCode;
	}
	
	/**
	 * 获取 原货区.
	 *
	 * @return the 原货区
	 */
	public String getOrigGoodsAreaCode() {
		return origGoodsAreaCode;
	}
	
	/**
	 * 设置 原货区.
	 *
	 * @param origGoodsAreaCode the new 原货区
	 */
	public void setOrigGoodsAreaCode(String origGoodsAreaCode) {
		this.origGoodsAreaCode = origGoodsAreaCode;
	}
	
	/**
	 * 获取 部门.
	 *
	 * @return the 部门
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门.
	 *
	 * @param orgCode the new 部门
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
}