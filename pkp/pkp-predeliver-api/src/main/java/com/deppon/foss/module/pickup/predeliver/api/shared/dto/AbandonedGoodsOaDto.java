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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonedGoodsOaDto.java
 * 
 * FILE NAME        	: AbandonedGoodsOaDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

public class AbandonedGoodsOaDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	// 运单号
	private String waybillNo;

	// 仓储部门 TFR T_OPT_WAYBILL_STOCK
	private String storageDept;

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
	 * @return the storageDept
	 */
	public String getStorageDept() {
		return storageDept;
	}

	/**
	 * @param storageDept the storageDept to see
	 */
	public void setStorageDept(String storageDept) {
		this.storageDept = storageDept;
	}

}