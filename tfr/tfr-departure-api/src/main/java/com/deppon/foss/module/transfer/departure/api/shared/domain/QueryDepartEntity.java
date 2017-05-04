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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/QueryDepartEntity.java
 *  
 *  FILE NAME          :QueryDepartEntity.java
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
import java.util.List;
import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 查询出发条件实体
 */
public class QueryDepartEntity extends BaseEntity {
	private static final long serialVersionUID = -3703272448562684594L;
	/************* 车牌号 **************/
	private String vehicleNo;
	/************* 司机编码 **************/
	private String driverCode;
	/************* 司机姓名**************/
	private String driverName;
	/************* 司机电话 **************/
	private String driverPhone;
	/************* 状态 **************/
	private String status;
	/************* 状态(复数) **************/
	private List<String> statuses;
	/************* 放行类型 **************/
	private String departType;
	/************* 放行事项**************/
	private List<String> departTypeIds;
	/***************** 放行原因（Lms表查询用） ********************/
	private List<String> departPlanType;
	private String ids;
	private List<String> departIds;
	/************* 放行人编码 **************/
	private String applyUserCode;
	/************* 人工放行人编码 **************/
	private String manualDepartUserCode;
	/************* pda放行人编码 **************/
	private String pdaDepartUserCode;
	/************* 申请部门 **************/
	private String applyOrgCode;
	/************* 明细信息时传到后台的ID ********************/
	private String keyId;
	/************* 取消申请时传到后台的id的值 **************/
	private List<String> idsForCancle;
	/************* LMS放行时传到后台的id值 **************/
	private List<String> idsForLMS;
	/************* 车辆归属部门 **************/
	private String truckOrgCode;
	/************* 开始放行时间 **************/
	private String beginDate;
	/************* 截至放行时间 **************/
	private String endDate;
	/**************** 用于后台数据库的查询 ********************/
	private Date beginTime;
	/**************** 用于后台数据库的查询 ********************/
	private Date endTime;
	/*** 查询车辆明细时判断是否是放行信息 */
	private List<String> departTypeList;
	/************** 当前部门 ***************/
	private String currentOrgCode;
	/************** 当前部门（复数） ***************/
	private List<String> currentOrgCodes;
	/************* 是否放行 **************/
	private String isDepart;
	/************* 单据类型**************/
	private String billLevel;
	/************* 交接单类型**************/
	private String handoverType;
	/************* 出发部门**************/
	private String origOrgCode;
	/************* 到达部门**************/
	private String destOrgCode;
	/************* 车辆任务类型**************/
	private String departItems;
	/************* 任务车辆ID**************/
	private String truckTaskId;
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepartType() {
		return departType;
	}
	public void setDepartType(String departType) {
		this.departType = departType;
	}
	public List<String> getDepartTypeIds() {
		return departTypeIds;
	}
	public void setDepartTypeIds(List<String> departTypeIds) {
		this.departTypeIds = departTypeIds;
	}
	public List<String> getDepartPlanType() {
		return departPlanType;
	}
	public void setDepartPlanType(List<String> departPlanType) {
		this.departPlanType = departPlanType;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getApplyUserCode() {
		return applyUserCode;
	}
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}
	public String getManualDepartUserCode() {
		return manualDepartUserCode;
	}
	public void setManualDepartUserCode(String manualDepartUserCode) {
		this.manualDepartUserCode = manualDepartUserCode;
	}
	public String getPdaDepartUserCode() {
		return pdaDepartUserCode;
	}
	public void setPdaDepartUserCode(String pdaDepartUserCode) {
		this.pdaDepartUserCode = pdaDepartUserCode;
	}
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public List<String> getIdsForCancle() {
		return idsForCancle;
	}
	public void setIdsForCancle(List<String> idsForCancle) {
		this.idsForCancle = idsForCancle;
	}
	public List<String> getIdsForLMS() {
		return idsForLMS;
	}
	public void setIdsForLMS(List<String> idsForLMS) {
		this.idsForLMS = idsForLMS;
	}
	public String getTruckOrgCode() {
		return truckOrgCode;
	}
	public void setTruckOrgCode(String truckOrgCode) {
		this.truckOrgCode = truckOrgCode;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public List<String> getDepartTypeList() {
		return departTypeList;
	}
	public void setDepartTypeList(List<String> departTypeList) {
		this.departTypeList = departTypeList;
	}
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}
	public List<String> getCurrentOrgCodes() {
		return currentOrgCodes;
	}
	public void setCurrentOrgCodes(List<String> currentOrgCodes) {
		this.currentOrgCodes = currentOrgCodes;
	}
	public String getIsDepart() {
		return isDepart;
	}
	public void setIsDepart(String isDepart) {
		this.isDepart = isDepart;
	}
	public String getBillLevel() {
		return billLevel;
	}
	public void setBillLevel(String billLevel) {
		this.billLevel = billLevel;
	}
	public String getHandoverType() {
		return handoverType;
	}
	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDepartItems() {
		return departItems;
	}
	public void setDepartItems(String departItems) {
		this.departItems = departItems;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public List<String> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	public String getTruckTaskId() {
		return truckTaskId;
	}
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	public List<String> getDepartIds() {
		return departIds;
	}
	public void setDepartIds(List<String> departIds) {
		this.departIds = departIds;
	}
	

}