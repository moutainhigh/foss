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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/QueryListDto.java
 * 
 * FILE NAME        	: QueryListDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

import java.util.List;

/**
 * QueryListDto 查询运单明细
 * @author 026113-foss-linwensheng
 *
 */
public class QueryListDto implements Serializable {

	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -5910849042678311911L;
	
	private List<String> waybillNoList;

	/**
	 * @return the waybillNoList
	 */
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	/**
	 * @param waybillNoList the waybillNoList to set
	 */
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	
}