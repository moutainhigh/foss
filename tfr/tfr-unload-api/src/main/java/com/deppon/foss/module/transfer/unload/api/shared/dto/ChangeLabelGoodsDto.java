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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/ChangeLabelGoodsDto.java
 *  
 *  FILE NAME          :ChangeLabelGoodsDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;

/**
 * 重贴标签货物Dto
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:55:38
 */
public class ChangeLabelGoodsDto extends ChangeLabelGoodsEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3020144758851703120L;
	
	/**开始日期**/
	private String beginDate;

	/**结束日期**/
	private String endDate;
	
	/**是否打印带"德邦物流"的标签**/
	private String isPrintLogo;
	/**库存状态**/
	private String stockStatus;

	/**库区编号**/
	private String goodsAreaCode;
	
	/**
	 * 获取是否打印带"德邦物流"的标签
	 * @return "Y"或"N"
	 */
	public String getIsPrintLogo() {
		return isPrintLogo;
	}
	/**
	 * 设置是否打印带"德邦物流"的标签
	 * @return "Y"或"N"
	 */
	public void setIsPrintLogo(String isPrintLogo) {
		this.isPrintLogo = isPrintLogo;
	}

	/**
	 * 获取 开始日期*.
	 *
	 * @return the 开始日期*
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置 开始日期*.
	 *
	 * @param beginDate the new 开始日期*
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 结束日期*.
	 *
	 * @return the 结束日期*
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置 结束日期*.
	 *
	 * @param endDate the new 结束日期*
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取 库存状态*.
	 *
	 * @return the 库存状态*
	 */
	public String getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * 设置 库存状态*.
	 *
	 * @param stockStatus the new 库存状态*
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	
	/**
	 * 获取 库区编号*.
	 *
	 * @return the 库区编号*
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 库区编号*.
	 *
	 * @param goodsAreaCode the new 库区编号*
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
}