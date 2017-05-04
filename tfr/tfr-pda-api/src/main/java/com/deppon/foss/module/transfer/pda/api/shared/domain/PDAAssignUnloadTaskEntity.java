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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/PDAAssignUnloadTaskEntity.java
 *  
 *  FILE NAME          :PDAAssignUnloadTaskEntity.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.domain
 * FILE    NAME: PDAAssignLoadTaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * PDA接口返回值:已分配卸车任务
 * @author dp-duyi
 * @date 2012-11-6 下午12:25:29
 */
public class PDAAssignUnloadTaskEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2439748550673685777L;
	/**车牌号*/
	private String vehicleNo;
	/**卸车单据*/
	private List<PDAAssignUnloadBillEntity> bills;
	
	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the 卸车单据.
	 *
	 * @return the 卸车单据
	 */
	public List<PDAAssignUnloadBillEntity> getBills() {
		return bills;
	}
	
	/**
	 * Sets the 卸车单据.
	 *
	 * @param bills the new 卸车单据
	 */
	public void setBills(List<PDAAssignUnloadBillEntity> bills) {
		this.bills = bills;
	}
}