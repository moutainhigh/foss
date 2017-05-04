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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/AbandonGoodsActionHistoryEntity.java
 * 
 * FILE NAME        	: AbandonGoodsActionHistoryEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @ClassName: AbandonGoodsActionHistoryEntity
 * @Description: 弃货历史纪录
 * @author
 * @date 2012-10-25 上午10:05:55
 */
public class AbandonGoodsActionHistoryEntity extends BaseEntity {
	/**
	 * @Fields serialVersionUID : 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  弃货表id
	 */
	private String tSrvAbandonApplicationId;

	/**
	 *  状态
	 */
	private String status;

	/**
	 *  操作人名称
	 */
	private String operator;

	/**
	 * 	操作人编码
	 */
	private String operatorCode;

	/**
	 *  操作部门名称
	 */
	private String operateOrgName;

	/**
	 *  操作部门编码
	 */
	private String operateOrgCode;

	/**
	 *  操作时间
	 */
	private Date operateTime;

	/**
	 * @return the tSrvAbandonApplicationId
	 */
	public String gettSrvAbandonApplicationId() {
		return tSrvAbandonApplicationId;
	}

	/**
	 * @param tSrvAbandonApplicationId the tSrvAbandonApplicationId to see
	 */
	public void settSrvAbandonApplicationId(String tSrvAbandonApplicationId) {
		this.tSrvAbandonApplicationId = tSrvAbandonApplicationId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to see
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
	 * @param operatorCode the operatorCode to see
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param operateOrgName the operateOrgName to see
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode the operateOrgCode to see
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to see
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}