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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/LeadTruckVO.java
 * 
 *  FILE NAME     :LeadTruckVO.java
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
 * FILE    NAME: TruckDepartPlanVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.LeadTruckEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.QueryLeadTruckEntity;


/**
 * 发车计划VO
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-22 下午3:06:09 *
 */
public class LeadTruckVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9186356163265383504L;
	
	/*******查询条件*********/
	private QueryLeadTruckEntity queryEntity;
	
	/***********询价列表***********/
	private List<LeadTruckEntity> leadTruckList;
	
	/***********登记表单***********/
	private LeadTruckEntity manualEntity;
	
	/**********当前部门的编码***********/
	private String currentDeptCode;
	
	MotorcadeEntity motorcadeEntity;

	private String ids;
	/**
	 * 获取 *****查询条件********.
	 *
	 * @return the *****查询条件********
	 */
	public QueryLeadTruckEntity getQueryEntity()
	{
		return queryEntity;
	}

	/**
	 * 设置 *****查询条件********.
	 *
	 * @param queryEntity the new *****查询条件********
	 */
	public void setQueryEntity(QueryLeadTruckEntity queryEntity)
	{
		this.queryEntity = queryEntity;
	}

	/**
	 * 获取 *********询价列表**********.
	 *
	 * @return the *********询价列表**********
	 */
	public List<LeadTruckEntity> getLeadTruckList()
	{
		return leadTruckList;
	}

	/**
	 * 设置 *********询价列表**********.
	 *
	 * @param leadTruckList the new *********询价列表**********
	 */
	public void setLeadTruckList(List<LeadTruckEntity> leadTruckList)
	{
		this.leadTruckList = leadTruckList;
	}

	/**
	 * 获取 *********登记表单**********.
	 *
	 * @return the *********登记表单**********
	 */
	public LeadTruckEntity getManualEntity()
	{
		return manualEntity;
	}

	/**
	 * 设置 *********登记表单**********.
	 *
	 * @param manualEntity the new *********登记表单**********
	 */
	public void setManualEntity(LeadTruckEntity manualEntity)
	{
		this.manualEntity = manualEntity;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public MotorcadeEntity getMotorcadeEntity() {
		return motorcadeEntity;
	}

	public void setMotorcadeEntity(MotorcadeEntity motorcadeEntity) {
		this.motorcadeEntity = motorcadeEntity;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	

}