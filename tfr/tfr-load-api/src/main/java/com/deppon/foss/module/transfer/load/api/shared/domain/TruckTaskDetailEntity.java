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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/TruckTaskDetailEntity.java
 *  
 *  FILE NAME          :TruckTaskDetailEntity.java
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
 * FILE    NAME: TruckTaskDetailEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务车辆明细
 * @author dp-duyi
 * @date 2012-11-7 上午8:54:29
 */
public class TruckTaskDetailEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4110491871464233831L;
	/**id*/
	private String id;
	/**parentId*/
	private String parentId;
	/**vehicleNo*/
	private String vehicleNo;
	/**lineName*/
	private String lineName;
	/**lineVirtualCode*/
	private String lineVirtualCode;
	/**frequecyNo*/
	private String frequecyNo;
	/**businessType*/
	private String businessType;
	/**origOrgCode*/
	private String origOrgCode;
	/**destOrgCode*/
	private String destOrgCode;
	/**origOrgName*/
	private String origOrgName;
	/**destOrgName*/
	private String destOrgName;
	/**state*/
	private String state;
	/**createTime*/
	private Date createTime;
	/**truckType*/
	private String truckType;
	/**planDepartTime*/
	private Date planDepartTime;
	/**actualDepartTime*/
	private Date actualDepartTime;
	/**planArriveTime*/
	private Date planArriveTime;
	/**actualArriveTime*/
	private Date actualArriveTime;
	/**vehicleSealId*/
	private String vehicleSealId;
	/**truckDepartId*/
	private String truckDepartId;
	/**truckArriveId*/
	private String truckArriveId;
	/**beCarLoad*/
	private String beCarLoad;
	/**loaderName*/
	private String loaderName;
	/**loaderCode*/
	private String loaderCode;
	/**vehicleOrgCode*/
	private String vehicleOrgCode;
	/**vehicleOrgName*/
	private String vehicleOrgName;
	/**vehicleOwnerType*/
	private String vehicleOwnerType;
	/**actualDepartType*/
	private String actualDepartType;
	
	private Date manualDepartTime;
	private Date manualArriveTime;
	/**挂牌号*/
	private String trailerVehicleNo;
	/**制单人*/
	private String creater;
	/**修改时间*/
	private Date modifyTime;
	/**月台号*/
	private String platformDistributeId;
	/**实际到达类型*/
	private String acrualArriveType;
	/**人工操作人员部门信息*/
	private String manualDepartUserCode;
//	private String manualDepartUserName;//106162 取消掉
	private String manualDepartOrgCode;
	private String manualDepartOrgName;
	//新增到达时候操作人 106162 2016-06-30
	private String manualArriveUserCode;
	
	/**是否有效 370210 2016-11-17 **/
	private String billLevel;
	/**单据类型（交接单、配载单） 370210 2016-11-17**/
	private String billType;
	
	public String getAcrualArriveType() {
		return acrualArriveType;
	}

	public void setAcrualArriveType(String acrualArriveType) {
		this.acrualArriveType = acrualArriveType;
	}

	public String getManualDepartUserCode() {
		return manualDepartUserCode;
	}

	public void setManualDepartUserCode(String manualDepartUserCode) {
		this.manualDepartUserCode = manualDepartUserCode;
	}

	/*public String getManualDepartUserName() {
		return manualDepartUserName;
	}

	public void setManualDepartUserName(String manualDepartUserName) {
		this.manualDepartUserName = manualDepartUserName;
	}*/

	public String getManualDepartOrgCode() {
		return manualDepartOrgCode;
	}

	public void setManualDepartOrgCode(String manualDepartOrgCode) {
		this.manualDepartOrgCode = manualDepartOrgCode;
	}

	public String getManualDepartOrgName() {
		return manualDepartOrgName;
	}

	public void setManualDepartOrgName(String manualDepartOrgName) {
		this.manualDepartOrgName = manualDepartOrgName;
	}

	public String getPlatformDistributeId() {
		return platformDistributeId;
	}

	public void setPlatformDistributeId(String platformDistributeId) {
		this.platformDistributeId = platformDistributeId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getTrailerVehicleNo() {
		return trailerVehicleNo;
	}

	public void setTrailerVehicleNo(String trailerVehicleNo) {
		this.trailerVehicleNo = trailerVehicleNo;
	}

	public Date getManualDepartTime() {
		return manualDepartTime;
	}

	public void setManualDepartTime(Date manualDepartTime) {
		this.manualDepartTime = manualDepartTime;
	}

	public Date getManualArriveTime() {
		return manualArriveTime;
	}

	public void setManualArriveTime(Date manualArriveTime) {
		this.manualArriveTime = manualArriveTime;
	}

	/**
	 * 运行时长
	 */
	private Integer runingTimes;
	
	/**
	 * Gets the actualDepartType.
	 *
	 * @return the actualDepartType
	 */
	public String getActualDepartType() {
		return actualDepartType;
	}
	
	/**
	 * Sets the actualDepartType.
	 *
	 * @param actualDepartType the new actualDepartType
	 */
	public void setActualDepartType(String actualDepartType) {
		this.actualDepartType = actualDepartType;
	}
	
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
	 * Gets the parentId.
	 *
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**
	 * Sets the parentId.
	 *
	 * @param parentId the new parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	 * Gets the lineName.
	 *
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}
	
	/**
	 * Sets the lineName.
	 *
	 * @param lineName the new lineName
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	/**
	 * Gets the lineVirtualCode.
	 *
	 * @return the lineVirtualCode
	 */
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}
	
	/**
	 * Sets the lineVirtualCode.
	 *
	 * @param lineVirtualCode the new lineVirtualCode
	 */
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}
	
	/**
	 * Gets the frequecyNo.
	 *
	 * @return the frequecyNo
	 */
	public String getFrequecyNo() {
		return frequecyNo;
	}
	
	/**
	 * Sets the frequecyNo.
	 *
	 * @param frequecyNo the new frequecyNo
	 */
	public void setFrequecyNo(String frequecyNo) {
		this.frequecyNo = frequecyNo;
	}
	
	/**
	 * Gets the businessType.
	 *
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}
	
	/**
	 * Sets the businessType.
	 *
	 * @param businessType the new businessType
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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
	 * Gets the origOrgName.
	 *
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * Sets the origOrgName.
	 *
	 * @param origOrgName the new origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * Gets the destOrgName.
	 *
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * Sets the destOrgName.
	 *
	 * @param destOrgName the new destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the createTime.
	 *
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the createTime.
	 *
	 * @param createTime the new createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * Gets the truckType.
	 *
	 * @return the truckType
	 */
	public String getTruckType() {
		return truckType;
	}
	
	/**
	 * Sets the truckType.
	 *
	 * @param truckType the new truckType
	 */
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
	
	/**
	 * Gets the planDepartTime.
	 *
	 * @return the planDepartTime
	 */
	public Date getPlanDepartTime() {
		return planDepartTime;
	}
	
	/**
	 * Sets the planDepartTime.
	 *
	 * @param planDepartTime the new planDepartTime
	 */
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}
	
	/**
	 * Gets the actualDepartTime.
	 *
	 * @return the actualDepartTime
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}
	
	/**
	 * Sets the actualDepartTime.
	 *
	 * @param actualDepartTime the new actualDepartTime
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}
	
	/**
	 * Gets the planArriveTime.
	 *
	 * @return the planArriveTime
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}
	
	/**
	 * Sets the planArriveTime.
	 *
	 * @param planArriveTime the new planArriveTime
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
	
	/**
	 * Gets the actualArriveTime.
	 *
	 * @return the actualArriveTime
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}
	
	/**
	 * Sets the actualArriveTime.
	 *
	 * @param actualArriveTime the new actualArriveTime
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}
	
	/**
	 * Gets the vehicleSealId.
	 *
	 * @return the vehicleSealId
	 */
	public String getVehicleSealId() {
		return vehicleSealId;
	}
	
	/**
	 * Sets the vehicleSealId.
	 *
	 * @param vehicleSealId the new vehicleSealId
	 */
	public void setVehicleSealId(String vehicleSealId) {
		this.vehicleSealId = vehicleSealId;
	}
	
	/**
	 * Gets the truckDepartId.
	 *
	 * @return the truckDepartId
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}
	
	/**
	 * Sets the truckDepartId.
	 *
	 * @param truckDepartId the new truckDepartId
	 */
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}
	
	/**
	 * Gets the truckArriveId.
	 *
	 * @return the truckArriveId
	 */
	public String getTruckArriveId() {
		return truckArriveId;
	}
	
	/**
	 * Sets the truckArriveId.
	 *
	 * @param truckArriveId the new truckArriveId
	 */
	public void setTruckArriveId(String truckArriveId) {
		this.truckArriveId = truckArriveId;
	}
	
	/**
	 * Gets the beCarLoad.
	 *
	 * @return the beCarLoad
	 */
	public String getBeCarLoad() {
		return beCarLoad;
	}
	
	/**
	 * Sets the beCarLoad.
	 *
	 * @param beCarLoad the new beCarLoad
	 */
	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}
	
	/**
	 * Gets the loaderName.
	 *
	 * @return the loaderName
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * Sets the loaderName.
	 *
	 * @param loaderName the new loaderName
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * Gets the loaderCode.
	 *
	 * @return the loaderCode
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the loaderCode.
	 *
	 * @param loaderCode the new loaderCode
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the vehicleOrgCode.
	 *
	 * @return the vehicleOrgCode
	 */
	public String getVehicleOrgCode() {
		return vehicleOrgCode;
	}
	
	/**
	 * Sets the vehicleOrgCode.
	 *
	 * @param vehicleOrgCode the new vehicleOrgCode
	 */
	public void setVehicleOrgCode(String vehicleOrgCode) {
		this.vehicleOrgCode = vehicleOrgCode;
	}
	
	/**
	 * Gets the vehicleOrgName.
	 *
	 * @return the vehicleOrgName
	 */
	public String getVehicleOrgName() {
		return vehicleOrgName;
	}
	
	/**
	 * Sets the vehicleOrgName.
	 *
	 * @param vehicleOrgName the new vehicleOrgName
	 */
	public void setVehicleOrgName(String vehicleOrgName) {
		this.vehicleOrgName = vehicleOrgName;
	}
	
	/**
	 * Gets the vehicleOwnerType.
	 *
	 * @return the vehicleOwnerType
	 */
	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}
	
	/**
	 * Sets the vehicleOwnerType.
	 *
	 * @param vehicleOwnerType the new vehicleOwnerType
	 */
	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	/**
	 * @return set the runingTimes
	 */
	public Integer getRuningTimes() {
		if(runingTimes == null) {
			runingTimes = 0;
		}
		return runingTimes;
	}

	/**
	 * @param runingTimes the runingTimes to set
	 */
	public void setRuningTimes(Integer runingTimes) {
		this.runingTimes = runingTimes;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 106162 2016-06-29 新增操作人code
	 * @return
	 */
	public String getManualArriveUserCode() {
		return manualArriveUserCode;
	}

	public void setManualArriveUserCode(String manualArriveUserCode) {
		this.manualArriveUserCode = manualArriveUserCode;
	}
	/**是否有效 370210 2016-11-17**/
	public String getBillLevel() {
		return billLevel;
	}

	public void setBillLevel(String billLevel) {
		this.billLevel = billLevel;
	}
	/**单据类型（交接单、配载单） 370210 2016-11-17**/
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	
}