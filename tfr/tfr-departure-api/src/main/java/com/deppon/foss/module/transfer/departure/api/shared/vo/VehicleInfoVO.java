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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/vo/VehicleInfoVO.java
 *  
 *  FILE NAME          :VehicleInfoVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.domain.ArrivalInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.BusinessInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.DepartInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.OnTheWayInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.VehicleInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.TruckTaskBillSummaryDto;

/**
 * 
 */
public class VehicleInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -293285666591619830L;

	/**
	 * 
	 */
	private String departType;
	
	/**
	 * 
	 */
	TruckDepartEntity departEntity;
	
	/**
	 * 
	 */
	private QueryDepartEntity queryEntity;
	
	/**
	 * 
	 */
	private List<TruckDepartEntity> departList;
	
	/**
	 * 
	 */
	private List<String> departTypeList;
	
	/*********车辆信息**********/
	VehicleInfoEntity vehicleInfoEntity;
	
	/*********司机联系方式信息**********/
	RelationInfoEntity relationInfoEntity;
	
	/*********车辆业务信息**********/
	BusinessInfoEntity businessInfoEntity;
	
	/*********根据车辆放行信息找到交接单/配载单信息**********/
	List<TruckTaskBillEntity> taskBillList = new ArrayList<TruckTaskBillEntity>();

	/**车辆任务下运单信息统计**/
	List<TruckTaskBillSummaryDto> taskBillSummaryList = new ArrayList<TruckTaskBillSummaryDto>();
	
	/*********申请放行信息**********/
	DepartInfoEntity departInfoEntity;
	
	/*********出发到达信息信息**********/
	ArrivalInfoEntity arrivalInfoEntity;
	
	/*********在途信息**********/
	OnTheWayInfoEntity onTheWayInfoEntity;
	
	/********打印纸质放行条信息***/
	TruckDepartEntity manualEntity;
	
	/*********记录操作状态***********/
	private String operatStatus;
	
	

	/**
	 * 
	 *
	 * @return 
	 */
	public List<TruckDepartEntity> getDepartList() {
		return departList;
	}

	/**
	 * 
	 *
	 * @param departList 
	 */
	public void setDepartList(List<TruckDepartEntity> departList) {
		this.departList = departList;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public TruckDepartEntity getDepartEntity() {
		return departEntity;
	}

	/**
	 * 
	 *
	 * @param departEntity 
	 */
	public void setDepartEntity(TruckDepartEntity departEntity) {
		this.departEntity = departEntity;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDepartType() {
		return departType;
	}

	/**
	 * 
	 *
	 * @param departType 
	 */
	public void setDepartType(String departType) {
		this.departType = departType;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public List<String> getDepartTypeList() {
		return departTypeList;
	}

	/**
	 * 
	 *
	 * @param departTypeList 
	 */
	public void setDepartTypeList(List<String> departTypeList) {
		this.departTypeList = departTypeList;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public QueryDepartEntity getQueryEntity()
	{
		return queryEntity;
	}

	/**
	 * 
	 *
	 * @param queryEntity 
	 */
	public void setQueryEntity(QueryDepartEntity queryEntity)
	{
		this.queryEntity = queryEntity;
	}

	/**
	 * 获取 *******车辆信息*********.
	 *
	 * @return the *******车辆信息*********
	 */
	public VehicleInfoEntity getVehicleInfoEntity()
	{
		return vehicleInfoEntity;
	}

	/**
	 * 设置 *******车辆信息*********.
	 *
	 * @param vehicleInfoEntity the new *******车辆信息*********
	 */
	public void setVehicleInfoEntity(VehicleInfoEntity vehicleInfoEntity)
	{
		this.vehicleInfoEntity = vehicleInfoEntity;
	}

	/**
	 * 获取 *******司机联系方式信息*********.
	 *
	 * @return the *******司机联系方式信息*********
	 */
	public RelationInfoEntity getRelationInfoEntity()
	{
		return relationInfoEntity;
	}

	/**
	 * 设置 *******司机联系方式信息*********.
	 *
	 * @param relationInfoEntity the new *******司机联系方式信息*********
	 */
	public void setRelationInfoEntity(RelationInfoEntity relationInfoEntity)
	{
		this.relationInfoEntity = relationInfoEntity;
	}

	/**
	 * 获取 *******车辆业务信息*********.
	 *
	 * @return the *******车辆业务信息*********
	 */
	public BusinessInfoEntity getBusinessInfoEntity()
	{
		return businessInfoEntity;
	}

	/**
	 * 设置 *******车辆业务信息*********.
	 *
	 * @param businessInfoEntity the new *******车辆业务信息*********
	 */
	public void setBusinessInfoEntity(BusinessInfoEntity businessInfoEntity)
	{
		this.businessInfoEntity = businessInfoEntity;
	}

	/**
	 * 获取 *******根据车辆放行信息找到交接单/配载单信息*********.
	 *
	 * @return the *******根据车辆放行信息找到交接单/配载单信息*********
	 */
	public List<TruckTaskBillEntity> getTaskBillList()
	{
		return taskBillList;
	}

	/**
	 * 设置 *******根据车辆放行信息找到交接单/配载单信息*********.
	 *
	 * @param taskBillList the new *******根据车辆放行信息找到交接单/配载单信息*********
	 */
	public void setTaskBillList(List<TruckTaskBillEntity> taskBillList)
	{
		this.taskBillList = taskBillList;
	}

	/**
	 * 获取 *******申请放行信息*********.
	 *
	 * @return the *******申请放行信息*********
	 */
	public DepartInfoEntity getDepartInfoEntity()
	{
		return departInfoEntity;
	}

	/**
	 * 设置 *******申请放行信息*********.
	 *
	 * @param departInfoEntity the new *******申请放行信息*********
	 */
	public void setDepartInfoEntity(DepartInfoEntity departInfoEntity)
	{
		this.departInfoEntity = departInfoEntity;
	}

	/**
	 * 获取 *******出发到达信息信息*********.
	 *
	 * @return the *******出发到达信息信息*********
	 */
	public ArrivalInfoEntity getArrivalInfoEntity()
	{
		return arrivalInfoEntity;
	}

	/**
	 * 设置 *******出发到达信息信息*********.
	 *
	 * @param arrivalInfoEntity the new *******出发到达信息信息*********
	 */
	public void setArrivalInfoEntity(ArrivalInfoEntity arrivalInfoEntity)
	{
		this.arrivalInfoEntity = arrivalInfoEntity;
	}

	/**
	 * 获取 *******在途信息*********.
	 *
	 * @return the *******在途信息*********
	 */
	public OnTheWayInfoEntity getOnTheWayInfoEntity()
	{
		return onTheWayInfoEntity;
	}

	/**
	 * 设置 *******在途信息*********.
	 *
	 * @param onTheWayInfoEntity the new *******在途信息*********
	 */
	public void setOnTheWayInfoEntity(OnTheWayInfoEntity onTheWayInfoEntity)
	{
		this.onTheWayInfoEntity = onTheWayInfoEntity;
	}

	/**
	 * 获取 ******打印纸质放行条信息**.
	 *
	 * @return the ******打印纸质放行条信息**
	 */
	public TruckDepartEntity getManualEntity()
	{
		return manualEntity;
	}

	/**
	 * 设置 ******打印纸质放行条信息**.
	 *
	 * @param manualEntity the new ******打印纸质放行条信息**
	 */
	public void setManualEntity(TruckDepartEntity manualEntity)
	{
		this.manualEntity = manualEntity;
	}

	/**
	 * 获取 *******记录操作状态**********.
	 *
	 * @return the *******记录操作状态**********
	 */
	public String getOperatStatus()
	{
		return operatStatus;
	}

	/**
	 * 设置 *******记录操作状态**********.
	 *
	 * @param operatStatus the new *******记录操作状态**********
	 */
	public void setOperatStatus(String operatStatus)
	{
		this.operatStatus = operatStatus;
	}

	public List<TruckTaskBillSummaryDto> getTaskBillSummaryList() {
		return taskBillSummaryList;
	}

	public void setTaskBillSummaryList(
			List<TruckTaskBillSummaryDto> taskBillSummaryList) {
		this.taskBillSummaryList = taskBillSummaryList;
	}
}