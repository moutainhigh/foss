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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/ChangeDto.java
 * 
 * FILE NAME        	: ChangeDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**   
 * <p>签收变更数据对象<br />
 * </p>
 * @title ChangeDto.java
 * @package com.deppon.foss.module.pickup.sign.api.shared.dto 
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-6
 */
public class ChangeDto implements Serializable{
	private static final long serialVersionUID = -4651238771780293780L;
	/**
	 * 付款
	 */
	private RepaymentEntity repaymentEntity;
	/**
	 * 到达联
	 */
	private ArriveSheetEntity arriveSheetEntity;
	/**
	 * 运单变更
	 */
	private WaybillSignResultEntity waybillSignResultEntity;
	/**
	 * @return repaymentEntity : return the property repaymentEntity.
	 */
	public RepaymentEntity getRepaymentEntity() {
		return repaymentEntity;
	}
	/**
	 * @param repaymentEntity : set the property repaymentEntity.
	 */
	public void setRepaymentEntity(RepaymentEntity repaymentEntity) {
		this.repaymentEntity = repaymentEntity;
	}
	/**
	 * @return arriveSheetEntity : return the property arriveSheetEntity.
	 */
	public ArriveSheetEntity getArriveSheetEntity() {
		return arriveSheetEntity;
	}
	/**
	 * @param arriveSheetEntity : set the property arriveSheetEntity.
	 */
	public void setArriveSheetEntity(ArriveSheetEntity arriveSheetEntity) {
		this.arriveSheetEntity = arriveSheetEntity;
	}
	/**
	 * @return waybillSignResultEntity : return the property waybillSignResultEntity.
	 */
	public WaybillSignResultEntity getWaybillSignResultEntity() {
		return waybillSignResultEntity;
	}
	/**
	 * @param waybillSignResultEntity : set the property waybillSignResultEntity.
	 */
	public void setWaybillSignResultEntity(
			WaybillSignResultEntity waybillSignResultEntity) {
		this.waybillSignResultEntity = waybillSignResultEntity;
	}
}