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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirWaybillSerialNoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 航空正单流水明细
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午3:48:13
 */
public class AirWaybillSerialNoEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2282731757656457196L;
	
	/**
	 * 航空正单明细ID
	 */
	private String airWaybillDetailId;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 生成流水号时间
	 */
	private Date createTime;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 总件数
	 */
	private Integer goodsQty;
	
    /**
     * 用于标记不可反选的字段
     */
    private boolean undeselect;
    
    /**
     * 用于标记默认选中的字段
     */
    private boolean defaultselect;
	
	/**
	 * 判断是否 用于标记默认选中的字段.
	 *
	 * @return the 用于标记默认选中的字段
	 */
	public boolean isDefaultselect() {
		return defaultselect;
	}

	/**
	 * 设置 用于标记默认选中的字段.
	 *
	 * @param defaultselect the new 用于标记默认选中的字段
	 */
	public void setDefaultselect(boolean defaultselect) {
		this.defaultselect = defaultselect;
	}

	/**
	 * 判断是否 用于标记不可反选的字段.
	 *
	 * @return the 用于标记不可反选的字段
	 */
	public boolean isUndeselect() {
		return undeselect;
	}

	/**
	 * 设置 用于标记不可反选的字段.
	 *
	 * @param undeselect the new 用于标记不可反选的字段
	 */
	public void setUndeselect(boolean undeselect) {
		this.undeselect = undeselect;
	}

	/**
	 * 流水号状态
	 */
	private String stockStatus;
	
	/**
	 * 获取 航空正单明细ID.
	 *
	 * @return the 航空正单明细ID
	 */
	public String getAirWaybillDetailId() {
		return airWaybillDetailId;
	}
	
	/**
	 * 设置 航空正单明细ID.
	 *
	 * @param airWaybillDetailId the new 航空正单明细ID
	 */
	public void setAirWaybillDetailId(String airWaybillDetailId) {
		this.airWaybillDetailId = airWaybillDetailId;
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
	 * 获取 生成流水号时间.
	 *
	 * @return the 生成流水号时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 生成流水号时间.
	 *
	 * @param createTime the new 生成流水号时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	/**
	 * 设置 总件数.
	 *
	 * @param goodsQty the new 总件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * 获取 流水号状态.
	 *
	 * @return the 流水号状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * 设置 流水号状态.
	 *
	 * @param stockStatus the new 流水号状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
}