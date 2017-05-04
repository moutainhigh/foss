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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/NewHandOverBillDto.java
 *  
 *  FILE NAME          :NewHandOverBillDto.java
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

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;

/** 
 * @className: NewHandOverBillVo
 * @author: ShiWei shiwei@outlook.com
 * @description: Vo对象，用于新增时接收前台传入的交接单信息
 * @date: 2012-10-17 上午8:24:19
 * 
 */

public class NewHandOverBillDto implements Serializable {

	private static final long serialVersionUID = -7990369287797626170L;
	
	//交接单基本信息
	private HandOverBillEntity handOverBillEntity;
	//运单库存信息列表
	private List<HandOverBillDetailEntity> waybillStockList;
	//流水号库存Map
	private List<HandOverBillSerialNoDetailEntity> serialNoStockList;
	
	public HandOverBillEntity getHandOverBillEntity() {
		return handOverBillEntity;
	}
	public void setHandOverBillEntity(HandOverBillEntity handOverBillEntity) {
		this.handOverBillEntity = handOverBillEntity;
	}
	public List<HandOverBillDetailEntity> getWaybillStockList() {
		return waybillStockList;
	}
	public void setWaybillStockList(List<HandOverBillDetailEntity> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}
	public List<HandOverBillSerialNoDetailEntity> getSerialNoStockList() {
		return serialNoStockList;
	}
	public void setSerialNoStockList(
			List<HandOverBillSerialNoDetailEntity> serialNoStockList) {
		this.serialNoStockList = serialNoStockList;
	}
}