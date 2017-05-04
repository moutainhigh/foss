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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/ContrabandInfoVo.java
 * 
 * FILE NAME        	: ContrabandInfoVo.java
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

import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;

/**
 * 违禁品信息Vo
 * @author foss-meiying
 * @date 2012-11-15 下午4:09:46
 * @since
 * @version
 */
public class ContrabandInfoVo implements Serializable {
	//序列
	private static final long serialVersionUID = -1005901960761003148L;
	/**
	 * 运单号 
	 */
	private String waybillNo;
	/**
	 * 违禁品签收dto
	 */
	private ContrabandInfoDto contrabandInfoDto = new ContrabandInfoDto();
	/**
	 * 丢货dto
	 */
	private LostCargoInfoDto lostCargoInfoDto;
	/**
	 * 丢货信息集合
	 */
	private List<LostCargoInfoDto> lostCargoInfoDtoList;
	
	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 违禁品签收dto.
	 *
	 * @return the 违禁品签收dto
	 */
	public ContrabandInfoDto getContrabandInfoDto() {
		return contrabandInfoDto;
	}

	/**
	 * Sets the 违禁品签收dto.
	 *
	 * @param contrabandInfoDto the new 违禁品签收dto
	 */
	public void setContrabandInfoDto(ContrabandInfoDto contrabandInfoDto) {
		this.contrabandInfoDto = contrabandInfoDto;
	}

	/**
	 * Gets the 丢货dto.
	 *
	 * @return the 丢货dto
	 */
	public LostCargoInfoDto getLostCargoInfoDto() {
		return lostCargoInfoDto;
	}

	/**
	 * Sets the 丢货dto.
	 *
	 * @param lostCargoInfoDto the new 丢货dto
	 */
	public void setLostCargoInfoDto(LostCargoInfoDto lostCargoInfoDto) {
		this.lostCargoInfoDto = lostCargoInfoDto;
	}

	/**
	 * Gets the 丢货信息集合.
	 *
	 * @return the 丢货信息集合
	 */
	public List<LostCargoInfoDto> getLostCargoInfoDtoList() {
		return lostCargoInfoDtoList;
	}

	/**
	 * Sets the 丢货信息集合.
	 *
	 * @param lostCargoInfoDtoList the new 丢货信息集合
	 */
	public void setLostCargoInfoDtoList(List<LostCargoInfoDto> lostCargoInfoDtoList) {
		this.lostCargoInfoDtoList = lostCargoInfoDtoList;
	}
	
}