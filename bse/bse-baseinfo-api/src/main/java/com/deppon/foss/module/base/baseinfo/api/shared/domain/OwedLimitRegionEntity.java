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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OwedLimitRegionEntity.java
 * 
 * FILE NAME        	: OwedLimitRegionEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class OwedLimitRegionEntity extends BaseEntity{
    
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1358509154362561304L;

    /**
     * 额度最小值.
     */
    private BigDecimal owedlimitMin;

    /**
     * 额度最大值.
     */
    private BigDecimal owedlimitMax;

    /**
     * 临欠额度.
     */
    private BigDecimal owedlimit;
    
    /**
     * 是否有效.
     */
    private String active;

    /**
     * 获取 额度最小值.
     *
     * @return  the owedlimitMin
     */
    public BigDecimal getOwedlimitMin() {
        return owedlimitMin;
    }

    /**
     * 设置 额度最小值.
     *
     * @param owedlimitMin the owedlimitMin to set
     */
    public void setOwedlimitMin(BigDecimal owedlimitMin) {
        this.owedlimitMin = owedlimitMin;
    }
    
    /**
     * 获取 额度最大值.
     *
     * @return  the owedlimitMax
     */
    public BigDecimal getOwedlimitMax() {
        return owedlimitMax;
    }
    
    /**
     * 设置 额度最大值.
     *
     * @param owedlimitMax the owedlimitMax to set
     */
    public void setOwedlimitMax(BigDecimal owedlimitMax) {
        this.owedlimitMax = owedlimitMax;
    }
    
    /**
     * 获取 临欠额度.
     *
     * @return  the owedlimit
     */
    public BigDecimal getOwedlimit() {
        return owedlimit;
    }
    
    /**
     * 设置 临欠额度.
     *
     * @param owedlimit the owedlimit to set
     */
    public void setOwedlimit(BigDecimal owedlimit) {
        this.owedlimit = owedlimit;
    }

    /**
     * 获取 是否有效.
     *
     * @return the 是否有效
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 是否有效.
     *
     * @param active the new 是否有效
     */
    public void setActive(String active) {
        this.active = active;
    }
}