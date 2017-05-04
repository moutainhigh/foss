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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/UnloadDiffReportDetailEntity.java
 *  
 *  FILE NAME          :UnloadDiffReportDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: UnloadDiffReportDetailEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告明细
 * @date: 2012-12-11 上午10:48:19
 * 
 */
public class UnloadDiffReportDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 2229078112506764267L;
	
	//卸车差异报告ID
	private String diffReportId;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//oa差错单编号
	private String oaMistakeBillNo;
	//差异类型
	private String diffType;
	//运输性质
	private String transProperty;
	//上一环节是否扫描
	private String isScanedLastTime;
	//卸货时间
	private Date unloadTime;
	//异常处理时间
	private Date exceptionHandleTime;
	//备注
	private String note;
	//卸车部门code
	private String unloadOrgCode;
	//处理人编号
	private String handlerCode;
	//处理人姓名
	private String handlerName;
	//处理部门编号
	private String handleOrgCode;
	//处理部门名称
	private String handleOrgName;
	//报告生成时间
	private Date reportCreatedTime;
	//卸车任务code
	private String unloadTaskNo;
	//卸车任务ID
	private String unloadTaskId;
	//卸车任务类型
	private String unloadTaskType;
	/**目的站**/
	private String targetOrg;
	
	/**库存状态**/
	private String stockState;
	
	/**车牌号*/
	private String vehicleNo;
	
	/**包号**/
	private String packageNo;
	
	//卸车扫描类型 
	private String scanState;
	
	// 产品类型
	private String productType;
	
	// 笼号
	private String  cageNo;
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCageNo() {
		return cageNo;
	}
	public void setCageNo(String cageNo) {
		this.cageNo = cageNo;
	}
	public String getUnloadTaskType() {
		return unloadTaskType;
	}
	public void setUnloadTaskType(String unloadTaskType) {
		this.unloadTaskType = unloadTaskType;
	}
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}
	public String getUnloadOrgCode() {
		return unloadOrgCode;
	}
	public void setUnloadOrgCode(String unloadOrgCode) {
		this.unloadOrgCode = unloadOrgCode;
	}
	public String getDiffReportId() {
		return diffReportId;
	}
	public void setDiffReportId(String diffReportId) {
		this.diffReportId = diffReportId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getHandlerCode() {
		return handlerCode;
	}
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public String getHandleOrgCode() {
		return handleOrgCode;
	}
	public void setHandleOrgCode(String handleOrgCode) {
		this.handleOrgCode = handleOrgCode;
	}
	public String getHandleOrgName() {
		return handleOrgName;
	}
	public void setHandleOrgName(String handleOrgName) {
		this.handleOrgName = handleOrgName;
	}
	public Date getReportCreatedTime() {
		return reportCreatedTime;
	}
	public void setReportCreatedTime(Date reportCreatedTime) {
		this.reportCreatedTime = reportCreatedTime;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getOaMistakeBillNo() {
		return oaMistakeBillNo;
	}
	public void setOaMistakeBillNo(String oaMistakeBillNo) {
		this.oaMistakeBillNo = oaMistakeBillNo;
	}
	public String getDiffType() {
		return diffType;
	}
	public void setDiffType(String diffType) {
		this.diffType = diffType;
	}
	public String getTransProperty() {
		return transProperty;
	}
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	public String getIsScanedLastTime() {
		return isScanedLastTime;
	}
	public void setIsScanedLastTime(String isScanedLastTime) {
		this.isScanedLastTime = isScanedLastTime;
	}
	public Date getUnloadTime() {
		return unloadTime;
	}
	public void setUnloadTime(Date unloadTime) {
		this.unloadTime = unloadTime;
	}
	public Date getExceptionHandleTime() {
		return exceptionHandleTime;
	}
	public void setExceptionHandleTime(Date exceptionHandleTime) {
		this.exceptionHandleTime = exceptionHandleTime;
	}
	/**   
	 * targetOrg   
	 *   
	 * @return  the targetOrg   
	 */
	
	public String getTargetOrg() {
		return targetOrg;
	}
	/**   
	 * @param targetOrg the targetOrg to set
	 * Date:2013-7-9上午10:16:13
	 */
	public void setTargetOrg(String targetOrg) {
		this.targetOrg = targetOrg;
	}
	public String getStockState() {
		return stockState;
	}
	public void setStockState(String stockState) {
		this.stockState = stockState;
	}
	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public final String getPackageNo() {
		return packageNo;
	}
	public final void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getScanState() {
		return scanState;
	}
	public void setScanState(String scanState) {
		this.scanState = scanState;
	}
	
}