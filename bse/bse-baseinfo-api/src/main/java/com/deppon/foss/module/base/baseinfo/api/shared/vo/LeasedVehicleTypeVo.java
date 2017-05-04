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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LeasedVehicleTypeVo.java
 * 
 * FILE NAME        	: LeasedVehicleTypeVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
/**
 * 用来响应“外请车白名单”申请（入库、可用、不可用、撤回）Action类的封装VO对象：SUC-104
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-11 上午10:00:53</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-11 上午10:00:53
 * @since
 * @version
 */
public class LeasedVehicleTypeVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3780837910472414786L;
    
    /**
     * "外请车车型"对象列表集合
     */
    private VehicleTypeEntity leasedVehicleType;
    
    /**
     * "外请车车型"对象
     */
    private List<VehicleTypeEntity> leasedVehicleTypeList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;

    /**
     * @return  the leasedVehicleType
     */
    public VehicleTypeEntity getLeasedVehicleType() {
        return leasedVehicleType;
    }

    /**
     * @param leasedVehicleType the leasedVehicleType to set
     */
    public void setLeasedVehicleType(VehicleTypeEntity leasedVehicleType) {
        this.leasedVehicleType = leasedVehicleType;
    }
    
    /**
     * @return  the leasedVehicleTypeList
     */
    public List<VehicleTypeEntity> getLeasedVehicleTypeList() {
        return leasedVehicleTypeList;
    }
    
    /**
     * @param leasedVehicleTypeList the leasedVehicleTypeList to set
     */
    public void setLeasedVehicleTypeList(
    	List<VehicleTypeEntity> leasedVehicleTypeList) {
        this.leasedVehicleTypeList = leasedVehicleTypeList;
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
