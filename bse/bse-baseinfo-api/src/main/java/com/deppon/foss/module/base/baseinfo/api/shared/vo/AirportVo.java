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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/AirportVo.java
 * 
 * FILE NAME        	: AirportVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
/**
 * 用来响应“机场信息”的Action类 的封装对象VO：SUC-52 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午3:01:21</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午3:01:21
 * @since
 * @version
 */
public class AirportVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2099990527236301161L;

    /**
     * "机场信息"对象
     */
    private AirportEntity airport;
    
    /**
     * "机场信息"对象列表集合
     */
    private List<AirportEntity> airportList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;

    
    /**
     * @return  the airport
     */
    public AirportEntity getAirport() {
        return airport;
    }

    
    /**
     * @param airport the airport to set
     */
    public void setAirport(AirportEntity airport) {
        this.airport = airport;
    }

    
    /**
     * @return  the airportList
     */
    public List<AirportEntity> getAirportList() {
        return airportList;
    }

    
    /**
     * @param airportList the airportList to set
     */
    public void setAirportList(List<AirportEntity> airportList) {
        this.airportList = airportList;
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
