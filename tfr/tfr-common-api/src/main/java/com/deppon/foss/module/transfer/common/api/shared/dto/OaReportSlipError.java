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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/OaReportSlipError.java
 *  
 *  FILE NAME          :OaReportSlipError.java
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
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.shared.dto
 * FILE    NAME: OaReportSlipError.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 封签差错数据实体
 * @author 046130-foss-xuduowei
 * @date 2012-11-28 上午9:10:03
 */
public class OaReportSlipError {
	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 	车辆类型
	 */
	private String carType;
	
    /**
     * 	封签类型
     */
	private String slipType;

	/**
	 * 	到达部门
	 */
	private String arrivedDept;
	
	/**
	 * 	封签号码
	 */
	private String slipNo;
	
	/**
	 * 	扫描人工号
	 */
	private String scannerUserID;

	/**
	 * 	扫描人姓名
	 */
	private String scannerUserName;
	
	/**
	 * 	扫描人所属部门
	 */
	private String scannerUserDept;
	
	/**
	 * 	扫描时间
	 */
	private Date scanTime;
	
	/**
	 * 	公司责任司机工号
	 */
	private String resDriverUserID;

	/**
	 * 	公司责任司机姓名
	 */
	private String resDriverUserName;
	
	/**
	 * 	公司责任司机所在部门
	 */
	private String resDriverDept;
	
	/**
	 * 	责任司机所在部门编号
	 */
	private String resDriverDeptID;
	
	/**
	 * 	外请责任司机姓名
	 */
	private String resOutDriverUserName;
	
	/**
	 * 	外请责任司机所在部门
	 */
	private String resOutDriverDept;
	
	/**
	 * 	事件经过
	 */
	private String eventReport;

	/**
	 * 获取 到达部门.
	 *
	 * @return the 到达部门
	 */
	public String getArrivedDept() {
		return arrivedDept;
	}

	/**
	 * 设置 到达部门.
	 *
	 * @param arrivedDept the new 到达部门
	 */
	public void setArrivedDept(String arrivedDept) {
		this.arrivedDept = arrivedDept;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getCarNo() {
		return carNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param carNo the new 车牌号
	 */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	/**
	 * 获取 事件经过.
	 *
	 * @return the 事件经过
	 */
	public String getEventReport() {
		return eventReport;
	}

	/**
	 * 设置 事件经过.
	 *
	 * @param eventReport the new 事件经过
	 */
	public void setEventReport(String eventReport) {
		this.eventReport = eventReport;
	}

	/**
	 * 获取 责任司机所在部门.
	 *
	 * @return the 责任司机所在部门
	 */
	public String getResDriverDept() {
		return resDriverDept;
	}

	/**
	 * 设置 责任司机所在部门.
	 *
	 * @param resDriverDept the new 责任司机所在部门
	 */
	public void setResDriverDept(String resDriverDept) {
		this.resDriverDept = resDriverDept;
	}

	/**
	 * 获取 责任司机所在部门编号.
	 *
	 * @return the 责任司机所在部门编号
	 */
	public String getResDriverDeptID() {
		return resDriverDeptID;
	}

	/**
	 * 设置 责任司机所在部门编号.
	 *
	 * @param resDriverDeptID the new 责任司机所在部门编号
	 */
	public void setResDriverDeptID(String resDriverDeptID) {
		this.resDriverDeptID = resDriverDeptID;
	}

	/**
	 * 获取 责任司机工号.
	 *
	 * @return the 责任司机工号
	 */
	public String getResDriverUserID() {
		return resDriverUserID;
	}

	/**
	 * 设置 责任司机工号.
	 *
	 * @param resDriverUserID the new 责任司机工号
	 */
	public void setResDriverUserID(String resDriverUserID) {
		this.resDriverUserID = resDriverUserID;
	}

	/**
	 * 获取 扫描人工号.
	 *
	 * @return the 扫描人工号
	 */
	public String getScannerUserID() {
		return scannerUserID;
	}

	/**
	 * 设置 扫描人工号.
	 *
	 * @param scannerUserID the new 扫描人工号
	 */
	public void setScannerUserID(String scannerUserID) {
		this.scannerUserID = scannerUserID;
	}

	/**
	 * 获取 扫描时间.
	 *
	 * @return the 扫描时间
	 */
	public Date getScanTime() {
		return scanTime;
	}

	/**
	 * 设置 扫描时间.
	 *
	 * @param scanTime the new 扫描时间
	 */
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	/**
	 * 获取 封签号码.
	 *
	 * @return the 封签号码
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * 设置 封签号码.
	 *
	 * @param slipNo the new 封签号码
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * 获取 封签类型.
	 *
	 * @return the 封签类型
	 */
	public String getSlipType() {
		return slipType;
	}

	/**
	 * 设置 封签类型.
	 *
	 * @param slipType the new 封签类型
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getScannerUserName() {
		return scannerUserName;
	}

	public void setScannerUserName(String scannerUserName) {
		this.scannerUserName = scannerUserName;
	}

	public String getScannerUserDept() {
		return scannerUserDept;
	}

	public void setScannerUserDept(String scannerUserDept) {
		this.scannerUserDept = scannerUserDept;
	}

	public String getResDriverUserName() {
		return resDriverUserName;
	}

	public void setResDriverUserName(String resDriverUserName) {
		this.resDriverUserName = resDriverUserName;
	}

	public String getResOutDriverUserName() {
		return resOutDriverUserName;
	}

	public void setResOutDriverUserName(String resOutDriverUserName) {
		this.resOutDriverUserName = resOutDriverUserName;
	}

	public String getResOutDriverDept() {
		return resOutDriverDept;
	}

	public void setResOutDriverDept(String resOutDriverDept) {
		this.resOutDriverDept = resOutDriverDept;
	}
}