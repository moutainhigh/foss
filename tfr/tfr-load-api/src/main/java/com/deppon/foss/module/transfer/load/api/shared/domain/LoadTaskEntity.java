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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoadTaskEntity.java
 *  
 *  FILE NAME          :LoadTaskEntity.java
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
 * FILE    NAME: LoadTaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * LoadTaskEntity
 * @author dp-duyi
 * @date 2012-10-30 上午11:04:54
 */
public class LoadTaskEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4428597868444927639L;
	/**id*/
	private String id;
	/**任务类型*/
	private String taskType;		
	/**任务编号*/
	private String taskNo;			
	/**派送单单号*/
	private String deliverBillNo;	
	/**车牌号*/
	private String vehicleNo;		
	/**月台虚拟编码*/
	private String platformId;		
	/**月台号*/
	private String platformNo;		
	/**装车开始时间*/
	private String loadStartTime;	
	/**装车结束时间*/
	private String loadEndTime;		
	/**任务状态*/
	private String state;			
	/**线路*/
	private String line;			
	/**出发部门名称*/
	private String origOrgName;		
	/**出发部门编号*/
	private String origOrgCode;		
	/**货物类型*/
	private String goodsType;		
	/**是否生成差异报告*/
	private String beCreateGapRep;	
	/**参加理货员人数*/
	private int loaderQty;          
	/**到达部门名称列表*/
	private String destOrgNames;     
	/**
	 * 卸车方式（PDA和非PDA）
	 */
	private String loadWay;
	/**transitGoodsType*/
	private String transitGoodsType;              //转货类型
	/**是否为子母件*/
	private String isPicPackage;
	/**运单号*/
	private String waybillNo;
	
	/** 装车类型 快递 1, 零担0, 合单2 **/
	private Integer sendType;
	
	private String errorMsg;
	
	private String device_no;
	
	private String submitTotalCount;
	
	/**
	 * @return the submitTotalCount
	 */
	public String getSubmitTotalCount() {
		return submitTotalCount;
	}


	/**
	 * @param submitTotalCount the submitTotalCount to set
	 */
	public void setSubmitTotalCount(String submitTotalCount) {
		this.submitTotalCount = submitTotalCount;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	/** 装车来源(0-foss,1-悟空同步过来) */
	private Integer loadSource = 0;
	
	
	public String getLoadWay() {
		return loadWay;
	}


	public void setLoadWay(String loadWay) {
		this.loadWay = loadWay;
	}


	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the 任务编号.
	 *
	 * @param taskNo the new 任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * Gets the 派送单单号.
	 *
	 * @return the 派送单单号
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}
	
	/**
	 * Sets the 派送单单号.
	 *
	 * @param deliverBillNo the new 派送单单号
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}
	
	/**
	 * Gets the 任务状态.
	 *
	 * @return the 任务状态
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the 任务状态.
	 *
	 * @param state the new 任务状态
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the 装车结束时间.
	 *
	 * @return the 装车结束时间
	 */
	public String getLoadEndTime() {
		return loadEndTime;
	}
	
	/**
	 * Sets the 装车结束时间.
	 *
	 * @param loadEndTime the new 装车结束时间
	 */
	public void setLoadEndTime(String loadEndTime) {
		this.loadEndTime = loadEndTime;
	}
	
	/**
	 * Gets the 月台虚拟编码.
	 *
	 * @return the 月台虚拟编码
	 */
	public String getPlatformId() {
		return platformId;
	}
	
	/**
	 * Sets the 月台虚拟编码.
	 *
	 * @param platformId the new 月台虚拟编码
	 */
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	
	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the 任务类型.
	 *
	 * @return the 任务类型
	 */
	public String getTaskType() {
		return taskType;
	}
	
	/**
	 * Sets the 任务类型.
	 *
	 * @param taskType the new 任务类型
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	/**
	 * Gets the 月台号.
	 *
	 * @return the 月台号
	 */
	public String getPlatformNo() {
		return platformNo;
	}
	
	/**
	 * Sets the 月台号.
	 *
	 * @param platformNo the new 月台号
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	/**
	 * Gets the 装车开始时间.
	 *
	 * @return the 装车开始时间
	 */
	public String getLoadStartTime() {
		return loadStartTime;
	}
	
	/**
	 * Sets the 装车开始时间.
	 *
	 * @param loadStartTime the new 装车开始时间
	 */
	public void setLoadStartTime(String loadStartTime) {
		this.loadStartTime = loadStartTime;
	}
	
	/**
	 * Gets the 线路.
	 *
	 * @return the 线路
	 */
	public String getLine() {
		return line;
	}
	
	/**
	 * Sets the 线路.
	 *
	 * @param line the new 线路
	 */
	public void setLine(String line) {
		this.line = line;
	}
	
	/**
	 * Gets the 出发部门名称.
	 *
	 * @return the 出发部门名称
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * Sets the 出发部门名称.
	 *
	 * @param origOrgName the new 出发部门名称
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * Gets the 出发部门编号.
	 *
	 * @return the 出发部门编号
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * Sets the 出发部门编号.
	 *
	 * @param origOrgCode the new 出发部门编号
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the 货物类型.
	 *
	 * @return the 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}
	
	/**
	 * Sets the 货物类型.
	 *
	 * @param goodsType the new 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	/**
	 * Gets the 是否生成差异报告.
	 *
	 * @return the 是否生成差异报告
	 */
	public String getBeCreateGapRep() {
		return beCreateGapRep;
	}
	
	/**
	 * Sets the 是否生成差异报告.
	 *
	 * @param beCreateGapRep the new 是否生成差异报告
	 */
	public void setBeCreateGapRep(String beCreateGapRep) {
		this.beCreateGapRep = beCreateGapRep;
	}
	
	/**
	 * Gets the 参加理货员人数.
	 *
	 * @return the 参加理货员人数
	 */
	public int getLoaderQty() {
		return loaderQty;
	}
	
	/**
	 * Sets the 参加理货员人数.
	 *
	 * @param loaderQty the new 参加理货员人数
	 */
	public void setLoaderQty(int loaderQty) {
		this.loaderQty = loaderQty;
	}
	
	/**
	 * Gets the 到达部门名称列表.
	 *
	 * @return the 到达部门名称列表
	 */
	public String getDestOrgNames() {
		return destOrgNames;
	}
	
	/**
	 * Sets the 到达部门名称列表.
	 *
	 * @param destOrgNames the new 到达部门名称列表
	 */
	public void setDestOrgNames(String destOrgNames) {
		this.destOrgNames = destOrgNames;
	}
	
	/**
	 * 
	 * Gets the transitGoodsType. 
	 * @author alfred
	 * @date 2013-11-13 上午11:32:09
	 * @return transitGoodsType
	 * @see
	 */
	public String getTransitGoodsType() {
		return transitGoodsType;
	}

	/**
	 * 
	 * <p> Sets the transitGoodsType</p> 
	 * @author alfred
	 * @date 2013-11-13 上午11:32:15
	 * @param transitGoodsType
	 * @see
	 */
	public void setTransitGoodsType(String transitGoodsType) {
		this.transitGoodsType = transitGoodsType;
	}

	/**
	 * 
	 * Gets the isPicPackage. 
	 * @author hongwy
	 * @date 2015-09-11 下午15:32:09
	 * @return isPicPackage
	 * @see
	 */
	public String getIsPicPackage() {
		return isPicPackage;
	}

	/**
	 * 
	 * Sets the isPicPackage. 
	 * @author hongwy
	 * @date 2015-09-11 下午15:32:09
	 * @return isPicPackage
	 * @see
	 */
	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}


	public String getWaybillNo() {
		return waybillNo;
	}


	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}


	public Integer getSendType() {
		return sendType;
	}


	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}


	public Integer getLoadSource() {
		if(loadSource == null) {
			return 0;
		}
		return loadSource;
	}


	public void setLoadSource(Integer loadSource) {
		this.loadSource = loadSource;
	}


	public String getDevice_no() {
		return device_no;
	}


	public void setDevice_no(String device_no) {
		this.device_no = device_no;
	}
	
	
	
}