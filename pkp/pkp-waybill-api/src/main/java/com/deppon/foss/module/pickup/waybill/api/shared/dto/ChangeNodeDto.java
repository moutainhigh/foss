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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/dto/ChangeNodeDto.java
 * 
 * FILE NAME        	: ChangeNodeDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.waybill.api.shared.dto;

import java.io.Serializable;

 
/***
 * 变更节点 Dto
 * @author 105089-foss-yangtong
 * @date 2012-11-28 上午11:39:56
 * @since
 * @version
 */
public class ChangeNodeDto implements Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//标签编号
	private String serialNo;
	//库存部门
    private String origOrgCode;
    //下一库存部门
    private String objectiveOrgCode;
    //执行节点
    private String exeNode;
    //原走货路径
    private String ofreightRoute;
    //新走货路径
    private String dfreightRoute;
    // 执行节点(可选)
 	private String actuatingNode;
 	//执行节点(部门名称)
 	private String exeNodeName;
 	
 	//更改单主键
 	private String waybillRfcId;
 	//运单号
 	private String waybillNo;
 	
 	// 超时处理（分钟）
 	private String timeoutHandleMinute;
 	// 超时提醒（次数）
 	private String timeoutRemindTotalNum;
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	/**
	 * @return the objectiveOrgCode
	 */
	public String getObjectiveOrgCode() {
		return objectiveOrgCode;
	}
	/**
	 * @param objectiveOrgCode the objectiveOrgCode to set
	 */
	public void setObjectiveOrgCode(String objectiveOrgCode) {
		this.objectiveOrgCode = objectiveOrgCode;
	}
	/**
	 * @return the exeNode
	 */
	public String getExeNode() {
		return exeNode;
	}
	/**
	 * @param exeNode the exeNode to set
	 */
	public void setExeNode(String exeNode) {
		this.exeNode = exeNode;
	}
	
	/**
	 * @return the ofreightRoute
	 */
	public String getOfreightRoute() {
		return ofreightRoute;
	}
	/**
	 * @param ofreightRoute the ofreightRoute to set
	 */
	public void setOfreightRoute(String ofreightRoute) {
		this.ofreightRoute = ofreightRoute;
	}
	/**
	 * @return the dfreightRoute
	 */
	public String getDfreightRoute() {
		return dfreightRoute;
	}
	/**
	 * @param dfreightRoute the dfreightRoute to set
	 */
	public void setDfreightRoute(String dfreightRoute) {
		this.dfreightRoute = dfreightRoute;
	}
	/**
	 * @return the labeledGoodTodoEntity
	 */
	/**
	 * @param labeledGoodTodoEntity the labeledGoodTodoEntity to set
	 */
	/**
	 * @return the actuatingNode
	 */
	public String getActuatingNode() {
		return actuatingNode;
	}
	/**
	 * @param actuatingNode the actuatingNode to set
	 */
	public void setActuatingNode(String actuatingNode) {
		this.actuatingNode = actuatingNode;
	}
	/**
	 * @return the exeNodeName
	 */
	public String getExeNodeName() {
		return exeNodeName;
	}
	/**
	 * @param exeNodeName the exeNodeName to set
	 */
	public void setExeNodeName(String exeNodeName) {
		this.exeNodeName = exeNodeName;
	}
	/**
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}
	/**
	 * @param waybillRfcId the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getTimeoutHandleMinute() {
		return timeoutHandleMinute;
	}
	public void setTimeoutHandleMinute(String timeoutHandleMinute) {
		this.timeoutHandleMinute = timeoutHandleMinute;
	}
	public String getTimeoutRemindTotalNum() {
		return timeoutRemindTotalNum;
	}
	public void setTimeoutRemindTotalNum(String timeoutRemindTotalNum) {
		this.timeoutRemindTotalNum = timeoutRemindTotalNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    

}