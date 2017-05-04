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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/AircraftTypeDto.java
 * 
 * FILE NAME        	: AircraftTypeDto.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AircraftTypeException;
/**
 * 用来扩展“机型信息”的数据库对应实体的DTO：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-25 下午4:42:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-25 下午4:42:16
 * @since
 * @version
 */
public class AircraftTypeDto extends AircraftTypeEntity implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5112182901331568184L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftTypeDto.class);
    
    public AircraftTypeDto(AircraftTypeEntity aircraftType) throws AircraftTypeException{
	try{
	    BeanUtils.copyProperties(aircraftType, this, new String[]{
			"load",
			"volumn",
			"cabinDoorWidth",
			"cabinDoorHeight",
			"singlePieceLimitLen",
			"singlePieceLimitWei",
			"createDate",
			"modifyDate"
		});
	   this.setCreateDate(aircraftType.getCreateDate());
	   this.setModifyDate(aircraftType.getModifyDate());
	   this.setLoad(aircraftType.getLoad());
	   this.setVolumn(aircraftType.getVolumn());
	   this.setCabinDoorHeight(aircraftType.getCabinDoorHeight());
	   this.setCabinDoorWidth(aircraftType.getCabinDoorWidth());
	   this.setSinglePieceLimitLen(aircraftType.getSinglePieceLimitLen());
	   this.setSinglePieceLimitWei(aircraftType.getSinglePieceLimitWei());
	} catch (AircraftTypeException e) {
	    LOGGER.error(e.getMessage(), e);
	    throw e;
	}
    }
    
    //单件最大重量字符串
    private String singlePieceLimitWeight;
    
    /**
     * @return  the singlePieceLimitWeight
     */
    public String getSinglePieceLimitWeight() {
        return singlePieceLimitWeight;
    }

    /**
     * @param singlePieceLimitWeight the singlePieceLimitWeight to set
     */
    public void setSinglePieceLimitWeight(String singlePieceLimitWeight) {
        this.singlePieceLimitWeight = singlePieceLimitWeight;
    }
}
