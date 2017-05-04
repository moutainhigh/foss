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
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/dto/LdpAgencySystemReportResultDto.java
 * 
 *  FILE NAME     :LdpAgencySystemReportResultDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/

package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 落地配全盘报表查询结果Dto
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-29 上午9:18:36
 */
public class LdpAgencySystemReportResultDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4935911195689100765L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 收货部门
	 */
	private String receiveOrgCode;
	
	/**
	 * 收货日期
	 */
	private Date billDate;
	
	/**
	 * 付款方式
	 */
	private String paymentMethod;
	
	/**
	 * 配载时间
	 */
	private Date handOverTime;
	
	/**
	 * 代理名称
	 */
	private String agencyCompanyName;
	
	/**
	 * 件数
	 */
	private Long amount;
	
	/**
	 * 重量(KG)
	 */
	private BigDecimal totalWeight;
	
	/**
	 * 体积(立方米)
	 */
	private BigDecimal totalVolume;
	
	/**
	 * 目的站
	 */
	private String targetOrgName;
	
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	
	/**
	 * 是否外发
	 */
	private String registerFlag;
	
	/**
	 * 外发单审核状态
	 */
	private String auditStatus;

	/**
	 * 数据库总记录条数
	 */
	private int totalRecordsInDB;
	
	/**
	 * 运单最终配载部门编码
	 */
	private String lastLoadOrgCode;
	
	
	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param  waybillNo  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param  receiveOrgCode  
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return billDate
	 */
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * @param  billDate  
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
	 * @return paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param  paymentMethod  
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return handOverTime
	 */
	public Date getHandOverTime() {
		return handOverTime;
	}

	/**
	 * @param  handOverTime  
	 */
	public void setHandOverTime(Date handOverTime) {
		this.handOverTime = handOverTime;
	}

	/**
	 * @return agencyCompanyName
	 */
	public String getAgencyCompanyName() {
		return agencyCompanyName;
	}

	/**
	 * @param  agencyCompanyName  
	 */
	public void setAgencyCompanyName(String agencyCompanyName) {
		this.agencyCompanyName = agencyCompanyName;
	}

	/**
	 * @return amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param  amount  
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}


	/**
	 * @return targetOrgName
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}

	/**
	 * @param  targetOrgName  
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	/**
	 * @return arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * @param  arriveTime  
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * @return registerFlag
	 */
	public String getRegisterFlag() {
		return registerFlag;
	}

	/**
	 * @param  registerFlag  
	 */
	public void setRegisterFlag(String registerFlag) {
		this.registerFlag = registerFlag;
	}

	/**
	 * @return auditStatus
	 */
	public String getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param  auditStatus  
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return totalRecordsInDB
	 */
	public int getTotalRecordsInDB() {
		return totalRecordsInDB;
	}

	/**
	 * @param  totalRecordsInDB  
	 */
	public void setTotalRecordsInDB(int totalRecordsInDB) {
		this.totalRecordsInDB = totalRecordsInDB;
	}

	
	/**
	 * @return  the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	
	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	
	/**
	 * @return  the totalWeight
	 */
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	
	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	
	/**
	 * @return  the totalVolume
	 */
	public BigDecimal getTotalVolume() {
		return totalVolume;
	}

	
	/**
	 * @param totalVolume the totalVolume to set
	 */
	public void setTotalVolume(BigDecimal totalVolume) {
		this.totalVolume = totalVolume;
	}
}
