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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/FlightDto.java
 * 
 * FILE NAME        	: FlightDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;
/**
 * 用来扩展“航班信息”的数据库对应实体的DTO：SUC-56
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-28 上午11:22:57</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-28 上午11:22:57
 * @since
 * @version
 */
public class FlightDto extends FlightEntity implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -3036638871122089381L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightDto.class);
    
    public FlightDto(FlightEntity flight) throws FlightException{
	try{
	    BeanUtils.copyProperties(flight, this, new String[]{
		    	"planLeaveTime",
		    	"planArriveTime",
		    	"flyTime",
			"createDate",
			"modifyDate"
		});
	    this.setPlanLeaveTime(flight.getPlanLeaveTime());
	    this.setPlanArriveTime(flight.getPlanArriveTime());
	    this.setFlyTime(flight.getFlyTime());
	    this.setCreateDate(flight.getCreateDate());
	    this.setModifyDate(flight.getModifyDate());
	} catch (FlightException e) {
	    LOGGER.error(e.getMessage(), e);
	    throw e;
	}
    }
    public FlightDto(){
    	
    }
    /**
     * 航空公司名称.
     */
    private String airlinesName;
    
    private String airlines;
    
    /**
     * 始发城市三字码
     */
    private String origCode;
    
    /**
     * 目的城市三字码
     */
    private String targetCode;
    
    /**
     * 始发城市三字码列表
     */
    private List<String> origCodes;
    
    /**
     * 目的城市三字码列表
     */
    private List<String> targetCodes;
    
    /**
     * 协议航班对应的空运总调列表
     */
    private List<String> airDispatchCodes;
    
    /**
     * 始发站城市名称.
     */
    private String originatingStationName;
    
    /**
     * 目的站城市名称.
     */
    private String destinationStationName;

    /**
     * 获取 航空公司名称.
     *
     * @return  the airlinesName
     */
    public String getAirlinesName() {
        return airlinesName;
    }
    
    /**
     * 设置 航空公司名称.
     *
     * @param airlinesName the airlinesName to set
     */
    public void setAirlinesName(String airlinesName) {
        this.airlinesName = airlinesName;
    }

    /**
     * 获取 始发站城市名称.
     *
     * @return  the originatingStationName
     */
    public String getOriginatingStationName() {
        return originatingStationName;
    }
    
    /**
     * 设置 始发站城市名称.
     *
     * @param originatingStationName the originatingStationName to set
     */
    public void setOriginatingStationName(String originatingStationName) {
        this.originatingStationName = originatingStationName;
    }
    
    /**
     * 获取 目的站城市名称.
     *
     * @return  the destinationStationName
     */
    public String getDestinationStationName() {
        return destinationStationName;
    }
	 
	
	public List<String> getOrigCodes() {
		return origCodes;
	}
	
	public void setOrigCodes(List<String> origCodes) {
		this.origCodes = origCodes;
	}
	
	public List<String> getTargetCodes() {
		return targetCodes;
	}
	
	public void setTargetCodes(List<String> targetCodes) {
		this.targetCodes = targetCodes;
	}
	/**
     * 设置 目的站城市名称.
     *
     * @param destinationStationName the destinationStationName to set
     */
    public void setDestinationStationName(String destinationStationName) {
        this.destinationStationName = destinationStationName;
    }
	public String getAirlines() {
		return airlines;
	}
	public void setAirlines(String airlines) {
		this.airlines = airlines;
	}
	public String getOrigCode() {
		return origCode;
	}
	public void setOrigCode(String origCode) {
		this.origCode = origCode;
	}
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public List<String> getAirDispatchCodes() {
		return airDispatchCodes;
	}
	public void setAirDispatchCodes(List<String> airDispatchCodes) {
		this.airDispatchCodes = airDispatchCodes;
	}
}
