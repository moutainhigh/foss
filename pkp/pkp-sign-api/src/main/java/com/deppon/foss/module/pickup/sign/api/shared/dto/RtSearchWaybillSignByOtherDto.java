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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/RtSearchWaybillSignByOtherDto.java
 * 
 * FILE NAME        	: RtSearchWaybillSignByOtherDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
public class RtSearchWaybillSignByOtherDto implements Serializable {

	public static final  String SUCCESS = "success";
	public static final  String WAYBILLNOINVALID = "waybillNo_invalid";
	public static final  String AUTHORIZATIONERROR = "authorization_error";

	private static final long serialVersionUID = 1372509362360011358L;

	private String searchResult;

	private WaybillEntity waybillEntity;

	/**
	 * @return the searchResult
	 */
	public String getSearchResult() {
		return searchResult;
	}

	/**
	 * @param searchResult the searchResult to see
	 */
	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}

	/**
	 * @return the waybillEntity
	 */
	public WaybillEntity getWaybillEntity() {
		return waybillEntity;
	}

	/**
	 * @param waybillEntity the waybillEntity to see
	 */
	public void setWaybillEntity(WaybillEntity waybillEntity) {
		this.waybillEntity = waybillEntity;
	}

}