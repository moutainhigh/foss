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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/ReturnBillProcessVo.java
 * 
 * FILE NAME        	: ReturnBillProcessVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RtSearchReturnBillProcessDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchReturnBillProcessDto;

/**
 * 签收单返单
 * @date 2012-11-21 下午1:32:04
 */
public class ReturnBillProcessVo implements Serializable {

	// 序列化版本号
	private static final long serialVersionUID = 1L;

	// 查询条件DTO
	private SearchReturnBillProcessDto searchReturnBillProcessDto = new SearchReturnBillProcessDto();

	// 返回查询的结果集合
	private List<RtSearchReturnBillProcessDto> rtSearchReturnBillProcessList;

	// 返回查询的结果detail
	private RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto;

	// 接口调用结果
	private ResultDto resultDto;

	// id
	private String id;

	// 部门区分
	private String orgDiff;

	public String getOrgDiff() {
		return orgDiff;
	}

	public void setOrgDiff(String orgDiff) {
		this.orgDiff = orgDiff;
	}

	/**
	 * @return the searchReturnBillProcessDto
	 */
	public SearchReturnBillProcessDto getSearchReturnBillProcessDto() {
		return searchReturnBillProcessDto;
	}

	/**
	 * @param searchReturnBillProcessDto the searchReturnBillProcessDto to see
	 */
	public void setSearchReturnBillProcessDto(SearchReturnBillProcessDto searchReturnBillProcessDto) {
		this.searchReturnBillProcessDto = searchReturnBillProcessDto;
	}

	/**
	 * @return the rtSearchReturnBillProcessList
	 */
	public List<RtSearchReturnBillProcessDto> getRtSearchReturnBillProcessList() {
		return rtSearchReturnBillProcessList;
	}

	/**
	 * @param rtSearchReturnBillProcessList the rtSearchReturnBillProcessList to
	 *            see
	 */
	public void setRtSearchReturnBillProcessList(List<RtSearchReturnBillProcessDto>
			rtSearchReturnBillProcessList) {
		this.rtSearchReturnBillProcessList = rtSearchReturnBillProcessList;
	}

	/**
	 * @return the rtSearchReturnBillProcessDto
	 */
	public RtSearchReturnBillProcessDto getRtSearchReturnBillProcessDto() {
		return rtSearchReturnBillProcessDto;
	}

	/**
	 * @param rtSearchReturnBillProcessDto the rtSearchReturnBillProcessDto to
	 *            see
	 */
	public void setRtSearchReturnBillProcessDto(
			RtSearchReturnBillProcessDto rtSearchReturnBillProcessDto) {
		this.rtSearchReturnBillProcessDto = rtSearchReturnBillProcessDto;
	}

	/**
	 * @return the resultDto
	 */
	public ResultDto getResultDto() {
		return resultDto;
	}

	/**
	 * @param resultDto the resultDto to see
	 */
	public void setResultDto(ResultDto resultDto) {
		this.resultDto = resultDto;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}
}