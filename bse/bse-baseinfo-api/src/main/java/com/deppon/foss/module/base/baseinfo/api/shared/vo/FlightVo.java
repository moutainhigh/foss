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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/FlightVo.java
 * 
 * FILE NAME        	: FlightVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
/**
 * 用来响应“航班信息”的Action类封装对象的VO：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午3:08:26</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午3:08:26
 * @since
 * @version
 */
public class FlightVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -6391272200264853177L;

    /**
     * "航班信息"对象
     */
    private FlightEntity flight;
    
    /**
     * 航班信息DTO
     */
    private FlightDto flightDto;
    /**
     * "航班信息"对象列表集合
     */
    private List<FlightEntity> flightList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;
    
    /**
     * 所属空运总掉集合
     */
    private List<String> belongsAirFreightDispatchs;

    
    /**
     * @return  the flight
     */
    public FlightEntity getFlight() {
        return flight;
    }

    
    /**
     * @param flight the flight to set
     */
    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    
    /**
     * @return  the flightList
     */
    public List<FlightEntity> getFlightList() {
        return flightList;
    }

    
    /**
     * @param flightList the flightList to set
     */
    public void setFlightList(List<FlightEntity> flightList) {
        this.flightList = flightList;
    }

    
    /**
     * @return  the batchIds
     */
    public List<String> getBatchIds() {
        return batchIds;
    }
 


	
	public FlightDto getFlightDto() {
		return flightDto;
	}


	
	public void setFlightDto(FlightDto flightDto) {
		this.flightDto = flightDto;
	}


	/**
     * @param batchIds the batchIds to set
     */
    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }


	public List<String> getBelongsAirFreightDispatchs() {
		return belongsAirFreightDispatchs;
	}


	public void setBelongsAirFreightDispatchs(
			List<String> belongsAirFreightDispatchs) {
		this.belongsAirFreightDispatchs = belongsAirFreightDispatchs;
	}
}
