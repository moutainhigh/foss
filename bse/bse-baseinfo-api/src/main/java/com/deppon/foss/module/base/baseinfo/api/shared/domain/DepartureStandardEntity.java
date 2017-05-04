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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/DepartureStandardEntity.java
 * 
 * FILE NAME        	: DepartureStandardEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 发车标准实体
 * @author foss-zhujunyong
 * @date Oct 23, 2012 5:51:01 PM
 * @version 1.0
 */
public class DepartureStandardEntity extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -5322904563817745573L;
    
    // 虚拟编码
    private String virtualCode;
    
    // 线路虚拟编码
    private String lineVirtualCode;
    
    // 准点发车时间(eg: 0200)
    private String leaveTime;
    
    // 准点到达时间(eg: 1645)
    private String arriveTime;
    
    // 准点到达时间的天数,默认是0
    private Long arriveDay;
    
    // 中转到达货最晚到达时间(eg: 0120)
    private String deadTime;

    // 中转到达货最晚到达的天数,默认是0
    private Long deadDay;
    
    // 是否有效
    private String active;
    
    // 备注
    private String notes;

    // 时效类型(卡车或普车) (只有运作到运作线路有，始发和到达线路无此属性)
    private String productType;
    
    // 班次号
    private Integer order;
    
    // 版本号
    private Long version;
    
    

    
    /**
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    
    /**
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    
    /**
     * @return  the lineVirtualCode
     */
    public String getLineVirtualCode() {
        return lineVirtualCode;
    }

    
    /**
     * @param lineVirtualCode the lineVirtualCode to set
     */
    public void setLineVirtualCode(String lineVirtualCode) {
        this.lineVirtualCode = lineVirtualCode;
    }

    
    /**
     * @return  the leaveTime
     */
    public String getLeaveTime() {
        return leaveTime;
    }

    
    /**
     * @param leaveTime the leaveTime to set
     */
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    
    /**
     * @return  the arriveTime
     */
    public String getArriveTime() {
        return arriveTime;
    }

    
    /**
     * @param arriveTime the arriveTime to set
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    
    /**
     * @return  the arriveDay
     */
    public Long getArriveDay() {
        return arriveDay;
    }

    
    /**
     * @param arriveDay the arriveDay to set
     */
    public void setArriveDay(Long arriveDay) {
        this.arriveDay = arriveDay;
    }

    
    /**
     * @return  the deadTime
     */
    public String getDeadTime() {
        return deadTime;
    }

    
    /**
     * @param deadTime the deadTime to set
     */
    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    
    /**
     * @return  the deadDay
     */
    public Long getDeadDay() {
        return deadDay;
    }

    
    /**
     * @param deadDay the deadDay to set
     */
    public void setDeadDay(Long deadDay) {
        this.deadDay = deadDay;
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
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }

    
    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }


    
    public String getProductType() {
        return productType;
    }


    
    public void setProductType(String productType) {
        this.productType = productType;
    }


    
    public Integer getOrder() {
        return order;
    }


    
    public void setOrder(Integer order) {
        this.order = order;
    }


    
    /**
     * @return  the version
     */
    public Long getVersion() {
        return version;
    }


    
    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }





    
}
