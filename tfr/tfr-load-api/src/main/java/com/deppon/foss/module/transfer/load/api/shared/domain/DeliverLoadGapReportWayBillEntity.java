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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/DeliverLoadGapReportWayBillEntity.java
 *  
 *  FILE NAME          :DeliverLoadGapReportWayBillEntity.java
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
 * FILE    NAME: DeliverLoadGapReportWayBillEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * DeliverLoadGapReportWayBillEntity
 * @author dp-duyi
 * @date 2012-10-29 上午10:18:26
 */
public class DeliverLoadGapReportWayBillEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3588593212417258592L;
	/**wayBillNo*/
	private String wayBillNo;
	/**reportId*/
	private String reportId;
	/**gapType*/
	private String gapType;
	/**planLoadQty*/
	private int planLoadQty;
	/**actualLoadQty*/
	private int actualLoadQty;
	/**lackGoodsQty*/
	private int lackGoodsQty;
	/**stockQty*/
	private int stockQty;
	
	/**
	 * 
	 */
	private double loadVolumeTotal;
	
	/**
	 * 
	 */
	private double loadWeightTotal;
	
	/**
	 * 
	 */
	private int loadQty;
	
	/**
	 * 
	 */
	private int scanQty;
	/**notes*/
	private String notes;
	/**transportType*/
	private String transportType;
	/**loadWayBillDetailId*/
	private String loadWayBillDetailId;
	/**goodsName*/
	private String goodsName;
	/**pack*/
	private String pack;
	/**receiveOrgName*/
	private String receiveOrgName;
	/**reachOrgName*/
	private String reachOrgName;
	
	/**
	 * 
	 *
	 * @return 
	 */
	public double getLoadVolumeTotal() {
		return loadVolumeTotal;
	}

	/**
	 * 
	 *
	 * @param loadVolumeTotal 
	 */
	public void setLoadVolumeTotal(double loadVolumeTotal) {
		this.loadVolumeTotal = loadVolumeTotal;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public double getLoadWeightTotal() {
		return loadWeightTotal;
	}

	/**
	 * 
	 *
	 * @param loadWeightTotal 
	 */
	public void setLoadWeightTotal(double loadWeightTotal) {
		this.loadWeightTotal = loadWeightTotal;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public int getLoadQty() {
		return loadQty;
	}

	/**
	 * 
	 *
	 * @param loadQty 
	 */
	public void setLoadQty(int loadQty) {
		this.loadQty = loadQty;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public int getScanQty() {
		return scanQty;
	}

	/**
	 * 
	 *
	 * @param scanQty 
	 */
	public void setScanQty(int scanQty) {
		this.scanQty = scanQty;
	}

	/**
	 * Gets the stockQty.
	 *
	 * @return the stockQty
	 */
	public int getStockQty() {
		return stockQty;
	}

	/**
	 * Sets the stockQty.
	 *
	 * @param stockQty the new stockQty
	 */
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
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
	 * Gets the gapType.
	 *
	 * @return the gapType
	 */
	public String getGapType() {
		return gapType;
	}
	
	/**
	 * Sets the gapType.
	 *
	 * @param gapType the new gapType
	 */
	public void setGapType(String gapType) {
		this.gapType = gapType;
	}
	
	/**
	 * Gets the planLoadQty.
	 *
	 * @return the planLoadQty
	 */
	public int getPlanLoadQty() {
		return planLoadQty;
	}
	
	/**
	 * Sets the planLoadQty.
	 *
	 * @param planLoadQty the new planLoadQty
	 */
	public void setPlanLoadQty(int planLoadQty) {
		this.planLoadQty = planLoadQty;
	}
	
	/**
	 * Gets the actualLoadQty.
	 *
	 * @return the actualLoadQty
	 */
	public int getActualLoadQty() {
		return actualLoadQty;
	}
	
	/**
	 * Sets the actualLoadQty.
	 *
	 * @param actualLoadQty the new actualLoadQty
	 */
	public void setActualLoadQty(int actualLoadQty) {
		this.actualLoadQty = actualLoadQty;
	}
	
	/**
	 * Gets the lackGoodsQty.
	 *
	 * @return the lackGoodsQty
	 */
	public int getLackGoodsQty() {
		return lackGoodsQty;
	}
	
	/**
	 * Sets the lackGoodsQty.
	 *
	 * @param lackGoodsQty the new lackGoodsQty
	 */
	public void setLackGoodsQty(int lackGoodsQty) {
		this.lackGoodsQty = lackGoodsQty;
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
	 * Gets the transportType.
	 *
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	
	/**
	 * Sets the transportType.
	 *
	 * @param transportType the new transportType
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	/**
	 * Gets the loadWayBillDetailId.
	 *
	 * @return the loadWayBillDetailId
	 */
	public String getLoadWayBillDetailId() {
		return loadWayBillDetailId;
	}
	
	/**
	 * Sets the loadWayBillDetailId.
	 *
	 * @param loadWayBillDetailId the new loadWayBillDetailId
	 */
	public void setLoadWayBillDetailId(String loadWayBillDetailId) {
		this.loadWayBillDetailId = loadWayBillDetailId;
	}
	
	/**
	 * Gets the goodsName.
	 *
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * Sets the goodsName.
	 *
	 * @param goodsName the new goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * Gets the pack.
	 *
	 * @return the pack
	 */
	public String getPack() {
		return pack;
	}
	
	/**
	 * Sets the pack.
	 *
	 * @param pack the new pack
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}
	
	/**
	 * Gets the receiveOrgName.
	 *
	 * @return the receiveOrgName
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	
	/**
	 * Sets the receiveOrgName.
	 *
	 * @param receiveOrgName the new receiveOrgName
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
	/**
	 * Gets the reachOrgName.
	 *
	 * @return the reachOrgName
	 */
	public String getReachOrgName() {
		return reachOrgName;
	}
	
	/**
	 * Sets the reachOrgName.
	 *
	 * @param reachOrgName the new reachOrgName
	 */
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
}