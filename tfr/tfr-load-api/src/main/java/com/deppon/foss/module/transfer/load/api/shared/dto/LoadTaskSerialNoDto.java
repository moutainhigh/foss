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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoadTaskSerialNoDto.java
 *  
 *  FILE NAME          :LoadTaskSerialNoDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: LoadTaskSerialnoDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;

/**
 * 1、删除扫描记录前查询装车运单及装车流水号明细
 * 2、提交前多货处理
 * @author dp-duyi
 * @date 2012-11-20 下午3:00:05
 */
public class LoadTaskSerialNoDto extends LoadSerialNoEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9000121161681617109L;
	/**扫描件数*/
	private int scanQty;
	/**装车件数*/
	private int loadQty;
	/**装车重量*/
	private double loadWeightTotal;
	/**装车体积*/
	private double loadVolumeTotal;
	/**任务编号*/
	private String taskNo;
	/**任务id*/
	private String taskId;
	/**任务状态*/
	private String taskState;
	/**运单号*/
	private String wayBillNo;
	/**库存件数*/
	private int stockQty;
	/**当前部门*/
	private String orgCode;
	
	public int getStockQty() {
		return stockQty;
	}

	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}

	/**
	 * Gets the 扫描件数.
	 *
	 * @return the 扫描件数
	 */
	public int getScanQty() {
		return scanQty;
	}
	
	/**
	 * Sets the 扫描件数.
	 *
	 * @param scanQty the new 扫描件数
	 */
	public void setScanQty(int scanQty) {
		this.scanQty = scanQty;
	}
	
	/**
	 * Gets the 装车件数.
	 *
	 * @return the 装车件数
	 */
	public int getLoadQty() {
		return loadQty;
	}
	
	/**
	 * Sets the 装车件数.
	 *
	 * @param loadQty the new 装车件数
	 */
	public void setLoadQty(int loadQty) {
		this.loadQty = loadQty;
	}
	
	/**
	 * Gets the 装车重量.
	 *
	 * @return the 装车重量
	 */
	public double getLoadWeightTotal() {
		return loadWeightTotal;
	}
	
	/**
	 * Sets the 装车重量.
	 *
	 * @param loadWeightTotal the new 装车重量
	 */
	public void setLoadWeightTotal(double loadWeightTotal) {
		this.loadWeightTotal = loadWeightTotal;
	}
	
	/**
	 * Gets the 装车体积.
	 *
	 * @return the 装车体积
	 */
	public double getLoadVolumeTotal() {
		return loadVolumeTotal;
	}
	
	/**
	 * Sets the 装车体积.
	 *
	 * @param loadVolumeTotal the new 装车体积
	 */
	public void setLoadVolumeTotal(double loadVolumeTotal) {
		this.loadVolumeTotal = loadVolumeTotal;
	}
	
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the 任务编号.
	 *
	 * @param taskNo the new 任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * Gets the 任务id.
	 *
	 * @return the 任务id
	 */
	public String getTaskId() {
		return taskId;
	}
	
	/**
	 * Sets the 任务id.
	 *
	 * @param taskId the new 任务id
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * Gets the 任务状态.
	 *
	 * @return the 任务状态
	 */
	public String getTaskState() {
		return taskState;
	}
	
	/**
	 * Sets the 任务状态.
	 *
	 * @param taskState the new 任务状态
	 */
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * Sets the 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * Gets the 当前部门.
	 *
	 * @return the 当前部门
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * Sets the 当前部门.
	 *
	 * @param scanQty the new 当前部门
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}