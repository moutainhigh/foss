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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateHandOverBillVehicleNoDto.java
 *  
 *  FILE NAME          :UpdateHandOverBillVehicleNoDto.java
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

import java.util.List;

/** 
 * @className: UpdateHandOverBillVehicleNoDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 修改配载单车牌号时，批量更新配载单下交接单的车牌号
 * @date: 2012-12-6 上午11:27:06
 * 
 */
public class UpdateHandOverBillVehicleNoDto {
	//新的车牌号
	private String vehicleNo;
	//交接单号list
	private List<String> handOverBillNoList;
	
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public List<String> getHandOverBillNoList() {
		return handOverBillNoList;
	}
	public void setHandOverBillNoList(List<String> handOverBillNoList) {
		this.handOverBillNoList = handOverBillNoList;
	}
	
}