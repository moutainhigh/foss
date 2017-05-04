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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaSignDetailDto.java
 * 
 * FILE NAME        	: PdaSignDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;

/**
 * 流水号、签收情况列表
 * @author foss-meiying
 * @date 2012-12-19 上午10:12:36
 * @since
 * @version
 */
public class PdaSignDetailDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6084989723637728147L;
	/**
	 * 签收情况 
	 * 		REFUSE为拒收
	 * 		SIGN为签收
	 */
	private String situation;
	/**
	 * 流水号
	 */
	private String serialNo;
	
	/**
	 * 图片地址
	 */
	private String picAddress;

	/***
	 * 是否内物短少
	 */
	private String goodShorts;
	/**
	 * 流水号签收情况
	 */
	private String signSituation;
	
	/**
	 * Gets the 签收情况.
	 *
	 * @return the 签收情况
	 */
	public String getSituation() {
		return situation;
	}

	/**
	 * Sets the 签收情况.
	 *
	 * @param situation the new 签收情况
	 */
	public void setSituation(String situation) {
		this.situation = situation;
	}

	/**
	 * Gets the 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the 图片地址.
	 *
	 * @return the 图片地址
	 */
	public String getPicAddress() {
		return picAddress;
	}

	/**
	 * Sets the 图片地址.
	 *
	 * @param picAddress the new 图片地址
	 */
	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}
	
	/**
	 * Gets the 是否内物短少.
	 *
	 * @return the 是否内物短少
	 */
	public String getGoodShorts() {
		return goodShorts;
	}

	/**
	 * Sets the 是否内物短少.
	 *
	 * @param goodShorts the new 是否内物短少
	 */
	public void setGoodShorts(String goodShorts) {
		this.goodShorts = goodShorts;
	}

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

}