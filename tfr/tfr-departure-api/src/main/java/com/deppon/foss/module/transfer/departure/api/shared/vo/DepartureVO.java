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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/vo/DepartureVO.java
 *  
 *  FILE NAME          :DepartureVO.java
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
import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehiclePrintDTO;

/**
 * 
 */
public class DepartureVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -293285666591619830L;

	/***********放行类型***********/
	private String departType;
	
	/***********放行信息***********/
	TruckDepartEntity departEntity;
	
	/***********查询条件***********/
	private QueryDepartEntity queryEntity;
	
	/***********放行信息***********/
	private List<TruckDepartEntity> departList;
	
	/***********用于处理取消或激活***********/
	private List<TruckDepartEntity> activeList;
	
	/**
	 * 
	 */
	private List<String> departTypeList;
	
	/********打印纸质放行条信息***/
	TruckDepartEntity manualEntity;
	
	/*********记录操作状态***********/
	private String operatStatus;
	
	/*********打印信息***********/
	private VehiclePrintDTO vehiclePrintDTO;
	
	private String ids;
	
	private String deliverbills;

	/**
	 * 获取 *********放行类型**********.
	 *
	 * @return the *********放行类型**********
	 */
	public String getDepartType()
	{
		return departType;
	}

	/**
	 * 设置 *********放行类型**********.
	 *
	 * @param departType the new *********放行类型**********
	 */
	public void setDepartType(String departType)
	{
		this.departType = departType;
	}

	/**
	 * 获取 *********放行信息**********.
	 *
	 * @return the *********放行信息**********
	 */
	public TruckDepartEntity getDepartEntity()
	{
		return departEntity;
	}

	/**
	 * 设置 *********放行信息**********.
	 *
	 * @param departEntity the new *********放行信息**********
	 */
	public void setDepartEntity(TruckDepartEntity departEntity)
	{
		this.departEntity = departEntity;
	}

	/**
	 * 获取 *********查询条件**********.
	 *
	 * @return the *********查询条件**********
	 */
	public QueryDepartEntity getQueryEntity()
	{
		return queryEntity;
	}

	/**
	 * 设置 *********查询条件**********.
	 *
	 * @param queryEntity the new *********查询条件**********
	 */
	public void setQueryEntity(QueryDepartEntity queryEntity)
	{
		this.queryEntity = queryEntity;
	}

	/**
	 * 获取 *********放行信息**********.
	 *
	 * @return the *********放行信息**********
	 */
	public List<TruckDepartEntity> getDepartList()
	{
		return departList;
	}

	/**
	 * 设置 *********放行信息**********.
	 *
	 * @param departList the new *********放行信息**********
	 */
	public void setDepartList(List<TruckDepartEntity> departList)
	{
		this.departList = departList;
	}

	/**
	 * 获取 *********用于处理取消或激活**********.
	 *
	 * @return the *********用于处理取消或激活**********
	 */
	public List<TruckDepartEntity> getActiveList()
	{
		return activeList;
	}

	/**
	 * 设置 *********用于处理取消或激活**********.
	 *
	 * @param activeList the new *********用于处理取消或激活**********
	 */
	public void setActiveList(List<TruckDepartEntity> activeList)
	{
		this.activeList = activeList;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public List<String> getDepartTypeList()
	{
		return departTypeList;
	}

	/**
	 * 
	 *
	 * @param departTypeList 
	 */
	public void setDepartTypeList(List<String> departTypeList)
	{
		this.departTypeList = departTypeList;
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

	public VehiclePrintDTO getVehiclePrintDTO() {
		return vehiclePrintDTO;
	}

	public void setVehiclePrintDTO(VehiclePrintDTO vehiclePrintDTO) {
		this.vehiclePrintDTO = vehiclePrintDTO;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getDeliverbills() {
		return deliverbills;
	}

	public void setDeliverbills(String deliverbills) {
		this.deliverbills = deliverbills;
	}

}