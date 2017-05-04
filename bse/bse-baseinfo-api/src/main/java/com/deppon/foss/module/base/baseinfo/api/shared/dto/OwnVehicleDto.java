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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/OwnVehicleDto.java
 * 
 * FILE NAME        	: OwnVehicleDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
/**
 * 用来扩展“公司车辆”的数据库对应实体的DTO：SUC-753 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-21 上午10:32:19</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-21 上午10:32:19
 * @since
 * @version
 */
public class OwnVehicleDto extends OwnTruckEntity {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8748232535811567129L;
    
    private static final Logger logger = LoggerFactory.getLogger(OwnVehicleDto.class);
    
    public OwnVehicleDto(OwnTruckEntity ownTruck) throws OwnVehicleException{
	try {
	    BeanUtils.copyProperties(ownTruck, this, new String[] {
		    "selfWeight", "vehicleLength", "vehicleWidth",
		    "vehicleHeight", "selfVolume", "deadLoad", "consumeFuel",
		    "vehicleDeadLoad", "tankVolume", "beginDate", "endDate" 
	    });
	    this.setSelfWeight(ownTruck.getSelfWeight());
	    this.setVehicleLength(ownTruck.getVehicleLength());
	    this.setVehicleWidth(ownTruck.getVehicleWidth());
	    this.setVehicleHeight(ownTruck.getVehicleHeight());
	    this.setSelfVolume(ownTruck.getSelfVolume());
	    this.setDeadLoad(ownTruck.getDeadLoad());
	    this.setConsumeFuel(ownTruck.getConsumeFuel());
	    this.setVehicleDeadLoad(ownTruck.getVehicleDeadLoad());
	    this.setTankVolume(ownTruck.getTankVolume());
	    this.setBeginDate(ownTruck.getBeginDate());
	    this.setEndDate(ownTruck.getEndDate());
	} catch (OwnVehicleException e) {
	    logger.error(e.getMessage(), e);
	    throw e;
	}
    }
    
    //部门名称
    private String orgName;
    
    //不可用原因
    private String unavilableName;
    
    /**
     * @return  the orgName
     */
    public String getOrgName() {
        return orgName;
    }
    
    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return  the unavilableName
     */
    public String getUnavilableName() {
        return unavilableName;
    }
    
    /**
     * @param unavilableName the unavilableName to set
     */
    public void setUnavilableName(String unavilableName) {
        this.unavilableName = unavilableName;
    }
}
