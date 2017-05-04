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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/dto/WaybillStockDto.java
 *  
 *  FILE NAME          :WaybillStockDto.java
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
import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;

/**
 * 封装了查询运单库存的查询条件
 * @author 097457-foss-wangqiang
 * @date 2012-10-16 下午5:04:56
 */
public class WaybillStockDto implements java.io.Serializable{
	
	private static final long serialVersionUID = -3136123338812808121L;
	/**
	 * 运单库存
	 */
	private WaybillStockEntity waybillStock;
	/**
	 * 起始时间
	 */
	private Date beginInStockTime;
	/**
	 * 截止时间
	 */
	private Date endInStockTime;
	
	/**
	* 提货方式
	*/
	private String receiveMethod;
	
	/** 产品*/
	private String productCode;
	
	/**
	 * 货区编号List
	 */
	private List<String> goodsAreaList;

	/**
	 * 获取 运单库存.
	 *
	 * @return the 运单库存
	 */
	public WaybillStockEntity getWaybillStock() {
		return waybillStock;
	}

	/**
	 * 设置 运单库存.
	 *
	 * @param waybillStock the new 运单库存
	 */
	public void setWaybillStock(WaybillStockEntity waybillStock) {
		this.waybillStock = waybillStock;
	}

	/**
	 * 获取 起始时间.
	 *
	 * @return the 起始时间
	 */
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}

	/**
	 * 设置 起始时间.
	 *
	 * @param beginInStockTime the new 起始时间
	 */
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}

	/**
	 * 获取 截止时间.
	 *
	 * @return the 截止时间
	 */
	public Date getEndInStockTime() {
		return endInStockTime;
	}

	/**
	 * 设置 截止时间.
	 *
	 * @param endInStockTime the new 截止时间
	 */
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
	}

	public List<String> getGoodsAreaList() {
		return goodsAreaList;
	}

	public void setGoodsAreaList(List<String> goodsAreaList) {
		this.goodsAreaList = goodsAreaList;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	

}