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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/TruckActionDetailEntity.java
 *  
 *  FILE NAME          :TruckActionDetailEntity.java
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
public class TruckActionDetailEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********绑定类型************/
	private String bundType;
	
	/**********任务车辆明细ID************/
	private String truckTaskDetailId;
	
	/**********状态************/
	private String status;
	
	/**********创建时间************/
	private Date createTime;
	
	/**********到达部门************/
	private String destOrgCode;
	
	/**********是否整车************/
	private String beCarLoad;
	
	/**********车辆归属类型************/
	private String vehicleOwnerType;
	
	/**********预计到达时间************/
	private Date planArriveTime;
	
	/**********预计出发时间************/
	private Date planDepartTime;
	
	/**********实际出发时间************/
	private Date actualDepartTime;
	/**********实际到达时间************/
	private Date actualArriveTime;
	
	/**********任务1************/
	private String job1;
	
	/**********任务2************/
	private String job2;
	
	/**********任务3************/
	private String job3;
	
	/**********任务4************/
	private String job4;
	
	/**********操作人名称************/
	private String operatorName;
	
	/**********操作人工号************/
	private String operatorCode;
	
	/**
	 * 运行时长
	 */
	private Integer runingTimes;

	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	/**
	 * 获取 ********任务车辆明细ID***********.
	 *
	 * @return the ********任务车辆明细ID***********
	 */
	public String getTruckTaskDetailId(){
		return truckTaskDetailId;
	}

	/**
	 * 设置 ********任务车辆明细ID***********.
	 *
	 * @param truckTaskDetailId the new ********任务车辆明细ID***********
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId){
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ********创建时间***********.
	 *
	 * @return the ********创建时间***********
	 */
	public Date getCreateTime(){
		return createTime;
	}

	/**
	 * 设置 ********创建时间***********.
	 *
	 * @param createTime the new ********创建时间***********
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	/**
	 * 获取 ********绑定类型***********.
	 *
	 * @return the ********绑定类型***********
	 */
	public String getBundType(){
		return bundType;
	}

	/**
	 * 设置 ********绑定类型***********.
	 *
	 * @param bundType the new ********绑定类型***********
	 */
	public void setBundType(String bundType){
		this.bundType = bundType;
	}

	/**
	 * 获取 ********状态***********.
	 *
	 * @return the ********状态***********
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 设置 ********状态***********.
	 *
	 * @param status the new ********状态***********
	 */
	public void setStatus(String status){
		this.status = status;
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
	 * 获取 ********任务1***********.
	 *
	 * @return the ********任务1***********
	 */
	public String getJob1(){
		return job1;
	}

	/**
	 * 设置 ********任务1***********.
	 *
	 * @param job1 the new ********任务1***********
	 */
	public void setJob1(String job1){
		this.job1 = job1;
	}

	/**
	 * 获取 ********任务2***********.
	 *
	 * @return the ********任务2***********
	 */
	public String getJob2(){
		return job2;
	}

	/**
	 * 设置 ********任务2***********.
	 *
	 * @param job2 the new ********任务2***********
	 */
	public void setJob2(String job2){
		this.job2 = job2;
	}

	/**
	 * 获取 ********任务3***********.
	 *
	 * @return the ********任务3***********
	 */
	public String getJob3(){
		return job3;
	}

	/**
	 * 设置 ********任务3***********.
	 *
	 * @param job3 the new ********任务3***********
	 */
	public void setJob3(String job3){
		this.job3 = job3;
	}

	public String getBeCarLoad(){
		return beCarLoad;
	}

	public void setBeCarLoad(String beCarLoad){
		this.beCarLoad = beCarLoad;
	}

	public String getJob4(){
		return job4;
	}

	public void setJob4(String job4){
		this.job4 = job4;
	}

	/**
	 * 
	 * <p>TODO(获取操作人姓名)</p> 
	 * @author alfred
	 * @date 2013-8-16 下午2:18:16
	 * @return
	 * @see
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 
	 * <p>(设置操作人姓名)</p> 
	 * @author alfred
	 * @date 2013-8-16 下午2:18:05
	 * @param operatorName
	 * @see
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 
	 * <p>(获取操作人工号)</p> 
	 * @author alfred
	 * @date 2013-8-16 下午2:17:57
	 * @return
	 * @see
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 
	 * @author alfred
	 * @date 2013-8-16 下午2:18:22
	 * @param operatorCode
	 * @see
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	public Date getPlanDepartTime() {
		return planDepartTime;
	}

	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}

	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
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
}