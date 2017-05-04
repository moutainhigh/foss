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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryHandOverBillConditionDto.java
 *  
 *  FILE NAME          :QueryHandOverBillConditionDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/** 
 * @className: QueryHandOverBillConditionDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 接收查询交接单界面查询条件dto
 * @date: 2012-10-23 下午5:06:10
 * 
 */
public class QueryHandOverBillConditionDto implements Serializable {

	private static final long serialVersionUID = -5100098282437828209L;
	
	//交接单号
	private String handOverBillNo;
	//出发部门
	private String departDept;
	//到达部门
	private String arriveDept;
	//交接类型
	private String handOverType;
	//是否已到达
	private String arriveCondition;
	//车牌号
	private String vehicleNo;
	//起始交接时间
	private Date beginHandOverTime;
	//截止交接时间
	private Date endHandOverTime;
	//当前部门
	private String currentDept;
	//转货类型
	private String tranGoodsType;
	//挂牌号
	private String trailerVehicleNo;
	//发车计划id
	private String truckPlanDetailVehicleNo;
	
	
	public String getTruckPlanDetailVehicleNo() {
		return truckPlanDetailVehicleNo;
	}
	public void setTruckPlanDetailVehicleNo(String truckPlanDetailVehicleNo) {
		this.truckPlanDetailVehicleNo = truckPlanDetailVehicleNo;
	}
	public String getTrailerVehicleNo() {
		return trailerVehicleNo;
	}
	public void setTrailerVehicleNo(String trailerVehicleNo) {
		this.trailerVehicleNo = trailerVehicleNo;
	}
	public String getCurrentDept() {
		return currentDept;
	}
	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getDepartDept() {
		return departDept;
	}
	public void setDepartDept(String departDept) {
		this.departDept = departDept;
	}
	public String getArriveDept() {
		return arriveDept;
	}
	public void setArriveDept(String arriveDept) {
		this.arriveDept = arriveDept;
	}
	public String getHandOverType() {
		return handOverType;
	}
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	public String getArriveCondition() {
		return arriveCondition;
	}
	public void setArriveCondition(String arriveCondition) {
		this.arriveCondition = arriveCondition;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public Date getBeginHandOverTime() {
		return beginHandOverTime;
	}
	public void setBeginHandOverTime(Date beginHandOverTime) {
		this.beginHandOverTime = beginHandOverTime;
	}
	public Date getEndHandOverTime() {
		return endHandOverTime;
	}
	public void setEndHandOverTime(Date endHandOverTime) {
		this.endHandOverTime = endHandOverTime;
	}
	public String getTranGoodsType() {
		return tranGoodsType;
	}
	public void setTranGoodsType(String tranGoodsType) {
		this.tranGoodsType = tranGoodsType;
	}
}