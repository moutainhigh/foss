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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/dto/AirStockInfoDto.java
 *  
 *  FILE NAME          :AirStockInfoDto.java
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
// TODO: Auto-generated Javadoc
/**
 * 空运库存列表.
 *
 * @author 038300-foss-pengzhen
 * @date 2013-03-14 下午2:41:17
 */
public class AirStockInfoDto implements Serializable{
	
	/** 序列id. */
	private static final long serialVersionUID = 3146373829006773145L;
	
	/** 代理网点编号. */
	private String ladingStationNumber;
	
	/** 代理网点名称. */
	private String ladingStation;
	
	/** 出港航班. */
	private String outBoundFlight;
	
	/** 运单号. */
	private String wayBillNumber;
	
    /** 出港日期. */
	private Date departureTime;
	
	
	/**
	 * Gets the lading station number.
	 *
	 * @return the lading station number
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}

	/**
	 * Sets the lading station number.
	 *
	 * @param ladingStationNumber the new lading station number
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * Gets the lading station.
	 *
	 * @return the lading station
	 */
	public String getLadingStation() {
		return ladingStation;
	}

	/**
	 * Sets the lading station.
	 *
	 * @param ladingStation the new lading station
	 */
	public void setLadingStation(String ladingStation) {
		this.ladingStation = ladingStation;
	}

	/**
	 * Gets the out bound flight.
	 *
	 * @return the out bound flight
	 */
	public String getOutBoundFlight() {
		return outBoundFlight;
	}

	/**
	 * Sets the out bound flight.
	 *
	 * @param outBoundFlight the new out bound flight
	 */
	public void setOutBoundFlight(String outBoundFlight) {
		this.outBoundFlight = outBoundFlight;
	}

	/**
	 * Gets the way bill number.
	 *
	 * @return the way bill number
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}

	/**
	 * Sets the way bill number.
	 *
	 * @param wayBillNumber the new way bill number
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}

	/**
	 * Gets the departure time.
	 *
	 * @return the departure time
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * Sets the departure time.
	 *
	 * @param departureTime the new departure time
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
}