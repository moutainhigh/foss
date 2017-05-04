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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/OrderVehicleVo.java
 * 
 *  FILE NAME     :OrderVehicleVo.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

/**
 * 约车申请Vo
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午1:14:32
 */
public class OrderVehicleVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 约车申请DTO */
	private OrderVehicleDto orderVehicleDto;
	
	/** 约车申请实体 */
	private OrderVehicleEntity orderVehicleEntity;
	
	/** 约车申请集合 */
	private List<OrderVehicleEntity> orderVehicleList;
	
	private List<String> orderTypeList;
	
	private List<String> orderVehicleApplyIdList;
	
	/** 约车审核DTO  */
	private PassOrderApplyDto passOrderApplyDto; 
	
	/** 营业部与车队关系 */
	private List<SalesMotorcadeEntity> salesMotorcadeList;
	
	/** 营业部编码  */
	private String salesdeptCode;
	
	private String belongTransforCenter;
	
	/** 所选车队编码 */
	private String motorcadeCode;
	/**车牌号*/
	private String vehicleNo;
	/**短途排班表获取的司机信息*/
	private SimpleTruckScheduleDto simpleTruckScheduleDto = new SimpleTruckScheduleDto();

	private VehicleDriverWithDto vehicleDriverWithDto = new VehicleDriverWithDto();
	
	private List<VehicleDriverWithDto> vehicleDriverWithList;
	
	protected Long totalCount = 0l;
	
	/**
     * @return  the totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }
    
    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    } 
	
	public List<VehicleDriverWithDto> getVehicleDriverWithList() {
		return vehicleDriverWithList;
	}

	public void setVehicleDriverWithList(List<VehicleDriverWithDto> vehicleDriverWithList) {
		this.vehicleDriverWithList = vehicleDriverWithList;
	}

	public VehicleDriverWithDto getVehicleDriverWithDto() {
		return vehicleDriverWithDto;
	}

	public void setVehicleDriverWithDto(VehicleDriverWithDto vehicleDriverWithDto) {
		this.vehicleDriverWithDto = vehicleDriverWithDto;
	}

	/**
	 * 获得orderVehicleDto
	 * @return the orderVehicleDto
	 */
	public OrderVehicleDto getOrderVehicleDto() {
		return orderVehicleDto;
	}

	/**
	 * 设置orderVehicleDto
	 * @param orderVehicleDto the orderVehicleDto to set
	 */
	public void setOrderVehicleDto(OrderVehicleDto orderVehicleDto) {
		this.orderVehicleDto = orderVehicleDto;
	}

	/**
	 * 获得orderVehicleList
	 * @return the orderVehicleList
	 */
	public List<OrderVehicleEntity> getOrderVehicleList() {
		return orderVehicleList;
	}

	/**
	 * 设置orderVehicleList
	 * @param orderVehicleList the orderVehicleList to set
	 */
	public void setOrderVehicleList(List<OrderVehicleEntity> orderVehicleList) {
		this.orderVehicleList = orderVehicleList;
	}

	/**
	 * 获得orderTypeList
	 * @return the orderTypeList
	 */
	public List<String> getOrderTypeList() {
		return orderTypeList;
	}

	/**
	 * 设置orderTypeList
	 * @param orderTypeList the orderTypeList to set
	 */
	public void setOrderTypeList(List<String> orderTypeList) {
		this.orderTypeList = orderTypeList;
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
	 * 获得orderVehicleApplyIdList
	 * @return the orderVehicleApplyIdList
	 */
	public List<String> getOrderVehicleApplyIdList() {
		return orderVehicleApplyIdList;
	}

	/**
	 * 设置orderVehicleApplyIdList
	 * @param orderVehicleApplyIdList the orderVehicleApplyIdList to set
	 */
	public void setOrderVehicleApplyIdList(List<String> orderVehicleApplyIdList) {
		this.orderVehicleApplyIdList = orderVehicleApplyIdList;
	}

	/**
	 * 获得passOrderApplyDto
	 * @return the passOrderApplyDto
	 */
	public PassOrderApplyDto getPassOrderApplyDto() {
		return passOrderApplyDto;
	}

	/**
	 * 设置passOrderApplyDto
	 * @param passOrderApplyDto the passOrderApplyDto to set
	 */
	public void setPassOrderApplyDto(PassOrderApplyDto passOrderApplyDto) {
		this.passOrderApplyDto = passOrderApplyDto;
	}

	/**
	 * 获得salesMotorcadeList
	 * @return the salesMotorcadeList
	 */
	public List<SalesMotorcadeEntity> getSalesMotorcadeList() {
		return salesMotorcadeList;
	}

	/**
	 * 设置salesMotorcadeList
	 * @param salesMotorcadeList the salesMotorcadeList to set
	 */
	public void setSalesMotorcadeList(List<SalesMotorcadeEntity> salesMotorcadeList) {
		this.salesMotorcadeList = salesMotorcadeList;
	}

	/**
	 * 获得salesdeptCode
	 * @return the salesdeptCode
	 */
	public String getSalesdeptCode() {
		return salesdeptCode;
	}

	/**
	 * 设置salesdeptCode
	 * @param salesdeptCode the salesdeptCode to set
	 */
	public void setSalesdeptCode(String salesdeptCode) {
		this.salesdeptCode = salesdeptCode;
	}

	public String getMotorcadeCode() {
		return motorcadeCode;
	}

	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public SimpleTruckScheduleDto getSimpleTruckScheduleDto() {
		return simpleTruckScheduleDto;
	}

	public void setSimpleTruckScheduleDto(SimpleTruckScheduleDto simpleTruckScheduleDto) {
		this.simpleTruckScheduleDto = simpleTruckScheduleDto;
	}

	public String getBelongTransforCenter() {
		return belongTransforCenter;
	}

	public void setBelongTransforCenter(String belongTransforCenter) {
		this.belongTransforCenter = belongTransforCenter;
	}
	
}