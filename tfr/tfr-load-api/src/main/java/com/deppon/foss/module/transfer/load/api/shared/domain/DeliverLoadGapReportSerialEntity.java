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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/DeliverLoadGapReportSerialEntity.java
 *  
 *  FILE NAME          :DeliverLoadGapReportSerialEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: DeliverLoadGapReportDetail.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * DeliverLoadGapReportSerialEntity
 * @author 042795-foss-duyi
 * @date 2012-10-26 上午11:34:03
 */
public class DeliverLoadGapReportSerialEntity extends BaseEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = 498895389152543517L;
	/**id*/
	private String id;
	/**wayBillNo*/
	private String wayBillNo;
	/**serialNo*/
	private String serialNo;
	/**scanState*/
	private String scanState;
	/**goodsState*/
	private String goodsState;
	/**reportId*/
	private String reportId;
	/**notes*/
	private String notes;
	/**scanTime*/
	private String scanTime;
	/**createTime*/
	private String createTime;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the wayBillNo.
	 *
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * Sets the wayBillNo.
	 *
	 * @param wayBillNo the new wayBillNo
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * Gets the serialNo.
	 *
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * Sets the serialNo.
	 *
	 * @param serialNo the new serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * Gets the scanState.
	 *
	 * @return the scanState
	 */
	public String getScanState() {
		return scanState;
	}
	
	/**
	 * Sets the scanState.
	 *
	 * @param scanState the new scanState
	 */
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	
	/**
	 * Gets the goodsState.
	 *
	 * @return the goodsState
	 */
	public String getGoodsState() {
		return goodsState;
	}
	
	/**
	 * Sets the goodsState.
	 *
	 * @param goodsState the new goodsState
	 */
	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	
	/**
	 * Gets the reportId.
	 *
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}
	
	/**
	 * Sets the reportId.
	 *
	 * @param reportId the new reportId
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * Gets the createTime.
	 *
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the createTime.
	 *
	 * @param createTime the new createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * Gets the scanTime.
	 *
	 * @return the scanTime
	 */
	public String getScanTime() {
		return scanTime;
	}
	
	/**
	 * Sets the scanTime.
	 *
	 * @param scanTime the new scanTime
	 */
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	
}