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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LeasedDriverVo.java
 * 
 * FILE NAME        	: LeasedDriverVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
/**
 * 用来交互“外请司机”信息的VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-14 上午10:43:43</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-14 上午10:43:43
 * @since
 * @version
 */
public class LeasedDriverVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -2983817286560510312L;

    /**
     * "外请司机"对象
     */
    private LeasedDriverEntity leasedDriver;
    
    /**
     * "外请司机"对象列表集合
     */
    private List<LeasedDriverEntity> leasedDriverList;

    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;
    
    /**
     * @return  the leasedDriver
     */
    public LeasedDriverEntity getLeasedDriver() {
        return leasedDriver;
    }
    
    /**
     * @param leasedDriver the leasedDriver to set
     */
    public void setLeasedDriver(LeasedDriverEntity leasedDriver) {
        this.leasedDriver = leasedDriver;
    }
    
    /**
     * @return  the leasedDriverList
     */
    public List<LeasedDriverEntity> getLeasedDriverList() {
        return leasedDriverList;
    }
    
    /**
     * @param leasedDriverList the leasedDriverList to set
     */
    public void setLeasedDriverList(List<LeasedDriverEntity> leasedDriverList) {
        this.leasedDriverList = leasedDriverList;
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
