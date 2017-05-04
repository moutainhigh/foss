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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/AircraftTypeVo.java
 * 
 * FILE NAME        	: AircraftTypeVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;
/**
 * 用来响应“机型信息”的Action类的封装对象VO：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午3:01:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午3:01:03
 * @since
 * @version
 */
public class AircraftTypeVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 7652523468338145917L;

    /**
     * "机型信息"对象
     */
    private AircraftTypeEntity aircraftType;
    
    /**
     * "机型信息"对象列表集合
     */
    private List<AircraftTypeEntity> aircraftTypeList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;

    
    /**
     * @return  the aircraftType
     */
    public AircraftTypeEntity getAircraftType() {
        return aircraftType;
    }

    
    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(AircraftTypeEntity aircraftType) {
        this.aircraftType = aircraftType;
    }

    
    /**
     * @return  the aircraftTypeList
     */
    public List<AircraftTypeEntity> getAircraftTypeList() {
        return aircraftTypeList;
    }

    
    /**
     * @param aircraftTypeList the aircraftTypeList to set
     */
    public void setAircraftTypeList(List<AircraftTypeEntity> aircraftTypeList) {
        this.aircraftTypeList = aircraftTypeList;
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
