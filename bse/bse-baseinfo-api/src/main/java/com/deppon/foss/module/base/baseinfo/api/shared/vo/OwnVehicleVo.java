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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/OwnVehicleVo.java
 * 
 * FILE NAME        	: OwnVehicleVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleDto;
/**
 * 用来响应“公司车辆（厢式车、挂车、拖头）”的Action类封装对象的VO：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午4:10:02</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午4:10:02
 * @since
 * @version
 */
public class OwnVehicleVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8664170452087207981L;

    /**
     * "公司车辆"对象
     */
    private OwnTruckEntity ownVehicle;
    
    /**
     * "公司车辆"对象列表集合
     */
    private List<OwnVehicleDto> ownVehicleList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;

    
    /**
     * @return  the ownVehicle
     */
    public OwnTruckEntity getOwnVehicle() {
        return ownVehicle;
    }

    
    /**
     * @param ownVehicle the ownVehicle to set
     */
    public void setOwnVehicle(OwnTruckEntity ownVehicle) {
        this.ownVehicle = ownVehicle;
    }

    
    /**
     * @return  the ownTruckEntityList
     */
    public List<OwnVehicleDto> getOwnVehicleList() {
        return ownVehicleList;
    }

    
    /**
     * @param ownTruckEntityList the ownTruckEntityList to set
     */
    public void setOwnVehicleList(List<OwnVehicleDto> ownVehicleList) {
	this.ownVehicleList = ownVehicleList;
    }

    
    /**
     * @return  the batchIds
     */
    public List<String> getBatchIds() {
        return batchIds;
    }

    
    /**
     * @param batchIds the batchIds to set
     */
    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }
}
