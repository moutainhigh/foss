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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/FreightRouteLineDto.java
 * 
 * FILE NAME        	: FreightRouteLineDto.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 走货路径线路
 * @author foss-zhujunyong
 * @date Nov 7, 2012 11:48:56 AM
 * @version 1.0
 */
public class FreightRouteLineDto implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2361265701903991392L;

    // 出发部门编码
    private String sourceCode;

    // 出发部门名称（扩展）
    private String sourceName;
    
    // 到达部门编码
    private String targetCode;
    
    // 到达部门名称（扩展）
    private String targetName;

    // 对应的线路虚拟编码
    private String lineVirtualCode;
    
    // 对应的线路名称（扩展）
    private String lineName;
    
    // 计划出发时间
    private Date leaveDate;
    
    // 计划到达时间
    private Date arriveDate;
    
    // 经停时间（到达以后的时间再加上经停时间）
    private Date passbyDate;
    
    // 运输类型（汽运，空运）-始发到达
    private String transType;
    
    // 线路类型（专线，偏线，空运）-中转
    private String lineType;

    // 线路类别 （始发，到达，中转到中转）
    private String lineSort;

    // 时效（单位：分钟）
    private long aging;
    
    // 走货路径虚拟编码（冗余，给接送货使用）
    private String freightRouteVirtualCode;
    
    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 21, 2013 1:15:29 PM
     * @return
     * @see
     */
    public String getFreightRouteVirtualCode() {
        return freightRouteVirtualCode;
    }



    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 21, 2013 1:15:34 PM
     * @param freightRouteVirtualCode
     * @see
     */
    public void setFreightRouteVirtualCode(String freightRouteVirtualCode) {
        this.freightRouteVirtualCode = freightRouteVirtualCode;
    }



    /**
     * @return  the aging
     */
    public long getAging() {
        return aging;
    }


    
    /**
     * @param aging the aging to set
     */
    public void setAging(long aging) {
        this.aging = aging;
    }


    /**
     * @return  the sourceCode
     */
    public String getSourceCode() {
        return sourceCode;
    }

    
    /**
     * @param sourceCode the sourceCode to set
     */
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    
    /**
     * @return  the targetCode
     */
    public String getTargetCode() {
        return targetCode;
    }

    
    /**
     * @param targetCode the targetCode to set
     */
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
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
     * @return  the leaveDate
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    
    /**
     * @param leaveDate the leaveDate to set
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    
    /**
     * @return  the arriveDate
     */
    public Date getArriveDate() {
        return arriveDate;
    }

    
    /**
     * @param arriveDate the arriveDate to set
     */
    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }


    
    public Date getPassbyDate() {
        return passbyDate;
    }


    
    public void setPassbyDate(Date passbyDate) {
        this.passbyDate = passbyDate;
    }


    
    /**
     * @return  the transType
     */
    public String getTransType() {
        return transType;
    }


    
    /**
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }


    
    /**
     * @return  the lineType
     */
    public String getLineType() {
        return lineType;
    }


    
    /**
     * @param lineType the lineType to set
     */
    public void setLineType(String lineType) {
        this.lineType = lineType;
    }


    
    /**
     * @return  the lineSort
     */
    public String getLineSort() {
        return lineSort;
    }


    
    /**
     * @param lineSort the lineSort to set
     */
    public void setLineSort(String lineSort) {
        this.lineSort = lineSort;
    }



    
    /**
     * @return  the sourceName
     */
    public String getSourceName() {
        return sourceName;
    }



    
    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }



    
    /**
     * @return  the targetName
     */
    public String getTargetName() {
        return targetName;
    }



    
    /**
     * @param targetName the targetName to set
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }



    
    /**
     * @return  the lineName
     */
    public String getLineName() {
        return lineName;
    }



    
    /**
     * @param lineName the lineName to set
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }


 
    
}
