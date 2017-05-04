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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StReportDto.java
 *  
 *  FILE NAME          :StReportDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;

/**
 * 清仓差异报告DTO
 * @author foss-wuyingjie
 * @date 2012-11-20 下午2:12:07
 */
public class StReportDto extends StDifferReportEntity{

	private static final long serialVersionUID = -7468351343732147006L;
	
	/**创建时间 起始时间*/
	private Date startTime;
	/**创建时间 截止时间*/
	private Date endTime;
	
	/**清仓员*/
	private String operator;
	/**运单号*/
	private String waybillNo;
	/**异常状态 ： 多货、少货*/
	private String exType;
	/**当前登录人所属部门*/
	private String currentDeptCode;
	/**清仓任务ID*/
	private String taskId;
	/**流水号*/
	private String serialNo;
	/**流水号list,用于查询关联差异明细*/
	private List<String> serailNosList;
	/**起始件数*/
	private int startQty;
	/**结束件数*/
	private int endQty;
	/**提货方式*/
	private String receiveMethod;
	/**分区名称*/
	private String districtName;
	/**行政区*/
	private String administrativeRegion;
	/**件区*/
	private String  pieceRegion;
	

	public List<String> getSerailNosList() {
		return serailNosList;
	}

	public void setSerailNosList(List<String> serailNosList) {
		this.serailNosList = serailNosList;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取 清仓员.
	 *
	 * @return the 清仓员
	 */
	public String getOperator() {
		return operator;
	}
	
	/**
	 * 设置 清仓员.
	 *
	 * @param operator the new 清仓员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * 获取 创建时间 起始时间.
	 *
	 * @return the 创建时间 起始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	
	/**
	 * 设置 创建时间 起始时间.
	 *
	 * @param startTime the new 创建时间 起始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 获取 创建时间 截止时间.
	 *
	 * @return the 创建时间 截止时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * 设置 创建时间 截止时间.
	 *
	 * @param endTime the new 创建时间 截止时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 异常状态 ： 多货、少货.
	 *
	 * @return the 异常状态 ： 多货、少货
	 */
	public String getExType() {
		return exType;
	}
	
	/**
	 * 设置 异常状态 ： 多货、少货.
	 *
	 * @param exType the new 异常状态 ： 多货、少货
	 */
	public void setExType(String exType) {
		this.exType = exType;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	/**
	 * 获取起始件数.
	 *
	 * @return the 起始件数
	 */
	public int getStartQty() {
		return startQty;
	}

	/**
	 * 设置 运起始件数.
	 *
	 * @param startQty the new 起始件数
	 */
	public void setStartQty(int startQty) {
		this.startQty = startQty;
	}

	/**
	 * 获取结束件数.
	 *
	 * @return the 结束件数
	 */
	public int getEndQty() {
		return endQty;
	}

	/**
	 * 设置 结束件数.
	 *
	 * @param endQty the new 结束件数
	 */
	public void setEndQty(int endQty) {
		this.endQty = endQty;
	}

	/**
	 * 获取提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 设置 提货方式.
	 *
	 * @param receiveMethod the new 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 获取分区名称.
	 *
	 * @return the 分区名称
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * 设置 分区名称.
	 *
	 * @param districtName the new 分区名称
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * 获取行政区名称.
	 *
	 * @return the 获取行政区名称
	 */
	public String getAdministrativeRegion() {
		return administrativeRegion;
	}

	/**
	 * 设置 行政区名称.
	 *
	 * @param administrativeRegion the new 行政区名称
	 */
	public void setAdministrativeRegion(String administrativeRegion) {
		this.administrativeRegion = administrativeRegion;
	}

	/**
	 * 获取分区名称.
	 *
	 * @return the 获取分区名称
	 */
	public String getPieceRegion() {
		return pieceRegion;
	}

	/**
	 * 设置 分区名称.
	 *
	 * @param pieceRegion the new 分区名称
	 */
	public void setPieceRegion(String pieceRegion) {
		this.pieceRegion = pieceRegion;
	}
}