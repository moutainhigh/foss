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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/vo/AssignUnloadTaskVo.java
 *  
 *  FILE NAME          :AssignUnloadTaskVo.java
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
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.vo
 * FILE    NAME: AssignUnloadTaskVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;

/**
 * AssignUnloadTaskVo
 * @author dp-duyi
 * @date 2012-10-19 下午2:13:18
 */
public class AssignUnloadTaskVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1417920862021849298L;
	/**bills*/
	private List<ArriveBillDto> bills;
	/**vehicles*/
	private List<AssignUnloadTaskTotalDto> vehicles;
	/**loaders*/
	private List<LoaderDto> loaders;
	/**tasks*/
	private List<AssignUnloadTaskDto> tasks;
	/**bill*/
	private ArriveBillDto bill;
	/**vehicle*/
	private AssignUnloadTaskTotalDto vehicle;
	/**loader*/
	private LoaderDto loader;
	/**task*/
	private AssignUnloadTaskDto task;
	private List<AssignUnloadTaskTotalDto> totalTasks;
	
	//未分配总票数
	private int assignWayBillQtyTotal;
	//未分配总重量
	private double assignWeightTotal;
	//未分配总体积
	private double assignVolumeTotal; 
	//未分配总件数
	private int assignGoodsQtyTotal;
	//提示语
	private String remind;
	//月台号
	private String platformNo;
	
	public List<AssignUnloadTaskTotalDto> getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(List<AssignUnloadTaskTotalDto> totalTasks) {
		this.totalTasks = totalTasks;
	}

	/**
	 * Gets the bills.
	 *
	 * @return the bills
	 */
	public List<ArriveBillDto> getBills() {
		return bills;
	}
	
	/**
	 * Sets the bills.
	 *
	 * @param bills the new bills
	 */
	public void setBills(List<ArriveBillDto> bills) {
		this.bills = bills;
	}
	
	/**
	 * Gets the vehicles.
	 *
	 * @return the vehicles
	 */
	public List<AssignUnloadTaskTotalDto> getVehicles() {
		return vehicles;
	}
	
	/**
	 * Sets the vehicles.
	 *
	 * @param vehicles the new vehicles
	 */
	public void setVehicles(List<AssignUnloadTaskTotalDto> vehicles) {
		this.vehicles = vehicles;
	}
	
	/**
	 * Gets the loaders.
	 *
	 * @return the loaders
	 */
	public List<LoaderDto> getLoaders() {
		return loaders;
	}
	
	/**
	 * Sets the loaders.
	 *
	 * @param loaders the new loaders
	 */
	public void setLoaders(List<LoaderDto> loaders) {
		this.loaders = loaders;
	}
	
	/**
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public List<AssignUnloadTaskDto> getTasks() {
		return tasks;
	}
	
	/**
	 * Sets the tasks.
	 *
	 * @param tasks the new tasks
	 */
	public void setTasks(List<AssignUnloadTaskDto> tasks) {
		this.tasks = tasks;
	}
	
	/**
	 * Gets the bill.
	 *
	 * @return the bill
	 */
	public ArriveBillDto getBill() {
		return bill;
	}
	
	/**
	 * Sets the bill.
	 *
	 * @param bill the new bill
	 */
	public void setBill(ArriveBillDto bill) {
		this.bill = bill;
	}
	
	/**
	 * Gets the vehicle.
	 *
	 * @return the vehicle
	 */
	public AssignUnloadTaskTotalDto getVehicle() {
		return vehicle;
	}
	
	/**
	 * Sets the vehicle.
	 *
	 * @param vehicle the new vehicle
	 */
	public void setVehicle(AssignUnloadTaskTotalDto vehicle) {
		this.vehicle = vehicle;
	}
	
	/**
	 * Gets the loader.
	 *
	 * @return the loader
	 */
	public LoaderDto getLoader() {
		return loader;
	}
	
	/**
	 * Sets the loader.
	 *
	 * @param loader the new loader
	 */
	public void setLoader(LoaderDto loader) {
		this.loader = loader;
	}
	
	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public AssignUnloadTaskDto getTask() {
		return task;
	}
	
	/**
	 * Sets the task.
	 *
	 * @param task the new task
	 */
	public void setTask(AssignUnloadTaskDto task) {
		this.task = task;
	}
	
	public int getAssignWayBillQtyTotal() {
		return assignWayBillQtyTotal;
	}
	public void setAssignWayBillQtyTotal(int assignWayBillQtyTotal) {
		this.assignWayBillQtyTotal = assignWayBillQtyTotal;
	}
	public double getAssignWeightTotal() {
		return assignWeightTotal;
	}
	public double getAssignVolumeTotal() {
		return assignVolumeTotal;
	}
	public int getAssignGoodsQtyTotal() {
		return assignGoodsQtyTotal;
	}
	public void setAssignWeightTotal(double assignWeightTotal) {
		this.assignWeightTotal = assignWeightTotal;
	}
	public void setAssignVolumeTotal(double assignVolumeTotal) {
		this.assignVolumeTotal = assignVolumeTotal;
	}
	public void setAssignGoodsQtyTotal(int assignGoodsQtyTotal) {
		this.assignGoodsQtyTotal = assignGoodsQtyTotal;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	/**
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * @param platformNo the platformNo to set
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
}