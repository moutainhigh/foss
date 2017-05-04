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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/WaybillVo.java
 * 
 * FILE NAME        	: WaybillVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/***
 * 运单信息
 * @author foss-meiying
 * @date 2012-10-16 下午3:15:21
 * @since
 * @version
 */
public class WaybillVo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1005901960761003148L;
	/**
	 *  运单信息List
	 */
	private List<WaybillEntity> waybillList;
	/**
	 *  运单实体
	 */
	private WaybillEntity waybill;
    /**
     * WaybillDto  运单dto
     */
	private WaybillDto dto;
	/**
	 *  到达联编号
	 */
	private String arrivsheetNo;
	/**
	 *  结清时间起
	 */
	private Date settleTimeStart;
	/**
	 *  结清时间止
	 */
	private Date settleTimeEnd;

	/**
	 * Gets the 结清时间起.
	 *
	 * @return the 结清时间起
	 */
	public Date getSettleTimeStart() {
		return settleTimeStart;
	}

	/**
	 * Sets the 结清时间起.
	 *
	 * @param settleTimeStart the new 结清时间起
	 */
	public void setSettleTimeStart(Date settleTimeStart) {
		this.settleTimeStart = settleTimeStart;
	}

	/**
	 * Gets the 结清时间止.
	 *
	 * @return the 结清时间止
	 */
	public Date getSettleTimeEnd() {
		return settleTimeEnd;
	}

	/**
	 * Sets the 结清时间止.
	 *
	 * @param settleTimeEnd the new 结清时间止
	 */
	public void setSettleTimeEnd(Date settleTimeEnd) {
		this.settleTimeEnd = settleTimeEnd;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivsheetNo() {
		return arrivsheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivsheetNo the new 到达联编号
	 */
	public void setArrivsheetNo(String arrivsheetNo) {
		this.arrivsheetNo = arrivsheetNo;
	}

	/**
	 * Gets the 运单实体.
	 *
	 * @return the 运单实体
	 */
	public WaybillEntity getWaybill() {
		return waybill;
	}

	/**
	 * Sets the 运单实体.
	 *
	 * @param waybill the new 运单实体
	 */
	public void setWaybill(WaybillEntity waybill) {
		this.waybill = waybill;
	}

	/**
	 * Gets the 运单信息List.
	 *
	 * @return the 运单信息List
	 */
	public List<WaybillEntity> getWaybillList() {
		return waybillList;
	}

	/**
	 * Sets the 运单信息List.
	 *
	 * @param waybillList the new 运单信息List
	 */
	public void setWaybillList(List<WaybillEntity> waybillList) {
		this.waybillList = waybillList;
	}

	public WaybillDto getDto() {
		return dto;
	}

	public void setDto(WaybillDto dto) {
		this.dto = dto;
	}

}