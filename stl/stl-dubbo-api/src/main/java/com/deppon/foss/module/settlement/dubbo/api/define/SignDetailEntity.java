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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/SignDetailEntity.java
 * 
 * FILE NAME        	: SignDetailEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.dubbo.api.define;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 签收明细Entity
 * @author foss-meiying
 * @date 2012-12-18 下午8:45:28
 * @since
 * @version
 */
public class SignDetailEntity extends BaseEntity{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2480073985081803136L;
	/**
	 *  到达联编号
	 */
	private String arrivesheetNo;
	/** 
	 * 流水号
	 */
	private String serialNo;
	/***
	 * 是否内物短少
	 */
	private String goodShorts;
	/**
	 * 签收情况
	 */
	private String situation;
	/**
	 * 签收情况
	 */
	private String oldSituation;
	private List<String> serialNos;
	
	/**
	 * 运单号 yanling 268377
	 */
	private String waybillNo;
	
	/**
	 * 更新时间
	 */
	private Date modifyTime;

	/**
	 * Gets the 更新时间.
	 *
	 * @param the modifyTime
	 */
	
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * Sets the 更新时间.
	 *
	 * @param serialNo the modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * Gets the 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the 是否内货短少.
	 *
	 * @return the 是否内货短少
	 */
	public String getGoodShorts() {
		return goodShorts;
	}

	/**
	 * Sets the 是否内货短少.
	 *
	 * @param goodShorts the new 是否内货短少
	 */
	public void setGoodShorts(String goodShorts) {
		this.goodShorts = goodShorts;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getOldSituation() {
		return oldSituation;
	}

	public void setOldSituation(String oldSituation) {
		this.oldSituation = oldSituation;
	}

	public List<String> getSerialNos() {
		return serialNos;
	}

	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
}