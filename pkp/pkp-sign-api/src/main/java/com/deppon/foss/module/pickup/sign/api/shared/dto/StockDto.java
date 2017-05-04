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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/StockDto.java
 * 
 * FILE NAME        	: StockDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/***
 * 中转库存Dto
 * @author foss-meiying
 * @date 2012-10-19 下午7:12:23
 * @since
 * @version
 */
public class StockDto implements Serializable {
	private static final long serialVersionUID = -2731217099657318563L;
	/** 
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 旧到达联id
	 */
	private String oldtSrvArriveSheetId;
	/**
	 * 新到达联id
	 */
	private String newtSrvArriveSheetId;
	/**
	 *  运单号
	 */
	private String waybillNo; 
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 是否作废
	 */
	private String destroyed;
	/**
	 * 到达联状态
	 */
	private String status;
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

	public String getOldtSrvArriveSheetId() {
		return oldtSrvArriveSheetId;
	}

	public void setOldtSrvArriveSheetId(String oldtSrvArriveSheetId) {
		this.oldtSrvArriveSheetId = oldtSrvArriveSheetId;
	}

	public String getNewtSrvArriveSheetId() {
		return newtSrvArriveSheetId;
	}

	public void setNewtSrvArriveSheetId(String newtSrvArriveSheetId) {
		this.newtSrvArriveSheetId = newtSrvArriveSheetId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDestroyed() {
		return destroyed;
	}

	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}