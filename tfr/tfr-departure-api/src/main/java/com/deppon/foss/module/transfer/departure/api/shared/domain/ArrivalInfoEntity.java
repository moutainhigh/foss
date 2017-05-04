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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/ArrivalInfoEntity.java
 *  
 *  FILE NAME          :ArrivalInfoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class ArrivalInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********计划出发时间************/
	private Date planDepartTime;
	
	/**********出发部门************/
	private String origOrgCode;
	
	/**********出发部门************/
	private String origOrgName;

	/**********实际出发时间************/
	private Date actualDepartTime;
	
	/**********计划到达时间************/
	private Date planArriveTime;
	
	/**********到达部门************/
	private String destOrgCode;
	
	/**********到达部门************/
	private String destOrgName;
	
	/**********实际到达时间************/
	private Date actualArriveTime;
	
	/**********车辆状态************/
	private String status;
	
	/**********车辆线路************/
	private String lineNo;
	
	/**********任务车辆类型************/
	private String businessType;
	


	/**
	 * 获取 ********出发部门***********.
	 *
	 * @return the ********出发部门***********
	 */
	public String getOrigOrgCode(){
		return origOrgCode;
	}

	/**
	 * 设置 ********出发部门***********.
	 *
	 * @param origOrgCode the new ********出发部门***********
	 */
	public void setOrigOrgCode(String origOrgCode){
		this.origOrgCode = origOrgCode;
	}


	/**
	 * 获取 ********到达部门***********.
	 *
	 * @return the ********到达部门***********
	 */
	public String getDestOrgCode(){
		return destOrgCode;
	}

	/**
	 * 设置 ********到达部门***********.
	 *
	 * @param destOrgCode the new ********到达部门***********
	 */
	public void setDestOrgCode(String destOrgCode){
		this.destOrgCode = destOrgCode;
	}


	/**
	 * 获取 ********车辆状态***********.
	 *
	 * @return the ********车辆状态***********
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 设置 ********车辆状态***********.
	 *
	 * @param status the new ********车辆状态***********
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * 获取 ********车辆线路***********.
	 *
	 * @return the ********车辆线路***********
	 */
	public String getLineNo(){
		return lineNo;
	}

	/**
	 * 设置 ********车辆线路***********.
	 *
	 * @param lineNo the new ********车辆线路***********
	 */
	public void setLineNo(String lineNo){
		this.lineNo = lineNo;
	}

	/**
	 * 获取 ********任务车辆类型***********.
	 *
	 * @return the ********任务车辆类型***********
	 */
	public String getBusinessType(){
		return businessType;
	}

	/**
	 * 设置 ********任务车辆类型***********.
	 *
	 * @param businessType the new ********任务车辆类型***********
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	/**
	 * 获取 ********计划出发时间***********.
	 *
	 * @return the ********计划出发时间***********
	 */
	public Date getPlanDepartTime(){
		return planDepartTime;
	}

	/**
	 * 设置 ********计划出发时间***********.
	 *
	 * @param planDepartTime the new ********计划出发时间***********
	 */
	public void setPlanDepartTime(Date planDepartTime){
		this.planDepartTime = planDepartTime;
	}

	/**
	 * 获取 ********实际出发时间***********.
	 *
	 * @return the ********实际出发时间***********
	 */
	public Date getActualDepartTime(){
		return actualDepartTime;
	}

	/**
	 * 设置 ********实际出发时间***********.
	 *
	 * @param actualDepartTime the new ********实际出发时间***********
	 */
	public void setActualDepartTime(Date actualDepartTime){
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * 获取 ********计划到达时间***********.
	 *
	 * @return the ********计划到达时间***********
	 */
	public Date getPlanArriveTime(){
		return planArriveTime;
	}

	/**
	 * 设置 ********计划到达时间***********.
	 *
	 * @param planArriveTime the new ********计划到达时间***********
	 */
	public void setPlanArriveTime(Date planArriveTime){
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 ********实际到达时间***********.
	 *
	 * @return the ********实际到达时间***********
	 */
	public Date getActualArriveTime(){
		return actualArriveTime;
	}

	/**
	 * 设置 ********实际到达时间***********.
	 *
	 * @param actualArriveTime the new ********实际到达时间***********
	 */
	public void setActualArriveTime(Date actualArriveTime){
		this.actualArriveTime = actualArriveTime;
	}

	/**
	 * 获取 ********出发部门***********.
	 *
	 * @return the ********出发部门***********
	 */
	public String getOrigOrgName(){
		return origOrgName;
	}

	/**
	 * 设置 ********出发部门***********.
	 *
	 * @param origOrgName the new ********出发部门***********
	 */
	public void setOrigOrgName(String origOrgName){
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 ********到达部门***********.
	 *
	 * @return the ********到达部门***********
	 */
	public String getDestOrgName(){
		return destOrgName;
	}

	/**
	 * 设置 ********到达部门***********.
	 *
	 * @param destOrgName the new ********到达部门***********
	 */
	public void setDestOrgName(String destOrgName){
		this.destOrgName = destOrgName;
	}
}