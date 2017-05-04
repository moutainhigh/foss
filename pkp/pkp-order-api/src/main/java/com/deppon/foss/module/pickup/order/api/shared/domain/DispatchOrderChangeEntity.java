/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/domain/DispatchOrderChangeEntity.java
 * 
 * FILE NAME        	: DispatchOrderChangeEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 调度订单变更表
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-2 下午1:50:13
 */
public class DispatchOrderChangeEntity extends BaseEntity {

	private static final long serialVersionUID = 820069042491223291L;
	/**
	 *  变更Id
	 */
	private String changeId;
	/**
	 *  变更时间
	 */
	private Date changeTime;
	
	/**
	 *  变更标记位（默认（未查询）:N/A,已查询：A,开关关闭为B）
	 */
	private String jobId;
	
	/**
	 *  产品类型 
	 */
	private String productCode;
	
	/**
	 *  所属城市编码 
	 */
	private String cityCode;

	/**
	 *  最早接货时间
	 */
	private Date earliestPickupTime;
	
	/**
	 * @return the changeId
	 */
	public String getChangeId() {
		return changeId;
	}

	/**
	 * @param changeId the changeId to set
	 */
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	/**
	 * @return the changeTime
	 */
	public Date getChangeTime() {
		return changeTime;
	}

	/**
	 * @param changeTime the changeTime to set
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}

	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}
	
}