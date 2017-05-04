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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto.java
 *  
 *  FILE NAME          :UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto.java
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
import java.util.List;

/** 
 * @className: UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description : 用于修改配载单的车牌号时批量更新配载单中的交接单的司机及车牌号信息
 * @date: 2013-1-9 下午8:20:25
 * 
 */
public class UpdateVehicleNoAndDriverInfoFromVehicleAssembleBillDto implements Serializable{

	private static final long serialVersionUID = -9048809842478852630L;
	
	/**
	 * 配载车次号
	 */
	private String vehicleAssembleNo;
	
	/**
	 * 交接单号list
	 */
	private List<String> handOverBillNoList;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 司机工号
	 */
	private String driverCode;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机电话
	 */
	private String driverTel;

	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	public List<String> getHandOverBillNoList() {
		return handOverBillNoList;
	}

	public void setHandOverBillNoList(List<String> handOverBillNoList) {
		this.handOverBillNoList = handOverBillNoList;
	}

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

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

}