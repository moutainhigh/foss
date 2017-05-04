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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryHandOverBillDto.java
 *  
 *  FILE NAME          :QueryHandOverBillDto.java
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
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;

/** 
 * @className: QueryHandOverBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 查询交接单，交接单信息dto，由交接单主表、任务车辆明细表构造而成
 * @date: 2012-10-23 上午11:22:27
 * 
 */
public class QueryHandOverBillDto extends HandOverBillEntity implements Serializable {

	private static final long serialVersionUID = -2066355499072897176L;
	//配载车次号
	private String vehicleAssembleNo;
	
	//出发时间
	private Date departTime;
	
	//到达时间
	private Date arriveTime;
	
	//外发代理
	private String agency;
	
	//件号
	private String cargoNo;
	
	//件类型
	private String cargoType;
	
	/**
	 * 交接单运单明细
	 */
	private List<HandOverBillDetailEntity> detailList;
	
	public List<HandOverBillDetailEntity> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<HandOverBillDetailEntity> detailList) {
		this.detailList = detailList;
	}
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	@DateFormat
	public Date getDepartTime() {
		return departTime;
	}
	@DateFormat
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	@DateFormat
	public Date getArriveTime() {
		return arriveTime;
	}
	@DateFormat
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	

}