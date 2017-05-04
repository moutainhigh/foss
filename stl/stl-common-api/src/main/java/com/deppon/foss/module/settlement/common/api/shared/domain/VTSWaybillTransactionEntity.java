/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/WaybillTransactionEntity.java
 * 
 * FILE NAME        	: WaybillTransactionEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 运单终结状态
 * @author 218392 张永雪
 * @date 2016-06-13 上午11:20:03
 */
public class VTSWaybillTransactionEntity extends BaseEntity
{

	private static final long serialVersionUID = 1L;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  业务完结
	 */
	private String businessOver;
	/**
	 *  财务完结
	 */
	private String financeOver;
	/**
	 *  业务完结
	 */
	private Date businessOverTime;
	/**
	 *  业务完结
	 */
	private Date financeOverTime;

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the businessOver
	 */
	public String getBusinessOver() {
		return businessOver;
	}

	/**
	 * @param businessOver the businessOver to see
	 */
	public void setBusinessOver(String businessOver) {
		this.businessOver = businessOver;
	}

	/**
	 * @return the financeOver
	 */
	public String getFinanceOver() {
		return financeOver;
	}

	/**
	 * @param financeOver the financeOver to see
	 */
	public void setFinanceOver(String financeOver) {
		this.financeOver = financeOver;
	}

	/**
	 * @return the businessOverTime
	 */
	public Date getBusinessOverTime() {
		return businessOverTime;
	}

	/**
	 * @param businessOverTime the businessOverTime to see
	 */
	public void setBusinessOverTime(Date businessOverTime) {
		this.businessOverTime = businessOverTime;
	}

	/**
	 * @return the financeOverTime
	 */
	public Date getFinanceOverTime() {
		return financeOverTime;
	}

	/**
	 * @param financeOverTime the financeOverTime to see
	 */
	public void setFinanceOverTime(Date financeOverTime) {
		this.financeOverTime = financeOverTime;
	}
}