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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/domain/DispatchOrderActionHistoryEntity.java
 * 
 * FILE NAME        	: DispatchOrderActionHistoryEntity.java
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
 * 调度订单操作历史实体
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午9:05:16
 */
public class DispatchOrderActionHistoryEntity extends BaseEntity {

	private static final long serialVersionUID = 4396108259497204313L;

	/** 
	 * 调度订单id
	 */
	private String tSrvDispatchOrderId;
	/** 
	 * 订单状态
	 */
	private String orderStatus;
	/** 
	 * 操作备注
	 */
	private String notes;
	/** 
	 * 操作人
	 */
	private String operator;
	/** 
	 * 操作人编码
	 */
	private String operatorCode;
	/** 
	 * 操作时间
	 */
	private Date operateTime;

	/**
	 * @return the tSrvDispatchOrderId
	 */
	public String gettSrvDispatchOrderId() {
		return tSrvDispatchOrderId;
	}

	/**
	 * @param tSrvDispatchOrderId the tSrvDispatchOrderId to set
	 */
	public void settSrvDispatchOrderId(String tSrvDispatchOrderId) {
		this.tSrvDispatchOrderId = tSrvDispatchOrderId;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}