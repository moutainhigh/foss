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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AirportDto.java
 * 
 * FILE NAME        	: AirportDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirportException;
/**
 * 用来扩展“机场信息”的数据库对应实体的DTO：SUC-52 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-25 上午10:57:53</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-25 上午10:57:53
 * @since
 * @version
 */
public class AirportDto extends AirportEntity implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3360717992164742772L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AirportDto.class);
    
    public AirportDto(AirportEntity airport) throws AirportException{
	try{
	    BeanUtils.copyProperties(airport, this, new String[]{
			"createDate",
			"modifyDate"
		});
	   this.setCreateDate(airport.getCreateDate());
	   this.setModifyDate(airport.getModifyDate());
	} catch (AirportException e) {
	    LOGGER.error(e.getMessage(), e);
	    throw e;
	}
    }
    
    //机场所在省份名称
    private String airportIntoProvinceName;
    
    //机场所在城市名称
    private String airportIntoCityName;
    
    //机场所在区县名称
    private String airportIntoAreaName;
    
    /**
     * @return  the airportIntoProvinceName
     */
    public String getAirportIntoProvinceName() {
        return airportIntoProvinceName;
    }
    
    /**
     * @param airportIntoProvinceName the airportIntoProvinceName to set
     */
    public void setAirportIntoProvinceName(String airportIntoProvinceName) {
        this.airportIntoProvinceName = airportIntoProvinceName;
    }

    /**
     * @return  the airportIntoCityName
     */
    public String getAirportIntoCityName() {
        return airportIntoCityName;
    }

    /**
     * @param airportIntoCityName the airportIntoCityName to set
     */
    public void setAirportIntoCityName(String airportIntoCityName) {
        this.airportIntoCityName = airportIntoCityName;
    }

    /**
     * @return  the airportIntoAreaName
     */
    public String getAirportIntoAreaName() {
        return airportIntoAreaName;
    }
    
    /**
     * @param airportIntoAreaName the airportIntoAreaName to set
     */
    public void setAirportIntoAreaName(String airportIntoAreaName) {
        this.airportIntoAreaName = airportIntoAreaName;
    }
}
