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
 *  FILE PATH          :/AirArriveSendInfoDto.java
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
import java.util.Date;

/**
 * 查询空运到达派送信息录入情况统计 返回结果
 * @author 099197-foss-zhoudejun
 * @date 2012-12-26 下午2:44:08
 */
public class AirArriveSendInfoDto implements Serializable {

	private static final long serialVersionUID = -8270826344678003241L;
	
	/** 代理网点编号 */
	private String ladingStationNumber;
	
	/** 代理网点名称 */
	private String ladingStation;
	
	/** 运单号 */
	private String wayBillNumber;
	
	/** 到达时间 */
	private Date arriveTime;
	
	/** 签收人 */
	private String signer;
	
	/** 签收时间 */
	private Date signTime;
	
	/** 出港日期 */
	private Date departureTime;

	/**
	 * 获取 代理网点编号.
	 *
	 * @return the 代理网点编号
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}

	/**
	 * 设置 代理网点编号.
	 *
	 * @param ladingStationNumber the new 代理网点编号
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * 获取 代理网点名称.
	 *
	 * @return the 代理网点名称
	 */
	public String getLadingStation() {
		return ladingStation;
	}

	/**
	 * 设置 代理网点名称.
	 *
	 * @param ladingStation the new 代理网点名称
	 */
	public void setLadingStation(String ladingStation) {
		this.ladingStation = ladingStation;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNumber the new 运单号
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}

	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}


	/**
	 * 获取 签收人.
	 *
	 * @return the 签收人
	 */
	public String getSigner() {
		return signer;
	}

	/**
	 * 设置 签收人.
	 *
	 * @param signer the new 签收人
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}

	/**
	 * 获取 签收时间.
	 *
	 * @return the 签收时间
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * 设置 签收时间.
	 *
	 * @param signTime the new 签收时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * 获取 出港日期.
	 *
	 * @return the 出港日期
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * 设置 出港日期.
	 *
	 * @param departureTime the new 出港日期
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

}