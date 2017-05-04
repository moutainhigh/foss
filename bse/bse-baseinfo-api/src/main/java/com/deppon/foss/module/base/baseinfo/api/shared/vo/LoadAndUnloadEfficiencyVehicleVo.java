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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LoadAndUnloadEfficiencyVehicleVo.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;


/**
 * 装卸车标准时间VO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-29 下午4:11:40
 */
public class LoadAndUnloadEfficiencyVehicleVo implements Serializable {
    private static final long serialVersionUID = 3620501034012743142L;
    
    // 装卸车标准-吨-时间列表
    private List<LoadAndUnloadEfficiencyVehicleEntity> loadAndUnloadEfficiencyVehicleList;

    // 装卸车标准-吨-时间详情
    private LoadAndUnloadEfficiencyVehicleEntity loadAndUnloadEfficiencyVehicleEntity;

    
    // 车长列表
    private List<VehicleTypeEntity> vehicleTypeEntityList;

    
    /**
     * 下面是get,set方法
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-16 下午2:20:42
     */
   


    
    public List<VehicleTypeEntity> getVehicleTypeEntityList() {
        return vehicleTypeEntityList;
    }


    
    public List<LoadAndUnloadEfficiencyVehicleEntity> getLoadAndUnloadEfficiencyVehicleList() {
		return loadAndUnloadEfficiencyVehicleList;
	}



	public void setLoadAndUnloadEfficiencyVehicleList(
			List<LoadAndUnloadEfficiencyVehicleEntity> loadAndUnloadEfficiencyVehicleList) {
		this.loadAndUnloadEfficiencyVehicleList = loadAndUnloadEfficiencyVehicleList;
	}



	public LoadAndUnloadEfficiencyVehicleEntity getLoadAndUnloadEfficiencyVehicleEntity() {
		return loadAndUnloadEfficiencyVehicleEntity;
	}



	public void setLoadAndUnloadEfficiencyVehicleEntity(
			LoadAndUnloadEfficiencyVehicleEntity loadAndUnloadEfficiencyVehicleEntity) {
		this.loadAndUnloadEfficiencyVehicleEntity = loadAndUnloadEfficiencyVehicleEntity;
	}



	public void setVehicleTypeEntityList(
    	List<VehicleTypeEntity> vehicleTypeEntityList) {
        this.vehicleTypeEntityList = vehicleTypeEntityList;
    }
    


}
