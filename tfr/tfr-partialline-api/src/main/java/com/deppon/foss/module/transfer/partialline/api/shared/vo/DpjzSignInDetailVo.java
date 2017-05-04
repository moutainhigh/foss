/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/vo/LdpExternalBillVo.java
 * 
 *  FILE NAME     :LdpExternalBillVo.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.DpjzSignInDetialBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;

/**
 * DpjzSignInDetialVo
 * 
 * @author foss-lln-269701
 * @date 2015-12-04 上午9:18:36
 */
public class DpjzSignInDetailVo implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -165940826818885556L;

	/**
	 * 家装送装明细及签收确认信息
	 */
	private DpjzSignInDetailDto dpjzSignInDetialBillDto;
	/**
	 * 家装送装明细及签收确认信息
	 * 核对内容
	 */
	private DpjzSignInDetialBillEntity dpjzSignInDetialCheckEntity;
	/**
	 * 家装送装明细及签收确认列表
	 */
	private List<DpjzSignInDetailDto> dpjzSignInDetialBill;
	
	/**
	 * 家装送装明细及签收确认信息
	 * @return the dpjzSignInDetialBillDto
	 */
	public DpjzSignInDetailDto getDpjzSignInDetialBillDto() {
		return dpjzSignInDetialBillDto;
	}

	/**
	 * 家装送装明细及签收确认信息
	 * @param dpjzSignInDetialBillDto the dpjzSignInDetialBillDto to set
	 */
	public void setDpjzSignInDetialBillDto(
			DpjzSignInDetailDto dpjzSignInDetialBillDto) {
		this.dpjzSignInDetialBillDto = dpjzSignInDetialBillDto;
	}

	/**
	 * 家装送装明细及签收确认列表
	 * @return the dpjzSignInDetialBill
	 */
	public List<DpjzSignInDetailDto> getDpjzSignInDetialBill() {
		return dpjzSignInDetialBill;
	}
	
	/**
	 * 家装送装明细及签收确认列表
	 * @param dpjzSignInDetialBill the dpjzSignInDetialBill to set
	 */
	public void setDpjzSignInDetialBill(
			List<DpjzSignInDetailDto> dpjzSignInDetialBill) {
		this.dpjzSignInDetialBill = dpjzSignInDetialBill;
	}

	/**
	 * @return the dpjzSignInDetialCheckEntity
	 */
	public DpjzSignInDetialBillEntity getDpjzSignInDetialCheckEntity() {
		return dpjzSignInDetialCheckEntity;
	}

	/**
	 * @param dpjzSignInDetialCheckEntity the dpjzSignInDetialCheckEntity to set
	 */
	public void setDpjzSignInDetialCheckEntity(
			DpjzSignInDetialBillEntity dpjzSignInDetialCheckEntity) {
		this.dpjzSignInDetialCheckEntity = dpjzSignInDetialCheckEntity;
	}

	
}