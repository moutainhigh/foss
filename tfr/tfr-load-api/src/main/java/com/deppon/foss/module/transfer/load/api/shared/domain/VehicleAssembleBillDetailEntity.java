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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/VehicleAssembleBillDetailEntity.java
 *  
 *  FILE NAME          :VehicleAssembleBillDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 配载单分录（交接单）实体
 * @author 045923-foss-shiwei
 * @date 2012-11-8 上午10:14:44
 */
public class VehicleAssembleBillDetailEntity extends BaseEntity{
	
	private static final long serialVersionUID = 4873615285117507623L;
	
	//交接单号
    private String handOverBillNo;
    //到达部门名称
    private String arriveDept;
    //配载单ID
    private String vehicleAssembleBillId;
    /*
     * 是否包交接单
     */
    private String bePackage;
    //车牌号
    private String vehicleNo;
    
    
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getBePackage() {
		return bePackage;
	}
	public void setBePackage(String bePackage) {
		this.bePackage = bePackage;
	}
	public String getVehicleAssembleBillId() {
		return vehicleAssembleBillId;
	}
	public void setVehicleAssembleBillId(String vehicleAssembleBillId) {
		this.vehicleAssembleBillId = vehicleAssembleBillId;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getArriveDept() {
		return arriveDept;
	}
	public void setArriveDept(String arriveDept) {
		this.arriveDept = arriveDept;
	}

}