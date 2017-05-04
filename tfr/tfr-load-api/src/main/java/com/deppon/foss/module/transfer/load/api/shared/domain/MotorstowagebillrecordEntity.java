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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/MotorstowagebillrecordEntity.java
 *  
 *  FILE NAME          :MotorstowagebillrecordEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 汽运配载单打印记录 
 * @author ibm-zhangyixin
 * @date 2012-11-20 下午2:19:24
 */
public class MotorstowagebillrecordEntity extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -7263267590645473121L;
	
	/**ID**/
	private String id;

	/**打印类型**/
	private String printType;

	/**配置单号**/
	private String vehicleassembleNo;

	/**打印人姓名**/
	private String printerName;
	
	/**打印人工号**/
	private String printerCode;

	/**打印时间**/
	private Date printTime;

	/**打印部门**/
	private String orgName;

	/**打印部门编号**/
	private String orgCode;
	
	/** 
	 * ID
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:24:31
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}
	
	/** 
	 * ID
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:24:31
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 打印类型*.
	 *
	 * @return the 打印类型*
	 */
	public String getPrintType() {
		return printType;
	}
	
	/**
	 * 设置 打印类型*.
	 *
	 * @param printType the new 打印类型*
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	
	/**
	 * 获取 配置单号*.
	 *
	 * @return the 配置单号*
	 */
	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}
	
	/**
	 * 设置 配置单号*.
	 *
	 * @param vehicleassembleNo the new 配置单号*
	 */
	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}
	
	/**
	 * 获取 打印人姓名*.
	 *
	 * @return the 打印人姓名*
	 */
	public String getPrinterName() {
		return printerName;
	}
	
	/**
	 * 设置 打印人姓名*.
	 *
	 * @param printerName the new 打印人姓名*
	 */
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	
	/**
	 * 获取 打印人工号*.
	 *
	 * @return the 打印人工号*
	 */
	public String getPrinterCode() {
		return printerCode;
	}
	
	/**
	 * 设置 打印人工号*.
	 *
	 * @param printerCode the new 打印人工号*
	 */
	public void setPrinterCode(String printerCode) {
		this.printerCode = printerCode;
	}
	
	/**
	 * 获取 打印时间*.
	 *
	 * @return the 打印时间*
	 */
	public Date getPrintTime() {
		return printTime;
	}
	
	/**
	 * 设置 打印时间*.
	 *
	 * @param printTime the new 打印时间*
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	
	/**
	 * 获取 打印部门*.
	 *
	 * @return the 打印部门*
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * 设置 打印部门*.
	 *
	 * @param orgName the new 打印部门*
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * 获取 打印部门编号*.
	 *
	 * @return the 打印部门编号*
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 打印部门编号*.
	 *
	 * @param orgCode the new 打印部门编号*
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}