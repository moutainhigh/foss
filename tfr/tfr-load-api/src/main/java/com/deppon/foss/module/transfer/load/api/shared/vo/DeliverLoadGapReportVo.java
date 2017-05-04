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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/DeliverLoadGapReportVo.java
 *  
 *  FILE NAME          :DeliverLoadGapReportVo.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.vo
 * FILE    NAME: DeliverLoadGapReport.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;

/**
 * DeliverLoadGapReportVo
 * @author dp-duyi
 * @date 2012-10-26 上午11:38:36
 */
public class DeliverLoadGapReportVo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6526124061192601156L;
	/**差异报告s*/
	private List<DeliverLoadGapReportEntity> reports;
	/**差异报告流水号明细*/
	private List<DeliverLoadGapReportSerialEntity> reportSerials;
	/**差异报告运单明细*/
	private List<DeliverLoadGapReportWayBillEntity> reportWayBills;
	/**差异报告*/
	private DeliverLoadGapReportEntity report;
	
	/** 差异报告 */
	private DeliverLoadGapReportWayBillEntity reportWayBill;
	/**查询开始时间*/
	private String queryTimeBegin;
	
	/** 查询结束时间*/
	private String queryTimeEnd;
	
	/**
	 * Gets the 差异报告s.
	 *
	 * @return the 差异报告s
	 */
	public List<DeliverLoadGapReportEntity> getReports() {
		return reports;
	}
	
	/**
	 * Sets the 差异报告s.
	 *
	 * @param reports the new 差异报告s
	 */
	public void setReports(List<DeliverLoadGapReportEntity> reports) {
		this.reports = reports;
	}
	
	/**
	 * Gets the 差异报告.
	 *
	 * @return the 差异报告
	 */
	public DeliverLoadGapReportEntity getReport() {
		return report;
	}
	
	/**
	 * Sets the 差异报告.
	 *
	 * @param report the new 差异报告
	 */
	public void setReport(DeliverLoadGapReportEntity report) {
		this.report = report;
	}
	
	/**
	 * Gets the 查询开始时间.
	 *
	 * @return the 查询开始时间
	 */
	public String getQueryTimeBegin() {
		return queryTimeBegin;
	}
	
	/**
	 * Sets the 查询开始时间.
	 *
	 * @param queryTimeBegin the new 查询开始时间
	 */
	public void setQueryTimeBegin(String queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	
	/**
	 * Gets the 查询结束时间.
	 *
	 * @return the 查询结束时间
	 */
	public String getQueryTimeEnd() {
		return queryTimeEnd;
	}
	
	/**
	 * Sets the 查询结束时间.
	 *
	 * @param queryTimeEnd the new 查询结束时间
	 */
	public void setQueryTimeEnd(String queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}
	
	/**
	 * Gets the 差异报告流水号明细.
	 *
	 * @return the 差异报告流水号明细
	 */
	public List<DeliverLoadGapReportSerialEntity> getReportSerials() {
		return reportSerials;
	}
	
	/**
	 * Sets the 差异报告流水号明细.
	 *
	 * @param reportSerials the new 差异报告流水号明细
	 */
	public void setReportSerials(
			List<DeliverLoadGapReportSerialEntity> reportSerials) {
		this.reportSerials = reportSerials;
	}
	
	/**
	 * Gets the 差异报告运单明细.
	 *
	 * @return the 差异报告运单明细
	 */
	public List<DeliverLoadGapReportWayBillEntity> getReportWayBills() {
		return reportWayBills;
	}
	
	/**
	 * Sets the 差异报告运单明细.
	 *
	 * @param reportWayBills the new 差异报告运单明细
	 */
	public void setReportWayBills(
			List<DeliverLoadGapReportWayBillEntity> reportWayBills) {
		this.reportWayBills = reportWayBills;
	}
	
	/**
	 * Gets the 差异报告.
	 *
	 * @return the 差异报告
	 */
	public DeliverLoadGapReportWayBillEntity getReportWayBill() {
		return reportWayBill;
	}
	
	/**
	 * Sets the 差异报告.
	 *
	 * @param reportWayBill the new 差异报告
	 */
	public void setReportWayBill(DeliverLoadGapReportWayBillEntity reportWayBill) {
		this.reportWayBill = reportWayBill;
	}
	
}