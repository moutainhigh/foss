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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/SignByOtherVo.java
 * 
 * FILE NAME        	: SignByOtherVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchWaybillSignByOtherDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;

public class SignByOtherVo implements java.io.Serializable {

	private static final long serialVersionUID = 1372509362360011358L;

	private SearchWaybillSignByOtherDto searchWaybillSignByOtherDto = new SearchWaybillSignByOtherDto();

	private RtSearchWaybillSignByOtherDto rtSearchWaybillSignByOtherDto = new RtSearchWaybillSignByOtherDto();

	/**
	 * @return the rtSearchWaybillSignByOtherDto
	 */
	public RtSearchWaybillSignByOtherDto getRtSearchWaybillSignByOtherDto() {
		return rtSearchWaybillSignByOtherDto;
	}

	/**
	 * @param rtSearchWaybillSignByOtherDto the rtSearchWaybillSignByOtherDto to
	 *            see
	 */
	public void setRtSearchWaybillSignByOtherDto(
			RtSearchWaybillSignByOtherDto rtSearchWaybillSignByOtherDto) {
		this.rtSearchWaybillSignByOtherDto = rtSearchWaybillSignByOtherDto;
	}

	/**
	 * @return the searchWaybillSignByOtherDto
	 */
	public SearchWaybillSignByOtherDto getSearchWaybillSignByOtherDto() {
		return searchWaybillSignByOtherDto;
	}

	/**
	 * @param searchWaybillSignByOtherDto the searchWaybillSignByOtherDto to see
	 */
	public void setSearchWaybillSignByOtherDto(
			SearchWaybillSignByOtherDto searchWaybillSignByOtherDto) {
		this.searchWaybillSignByOtherDto = searchWaybillSignByOtherDto;
	}

}