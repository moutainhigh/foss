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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/LmsTruckDepartPlanEntity.java
 *  
 *  FILE NAME          :LmsTruckDepartPlanEntity.java
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
 * Lms计划放行
 * @author IBM-liubinbin
 * @date 2012-10-30 上午11:16:40
 */
public class LmsTruckDepartPlanEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;
	
    /**
     * 
     */
    private String vehicleNo;//车牌号
    
    /**
     * 
     */
    private String truckOrgCode;//车辆所属
    
    /**
     * 
     */
    private String truckOrgName;//车辆所属部门名称
    
    /**
     * 
     */
    private String truckType;//车辆类型
    
    /**
     * 
     */
    private String driverCode;//司机编码
    
    /**
     * 
     */
    private String driverPhone;//司机电话
    
    /**
     * 
     */
    private String departPlanType;//放行类型
    
    /**
     * 
     */
    private Date planDepartTime;  //计划出发时间
    
    /**
     * 
     */
    private Date planEndTime;   //计划放行截至时间
    
    /**
     * 
     */
    private String truckDepartId;//车辆放行ID
    
    /**
     * 
     */
    private Date createTime;//创建时间
    
    /**
     * 
     */
    private String driverName;//司机姓名
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}

	/**
	 * 
	 *
	 * @param vehicleNo 
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getTruckOrgCode(){
		return truckOrgCode;
	}

	/**
	 * 
	 *
	 * @param truckOrgCode 
	 */
	public void setTruckOrgCode(String truckOrgCode){
		this.truckOrgCode = truckOrgCode;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getTruckType(){
		return truckType;
	}

	/**
	 * 
	 *
	 * @param truckType 
	 */
	public void setTruckType(String truckType){
		this.truckType = truckType;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDriverCode(){
		return driverCode;
	}

	/**
	 * 
	 *
	 * @param driverCode 
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDriverPhone(){
		return driverPhone;
	}

	/**
	 * 
	 *
	 * @param driverPhone 
	 */
	public void setDriverPhone(String driverPhone){
		this.driverPhone = driverPhone;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDepartPlanType(){
		return departPlanType;
	}

	/**
	 * 
	 *
	 * @param departPlanType 
	 */
	public void setDepartPlanType(String departPlanType){
		this.departPlanType = departPlanType;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getPlanDepartTime(){
		return planDepartTime;
	}

	/**
	 * 
	 *
	 * @param planDepartTime 
	 */
	public void setPlanDepartTime(Date planDepartTime){
		this.planDepartTime = planDepartTime;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getTruckDepartId(){
		return truckDepartId;
	}

	/**
	 * 
	 *
	 * @param truckDepartId 
	 */
	public void setTruckDepartId(String truckDepartId){
		this.truckDepartId = truckDepartId;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getCreateTime(){
		return createTime;
	}

	/**
	 * 
	 *
	 * @param createTime 
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDriverName(){
		return driverName;
	}

	/**
	 * 
	 *
	 * @param driverName 
	 */
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getTruckOrgName(){
		return truckOrgName;
	}

	/**
	 * 
	 *
	 * @param truckOrgName 
	 */
	public void setTruckOrgName(String truckOrgName){
		this.truckOrgName = truckOrgName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getPlanEndTime(){
		return planEndTime;
	}

	/**
	 * 
	 *
	 * @param planEndTime 
	 */
	public void setPlanEndTime(Date planEndTime){
		this.planEndTime = planEndTime;
	}

}