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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateHandOverBillSerialNoPreHandOverStateDto.java
 *  
 *  FILE NAME          :UpdateHandOverBillSerialNoPreHandOverStateDto.java
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
 * @className: UpdateHandOverBillSerialNoPreHandOverStateDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 更新交接单中流水号的预配状态时传入的对象
 * @date: 2012-11-28 下午7:42:20
 * 
 */
public class UpdateHandOverBillSerialNoPreHandOverStateDto implements Serializable {

	private static final long serialVersionUID = 4069194222476269243L;
	
	//交接单号
	private String handOverBillNo;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//预配状态
	private String preHandOverStatus;
	
	public String getPreHandOverStatus() {
		return preHandOverStatus;
	}
	public void setPreHandOverStatus(String preHandOverStatus) {
		this.preHandOverStatus = preHandOverStatus;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}