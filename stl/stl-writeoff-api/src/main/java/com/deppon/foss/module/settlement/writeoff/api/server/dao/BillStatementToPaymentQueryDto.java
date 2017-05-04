/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-pay-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/shared/dto/BillStatementToPaymentQueryDto.java
 * 
 * FILE NAME        	: BillStatementToPaymentQueryDto.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 按对账单还款的输入参数Dto
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-26 下午3:55:42
 */
public class BillStatementToPaymentQueryDto implements Serializable {

	/**
	 * 按对账单还款的输入参数Dto序列号
	 */
	private static final long serialVersionUID = 4082914059626937390L;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 对账单编号数组
	 */
	private String[] statementBillNos;

	/**
	 * 对账单版本号数组
	 */
	private String[] versionNos;

	/**
	 * 还款方式
	 */
	private String repaymentType;

	/**
	 * 汇款编号
	 */
	private String remittanceNumber;

	/**
	 * 还款金额
	 */
	private BigDecimal repaymentAmount;

	/**
	 * 备注
	 */
	private String description;

	/**
	 * 在线支付单号
	 */
	private String onlinePaymentNo;
	
	
	/**
	 * 汇款人
	 */
	private String remittanceName;

	/**
	 * 还款单备注
	 */
	private String repaymentNotes;
	
	/**
	 * 对账单描述信息
	 */
	private String notes;
	/**
	 * @return 
		remittanceName
	 */
	public String getRemittanceName() {
		return remittanceName;
	}


	
	/**
	 * @param 
		remittanceName
	 */
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}


	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	
	/**
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	/**
	 * @return statementBillNos
	 */
	public String[] getStatementBillNos() {
		return statementBillNos;
	}

	
	/**
	 * @param statementBillNos
	 */
	public void setStatementBillNos(String[] statementBillNos) {
		this.statementBillNos = statementBillNos;
	}

	
	/**
	 * @return versionNos
	 */
	public String[] getVersionNos() {
		return versionNos;
	}

	
	/**
	 * @param versionNos
	 */
	public void setVersionNos(String[] versionNos) {
		this.versionNos = versionNos;
	}

	
	/**
	 * @return repaymentType
	 */
	public String getRepaymentType() {
		return repaymentType;
	}

	
	/**
	 * @param repaymentType
	 */
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	
	/**
	 * @return remittanceNumber
	 */
	public String getRemittanceNumber() {
		return remittanceNumber;
	}

	
	/**
	 * @param remittanceNumber
	 */
	public void setRemittanceNumber(String remittanceNumber) {
		this.remittanceNumber = remittanceNumber;
	}

	
	/**
	 * @return repaymentAmount
	 */
	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	
	/**
	 * @param repaymentAmount
	 */
	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	
	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * @return onlinePaymentNo
	 */
	public String getOnlinePaymentNo() {
		return onlinePaymentNo;
	}

	
	/**
	 * @param onlinePaymentNo
	 */
	public void setOnlinePaymentNo(String onlinePaymentNo) {
		this.onlinePaymentNo = onlinePaymentNo;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getRepaymentNotes() {
		return repaymentNotes;
	}



	public void setRepaymentNotes(String repaymentNotes) {
		this.repaymentNotes = repaymentNotes;
	}

	
}
