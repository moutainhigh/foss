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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/LoadGaprepDetailDto.java
 * 
 * FILE NAME        	: LoadGaprepDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 装车差异报告少货明细Dto，属性从com.deppon.foss.module.transfer.load.api.shared.domain.
 * DeliverLoadGapReportSerialEntity拷贝
 * @author ibm-wangxiexu
 * @date 2012-12-5 下午6:07:35
 */
public class LoadGaprepDetailDto implements Serializable {
	private static final long serialVersionUID = 4089157883866230783L;

	private String serialNo;
	private String scanState;
	private String goodsState;
	private String scanTime;

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the scanState
	 */
	public String getScanState() {
		return scanState;
	}

	/**
	 * @param scanState the scanState to see
	 */
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}

	/**
	 * @return the goodsState
	 */
	public String getGoodsState() {
		return goodsState;
	}

	/**
	 * @param goodsState the goodsState to see
	 */
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	/**
	 * @return the scanTime
	 */
	public String getScanTime() {
		return scanTime;
	}

	/**
	 * @param scanTime the scanTime to see
	 */
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}

}