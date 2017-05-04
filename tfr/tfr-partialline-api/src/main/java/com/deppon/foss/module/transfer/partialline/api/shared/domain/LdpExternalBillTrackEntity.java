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
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/domain/LdpExternalBillTrackEntity.java
 * 
 *  FILE NAME     :LdpExternalBillTrackEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 落地配外发单轨迹实体
 * 
 * @author liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillTrackEntity extends BaseEntity {

	private static final long serialVersionUID = -8102274328129581810L;
	
	/**
	 * id
	 * 
	 */
	private String id;
	/**
	 * 轨迹流水
	 * 
	 */
	 private String traceId;
	 /**
	  * 运单号
	  * 
	  */
	 private String waybillNo;
	 /**
	  * 代理公司编码
	  * 
	  */
	 private String agentCompanyCode;
	 /**
	  * 代理公司名称
	  * 
	  */
	 private String agentCompanyName;
	/**
	 * 代理网点编码
	 * 
	 */
	 private String agentOrgCode;
	 /**
	  * 代理网点名称
	  * 
	  */
	 private String agentOrgName;
	 /**
	  * 操作所在城市
	  * 
	  */
	 private String operationcity;
	 /**
	  * 操作类型：到达-1，离开-2，派件-3，派件失败-4，拒签-99
	  * 
	  */
	 private String operationtype;
	 /**
	  * 派件人姓名
	  * 
	  */
	 private String dispatchname;
	 /**
	  * 派件人手机号
	  * 
	  */
	 private String dispatchphoneno;
	 /**
	  * 操作时间
	  * 
	  */
	 private Date operationTime;
	 /**
	  * 操作人
	  * 
	  */
	 private String operationUserName;
	 /**
	  * 操作描述
	  * 
	  */
	 private String operationDescribe;
	 
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 轨迹流水
	 * 
	 */
	public String getTraceId() {
		return traceId;
	}
	
	/**
	 * 轨迹流水
	 * 
	 */
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	 /**
	  * 运单号
	  * 
	  */
	public String getWaybillNo() {
		return waybillNo;
	}
	 /**
	  * 运单号
	  * 
	  */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	 /**
	  * 代理公司编码
	  * 
	  */
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	
	 /**
	  * 代理公司编码
	  * 
	  */
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	
	/**
	  * 代理公司名称
	  * 
	  */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}
	
	/**
	  * 代理公司名称
	  * 
	  */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}
	
	/**
	 * 代理网点编码
	 * 
	 */
	public String getAgentOrgCode() {
		return agentOrgCode;
	}
	
	/**
	 * 代理网点编码
	 * 
	 */
	public void setAgentOrgCode(String agentOrgCode) {
		this.agentOrgCode = agentOrgCode;
	}
	
	/**
	  * 代理网点名称
	  * 
	  */
	public String getAgentOrgName() {
		return agentOrgName;
	}
	
	/**
	  * 代理网点名称
	  * 
	  */
	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}
	
	/**
	  * 操作所在城市
	  * 
	  */
	public String getOperationcity() {
		return operationcity;
	}
	
	/**
	  * 操作所在城市
	  * 
	  */
	public void setOperationcity(String operationcity) {
		this.operationcity = operationcity;
	}
	
	/**
	  * 操作类型：到达-1，离开-2，派件-3，派件失败-4
	  * 
	  */
	public String getOperationtype() {
		return operationtype;
	}
	
	/**
	  * 操作类型：到达-1，离开-2，派件-3，派件失败-4
	  * 
	  */
	public void setOperationtype(String operationtype) {
		this.operationtype = operationtype;
	}
	
	/**
	  * 派件人姓名
	  * 
	  */
	public String getDispatchname() {
		return dispatchname;
	}
	
	/**
	  * 派件人姓名
	  * 
	  */
	public void setDispatchname(String dispatchname) {
		this.dispatchname = dispatchname;
	}
	
	/**
	  * 派件人手机号
	  * 
	  */
	public String getDispatchphoneno() {
		return dispatchphoneno;
	}
	
	/**
	  * 派件人手机号
	  * 
	  */
	public void setDispatchphoneno(String dispatchphoneno) {
		this.dispatchphoneno = dispatchphoneno;
	}
	
	/**
	  * 操作时间
	  * 
	  */
	public Date getOperationTime() {
		return operationTime;
	}
	
	/**
	  * 操作时间
	  * 
	  */
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	/**
	  * 操作人
	  * 
	  */
	public String getOperationUserName() {
		return operationUserName;
	}
	
	/**
	  * 操作人
	  * 
	  */
	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}
	
	/**
	  * 操作描述
	  * 
	  */
	public String getOperationDescribe() {
		return operationDescribe;
	}
	
	/**
	  * 操作描述
	  * 
	  */
	public void setOperationDescribe(String operationDescribe) {
		this.operationDescribe = operationDescribe;
	}
		 
	
}
	