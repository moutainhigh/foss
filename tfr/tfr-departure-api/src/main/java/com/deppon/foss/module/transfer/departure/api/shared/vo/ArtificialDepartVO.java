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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/vo/ArtificialDepartVO.java
 *  
 *  FILE NAME          :ArtificialDepartVO.java
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
import com.deppon.foss.module.transfer.departure.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;

/**
 * 
 */
public class ArtificialDepartVO implements Serializable{
	
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -293285666591619830L;

	/**********查询LMS条件************/
	private QueryDepartEntity queryEntity;
	
	/**********查询LMS结果列表************/
	private List<TruckDepartEntity> departList;
	
	/********新增录入框表单（临时）***/
	private TruckDepartEntity temporaryEntity;
	
	/********新增录入框表单（任务车辆）***/
	private TruckDepartEntity taskEntity;
	
	/********通过车牌号查找司机信息（录入时用）***/
	private  RelationInfoEntity relationInfoEntity;
	
	/*********记录操作状态***********/
	private String operatStatus;
	
	/**********司机编号************/
	private String driverCode;
	
	/**********司机姓名************/
	private String driverName;
	
	/**********联系方式************/
	private String driverPhone;
	
	/**********车牌号************/
	private String vehicleNo;

	/**
	 * 获取 ********查询LMS条件***********.
	 *
	 * @return the ********查询LMS条件***********
	 */
	public QueryDepartEntity getQueryEntity()
	{
		return queryEntity;
	}

	/**
	 * 设置 ********查询LMS条件***********.
	 *
	 * @param queryEntity the new ********查询LMS条件***********
	 */
	public void setQueryEntity(QueryDepartEntity queryEntity)
	{
		this.queryEntity = queryEntity;
	}

	/**
	 * 获取 ********查询LMS结果列表***********.
	 *
	 * @return the ********查询LMS结果列表***********
	 */
	public List<TruckDepartEntity> getDepartList()
	{
		return departList;
	}

	/**
	 * 设置 ********查询LMS结果列表***********.
	 *
	 * @param departList the new ********查询LMS结果列表***********
	 */
	public void setDepartList(List<TruckDepartEntity> departList)
	{
		this.departList = departList;
	}

	/**
	 * 获取 ******新增录入框表单（临时）**.
	 *
	 * @return the ******新增录入框表单（临时）**
	 */
	public TruckDepartEntity getTemporaryEntity()
	{
		return temporaryEntity;
	}

	/**
	 * 设置 ******新增录入框表单（临时）**.
	 *
	 * @param temporaryEntity the new ******新增录入框表单（临时）**
	 */
	public void setTemporaryEntity(TruckDepartEntity temporaryEntity)
	{
		this.temporaryEntity = temporaryEntity;
	}

	/**
	 * 获取 ******新增录入框表单（任务车辆）**.
	 *
	 * @return the ******新增录入框表单（任务车辆）**
	 */
	public TruckDepartEntity getTaskEntity()
	{
		return taskEntity;
	}

	/**
	 * 设置 ******新增录入框表单（任务车辆）**.
	 *
	 * @param taskEntity the new ******新增录入框表单（任务车辆）**
	 */
	public void setTaskEntity(TruckDepartEntity taskEntity)
	{
		this.taskEntity = taskEntity;
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

	/**
	 * 获取 ********司机编号***********.
	 *
	 * @return the ********司机编号***********
	 */
	public String getDriverCode()
	{
		return driverCode;
	}

	/**
	 * 设置 ********司机编号***********.
	 *
	 * @param driverCode the new ********司机编号***********
	 */
	public void setDriverCode(String driverCode)
	{
		this.driverCode = driverCode;
	}

	/**
	 * 获取 ********联系方式***********.
	 *
	 * @return the ********联系方式***********
	 */
	public String getDriverPhone()
	{
		return driverPhone;
	}

	/**
	 * 设置 ********联系方式***********.
	 *
	 * @param driverPhone the new ********联系方式***********
	 */
	public void setDriverPhone(String driverPhone)
	{
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 ******通过车牌号查找司机信息（录入时用）**.
	 *
	 * @return the ******通过车牌号查找司机信息（录入时用）**
	 */
	public RelationInfoEntity getRelationInfoEntity()
	{
		return relationInfoEntity;
	}

	/**
	 * 设置 ******通过车牌号查找司机信息（录入时用）**.
	 *
	 * @param relationInfoEntity the new ******通过车牌号查找司机信息（录入时用）**
	 */
	public void setRelationInfoEntity(RelationInfoEntity relationInfoEntity)
	{
		this.relationInfoEntity = relationInfoEntity;
	}

	/**
	 * 获取 ********车牌号***********.
	 *
	 * @return the ********车牌号***********
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ********车牌号***********.
	 *
	 * @param vehicleNo the new ********车牌号***********
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


}