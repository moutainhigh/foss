/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/FlightDto.java
 * 
 * FILE NAME        	: FlightDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 航班DTO
 * @author DP-Foss-zhangbin
 * @date 2013-1-11 上午8:25:49
 */
public class FlightDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7273510947256230454L;
    //航班号
    /**
     * 
     */
    private String flightNo;
    //始发站
    /**
     * 
     */
    private String departureAirport;
    
     //始发站
    /**
      * 
      */
     private String departureAirportName;

    //目的站
    /**
     * 
     */
    private String destinationAirport;

    //计划起飞时间
    /**
     * 
     */
    private Date planLeaveTime;

    //计划到达时间
    /**
     * 
     */
    private Date planArriveTime;

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDepartureAirport() {
		return departureAirport;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * 
	 *
	 * @param flightNo 
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * 
	 *
	 * @param departureAirport 
	 */
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDepartureAirportName() {
		return departureAirportName;
	}

	/**
	 * 
	 *
	 * @param departureAirportName 
	 */
	public void setDepartureAirportName(String departureAirportName) {
		this.departureAirportName = departureAirportName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * 
	 *
	 * @param destinationAirport 
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getPlanLeaveTime() {
		return planLeaveTime;
	}

	/**
	 * 
	 *
	 * @param planLeaveTime 
	 */
	public void setPlanLeaveTime(Date planLeaveTime) {
		this.planLeaveTime = planLeaveTime;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	/**
	 * 
	 *
	 * @param planArriveTime 
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
    
    
    
}