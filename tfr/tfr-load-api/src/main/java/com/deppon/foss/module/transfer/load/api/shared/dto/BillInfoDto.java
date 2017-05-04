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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/BillInfoDto.java
 *  
 *  FILE NAME          :BillInfoDto.java
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

/**
 * 单据信息Dto
 * @author ibm-zhangyixin
 * @date 2013-1-8 上午10:56:58
 */
public class BillInfoDto implements Serializable {
	
	private static final long serialVersionUID = -2879009541863846514L;

	/**车牌号**/
	private String vehicleNo;
	
	/**线路**/
	private String lineName;

	/**司机名称**/
	private String driverName;

	/**车辆状态**/
	private String status;

	/**单据编号**/
	private String billNo;

	/**
	 * 获取 车牌号*.
	 *
	 * @return the 车牌号*
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号*.
	 *
	 * @param vehicleNo the new 车牌号*
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 线路*.
	 *
	 * @return the 线路*
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 线路*.
	 *
	 * @param lineName the new 线路*
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 司机名称*.
	 *
	 * @return the 司机名称*
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机名称*.
	 *
	 * @param driverName the new 司机名称*
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 单据编号*.
	 *
	 * @return the 单据编号*
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置 单据编号*.
	 *
	 * @param billNo the new 单据编号*
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**   
	 * status   
	 *   
	 * @return  the status   
	 */
	
	public String getStatus() {
		return status;
	}

	/**   
	 * @param status the status to set
	 * Date:2013-5-15下午5:07:05
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}