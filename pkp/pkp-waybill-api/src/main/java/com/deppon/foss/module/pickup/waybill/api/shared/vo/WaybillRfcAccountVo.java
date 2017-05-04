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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/vo/WaybillRfcAccountVo.java
 * 
 * FILE NAME        	: WaybillRfcAccountVo.java
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
package com.deppon.foss.module.pickup.waybill.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;

/**
 * 
 * 更改单申请VO
 * 
 * @author ibm-wangfei
 * @date Jan 29, 2013 11:26:53 AM
 */
public class WaybillRfcAccountVo implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 更改单申请查询条件dto
	 */
	private WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition;
	/**
	 * 更改单申请结果List
	 */
	private List<WaybillRfcForAccountServiceDto> waybillRfcForAccountServiceDto;
	
	/**
	 * 待更新的id
	 */
	private String ids;

	/**
	 * @return the waybillRfcForAccountServiceCondition
	 */
	public WaybillRfcForAccountServiceCondition getWaybillRfcForAccountServiceCondition() {
		return waybillRfcForAccountServiceCondition;
	}

	/**
	 * @param waybillRfcForAccountServiceCondition the waybillRfcForAccountServiceCondition to set
	 */
	public void setWaybillRfcForAccountServiceCondition(
			WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition) {
		this.waybillRfcForAccountServiceCondition = waybillRfcForAccountServiceCondition;
	}

	/**
	 * 获取 更改单申请结果List.
	 *
	 * @return the 更改单申请结果List
	 */
	public List<WaybillRfcForAccountServiceDto> getWaybillRfcForAccountServiceDto() {
		return waybillRfcForAccountServiceDto;
	}

	/**
	 * 设置 更改单申请结果List.
	 *
	 * @param waybillRfcForAccountServiceDto the new 更改单申请结果List
	 */
	public void setWaybillRfcForAccountServiceDto(List<WaybillRfcForAccountServiceDto> waybillRfcForAccountServiceDto) {
		this.waybillRfcForAccountServiceDto = waybillRfcForAccountServiceDto;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
}