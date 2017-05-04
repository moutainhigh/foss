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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/LoadGaprepWaybillDto.java
 * 
 * FILE NAME        	: LoadGaprepWaybillDto.java
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
 * 派送装车任务明细DTO，属性从com.deppon.foss.module.transfer.load.api.shared.domain.
 * DeliverLoadGapReportWayBillEntity中拷贝
 * @author ibm-wangxiexu
 * @date 2012-11-30 下午4:50:10
 */
public class LoadGaprepWaybillDto implements Serializable {
	private static final long serialVersionUID = 3683393091409997967L;

	private String wayBillNo;
	private String reportId;
	private String gapType;
	private int planLoadQty;
	private int actualLoadQty;
	private int lackGoodsQty;
	private String notes;
	private String transportType;

	/**
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * @param wayBillNo the wayBillNo to see
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to see
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the gapType
	 */
	public String getGapType() {
		return gapType;
	}

	/**
	 * @param gapType the gapType to see
	 */
	public void setGapType(String gapType) {
		this.gapType = gapType;
	}

	/**
	 * @return the planLoadQty
	 */
	public int getPlanLoadQty() {
		return planLoadQty;
	}

	/**
	 * @param planLoadQty the planLoadQty to see
	 */
	public void setPlanLoadQty(int planLoadQty) {
		this.planLoadQty = planLoadQty;
	}

	/**
	 * @return the actualLoadQty
	 */
	public int getActualLoadQty() {
		return actualLoadQty;
	}

	/**
	 * @param actualLoadQty the actualLoadQty to see
	 */
	public void setActualLoadQty(int actualLoadQty) {
		this.actualLoadQty = actualLoadQty;
	}

	/**
	 * @return the lackGoodsQty
	 */
	public int getLackGoodsQty() {
		return lackGoodsQty;
	}

	/**
	 * @param lackGoodsQty the lackGoodsQty to see
	 */
	public void setLackGoodsQty(int lackGoodsQty) {
		this.lackGoodsQty = lackGoodsQty;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType the transportType to see
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

}