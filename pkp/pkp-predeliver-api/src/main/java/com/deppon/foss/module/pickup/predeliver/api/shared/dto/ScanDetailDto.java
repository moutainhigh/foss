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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ScanDetailDto.java
 * 
 * FILE NAME        	: ScanDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 装车任务扫描明细，属性从com.deppon.foss.module.transfer.load.api.shared.domain中拷贝
 * @author ibm-wangxiexu
 * @date 2012-12-4 下午4:56:31
 */
public class ScanDetailDto implements Serializable {
	private static final long serialVersionUID = 9210832973432854328L;

	private String beLoaded; // 是否装车
	private String scanState; // 扫描状态
	private String goodsState; // 货物状态
	private Date loadTime; // 操作时间
	private String serialNo; // 流水号

	/**
	 * @return the beLoaded
	 */
	public String getBeLoaded() {
		return beLoaded;
	}

	/**
	 * @param beLoaded the beLoaded to see
	 */
	public void setBeLoaded(String beLoaded) {
		this.beLoaded = beLoaded;
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
	 * @return the loadTime
	 */
	public Date getLoadTime() {
		return loadTime;
	}

	/**
	 * @param loadTime the loadTime to see
	 */
	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

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

}