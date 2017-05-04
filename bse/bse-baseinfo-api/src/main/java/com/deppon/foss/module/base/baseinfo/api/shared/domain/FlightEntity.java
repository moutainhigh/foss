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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/FlightEntity.java
 * 
 * FILE NAME        	: FlightEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.base.util.define.DateTimeConstants;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.DateUtils;
/**
 * 
 * 用来存储交互“航班信息”的数据库对应实体：SUC-56
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:14:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:14:16
 * @since
 * @version
 */
public class FlightEntity extends BaseEntity {
    /**
     *  Serial Version UID
     */
    private static final long serialVersionUID = 2044652939269056363L;

    //航班类别
    private String flightSort;

    //航班号
    private String flightNo;

    //航空公司
    private String airlines;

    //始发站
    private String departureAirport;

    //目的站
    private String destinationAirport;

    //计划起飞时间
    private Date planLeaveTime;

    //计划到达时间
    private Date planArriveTime;

    //飞机机型
    private String aircraftType;

    //始发站代码
    private String origCode;

    //航行时间
    private BigDecimal flyTime;

    //是否周一飞
    private String flyOnMonday;

    //是否周二飞
    private String flyOnTuesday;

    //是否周三飞
    private String flyOnWednesday;

    //是否周四飞
    private String flyOnThursday;

    //是否周五飞
    private String flyOnFriday;

    //是否周六飞
    private String flyOnSaturday;

    //是否周日飞
    private String flyOnSunday;

    //是否启用
    private String active;
    
    //目的站代码
    private String targetCode;

	//是否协议航班
    private String isAgreementFlight;

    //日均协议货量（公斤）
	private BigDecimal dailyAgreementVolume;
	
	//所属空运总调
	private String belongsAirFreightDispatch;
	
	//所属空运总调名称
	private String belongsAirFreightDispatchName;
	
    public FlightEntity(){
    	
    }
    /**
     * @return  the flightSort
     */
    public String getFlightSort() {
        return flightSort;
    }
    
    /**
     * @param flightSort the flightSort to set
     */
    public void setFlightSort(String flightSort) {
        this.flightSort = flightSort;
    }

    /**
     * @return  the flightNo
     */
    public String getFlightNo() {
        return flightNo;
    }

    
    /**
     * @param flightNo the flightNo to set
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    
    /**
     * @return  the airlines
     */
    public String getAirlines() {
        return airlines;
    }

    
    /**
     * @param airlines the airlines to set
     */
    public void setAirlines(String airlines) {
        this.airlines = airlines;
    }

    
    /**
     * @return  the departureAirport
     */
    public String getDepartureAirport() {
        return departureAirport;
    }

    
    /**
     * @param departureAirport the departureAirport to set
     */
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }
    
    /**
     * @return  the destinationAirport
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }
    
    /**
     * @param destinationAirport the destinationAirport to set
     */
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
    
    /**
     * @return  the planLeaveTime
     */
    public Date getPlanLeaveTime() {
        return planLeaveTime;
    }
    
    /**
     * @param planLeaveTime the planLeaveTime to set
     */
    public void setPlanLeaveTime(Date planLeaveTime) {
	if(null != planLeaveTime){
	    StringBuffer sb = new StringBuffer(DateTimeConstants.DEFAULT_DATE);
	    sb.append(" ").append(DateUtils.convert(planLeaveTime, DateUtils.TIME_FORMAT));
	    planLeaveTime = DateUtils.convert(sb.toString(), DateUtils.DATE_TIME_FORMAT);
	}
        this.planLeaveTime = planLeaveTime;
    }
    
    /**
     * @return  the planArriveTime
     */
    public Date getPlanArriveTime() {
        return planArriveTime;
    }
    
    /**
     * @param planArriveTime the planArriveTime to set
     */
    public void setPlanArriveTime(Date planArriveTime) {
	if(null != planArriveTime){
	    StringBuffer sb = new StringBuffer(DateTimeConstants.DEFAULT_DATE);
	    sb.append(" ").append(DateUtils.convert(planArriveTime, DateUtils.TIME_FORMAT));
	    planArriveTime = DateUtils.convert(sb.toString(), DateUtils.DATE_TIME_FORMAT);
	}
        this.planArriveTime = planArriveTime;
    }
    
    /**
     * @return  the aircraftType
     */
    public String getAircraftType() {
        return aircraftType;
    }
    
    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }
    
    /**
     * @return  the origCode
     */
    public String getOrigCode() {
        return origCode;
    }
    
    /**
     * @param origCode the origCode to set
     */
    public void setOrigCode(String origCode) {
        this.origCode = origCode;
    }

    /**
     * @return  the flyTime
     */
    public BigDecimal getFlyTime() {
        return flyTime;
    }

    /**
     * @param flyTime the flyTime to set
     */
    public void setFlyTime(BigDecimal flyTime) {
        this.flyTime = flyTime;
    }
    
    /**
     * @return  the flyOnMonday
     */
    public String getFlyOnMonday() {
        return flyOnMonday;
    }
    
    /**
     * @param flyOnMonday the flyOnMonday to set
     */
    public void setFlyOnMonday(String flyOnMonday) {
        this.flyOnMonday = flyOnMonday;
    }

    /**
     * @return  the flyOnTuesday
     */
    public String getFlyOnTuesday() {
        return flyOnTuesday;
    }

    /**
     * @param flyOnTuesday the flyOnTuesday to set
     */
    public void setFlyOnTuesday(String flyOnTuesday) {
        this.flyOnTuesday = flyOnTuesday;
    }

    /**
     * @return  the flyOnWednesday
     */
    public String getFlyOnWednesday() {
        return flyOnWednesday;
    }

    /**
     * @param flyOnWednesday the flyOnWednesday to set
     */
    public void setFlyOnWednesday(String flyOnWednesday) {
        this.flyOnWednesday = flyOnWednesday;
    }
    
    /**
     * @return  the flyOnThursday
     */
    public String getFlyOnThursday() {
        return flyOnThursday;
    }

    /**
     * @param flyOnThursday the flyOnThursday to set
     */
    public void setFlyOnThursday(String flyOnThursday) {
        this.flyOnThursday = flyOnThursday;
    }
    
    /**
     * @return  the flyOnFriday
     */
    public String getFlyOnFriday() {
        return flyOnFriday;
    }
    
    /**
     * @param flyOnFriday the flyOnFriday to set
     */
    public void setFlyOnFriday(String flyOnFriday) {
        this.flyOnFriday = flyOnFriday;
    }
    
    /**
     * @return  the flyOnSaturday
     */
    public String getFlyOnSaturday() {
        return flyOnSaturday;
    }
    
    /**
     * @param flyOnSaturday the flyOnSaturday to set
     */
    public void setFlyOnSaturday(String flyOnSaturday) {
        this.flyOnSaturday = flyOnSaturday;
    }

    /**
     * @return  the flyOnSunday
     */
    public String getFlyOnSunday() {
        return flyOnSunday;
    }
    
    /**
     * @param flyOnSunday the flyOnSunday to set
     */
    public void setFlyOnSunday(String flyOnSunday) {
        this.flyOnSunday = flyOnSunday;
    }

    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * @return  the targetCode
     */
    public String getTargetCode() {
        return targetCode;
    }

    
    /**
     * @param targetCode the targetCode to set
     */
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
	public String getIsAgreementFlight() {
		return isAgreementFlight;
	}
	public void setIsAgreementFlight(String isAgreementFlight) {
		this.isAgreementFlight = isAgreementFlight;
	}
	public String getBelongsAirFreightDispatch() {
		return belongsAirFreightDispatch;
	}
	public void setBelongsAirFreightDispatch(String belongsAirFreightDispatch) {
		this.belongsAirFreightDispatch = belongsAirFreightDispatch;
	}
	public BigDecimal getDailyAgreementVolume() {
		return dailyAgreementVolume;
	}
	public void setDailyAgreementVolume(BigDecimal dailyAgreementVolume) {
		this.dailyAgreementVolume = dailyAgreementVolume;
	}
	public String getBelongsAirFreightDispatchName() {
		return belongsAirFreightDispatchName;
	}
	public void setBelongsAirFreightDispatchName(
			String belongsAirFreightDispatchName) {
		this.belongsAirFreightDispatchName = belongsAirFreightDispatchName;
	}
}
