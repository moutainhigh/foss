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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirHandOverBillGetSerialNoDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;


/**
 * 储存查询得到的运单号和流水号
 * @author LiuXue
 * @date 2012-11-1 9:53:59
 */
public class AirHandOverBillGetSerialNoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1321575588810917110L;
	/**
	 * 运单号
	 */
	private String wayBillNo;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 当前部门编号
	 */
	private String orgCode;
	/**
	 * 是否生效
	 */
	@SuppressWarnings("unused")
	private static final String isValid = "Y";

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/**
	 * 获取 当前部门编号.
	 *
	 * @return the 当前部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 当前部门编号.
	 *
	 * @param orgCode the new 当前部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}