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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/RouteLineInfoDto.java
 * 
 * FILE NAME        	: RouteLineInfoDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * @author foss-jiangfei
 * @date 2012-12-3 下午1:36:30
 * DTO接收从中转获取的走货路径  PathDetailEntity
 *
 */
public class RouteLineInfoDto implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -1170696731041136600L;


	// 运单号
	private String waybillNo;
	// 流水号
	private String goodsNo;
	// 出发部门
	private String origOrgCode;
	// 下一到达部门
	private String objectiveOrgCode;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getObjectiveOrgCode() {
		return objectiveOrgCode;
	}
	public void setObjectiveOrgCode(String objectiveOrgCode) {
		this.objectiveOrgCode = objectiveOrgCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}