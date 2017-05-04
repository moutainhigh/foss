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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillTransactionStatEntity.java
 * 
 * FILE NAME        	: WaybillTransactionStatEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 *  运单事物状态对象(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:DP-shaohongliang,date:2012-10-22 下午5:40:17, </p>
 * @author DP-shaohongliang
 * @date 2012-10-22 下午5:40:17
 * @since
 * @version
 */
public class WaybillTransactionStatEntity extends BaseEntity{
    /**
     * 序列化版本号（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = 3580339211083162704L;

    // 运单号
    private String waybillNo;

    // 业务完结
    private String businessOver;

    // 财务完结
    private String financeOver;

    // 业务完结时间
    private Date businessOverTime;

    // 财务完结时间
    private Date financeOverTime;

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
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
	 * @param businessOver the businessOver to set
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
	 * @param financeOver the financeOver to set
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
	 * @param businessOverTime the businessOverTime to set
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
	 * @param financeOverTime the financeOverTime to set
	 */
	public void setFinanceOverTime(Date financeOverTime) {
		this.financeOverTime = financeOverTime;
	}
}