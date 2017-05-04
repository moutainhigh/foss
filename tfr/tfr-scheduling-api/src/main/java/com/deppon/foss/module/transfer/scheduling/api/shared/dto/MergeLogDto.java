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
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/MergeLogDto.java
 * 
 *  FILE NAME     :MergeLogDto.java
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
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: MergeLogDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合发记录Dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-3 下午3:12:45
 */
public class MergeLogDto implements java.io.Serializable {

	private static final long serialVersionUID = 2411844151840313629L;
	/**
	 * 操作时间
	 */
	private Date operationDate;
	/**
	 * 操作时间(字符型)
	 */
	private Date operationDateStr;
	/**
	 * 操作
	 */
	private String operatorName;
	/**
	 * 操作人Code
	 */
	private String operatorCode;
	/**
	 * 操作描述
	 */
	private String operationDesc;
	/**
	 * 合入重量
	 */
	private BigDecimal meringInWeightTotal;
	/**
	 * 合入体积
	 */
	private BigDecimal meringInVolTotal;
	/**
	 * 合入票数
	 */
	private BigDecimal meringInBillTotal;
	/**
	 * 合出重量
	 */
	private BigDecimal meringOutWeightTotal;
	/**
	 * 合出体积
	 */
	private BigDecimal meringOutVolTotal;
	/**
	 * 合出票数
	 */
	private BigDecimal meringOutBillTotal;

	/**
	 * 获取 操作时间.
	 * 
	 * @return the 操作时间
	 */
	public Date getOperationDate() {
		return operationDate;
	}

	/**
	 * 设置 操作时间.
	 * 
	 * @param operationDate
	 *            the new 操作时间
	 */
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * 获取 操作.
	 * 
	 * @return the 操作
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 设置 操作.
	 * 
	 * @param operatorName
	 *            the new 操作
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 获取 操作人Code.
	 * 
	 * @return the 操作人Code
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 设置 操作人Code.
	 * 
	 * @param operatorCode
	 *            the new 操作人Code
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 获取 操作描述.
	 * 
	 * @return the 操作描述
	 */
	public String getOperationDesc() {
		return operationDesc;
	}

	/**
	 * 设置 操作描述.
	 * 
	 * @param operationDesc
	 *            the new 操作描述
	 */
	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	/**
	 * 获取 合入重量.
	 * 
	 * @return the 合入重量
	 */
	public BigDecimal getMeringInWeightTotal() {
		return meringInWeightTotal;
	}

	/**
	 * 设置 合入重量.
	 * 
	 * @param meringInWeightTotal
	 *            the new 合入重量
	 */
	public void setMeringInWeightTotal(BigDecimal meringInWeightTotal) {
		this.meringInWeightTotal = meringInWeightTotal;
	}

	/**
	 * 获取 合入体积.
	 * 
	 * @return the 合入体积
	 */
	public BigDecimal getMeringInVolTotal() {
		return meringInVolTotal;
	}

	/**
	 * 设置 合入体积.
	 * 
	 * @param meringInVolTotal
	 *            the new 合入体积
	 */
	public void setMeringInVolTotal(BigDecimal meringInVolTotal) {
		this.meringInVolTotal = meringInVolTotal;
	}

	/**
	 * 获取 合入票数.
	 * 
	 * @return the 合入票数
	 */
	public BigDecimal getMeringInBillTotal() {
		return meringInBillTotal;
	}

	/**
	 * 设置 合入票数.
	 * 
	 * @param meringInBillTotal
	 *            the new 合入票数
	 */
	public void setMeringInBillTotal(BigDecimal meringInBillTotal) {
		this.meringInBillTotal = meringInBillTotal;
	}

	/**
	 * 获取 合出重量.
	 * 
	 * @return the 合出重量
	 */
	public BigDecimal getMeringOutWeightTotal() {
		return meringOutWeightTotal;
	}

	/**
	 * 设置 合出重量.
	 * 
	 * @param meringOutWeightTotal
	 *            the new 合出重量
	 */
	public void setMeringOutWeightTotal(BigDecimal meringOutWeightTotal) {
		this.meringOutWeightTotal = meringOutWeightTotal;
	}

	/**
	 * 获取 合出体积.
	 * 
	 * @return the 合出体积
	 */
	public BigDecimal getMeringOutVolTotal() {
		return meringOutVolTotal;
	}

	/**
	 * 设置 合出体积.
	 * 
	 * @param meringOutVolTotal
	 *            the new 合出体积
	 */
	public void setMeringOutVolTotal(BigDecimal meringOutVolTotal) {
		this.meringOutVolTotal = meringOutVolTotal;
	}

	/**
	 * 获取 合出票数.
	 * 
	 * @return the 合出票数
	 */
	public BigDecimal getMeringOutBillTotal() {
		return meringOutBillTotal;
	}

	/**
	 * 设置 合出票数.
	 * 
	 * @param meringOutBillTotal
	 *            the new 合出票数
	 */
	public void setMeringOutBillTotal(BigDecimal meringOutBillTotal) {
		this.meringOutBillTotal = meringOutBillTotal;
	}

	/**
	 * 获取 操作时间(字符型).
	 * 
	 * @return the 操作时间(字符型)
	 */
	public Date getOperationDateStr() {
		return operationDateStr;
	}

	/**
	 * 设置 操作时间(字符型).
	 * 
	 * @param operationDateStr
	 *            the new 操作时间(字符型)
	 */
	public void setOperationDateStr(Date operationDateStr) {
		this.operationDateStr = operationDateStr;
	}

}