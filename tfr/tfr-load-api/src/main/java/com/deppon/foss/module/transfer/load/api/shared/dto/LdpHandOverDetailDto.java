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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LdpHandOverDetailDto.java
 *  
 *  FILE NAME          :LdpHandOverDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * @author: ibm-liuzhaowei
 * @description: 交接单明细（落地配公司查询落地配交接单时使用)
 * @date: 2013-08-05 下午04:19:39
 * 
 */
public class LdpHandOverDetailDto implements Serializable {
	
	private static final long serialVersionUID = 4199199833119647704L;
	/**
	 * 交接单号
	 * 
	 */
	private String waybillNo;
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
	 * 货物名称
	 * 
	 */
	private String goodsName;
	/**
	 * 货物类型(A_TYPE: A货  B_TYPE：B货ALL: 全部)
	 * 
	 */
	private String goodsType;
	/**
	 * 声明价值（元）
	 * 
	 */
	private BigDecimal declarationValue;
	/**
	 * 交接时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	private Date handoverTime;
	/**
	 *件数
	 * 
	 */
	private Integer goodsQTY;
	/**
	 * 重量（KG）
	 * 
	 */
	private BigDecimal weight;
	/**
	 * 体积（m3）
	 * 
	 */
	private BigDecimal volume;
	/**
	 * 备注
	 * 
	 */
	private String notes;
	
	
	/**
	 * 交接单号
	 * 
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 交接单号
	 * 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
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
	 * 货物名称
	 * 
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 货物名称
	 * 
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 货物类型(A_TYPE: A货  B_TYPE：B货ALL: 全部)
	 * 
	 */
	public String getGoodsType() {
		return goodsType;
	}
	/**
	 * 货物类型(A_TYPE: A货  B_TYPE：B货ALL: 全部)
	 * 
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	/**
	 * 声明价值（元）
	 * 
	 */
	public BigDecimal getDeclarationValue() {
		return declarationValue;
	}
	/**
	 * 声明价值（元）
	 * 
	 */
	public void setDeclarationValue(BigDecimal declarationValue) {
		this.declarationValue = declarationValue;
	}
	/**
	 * 交接时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	public Date getHandoverTime() {
		return handoverTime;
	}
	/**
	 * 交接时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}
	/**
	 *件数
	 * 
	 */
	public Integer getGoodsQTY() {
		return goodsQTY;
	}
	/**
	 *件数
	 * 
	 */
	public void setGoodsQTY(Integer goodsQTY) {
		this.goodsQTY = goodsQTY;
	}
	/**
	 * 重量（KG）
	 * 
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * 重量（KG）
	 * 
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * 体积（m3）
	 * 
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	/**
	 * 体积（m3）
	 * 
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**
	 * 备注
	 * 
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 备注
	 * 
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
