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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadTaskDto.java
 *  
 *  FILE NAME          :LoadTaskDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: LoadTaskDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.List;

/**
 * PDA传给foss的装车任务参数
 * @author dp-332219
 * @date 2016-11-21
 */
public class LoadSaleTaskDto extends PDATaskDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**任务类型*/
	private String loadTaskType;
	/**货物类型*/
	private String goodsType;
	/**派送单号*/
	private String deliverBillNo;
	/**目的站编码*/
	private List<String> destOrgCodes;
	/**快递员工号**/
	private String tallyerCode;
	

	
	/**
	 * Gets the 任务类型.
	 *
	 * @return the 任务类型
	 */
	public String getLoadTaskType() {
		return loadTaskType;
	}
	
	/**
	 * Sets the 任务类型.
	 *
	 * @param loadTaskType the new 任务类型
	 */
	public void setLoadTaskType(String loadTaskType) {
		this.loadTaskType = loadTaskType;
	}
	
	/**
	 * Gets the 货物类型.
	 *
	 * @return the 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}
	
	/**
	 * Sets the 货物类型.
	 *
	 * @param goodsType the new 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	/**
	 * Gets the 派送单号.
	 *
	 * @return the 派送单号
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}
	
	/**
	 * Sets the 派送单号.
	 *
	 * @param deliverBillNo the new 派送单号
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}
	
	/**
	 * Gets the 目的站编码.
	 *
	 * @return the 目的站编码
	 */
	public List<String> getDestOrgCodes() {
		return destOrgCodes;
	}
	
	/**
	 * Sets the 目的站编码.
	 *
	 * @param destOrgCodes the new 目的站编码
	 */
	public void setDestOrgCodes(List<String> destOrgCodes) {
		this.destOrgCodes = destOrgCodes;
	}

	public String getTallyerCode() {
		return tallyerCode;
	}

	public void setTallyerCode(String tallyerCode) {
		this.tallyerCode = tallyerCode;
	}

	
	
	
}