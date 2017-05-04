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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/TruckGPSTaskEntity.java
 *  
 *  FILE NAME          :TruckGPSTaskEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: TruckGPSTask.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * GPS任务列表
 * @author dp-duyi
 * @date 2012-11-7 上午9:12:09
 */
public class TruckGPSTaskEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9153117575245779832L;
	/**id*/
	private String id;
	/**truckTaskDetailId*/
	private String truckTaskDetailId;
	/**vehicleNo*/
	private String vehicleNo;
	/**gpsDeviceNo*/
	private String gpsDeviceNo;
	/**origOrgCode*/
	private String origOrgCode;
	/**destOrgCode*/
	private String destOrgCode;
	/**gpsDepartTime*/
	private Date gpsDepartTime;
	/**gpsArriveTime*/
	private Date gpsArriveTime;
	/**operateType*/
	private int operateType; //操作类型：新增：0，删除：1
	/**beSuccess*/
	private String beSuccess; //同步到gps是否成功：1成功，0不成功
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the truckTaskDetailId.
	 *
	 * @return the truckTaskDetailId
	 */
	public String getTruckTaskDetailId() {
		return truckTaskDetailId;
	}
	
	/**
	 * Sets the truckTaskDetailId.
	 *
	 * @param truckTaskDetailId the new truckTaskDetailId
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId) {
		this.truckTaskDetailId = truckTaskDetailId;
	}
	
	/**
	 * Gets the vehicleNo.
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the vehicleNo.
	 *
	 * @param vehicleNo the new vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the gpsDeviceNo.
	 *
	 * @return the gpsDeviceNo
	 */
	public String getGpsDeviceNo() {
		return gpsDeviceNo;
	}
	
	/**
	 * Sets the gpsDeviceNo.
	 *
	 * @param gpsDeviceNo the new gpsDeviceNo
	 */
	public void setGpsDeviceNo(String gpsDeviceNo) {
		this.gpsDeviceNo = gpsDeviceNo;
	}
	
	/**
	 * Gets the origOrgCode.
	 *
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * Sets the origOrgCode.
	 *
	 * @param origOrgCode the new origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the destOrgCode.
	 *
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the destOrgCode.
	 *
	 * @param destOrgCode the new destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * Gets the gpsDepartTime.
	 *
	 * @return the gpsDepartTime
	 */
	public Date getGpsDepartTime() {
		return gpsDepartTime;
	}
	
	/**
	 * Sets the gpsDepartTime.
	 *
	 * @param gpsDepartTime the new gpsDepartTime
	 */
	public void setGpsDepartTime(Date gpsDepartTime) {
		this.gpsDepartTime = gpsDepartTime;
	}
	
	/**
	 * Gets the gpsArriveTime.
	 *
	 * @return the gpsArriveTime
	 */
	public Date getGpsArriveTime() {
		return gpsArriveTime;
	}
	
	/**
	 * Sets the gpsArriveTime.
	 *
	 * @param gpsArriveTime the new gpsArriveTime
	 */
	public void setGpsArriveTime(Date gpsArriveTime) {
		this.gpsArriveTime = gpsArriveTime;
	}
	
	/**
	 * Gets the operateType.
	 *
	 * @return the operateType
	 */
	public int getOperateType() {
		return operateType;
	}
	
	/**
	 * Sets the operateType.
	 *
	 * @param operateType the new operateType
	 */
	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}
	
	/**
	 * Gets the beSuccess.
	 *
	 * @return the beSuccess
	 */
	public String getBeSuccess() {
		return beSuccess;
	}
	
	/**
	 * Sets the beSuccess.
	 *
	 * @param beSuccess the new beSuccess
	 */
	public void setBeSuccess(String beSuccess) {
		this.beSuccess = beSuccess;
	}
}