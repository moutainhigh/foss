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
 *  FILE PATH          :/AirCargoVolumeDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeQueryEntity;

/**
 *  空运货量统计Dto
 * 
 * @author dp-liming
 * @date 2012-10-18 下午17:39:30
 */
public class AirCargoVolumeDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7764790444913565859L;

	/**
	 * 空运货量明细
	 */
	private AirCargoVolumeQueryEntity airCargoVolume;
	
	/**
	 * 起始时间
	 */
	private String beginCreateTime;
	/**
	 * 截止时间
	 */
	private String  endCreateTime;
	
	/**
	 * 配载开始时间
	 */
	private String handoverTimeBegin;
	
	/**
	 * 配载结束时间
	 */
	private String handoverTimeEnd;
	
	/**
	 * 空运总调
	 */
	private String assembleOrgName;
		
	/** 
	 *  开单
	 */
	private String transportpathStatusBilling;
	
	/** 
	 *   已交接 
	 */
	private String  transportpathStatusHandover ;
			
	/**
	 *   库存中
	 */
	private String  transportpathStatusInstore;
	
	/**
	 *  已配载
	 */
	private String  isLoad;
	
	/**
	 *  库存状态list
	 */	
	private List<String> stockStatusList;
	
	/**
	 *  是否可用
	 */
	private String  active;
	
	/**
	 *  运输性质
	 */
	private String  productCode;
	
	/**
	 * 已配载且到达
	 */
	public static final String loadingAndArrive = AirfreightConstants.AIRFREIGHT_ISLOADING_AND_ARRIVE;
	
	/**
	 * 获取 配载开始时间.
	 *
	 * @return the 配载开始时间
	 */
	public String getHandoverTimeBegin() {
		return handoverTimeBegin;
	}

	/**
	 * 设置 配载开始时间.
	 *
	 * @param handoverTimeBegin the new 配载开始时间
	 */
	public void setHandoverTimeBegin(String handoverTimeBegin) {
		this.handoverTimeBegin = handoverTimeBegin;
	}

	/**
	 * 获取 配载结束时间.
	 *
	 * @return the 配载结束时间
	 */
	public String getHandoverTimeEnd() {
		return handoverTimeEnd;
	}

	/**
	 * 设置 配载结束时间.
	 *
	 * @param handoverTimeEnd the new 配载结束时间
	 */
	public void setHandoverTimeEnd(String handoverTimeEnd) {
		this.handoverTimeEnd = handoverTimeEnd;
	}

	/**
	 * 获取 空运货量明细.
	 *
	 * @return the 空运货量明细
	 */
	public AirCargoVolumeQueryEntity getAirCargoVolume() {
		return airCargoVolume;
	}

	/**
	 * 设置 空运货量明细.
	 *
	 * @param airCargoVolume the new 空运货量明细
	 */
	public void setAirCargoVolume(AirCargoVolumeQueryEntity airCargoVolume) {
		this.airCargoVolume = airCargoVolume;
	}

	
	
	/**
	 * 获取 起始时间.
	 *
	 * @return the 起始时间
	 */
	public String getBeginCreateTime() {
		return beginCreateTime;
	}

	/**
	 * 设置 起始时间.
	 *
	 * @param beginCreateTime the new 起始时间
	 */
	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	/**
	 * 获取 截止时间.
	 *
	 * @return the 截止时间
	 */
	public String getEndCreateTime() {
		return endCreateTime;
	}

	/**
	 * 设置 截止时间.
	 *
	 * @param endCreateTime the new 截止时间
	 */
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	/**
	 * 获取 空运总调.
	 *
	 * @return the 空运总调
	 */
	public String getAssembleOrgName() {
		return assembleOrgName;
	}

	/**
	 * 设置 空运总调.
	 *
	 * @param assembleOrgName the new 空运总调
	 */
	public void setAssembleOrgName(String assembleOrgName) {
		this.assembleOrgName = assembleOrgName;
	}

	/**
	 * 获取 开单.
	 *
	 * @return the 开单
	 */
	public String getTransportpathStatusBilling() {
		return transportpathStatusBilling;
	}

	/**
	 * 设置 开单.
	 *
	 * @param transportpathStatusBilling the new 开单
	 */
	public void setTransportpathStatusBilling(String transportpathStatusBilling) {
		this.transportpathStatusBilling = transportpathStatusBilling;
	}

	/**
	 * 获取 已交接.
	 *
	 * @return the 已交接
	 */
	public String getTransportpathStatusHandover() {
		return transportpathStatusHandover;
	}

	/**
	 * 设置 已交接.
	 *
	 * @param transportpathStatusHandover the new 已交接
	 */
	public void setTransportpathStatusHandover(String transportpathStatusHandover) {
		this.transportpathStatusHandover = transportpathStatusHandover;
	}

	/**
	 * 获取 库存中.
	 *
	 * @return the 库存中
	 */
	public String getTransportpathStatusInstore() {
		return transportpathStatusInstore;
	}

	/**
	 * 设置 库存中.
	 *
	 * @param transportpathStatusInstore the new 库存中
	 */
	public void setTransportpathStatusInstore(String transportpathStatusInstore) {
		this.transportpathStatusInstore = transportpathStatusInstore;
	}

	/**
	 * 获取 已配载.
	 *
	 * @return the 已配载
	 */
	public String getIsLoad() {
		return isLoad;
	}

	/**
	 * 设置 已配载.
	 *
	 * @param isLoad the new 已配载
	 */
	public void setIsLoad(String isLoad) {
		this.isLoad = isLoad;
	}

	/**
	 * 获取 库存状态list.
	 *
	 * @return the 库存状态list
	 */
	public List<String> getStockStatusList() {
		return stockStatusList;
	}

	/**
	 * 设置 库存状态list.
	 *
	 * @param stockStatusList the new 库存状态list
	 */
	public void setStockStatusList(List<String> stockStatusList) {
		this.stockStatusList = stockStatusList;
	}

	/**
	 * 获取 是否可用.
	 *
	 * @return the 是否可用
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 是否可用.
	 *
	 * @param active the new 是否可用
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	
	
	
	
}