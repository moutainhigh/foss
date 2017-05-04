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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/ForecastDto.java
 * 
 *  FILE NAME     :ForecastDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import com.deppon.foss.module.transfer.platform.api.shared.domain.BillingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ChangeQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.InTransitEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;

import java.io.Serializable;

/**
 *  货量预测DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class ForecastDto implements Serializable {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -2470702017293793261L;
	
	/**
	 * 货量预测开单表entity
	 */
	private BillingEntity billingEntity;
	
	/**
	 * 修改货量预测货量entity
	 */
	private ChangeQuantityEntity changeQuantityEntity;
	
	/**
	 * 货量预测entity
	 */
	private ForecastQuantityEntity forecastQuantityEntity;
	
	/**
	 * 货量预测在途中表entity
	 */
	private InTransitEntity inTransitEntity;
	
	/**
	 * 走货路径entity
	 */
	private TransportPathEntity transportPathEntity;
	
	/**
	 * 走货路径明细entity
	 */
	private PathDetailEntity pathDetailEntity;

	/**
	 * 获取 货量预测开单表entity.
	 *
	 * @return the 货量预测开单表entity
	 */
	public BillingEntity getBillingEntity() {
		return billingEntity;
	}

	/**
	 * 设置 货量预测开单表entity.
	 *
	 * @param billingEntity the new 货量预测开单表entity
	 */
	public void setBillingEntity(BillingEntity billingEntity) {
		this.billingEntity = billingEntity;
	}

	/**
	 * 获取 修改货量预测货量entity.
	 *
	 * @return the 修改货量预测货量entity
	 */
	public ChangeQuantityEntity getChangeQuantityEntity() {
		return changeQuantityEntity;
	}

	/**
	 * 设置 修改货量预测货量entity.
	 *
	 * @param changeQuantityEntity the new 修改货量预测货量entity
	 */
	public void setChangeQuantityEntity(ChangeQuantityEntity changeQuantityEntity) {
		this.changeQuantityEntity = changeQuantityEntity;
	}

	/**
	 * 获取 货量预测entity.
	 *
	 * @return the 货量预测entity
	 */
	public ForecastQuantityEntity getForecastQuantityEntity() {
		return forecastQuantityEntity;
	}

	/**
	 * 设置 货量预测entity.
	 *
	 * @param forecastQuantityEntity the new 货量预测entity
	 */
	public void setForecastQuantityEntity(
			ForecastQuantityEntity forecastQuantityEntity) {
		this.forecastQuantityEntity = forecastQuantityEntity;
	}

	/**
	 * 获取 货量预测在途中表entity.
	 *
	 * @return the 货量预测在途中表entity
	 */
	public InTransitEntity getInTransitEntity() {
		return inTransitEntity;
	}

	/**
	 * 设置 货量预测在途中表entity.
	 *
	 * @param inTransitEntity the new 货量预测在途中表entity
	 */
	public void setInTransitEntity(InTransitEntity inTransitEntity) {
		this.inTransitEntity = inTransitEntity;
	}

	/**
	 * 获取 走货路径entity.
	 *
	 * @return the 走货路径entity
	 */
	public TransportPathEntity getTransportPathEntity() {
		return transportPathEntity;
	}

	/**
	 * 设置 走货路径entity.
	 *
	 * @param transportPathEntity the new 走货路径entity
	 */
	public void setTransportPathEntity(TransportPathEntity transportPathEntity) {
		this.transportPathEntity = transportPathEntity;
	}

	/**
	 * 获取 走货路径明细entity.
	 *
	 * @return the 走货路径明细entity
	 */
	public PathDetailEntity getPathDetailEntity() {
		return pathDetailEntity;
	}

	/**
	 * 设置 走货路径明细entity.
	 *
	 * @param pathDetailEntity the new 走货路径明细entity
	 */
	public void setPathDetailEntity(PathDetailEntity pathDetailEntity) {
		this.pathDetailEntity = pathDetailEntity;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}