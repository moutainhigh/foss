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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/PassOrderVehicleVo.java
 * 
 *  FILE NAME     :PassOrderVehicleVo.java
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
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.vo
 * FILE    NAME: PassOrderVehicleVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

/**
 * 审核约车Vo
 * @author 104306-foss-wangLong
 * @date 2012-11-22 下午6:37:44
 */
public class PassOrderVehicleVo implements Serializable {
	
	private static final long serialVersionUID = 233533210449927747L;
	
	private String orderId;
	
	/** 约车申请实体  */
	private OrderVehicleEntity orderVehicleEntity;
	
	/** 审核约车实体.  */
	private PassOrderApplyEntity passOrderApplyEntity;
	
	/** 审核log实体  */
	private AuditOrderApplyEntity auditOrderApplyEntity;

	/** 约车申请List */
	private List<OrderVehicleEntity> orderVehicleResultList;
	
	/** 审核约车Log */
	private List<AuditOrderApplyEntity> auditOrderApplyResultList;
	
	/** 约车审核DTO */
	private PassOrderApplyDto passOrderApplyDto = new PassOrderApplyDto();
	
	/** 车队编码  */
	private String transDepartment;
	
	/** 车队小组  */
	private List<OrgAdministrativeInfoEntity> transTeamList; 

	/** 车辆司机dto */
	private VehicleDriverWithDto vehicleDriverWithDto;

	/** 车辆司机dtoList */
	private List<VehicleDriverWithDto> vehicleDriverWithDtoList;
	
	/** 单号Id list */
	private List<String> orderIdList;
	
	/** 标示从约车申请页面 带过去的数据  处理到最后一条时  是否加载当前部门下的全部数据   */
	private boolean isLoadAll;
	
	/**
	 * 获得orderId
	 * @return the orderId
	 */	
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 获得passOrderApplyEntity
	 * @return the passOrderApplyEntity
	 */
	public PassOrderApplyEntity getPassOrderApplyEntity() {
		return passOrderApplyEntity;
	}
	
	/**
	 * 获得auditOrderApplyEntity
	 * @return the auditOrderApplyEntity
	 */
	public AuditOrderApplyEntity getAuditOrderApplyEntity() {
		return auditOrderApplyEntity;
	}

	/**
	 * 获得orderVehicleEntity
	 * @return the orderVehicleEntity
	 */
	public OrderVehicleEntity getOrderVehicleEntity() {
		return orderVehicleEntity;
	}
	
	/**
	 * 获得orderVehicleResultList
	 * @return the orderVehicleResultList
	 */
	public List<OrderVehicleEntity> getOrderVehicleResultList() {
		return orderVehicleResultList;
	}
	
	/**
	 * 获得auditOrderApplyResultList
	 * @return the auditOrderApplyResultList
	 */
	public List<AuditOrderApplyEntity> getAuditOrderApplyResultList() {
		return auditOrderApplyResultList;
	}
	
	/**
	 * 获得passOrderApplyDto
	 * @return the passOrderApplyDto
	 */	
	public PassOrderApplyDto getPassOrderApplyDto() {
		return passOrderApplyDto;
	}

	/**
	 * 设置orderId
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * 设置orderVehicleEntity
	 * @param orderVehicleEntity the orderVehicleEntity to set
	 */
	public void setOrderVehicleEntity(OrderVehicleEntity orderVehicleEntity) {
		this.orderVehicleEntity = orderVehicleEntity;
	}

	/**
	 * 设置passOrderApplyEntity
	 * @param passOrderApplyEntity the passOrderApplyEntity to set
	 */
	public void setPassOrderApplyEntity(PassOrderApplyEntity passOrderApplyEntity) {
		this.passOrderApplyEntity = passOrderApplyEntity;
	}

	/**
	 * 设置auditOrderApplyEntity
	 * @param auditOrderApplyEntity the auditOrderApplyEntity to set
	 */
	public void setAuditOrderApplyEntity(AuditOrderApplyEntity auditOrderApplyEntity) {
		this.auditOrderApplyEntity = auditOrderApplyEntity;
	}

	/**
	 * 设置orderVehicleResultList
	 * @param orderVehicleResultList the orderVehicleResultList to set
	 */
	public void setOrderVehicleResultList(
			List<OrderVehicleEntity> orderVehicleResultList) {
		this.orderVehicleResultList = orderVehicleResultList;
	}

	/**
	 * 设置orderVehicleResultList
	 * @param orderVehicleResultList the orderVehicleResultList to set
	 */
	public void setAuditOrderApplyResultList(
			List<AuditOrderApplyEntity> auditOrderApplyResultList) {
		this.auditOrderApplyResultList = auditOrderApplyResultList;
	}

	/**
	 * 设置passOrderApplyDto
	 * @param passOrderApplyDto the passOrderApplyDto to set
	 */
	public void setPassOrderApplyDto(PassOrderApplyDto passOrderApplyDto) {
		this.passOrderApplyDto = passOrderApplyDto;
	}

	/**
	 * 获得transDepartment
	 * @return the transDepartment
	 */	
	public String getTransDepartment() {
		return transDepartment;
	}

	/**
	 * 设置transDepartment
	 * @param transDepartment the transDepartment to set
	 */
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}

	/**
	 * 获得transTeamList
	 * @return the transTeamList
	 */	
	public List<OrgAdministrativeInfoEntity> getTransTeamList() {
		return transTeamList;
	}

	/**
	 * 设置transTeamList
	 * @param transTeamList the transTeamList to set
	 */
	public void setTransTeamList(List<OrgAdministrativeInfoEntity> transTeamList) {
		this.transTeamList = transTeamList;
	}

	/**
	 * 获得vehicleDriverWithDto
	 * @return the vehicleDriverWithDto
	 */	
	public VehicleDriverWithDto getVehicleDriverWithDto() {
		return vehicleDriverWithDto;
	}

	/**
	 * 设置vehicleDriverWithDto
	 * @param vehicleDriverWithDto the vehicleDriverWithDto to set
	 */
	public void setVehicleDriverWithDto(VehicleDriverWithDto vehicleDriverWithDto) {
		this.vehicleDriverWithDto = vehicleDriverWithDto;
	}

	/**
	 * 获得vehicleDriverWithDtoList
	 * @return the vehicleDriverWithDtoList
	 */	
	public List<VehicleDriverWithDto> getVehicleDriverWithDtoList() {
		return vehicleDriverWithDtoList;
	}

	/**
	 * 设置vehicleDriverWithDtoList
	 * @param vehicleDriverWithDtoList the vehicleDriverWithDtoList to set
	 */
	public void setVehicleDriverWithDtoList(
			List<VehicleDriverWithDto> vehicleDriverWithDtoList) {
		this.vehicleDriverWithDtoList = vehicleDriverWithDtoList;
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

	/**
	 * 获得isLoadAll
	 * @return the isLoadAll
	 */
	public boolean isLoadAll() {
		return isLoadAll;
	}

	/**
	 * 设置isLoadAll
	 * @param isLoadAll the isLoadAll to set
	 */
	public void setIsLoadAll(boolean isLoadAll) {
		this.isLoadAll = isLoadAll;
	}
}