/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirSpaceDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 舱位明细entity
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:00:29
 */
public class AirSpaceDetailEntity extends BaseEntity{
	private static final long serialVersionUID = 8099702086825277804L;
	/**舱位id*/
    private String airspaceId;  
    /**航班类型id*/
    private String flightType;  
    /**总舱位*/
    private BigDecimal spaceTotal;  

    /**
     * 获取 舱位id.
     *
     * @return the 舱位id
     */
    public String getAirspaceId() {
        return airspaceId;
    }

    /**
     * 设置 舱位id.
     *
     * @param airspaceId the new 舱位id
     */
    public void setAirspaceId(String airspaceId) {
        this.airspaceId = airspaceId;
    }

    /**
     * 获取 航班类型id.
     *
     * @return the 航班类型id
     */
    public String getFlightType() {
        return flightType;
    }

    /**
     * 设置 航班类型id.
     *
     * @param flightType the new 航班类型id
     */
    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    /**
     * 获取 总舱位.
     *
     * @return the 总舱位
     */
    public BigDecimal getSpaceTotal() {
        return spaceTotal;
    }

    /**
     * 设置 总舱位.
     *
     * @param spaceTotal the new 总舱位
     */
    public void setSpaceTotal(BigDecimal spaceTotal) {
        this.spaceTotal = spaceTotal;
    }
}