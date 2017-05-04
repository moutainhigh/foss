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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/DeleteHandOverBillFromVehicleAssembleBillDto.java
 *  
 *  FILE NAME          :DeleteHandOverBillFromVehicleAssembleBillDto.java
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
 * @className: DeleteHandOverBillFromVehicleAssembleBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 批量删除配载单中交接单时使用
 * @date: 2012-11-20 上午10:39:39
 * 
 */
public class DeleteHandOverBillFromVehicleAssembleBillDto implements Serializable {

	private static final long serialVersionUID = -3577018885981408673L;
	//配载单ID
	private String vehicleAssembleBillId;
	//交接单编号
	private List<String> handOverBillList;
	
	public String getVehicleAssembleBillId() {
		return vehicleAssembleBillId;
	}
	public void setVehicleAssembleBillId(String vehicleAssembleBillId) {
		this.vehicleAssembleBillId = vehicleAssembleBillId;
	}
	public List<String> getHandOverBillList() {
		return handOverBillList;
	}
	public void setHandOverBillList(List<String> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}

}