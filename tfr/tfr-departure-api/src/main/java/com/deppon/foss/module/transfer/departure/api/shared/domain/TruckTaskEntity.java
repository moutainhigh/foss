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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/TruckTaskEntity.java
 *  
 *  FILE NAME          :TruckTaskEntity.java
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

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class TruckTaskEntity  extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;
	
	/**
	 * 
	 */
	private String truckTaskId;//任务ID
    
    /**
     * 
     */
    private String vehicleNo;//车牌号
    
    /**
     * 
     */
    private String lineNo;//线路
    
    /**
     * 
     */
    private String businessType;//车辆业务类型
    
    /**
     * 
     */
    private String driverCode;//司机编号
    
    /**
     * 
     */
    private String driverName;//司机姓名
    
    /**
     * 
     */
    private String driverPhone;//司机电话
    
    /**
     * 
     */
    private String status;//状态
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getTruckTaskId(){
		return truckTaskId;
	}

	/**
	 * 
	 *
	 * @param truckTaskId 
	 */
	public void setTruckTaskId(String truckTaskId){
		this.truckTaskId = truckTaskId;
	}

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
	public String getLineNo(){
		return lineNo;
	}

	/**
	 * 
	 *
	 * @param lineNo 
	 */
	public void setLineNo(String lineNo){
		this.lineNo = lineNo;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getBusinessType(){
		return businessType;
	}

	/**
	 * 
	 *
	 * @param businessType 
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
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
	public String getStatus(){
		return status;
	}

	/**
	 * 
	 *
	 * @param status 
	 */
	public void setStatus(String status){
		this.status = status;
	}


}