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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/AircraftTypeEntity.java
 * 
 * FILE NAME        	: AircraftTypeEntity.java
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
 * 用来存储交互“机型信息”的数据库对应实体：SUC-785
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:32:08</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:32:08
 * @since
 * @version
 */
public class AircraftTypeEntity extends BaseEntity {
    /**
     *  Serial Version UID
     */
    private static final long serialVersionUID = 1740680030079893420L;

    //机型代码
    private String code;

    //飞机类别
    private String aircraftSort;

    //载重
    private BigDecimal load;

    //舱位体积
    private BigDecimal volumn;

    //舱门尺寸-宽
    private BigDecimal cabinDoorWidth;

    //舱门尺寸-高
    private BigDecimal cabinDoorHeight;

    //单件最大长度
    private BigDecimal singlePieceLimitLen;

    //单件最大重量
    private BigDecimal singlePieceLimitWei;

    //是否启用
    private String active;
    
    //长度是否应用于所有航空公司
    private String isLimitLenght;
    
    //重量是否应用于所有航空公司
    private String isLimitWeight;

    /**
     * @return  the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return  the aircraftSort
     */
    public String getAircraftSort() {
        return aircraftSort;
    }
    
    /**
     * @param aircraftSort the aircraftSort to set
     */
    public void setAircraftSort(String aircraftSort) {
        this.aircraftSort = aircraftSort;
    }

    /**
     * @return  the load
     */
    public BigDecimal getLoad() {
        return load;
    }
    
    /**
     * @param load the load to set
     */
    public void setLoad(BigDecimal load) {
        this.load = load;
    }
    
    /**
     * @return  the volumn
     */
    public BigDecimal getVolumn() {
        return volumn;
    }
    
    /**
     * @param volumn the volumn to set
     */
    public void setVolumn(BigDecimal volumn) {
        this.volumn = volumn;
    }
    
    /**
     * @return  the cabinDoorWidth
     */
    public BigDecimal getCabinDoorWidth() {
        return cabinDoorWidth;
    }
    
    /**
     * @param cabinDoorWidth the cabinDoorWidth to set
     */
    public void setCabinDoorWidth(BigDecimal cabinDoorWidth) {
        this.cabinDoorWidth = cabinDoorWidth;
    }
    
    /**
     * @return  the cabinDoorHeight
     */
    public BigDecimal getCabinDoorHeight() {
        return cabinDoorHeight;
    }

    /**
     * @param cabinDoorHeight the cabinDoorHeight to set
     */
    public void setCabinDoorHeight(BigDecimal cabinDoorHeight) {
        this.cabinDoorHeight = cabinDoorHeight;
    }
    
    /**
     * @return  the singlePieceLimitLen
     */
    public BigDecimal getSinglePieceLimitLen() {
        return singlePieceLimitLen;
    }

    /**
     * @param singlePieceLimitLen the singlePieceLimitLen to set
     */
    public void setSinglePieceLimitLen(BigDecimal singlePieceLimitLen) {
        this.singlePieceLimitLen = singlePieceLimitLen;
    }
    
    /**
     * @return  the singlePieceLimitWei
     */
    public BigDecimal getSinglePieceLimitWei() {
        return singlePieceLimitWei;
    }
    
    /**
     * @param singlePieceLimitWei the singlePieceLimitWei to set
     */
    public void setSinglePieceLimitWei(BigDecimal singlePieceLimitWei) {
        this.singlePieceLimitWei = singlePieceLimitWei;
    }

    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * @return  the isLimitLenght
     */
    public String getIsLimitLenght() {
        return isLimitLenght;
    }

    /**
     * @param isLimitLenght the isLimitLenght to set
     */
    public void setIsLimitLenght(String isLimitLenght) {
        this.isLimitLenght = isLimitLenght;
    }

    /**
     * @return  the isLimitWeight
     */
    public String getIsLimitWeight() {
        return isLimitWeight;
    }

    /**
     * @param isLimitWeight the isLimitWeight to set
     */
    public void setIsLimitWeight(String isLimitWeight) {
        this.isLimitWeight = isLimitWeight;
    }
}
