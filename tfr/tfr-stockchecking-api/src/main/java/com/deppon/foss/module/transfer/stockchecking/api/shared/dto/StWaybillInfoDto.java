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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StWaybillInfoDto.java
 *  
 *  FILE NAME          :StWaybillInfoDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.dto;

/**
 * 查看清仓任务明细
 * @author foss-wuyingjie
 * @date 2012-11-15 下午4:26:59
 */
public class StWaybillInfoDto {
	/**运单号*/
	private String waybillNo;				
	/**产品编号*/
	private String productCode;				
	/**产品编号描述*/
	private String productCodeDesc;			
	/**库存件数*/
	private String goodsAreaNum;			
	/**扫描件数*/
	private String scanNum;					
	/**重量*/
	private Double weight;					
	/**体积*/
	private Double volume;					
	
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
	 * 获取 产品编号.
	 *
	 * @return the 产品编号
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置 产品编号.
	 *
	 * @param productCode the new 产品编号
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 获取 产品编号描述.
	 *
	 * @return the 产品编号描述
	 */
	public String getProductCodeDesc() {
		return productCodeDesc;
	}
	
	/**
	 * 设置 产品编号描述.
	 *
	 * @param productCodeDesc the new 产品编号描述
	 */
	public void setProductCodeDesc(String productCodeDesc) {
		this.productCodeDesc = productCodeDesc;
	}
	
	/**
	 * 获取 库存件数.
	 *
	 * @return the 库存件数
	 */
	public String getGoodsAreaNum() {
		return goodsAreaNum;
	}
	
	/**
	 * 设置 库存件数.
	 *
	 * @param goodsAreaNum the new 库存件数
	 */
	public void setGoodsAreaNum(String goodsAreaNum) {
		this.goodsAreaNum = goodsAreaNum;
	}
	
	/**
	 * 获取 扫描件数.
	 *
	 * @return the 扫描件数
	 */
	public String getScanNum() {
		return scanNum;
	}
	
	/**
	 * 设置 扫描件数.
	 *
	 * @param scanNum the new 扫描件数
	 */
	public void setScanNum(String scanNum) {
		this.scanNum = scanNum;
	}
	
	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public Double getWeight() {
		return weight;
	}
	
	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public Double getVolume() {
		return volume;
	}
	
	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(Double volume) {
		this.volume = volume;
	}
}