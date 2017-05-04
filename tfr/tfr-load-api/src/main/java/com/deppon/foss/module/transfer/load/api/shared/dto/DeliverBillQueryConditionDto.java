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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/DeliverBillQueryConditionDto.java
 *  
 *  FILE NAME          :DeliverBillQueryConditionDto.java
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
 * DeliverBillQueryConditionDto
 * @author dp-duyi
 * @date 2012-12-24 上午11:03:22
 */
public class DeliverBillQueryConditionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7014531801048804145L;
	
	/**单据编号*/
	private String billNo;
	/**车牌号*/
	private String vehicleNo;
	/**开始时间*/
	private String createTimeBegin;
	/**结束时间*/
	private String createTimeEnd;
	/**登录部门*/
	private String loginOrgCode;
	/**外场*/
	private String transferCenterCode;
	/**派送单状态*/
	private String deliverState;
	
	/**
	 * 非正常库区的库区类型
	 */
	private List<String> unnormalGoodsAreaTypeList;
	
	public List<String> getUnnormalGoodsAreaTypeList() {
		return unnormalGoodsAreaTypeList;
	}

	public void setUnnormalGoodsAreaTypeList(List<String> unnormalGoodsAreaTypeList) {
		this.unnormalGoodsAreaTypeList = unnormalGoodsAreaTypeList;
	}

	/**
	 * Gets the 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the 单据编号.
	 *
	 * @param billNo the new 单据编号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
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
	 * Gets the 开始时间.
	 *
	 * @return the 开始时间
	 */
	public String getCreateTimeBegin() {
		return createTimeBegin;
	}
	
	/**
	 * Sets the 开始时间.
	 *
	 * @param createTimeBegin the new 开始时间
	 */
	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	
	/**
	 * Gets the 结束时间.
	 *
	 * @return the 结束时间
	 */
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	
	/**
	 * Sets the 结束时间.
	 *
	 * @param createTimeEnd the new 结束时间
	 */
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	
	/**
	 * Gets the 登录部门.
	 *
	 * @return the 登录部门
	 */
	public String getLoginOrgCode() {
		return loginOrgCode;
	}
	
	/**
	 * Sets the 登录部门.
	 *
	 * @param loginOrgCode the new 登录部门
	 */
	public void setLoginOrgCode(String loginOrgCode) {
		this.loginOrgCode = loginOrgCode;
	}
	
	/**
	 * Gets the 外场.
	 *
	 * @return the 外场
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}
	
	/**
	 * Sets the 外场.
	 *
	 * @param transferCenterCode the new 外场
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}
	
	/**
	 * Gets the 派送单状态.
	 *
	 * @return the 派送单状态
	 */
	public String getDeliverState() {
		return deliverState;
	}
	
	/**
	 * Sets the 派送单状态.
	 *
	 * @param deliverState the new 派送单状态
	 */
	public void setDeliverState(String deliverState) {
		this.deliverState = deliverState;
	}
}