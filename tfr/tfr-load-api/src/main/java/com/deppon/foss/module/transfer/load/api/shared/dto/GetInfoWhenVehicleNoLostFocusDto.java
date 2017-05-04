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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/GetInfoWhenVehicleNoLostFocusDto.java
 *  
 *  FILE NAME          :GetInfoWhenVehicleNoLostFocusDto.java
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

import java.math.BigDecimal;

/** 
 * @className: GetInfoWhenVehicleNoLostFocusDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 当配载单中“车牌号”失去焦点时，获取信息组成的dto
 * @date: 2013-2-25 下午2:11:45
 * 
 */
public class GetInfoWhenVehicleNoLostFocusDto {
	
	/**
	 * 司机code
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
	
	/**
	 * 班次
	 */
	private String frequencyNo;
	
	/**
	 * 货柜号
	 */
	private String containerNo;
	
	/**
	 * 运行时数
	 */
	private BigDecimal runHours;
	
	/**
	 * 线路虚拟编码
	 */
	private String lineNo;
	
	/**
	 * 挂牌号
	 * */
	private String trailerVehicleNo;
	
	

	public String getTrailerVehicleNo() {
		return trailerVehicleNo;
	}

	public void setTrailerVehicleNo(String trailerVehicleNo) {
		this.trailerVehicleNo = trailerVehicleNo;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
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

	public String getFrequencyNo() {
		return frequencyNo;
	}

	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public BigDecimal getRunHours() {
		return runHours;
	}

	public void setRunHours(BigDecimal runHours) {
		this.runHours = runHours;
	}
	
}