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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PDAGoodsAttrDto.java
 * 
 * FILE NAME        	: PDAGoodsAttrDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * PDA上传重量体积接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-19 上午10:00:14,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-19 上午10:00:14
 * @since
 * @version
 */
public class PDAGoodsAttrDto implements Serializable {
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 7620377464984009937L;
	//运单号
	private String waybillNo;
	//重量
	private BigDecimal weight;
	//体积
	private BigDecimal volume;
	//操作人工号
	private String operator;
	//操作人所在部门编号
	private String operaterOrg;
	//操作时间
	private Date operTime;

	
	public String getWaybillNo() {
		return waybillNo;
	}

	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	public BigDecimal getWeight() {
		return weight;
	}

	
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	
	public BigDecimal getVolume() {
		return volume;
	}

	
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	
	public String getOperator() {
		return operator;
	}

	
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	public Date getOperTime() {
		return operTime;
	}

	
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}


	public String getOperaterOrg() {
		return operaterOrg;
	}


	public void setOperaterOrg(String operaterOrg) {
		this.operaterOrg = operaterOrg;
	}
	
}