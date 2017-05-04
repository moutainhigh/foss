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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/complex/LoadAndUnloadEfficiencyVehicleDto.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain.complex;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 根据车辆的车牌号，部门编码，查询装卸车标准（卸一车需要多长时间） DTO
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class LoadAndUnloadEfficiencyVehicleDto  extends BaseEntity {
    private static final long serialVersionUID = -3567231351672695546L;

    /**
    * 装车小时数
    */	
    private String loadHour;
    
    /**
    * 装车分钟数
    */	
    private String loadMinute;

    /**
    * 卸车小时数
    */	
    private String unloadHour;

    /**
    * 卸车分钟数
    */	
    private String unloadMinute;
    
    public String getLoadHour() {
        return loadHour;
    }
    
    public void setLoadHour(String loadHour) {
        this.loadHour = loadHour;
    }
    
    public String getLoadMinute() {
        return loadMinute;
    }
    
    public void setLoadMinute(String loadMinute) {
        this.loadMinute = loadMinute;
    }
    
    public String getUnloadHour() {
        return unloadHour;
    }
    
    public void setUnloadHour(String unloadHour) {
        this.unloadHour = unloadHour;
    }
    
    public String getUnloadMinute() {
        return unloadMinute;
    }
    
    public void setUnloadMinute(String unloadMinute) {
        this.unloadMinute = unloadMinute;
    }

    
}
