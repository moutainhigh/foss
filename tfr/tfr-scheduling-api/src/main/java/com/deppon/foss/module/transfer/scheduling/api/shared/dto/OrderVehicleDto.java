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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/OrderVehicleDto.java
 * 
 *  FILE NAME     :OrderVehicleDto.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;

/**
 *  约车申请DTO
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:50:34
 */
public class OrderVehicleDto implements Serializable {
	
	private static final long serialVersionUID = 2503911558468096776L;

	private OrderVehicleEntity orderVehicleEntity = new OrderVehicleEntity();
	
	/** 用车时间 */
	private Date beginPredictUseTime;
	
	private Date endPredictUseTime;
	
	/** 申请时间 */
	private Date beginApplyTime;
	
	private Date endApplyTime;
	
	/** 状态 */
	private List<String> statusList;
	
	/** 单号Id list */
	private List<String> orderIdList;
	
	private List<VehicleDriverWithDto> vehicleDriverWithList;
	
	private Long totalCount = 0l;

	public List<VehicleDriverWithDto> getVehicleDriverWithList() {
		return vehicleDriverWithList;
	}

	public void setVehicleDriverWithList(List<VehicleDriverWithDto> vehicleDriverWithList) {
		this.vehicleDriverWithList = vehicleDriverWithList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获得orderVehicleEntity
	 * @return the orderVehicleEntity
	 */
	public OrderVehicleEntity getOrderVehicleEntity() {
		return orderVehicleEntity;
	}

	/**
	 * 设置orderVehicleEntity
	 * @param orderVehicleEntity the orderVehicleEntity to set
	 */
	public void setOrderVehicleEntity(OrderVehicleEntity orderVehicleEntity) {
		this.orderVehicleEntity = orderVehicleEntity;
	}

	/**
	 * 获得beginPredictUseTime
	 * @return the beginPredictUseTime
	 */
	public Date getBeginPredictUseTime() {
		return beginPredictUseTime;
	}

	/**
	 * 设置beginPredictUseTime
	 * @param beginPredictUseTime the beginPredictUseTime to set
	 */
	public void setBeginPredictUseTime(Date beginPredictUseTime) {
		this.beginPredictUseTime = beginPredictUseTime;
	}

	/**
	 * 获得endPredictUseTime
	 * @return the endPredictUseTime
	 */
	public Date getEndPredictUseTime() {
		if (beginPredictUseTime != null  && endPredictUseTime == null) {
			endPredictUseTime = new Date();
		}
		return endPredictUseTime;
	}

	/**
	 * 设置endPredictUseTime
	 * @param endPredictUseTime the endPredictUseTime to set
	 */
	public void setEndPredictUseTime(Date endPredictUseTime) {
		this.endPredictUseTime = endPredictUseTime;
	}

	/**
	 * 获得beginApplyTime
	 * @return the beginApplyTime
	 */
	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	/**
	 * 设置beginApplyTime
	 * @param beginApplyTime the beginApplyTime to set
	 */
	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	/**
	 * 获得endApplyTime
	 * @return the endApplyTime
	 */
	public Date getEndApplyTime() {
		if (beginApplyTime != null  && endApplyTime == null) {
			endApplyTime = new Date();
		}
		return endApplyTime;
	}

	/**
	 * 设置endApplyTime
	 * @param endApplyTime the endApplyTime to set
	 */
	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

	/**
	 * 获得statusList
	 * @return the statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	/**
	 * 设置statusList
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	/**
	 * 获得orderIdList
	 * @return the orderIdList
	 */
	public List<String> getOrderIdList() {
		return orderIdList;
	}

	/**
	 * 设置orderIdList
	 * @param orderIdList the orderIdList to set
	 */
	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}
}